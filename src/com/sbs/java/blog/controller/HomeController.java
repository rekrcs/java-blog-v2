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
		int fiveLatestArticle = 5;
		List<Article> articles = articleService.getForPrintListArticles(fiveLatestArticle);
		req.setAttribute("articles", articles);
		return "home/main.jsp";
	}

}
