package org.mix3.tagging.model;

import java.io.Serializable;

import org.mix3.tagging.entity.Tag;

@SuppressWarnings("serial")
public class TagModel implements Serializable{
	private Integer id;
	private String name;
	public TagModel(Tag tag){
		this.id = tag.getID();
		this.name = tag.getName();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return doConvert(name);
	}
	public void setName(String name) {
		this.name = name;
	}
	
    public String doConvert(String input){
		StringBuffer sb = new StringBuffer();
		if( input == null ) return "";
			for(int i=0;i<input.length();i++){
			if( input.charAt(i) == '<'){
				sb.append( "&lt;" );
			}else if(input.charAt(i) == '>'){
				sb.append( "&gt;" );
			}else if( input.charAt(i) == '\\'){
				sb.append( "&quot;" );
			}else if( input.charAt(i) == '&'){
				sb.append( "&amp;" );
			}else {
				sb.append( input.charAt(i));
			}
		}
		return sb.toString();
	}
}
