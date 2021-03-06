package org.mix3.tagging.service;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;

import net.java.ao.DBParam;
import net.java.ao.EntityManager;
import net.java.ao.Query;
import net.java.ao.Transaction;
import net.java.ao.db.C3P0PoolProvider;

import org.mix3.tagging.entity.Article;
import org.mix3.tagging.entity.ArticleToTag;
import org.mix3.tagging.entity.Setting;
import org.mix3.tagging.entity.Tag;
import org.mix3.tagging.model.ArticleModel;
import org.mix3.tagging.model.SettingModel;
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
		
		em = new EntityManager(new C3P0PoolProvider(new H2DatabaseProvider(uri, username, password)));
//		Logger.getLogger("net.java.ao").setLevel(Level.FINE);
		
		em.migrate(Article.class, Tag.class, ArticleToTag.class, Setting.class);
		if(em.count(Setting.class) == 0){
			Setting settings = em.create(Setting.class, new DBParam[]{
				new DBParam("url", "http://192.168.24.97:8080/"),
				new DBParam("userid", "admin"),
				new DBParam("password", Utils.digest("password"))
			});
			settings.save();
		}
	}
	
	/*
	private Properties getDBProperties() {
		Properties back = new Properties();
		InputStream is = WicketApplication.class.getResourceAsStream("/db.properties");
		if (is == null) {
			throw new RuntimeException("Unable to locate db.properties");
		}
		try {
			back.load(is);
			is.close();
		} catch (IOException e) {
			throw new RuntimeException("Unable to load db.properties");
		}
		return back;
	}
	*/

	public List<TagModel> createArticle(final String title, final String excerpt, final String url, final String tag) throws SQLException {
		new Transaction<Object>(em){
			@Override
			protected Object run() throws SQLException {
				//Tagの登録 
				List<Tag> tagList = new ArrayList<Tag>();
				String[] tags = tag.replaceAll("　", " ").split(" ");
				for(String t : tags){
					if(t.equals("")){
						continue;
					}
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
				
				// Articleの登録
				Article[] findArticles = em.find(Article.class, Query.select().where("url like ?", url));
				Article updateArticle;
				if(findArticles.length > 0){
					// 既に登録されている場合は、関連付けられているタグとの関連を抹殺
					em.delete(em.find(ArticleToTag.class, Query.select().where("articleID=?", findArticles[0].getID())));
					
					// アップデート
					findArticles[0].setTitle(title);
					findArticles[0].setExcerpt(excerpt);
					findArticles[0].setUrl(url);
					findArticles[0].setDate(new Timestamp(System.currentTimeMillis()));
					findArticles[0].save();
					updateArticle = findArticles[0];
				}else{
					// 新規作成
					updateArticle = em.create(Article.class, new DBParam[]{
						new DBParam("title", title),
						new DBParam("excerpt", excerpt),
						new DBParam("url", url),
						new DBParam("date", new Timestamp(System.currentTimeMillis()))
					});
					updateArticle.save();
				}
				
				// Article と Tagの間に関連を作る
				for(Tag related : tagList){
					ArticleToTag att = em.create(ArticleToTag.class, new DBParam[]{
						new DBParam("articleID", updateArticle.getID()),
						new DBParam("tagID", related.getID())
					});
					att.save();
				}
				
				System.out.println("Article");
				System.out.println("\ttitle : "+updateArticle.getTitle());
				System.out.println("\texcerpt : "+updateArticle.getExcerpt());
				System.out.println("\tURL : "+updateArticle.getUrl());
				System.out.println("\tDate : "+updateArticle.getDate());
				System.out.println("Tag");
				for(Tag related : updateArticle.getTags()){
					System.out.println("\tname : "+related.getName());
				}
				return null;
			}
		}.execute();
		return findArticleToTag(url);
	}

	public List<TagModel> findArticleToTag(String url) throws SQLException {
		Article[] post = em.find(Article.class, Query.select().where("url like ?", url));
		List<TagModel> tagList = new ArrayList<TagModel>();
		if(post.length > 0){
			for(Tag tag : post[0].getTags()){
				tagList.add(new TagModel(tag));
			}
		}else{
			throw new RuntimeErrorException(new Error("RuntimeErrorException Error"), "RuntimeErrorException Error");
		}
		return tagList;
	}
	public List<ArticleModel> searchArticle(String url) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public List<ArticleModel> findTagToArticle(int id) throws SQLException {
		Article[] article = em.find(Article.class, Query.select("article.id").join(ArticleToTag.class, "article.id = articleToTag.articleID").where("articleToTag.tagID=?", id));
//		ArticleToTag[] articletotag = em.find(ArticleToTag.class, Query.select().where("tagID=?", id));
		List<ArticleModel> articleList = new ArrayList<ArticleModel>();
		for(Article a : article){
			ArticleModel articleModel = new ArticleModel();
			articleModel.setUrl(a.getUrl());
			articleList.add(articleModel);
		}
		return articleList;
	}

	public SettingModel getSetting() throws SQLException {
		return new SettingModel(em.get(Setting.class, 1));
	}

	public void setSetting(SettingModel settingModel) throws SQLException {
		Setting setting = em.get(Setting.class, 1);
		setting.setUrl(settingModel.getUrl());
		setting.setUserId(settingModel.getUserId());
		setting.setPassword(settingModel.getPassword());
		setting.save();
	}
	
	public void testList() throws SQLException{
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(
		          new OutputStreamWriter(
		                new FileOutputStream("C:\\tagList.txt"),"UTF-8"));
			Tag[] tagList = em.find(Tag.class);
			for(Tag tag : tagList){
				Integer num = tag.getArticles().length;
				if(num > 10){
					bw.write(tag.getName()+"("+num+")\n");
				}
			}
			bw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
