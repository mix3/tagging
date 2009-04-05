package org.mix3.tagging.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.java.ao.DBParam;
import net.java.ao.EntityManager;
import net.java.ao.Query;
import net.java.ao.Transaction;

import org.mix3.tagging.entity.Post;
import org.mix3.tagging.entity.PostToTag;
import org.mix3.tagging.entity.Setting;
import org.mix3.tagging.entity.Tag;
import org.mix3.tagging.entity.User;
import org.mix3.tagging.entity.UserToPost;
import org.mix3.tagging.model.TagModel;
import org.mix3.tagging.utils.H2DatabaseProvider;
import org.mix3.tagging.utils.Utils;

import com.google.inject.Singleton;

@Singleton
public class ServiceImpl implements Service{
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public ServiceImpl() throws SQLException, NoSuchAlgorithmException{
		Properties dbProperties = Utils.getDBProperties();
		String uri = dbProperties.getProperty("db.uri");
		String username = dbProperties.getProperty("db.username");
		String password = dbProperties.getProperty("db.password");
		
		em = new EntityManager(new H2DatabaseProvider(uri, username, password));
		Logger.getLogger("net.java.ao").setLevel(Level.FINE);
		
		em.migrate(User.class, Post.class, UserToPost.class, Tag.class, PostToTag.class, Setting.class);
		if(em.count(Setting.class) == 0){
			em.create(Setting.class, new DBParam[]{
				new DBParam("url", "http://localhost:8080/")
			}).save();
			em.create(User.class, new DBParam[]{
				new DBParam("userid", "admin"),
				new DBParam("password", "password"),
				new DBParam("token", Utils.getRandom())
			}).save();
		}
		User admin = em.get(User.class, 1);
		System.out.println("UID  :"+admin.getUserId());
		System.out.println("PASS :"+admin.getPassword());
		System.out.println("TOKEN:"+admin.getToken());
	}

	public boolean signIn(String userId, String password) throws SQLException {
//		if(false){
//			throw new SQLException("debug");
//		}
		User[] user = em.find(User.class);
		for(User u : user){
			if(u.getUserId().equals(userId) && u.getPassword().equals(password)){
				return true;
			}
		}
		return false;
	}

	public String getAdmin() {
		return em.get(User.class, 1).getUserId();
	}
	
	public List<TagModel> createPost(String token, final String title, final String excerpt, final String url, final String tag) throws SQLException {
		if(true){
			token = em.get(User.class, 1).getToken();
		}
		final User[] user = em.find(User.class, Query.select().where("token like ?", token));
		if(user.length == 0){
			throw new SQLException();
		}
		new Transaction<Object>(em){
			@Override
			protected Object run() throws SQLException {
				//Tagの登録 
				List<Tag> tagList = new ArrayList<Tag>();
				String[] tags = tag.replaceAll("[　|\n|\r|\t]+", " ").split(" ");
				
				if(tags.length > 0 && !tags[0].equals("")){
					for(String t : tags){
						System.out.println("tag -> "+t);
						Tag[] findTags = em.find(Tag.class, Query.select().where("name like ?", t));
						if(findTags.length > 0){
							tagList.add(findTags[0]);
						}else{
							Tag createTag = em.create(Tag.class, new DBParam[]{
								new DBParam("name", t)
							});
							createTag.save();
							tagList.add(createTag);
						}
					}
				}
				
				// Postの登録
				Post[] findPosts = em.find(Post.class, Query.select().where("url like ?", url));
				Post updatePost;
				if(findPosts.length > 0){
					// 既に登録されている場合は、関連付けられているタグとの関連を抹殺
					em.delete(em.find(PostToTag.class, Query.select().where("postID=?", findPosts[0].getID())));
					
					// アップデート
					findPosts[0].setTitle(title);
					findPosts[0].setExcerpt(excerpt);
					findPosts[0].setUrl(url);
					findPosts[0].setDate(new Timestamp(System.currentTimeMillis()));
					findPosts[0].save();
					updatePost = findPosts[0];
				}else{
					// 新規作成
					updatePost = em.create(Post.class, new DBParam[]{
						new DBParam("title", title),
						new DBParam("excerpt", excerpt),
						new DBParam("url", url),
						new DBParam("date", new Timestamp(System.currentTimeMillis()))
					});
					updatePost.save();
					
					// User と Post の関連を作る
					em.create(UserToPost.class, new DBParam[]{
						new DBParam("userId", user[0].getID()),
						new DBParam("postId", updatePost.getID())
					}).save();
				}
				
				// Post と Tagの間に関連を作る
				for(Tag related : tagList){
					PostToTag att = em.create(PostToTag.class, new DBParam[]{
						new DBParam("postID", updatePost.getID()),
						new DBParam("tagID", related.getID())
					});
					att.save();
				}
				
				System.out.println("Post");
				System.out.println("\ttitle : "+updatePost.getTitle());
				System.out.println("\texcerpt : "+updatePost.getExcerpt());
				System.out.println("\tURL : "+updatePost.getUrl());
				System.out.println("\tDate : "+updatePost.getDate());
				System.out.println("Tag");
				for(Tag related : updatePost.getTags()){
					System.out.println("\tname : "+related.getName());
				}
				return null;
			}
		}.execute();
		return findPostToTag(url);
	}
	public List<TagModel> findPostToTag(String url) throws SQLException {
		Post[] post = em.find(Post.class, Query.select().where("url like ?", url));
		List<TagModel> tagList = new ArrayList<TagModel>();
		if(post.length > 0){
			for(Tag tag : post[0].getTags()){
				tagList.add(new TagModel(tag));
			}
		}
		return tagList;
	}
	
	/*
	public List<ArticleModel> searchArticle(String url) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public List<ArticleModel> findTagToArticle(int id) throws SQLException {
		ArticleToTag[] articletotag = em.find(ArticleToTag.class, Query.select().where("tagID=?", id));
		List<ArticleModel> articleList = new ArrayList<ArticleModel>();
		for(ArticleToTag a : articletotag){
			articleList.add(new ArticleModel(a.getArticle()));
		}
		return articleList;
	}

	public SettingModel getSetting() throws SQLException {
		return new SettingModel(em.get(Setting.class, 1));
	}

	public Map<String, String> signIn(String userId, String password) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	*/
}
