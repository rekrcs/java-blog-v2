package com.sbs.java.blog.dto;

import java.util.Map;

public class ArticleReply extends Dto {
	private String body;
	private int articleId;
	private int memberId;

	public ArticleReply(Map<String, Object> row) {
		super(row);
		this.body = (String) row.get("body");
		this.articleId = (int) row.get("articleId");
		this.memberId = (int) row.get("memberId");
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getBodyForXTemplate() {
		return body.replaceAll("(?i)script", "<!--REPLACE:script-->");
	}

	@Override
	public String toString() {
		return "ArticleReply [body=" + body + ", articleId=" + articleId + ", memberId=" + memberId + ", getId()="
				+ getId() + ", getRegDate()=" + getRegDate() + ", getUpdateDate()=" + getUpdateDate() + "]";
	}



}
