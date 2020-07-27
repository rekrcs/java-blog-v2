package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.List;

import com.sbs.java.blog.dao.ArticleDao;
import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.CateItem;

public class ArticleService extends Service {

	private ArticleDao articleDao;

	public ArticleService(Connection dbConn) {
		articleDao = new ArticleDao(dbConn);
	}

	public List<Article> getForPrintListArticles(int showArticlesInMainPage) {
		return articleDao.getForPrintListArticles(showArticlesInMainPage);
	}

	public List<Article> getForPrintListArticles(int page, int itemsInAPage, int cateItemId) {
		return articleDao.getForPrintListArticles(page, itemsInAPage, cateItemId);
	}

	public List<Article> getForPrintListArticles(int page, int itemsInAPage, int cateItemId, String searchKeywordType,
			String searchKeyword) {
		return articleDao.getForPrintListArticles(page, itemsInAPage, cateItemId, searchKeywordType, searchKeyword);
	}

	public int getForPrintListArticlesCount(int cateItemId, String searchKeywordType, String searchKeyword) {
		return articleDao.getForPrintListArticlesCount(cateItemId, searchKeywordType, searchKeyword);
	}

	public Article getForPrintArticle(int id) {
		return articleDao.getForPrintArticle(id);
	}

	public List<CateItem> getForPrintCateItems() {
		return articleDao.getForPrintCateItems();
	}

	public CateItem getCateItem(int cateItemId) {
		return articleDao.getCateItem(cateItemId);
	}

	public int write(int cateItemId, String title, String body, int memberId) {
		return articleDao.write(cateItemId, title, body, memberId);
	}

	public void increaseHit(int id) {
		articleDao.increaseHit(id);
	}

	public int modify(int id, int cateItemId, String title, String body) {
		return articleDao.modify(id, cateItemId, title, body);
	}

	public int delete(int id) {
		return articleDao.delete(id);
	}

	public int deleteReply(int id) {
		return articleDao.deleteReply(id);
	}

	public int replyWrite(String body, int articleId, int memberId) {
		return articleDao.replyWrite(body, articleId, memberId);
	}

	public List<ArticleReply> getArticleReplyPage(int id, int page, int itemsInAPage) {
		return articleDao.getArticleReplyPage(id, page, itemsInAPage);

	}

	public ArticleReply getArticleReplyForPrint(int id) {

		return articleDao.getArticleReplyForPrint(id);
	}

	public int modifyReply(int id, String body) {
		return articleDao.modifyReply(id, body);
	}

	public int getForPrintListReplyCount(int id) {
		return articleDao.getForPrintListReplyCount(id);
	}

	public int getArticleReplyCount(int id) {
		return articleDao.getForPrintListReplyCount(id);
	}

	public List<Article> getMemberForNickNames() {
		return articleDao.getmemberForNickNames();
	}

	public int getTotalCateItems() {
		return articleDao.getTotalCateItems();
	}

	public int getTotalCateItems(int id) {
		return articleDao.getTotalCateItems(id);
	}

	public List<Article> getForPrintListInOrderHit(int showArticlesInMainPage) {
		return articleDao.getForPrintListInOrderHit(showArticlesInMainPage);
	}

	public List<Article> getForPrintArticlesByMemberId(int id) {
		return articleDao.getForPrintArticlesByMemberId(id);
	}

	public int getForPrintListArticlesCountByMemberId(int id) {
		return articleDao.getForPrintListArticlesCountByMemberId(id);
	}

	public CateItem getForPrintCateItemNameByArticleId(int id) {
		return articleDao.getForPrintCateItemNameByArticleId(id);
	}
}
