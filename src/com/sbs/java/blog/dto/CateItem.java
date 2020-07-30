package com.sbs.java.blog.dto;

import java.util.Map;

public class CateItem extends Dto {
	private String name;

	public CateItem(Map<String, Object> row) {
		super(row);

		this.name = (String) row.get("name");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CateItem [name=" + name + ", getId()=" + getId() + ", getRegDate()=" + getRegDate()
				+ ", getUpdateDate()=" + getUpdateDate() + "]";
	}

}
