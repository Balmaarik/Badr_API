package com.Almaarik.badr_api;

import android.graphics.Bitmap;

public class Place {
	private String _address;
	private String _inconUrl;
	private String _id;
	private String _name;
	private Bitmap _bitmap;
	
	public void setId(String id){
		this._id = id;
	}
	
	public void setBitmap(Bitmap bitmap){
		this._bitmap = bitmap;
	}
	
	public void setAddress(String address){
		this._address = address;
	}
	
	public void setInconUrl(String inconUrl){
		this._inconUrl = inconUrl;
	}
	
	public void setName(String name){
		this._name = name;
	}
	
	public String getName(){
		return _name;
	}
	
	public String getId(){
		return _id;
	}
	
	public String getIinconUrl(){
		return _inconUrl;
	}
	
	public String getAaddress(){
		return _address;
	}
	
	public Bitmap getBitmap(){
		return _bitmap;
	}

}
