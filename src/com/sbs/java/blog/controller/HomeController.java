package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;

public class HomeController extends Controller {
	public HomeController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "main":
			return doActionMain();
		case "aboutMe":
			return doActionAboutMe();
		}

		return "";
	}

	private String doActionAboutMe() {
		return "home/aboutMe.jsp";
	}

	private String doActionMain() {
		//최신 게시물 순서
		int showArticlesInMainPage = 5;
		List<Article> articles = articleService.getForPrintListArticles(showArticlesInMainPage);
		req.setAttribute("articles", articles);
		
		//조회수 높은 순서
		List<Article> articleHits = articleService.getForPrintListInOrderHit(showArticlesInMainPage);
		req.setAttribute("articleHits", articleHits);
		return "home/main.jsp";
	}

	@Override
	public String getControllerName() {
		return "home";
	}

}
