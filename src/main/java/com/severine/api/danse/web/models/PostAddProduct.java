package com.severine.api.danse.web.models;

import java.util.List;

public class PostAddProduct {

	Long id;
	String name;
	String comment;
	String picture;
	Integer totalQuantity;
	PostAddName kind;
	PostAddName type;
	PostAddName category;
	PostAddName box;
	PostAddColour colour;
	List<PostAddSizeQuantity> sizeQuantities;
	List<PostPicture> pictures;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public List<PostAddSizeQuantity> getSizeQuantities() {
		return sizeQuantities;
	}
	public void setSizeQuantities(List<PostAddSizeQuantity> sizeQuantities) {
		this.sizeQuantities = sizeQuantities;
	}
	public PostAddName getKind() {
		return kind;
	}
	public void setKind(PostAddName kind) {
		this.kind = kind;
	}
	public PostAddName getType() {
		return type;
	}
	public void setType(PostAddName type) {
		this.type = type;
	}
	public PostAddName getCategory() {
		return category;
	}
	public void setCategory(PostAddName category) {
		this.category = category;
	}
	public PostAddName getBox() {
		return box;
	}
	public void setBox(PostAddName box) {
		this.box = box;
	}
	public PostAddColour getColour() {
		return colour;
	}
	public void setColour(PostAddColour colour) {
		this.colour = colour;
	}
	public List<PostPicture> getPictures() {
		return pictures;
	}
	public void setPictures(List<PostPicture> pictures) {
		this.pictures = pictures;
	}
	
	
	
}
