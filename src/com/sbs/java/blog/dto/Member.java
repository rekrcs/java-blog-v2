package com.sbs.java.blog.dto;

import java.util.Map;

public class Member extends Dto {
	private String loginId;
	private String loginPw;
	private String loginPwConfirm;
	private String name;
	private String email;
	private String nickname;
	private int mailAuthStatus;
	private int delStatus;

	public Member(Map<String, Object> row) {
		super(row);
		this.loginId = (String) row.get("loginId");
		this.loginPw = (String) row.get("loginPw");
		this.loginPwConfirm = (String) row.get("loginPwConfirm");
		this.name = (String) row.get("name");
		this.email = (String) row.get("email");
		this.nickname = (String) row.get("nickname");
		this.mailAuthStatus = (int) row.get("mailAuthStatus");
		this.delStatus = (int) row.get("delStatus");
	}

	public int getMailAuthStatus() {
		return mailAuthStatus;
	}

	public void setMailAuthStatus(int mailAuthStatus) {
		this.mailAuthStatus = mailAuthStatus;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLoginPwConfirm() {
		return loginPwConfirm;
	}

	public void setLoginPwConfirm(String loginPwConfirm) {
		this.loginPwConfirm = loginPwConfirm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "Member [loginId=" + loginId + ", loginPw=" + loginPw + ", loginPwConfirm=" + loginPwConfirm + ", name="
				+ name + ", email=" + email + ", nickname=" + nickname + ", mailAuthStatus=" + mailAuthStatus
				+ ", delStatus=" + delStatus + ", getId()=" + getId() + ", getRegDate()=" + getRegDate()
				+ ", getExtra()=" + getExtra() + "]";
	}

}
