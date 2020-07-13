package com.sbs.java.blog.dto;

import java.util.Map;

public class Member extends Dto {
	private String updateDate;
	private String loginId;
	private String loginPw;
	private String loginPwConfirm;
	private String name;
	private String email;
	private String nickName;

	public Member(Map<String, Object> row) {
		super(row);
		this.loginId = (String) row.get("loginId");
		this.updateDate = (String) row.get("updateDate");
		this.loginPw = (String) row.get("loginPw");
		this.loginPwConfirm = (String) row.get("loginPwConfirm");
		this.name = (String) row.get("name");
		this.email = (String) row.get("email");
		this.nickName = (String) row.get("nickName");
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLoginPwConfirm() {
		return loginPwConfirm;
	}

	public void setLoginPwConfirm(String loginPwConfirm) {
		this.loginPwConfirm = loginPwConfirm;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Member [updateDate=" + updateDate + ", loginId=" + loginId + ", loginPw=" + loginPw
				+ ", loginPwConfirm=" + loginPwConfirm + ", name=" + name + ", email=" + email + ", nickName="
				+ nickName + ", getId()=" + getId() + ", getRegDate()=" + getRegDate() + "]";
	}

}
