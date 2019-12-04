package com.jaydenxiao.common.ui.view.address;

import java.io.Serializable;
import java.util.List;

/**
 * @author LOVE
 * 
 *
 */
@SuppressWarnings("serial")
public class Cityinfo implements Serializable {
	private String AreaId="";
	private String Name="";
	private String ShortName="";
	private String Lng="";
	private String Lat="";
	private int Sort=0;
	private String Position="";
	private String AreaCode="";
	private List<Cityinfo> children;

	public String getAreaId() {
		return AreaId;
	}

	public void setAreaId(String areaId) {
		AreaId = areaId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getShortName() {
		return ShortName;
	}

	public void setShortName(String shortName) {
		ShortName = shortName;
	}

	public String getLng() {
		return Lng;
	}

	public void setLng(String lng) {
		Lng = lng;
	}

	public String getLat() {
		return Lat;
	}

	public void setLat(String lat) {
		Lat = lat;
	}

	public int getSort() {
		return Sort;
	}

	public void setSort(int sort) {
		Sort = sort;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public String getAreaCode() {
		return AreaCode;
	}

	public void setAreaCode(String areaCode) {
		AreaCode = areaCode;
	}

	public List<Cityinfo> getChildren() {
		return children;
	}

	public void setChildren(List<Cityinfo> children) {
		this.children = children;
	}
}
