package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.service.ArticleService;
import com.sbs.java.blog.service.MemberService;

public abstract class Controller {
	protected Connection dbConn;
	protected String actionMethodName;
	protected HttpServletRequest req;
	protected HttpServletResponse resp;

	protected ArticleService articleService;
	protected MemberService memberService;

	public Controller(Connection dbConn, String actionMethodName, HttpServletRequest req, HttpServletResponse resp) {
		this.dbConn = dbConn;
		this.actionMethodName = actionMethodName;
		this.req = req;
		this.resp = resp;
		articleService = new ArticleService(dbConn);
		memberService = new MemberService(dbConn);
	}

	public void beforeAction() {
		// 액션 전 실행
		// 이 메서드는 모든 컨트롤러의 모든 액션이 실행되기 전에 실행된다.
		List<CateItem> cateItems = articleService.getForPrintCateItems();
		List<Article> articlesForFooter = articleService.getForPrintListArticles(5);
		
		req.setAttribute("cateItems", cateItems);
		req.setAttribute("articlesForFooter", articlesForFooter);
	}

	public void afterAction() {
		// 액션 후 실행
	}

	public abstract String doAction();

	public String executeAction() {
		beforeAction();
		String rs = doAction();
		afterAction();

		return rs;
	}
}
