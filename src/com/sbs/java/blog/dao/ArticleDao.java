package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class ArticleDao extends Dao {
	private Connection dbConn;

	public ArticleDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public List<Article> getForPrintListArticles(int page, int itemsInAPage, int cateItemId, String searchKeywordType,
			String searchKeyword) {
		SecSql sql = new SecSql();
		int limitFrom = (page - 1) * itemsInAPage;

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE displayStatus = 1");
		if (cateItemId != 0) {
			sql.append("AND cateItemId = ?", cateItemId);
		}
		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			sql.append("AND title LIKE CONCAT('%', ?, '%')", searchKeyword);
		}
		sql.append("ORDER BY id DESC ");
		sql.append("LIMIT ?, ? ", limitFrom, itemsInAPage);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public List<Article> getForPrintListArticles(int fiveLatestArticle) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE displayStatus = 1");
		sql.append("ORDER BY id DESC ");
		sql.append("LIMIT ?", fiveLatestArticle);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public int getForPrintListArticlesCount(int cateItemId, String searchKeywordType, String searchKeyword) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) AS cnt ");
		sql.append("FROM article ");
		sql.append("WHERE displayStatus = 1 ");

		if (cateItemId != 0) {
			sql.append("AND cateItemId = ? ", cateItemId);
		}

		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			sql.append("AND title LIKE CONCAT('%', ?, '%')", searchKeyword);
		}

		int count = DBUtil.selectRowIntValue(dbConn, sql);
		return count;
	}

	public Article getForPrintArticle(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT *, 'Youn' AS extra__writer ");
		sql.append("FROM article ");
		sql.append("WHERE 1 ");
		sql.append("AND id = ? ", id);
		sql.append("AND displayStatus = 1 ");

		return new Article(DBUtil.selectRow(dbConn, sql));
	}

	public List<CateItem> getForPrintCateItems() {
		SecSql sql = new SecSql();

		sql.append("SELECT * ");
		sql.append("FROM cateItem ");
		sql.append("WHERE 1 ");
		sql.append("ORDER BY id ASC ");

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<CateItem> cateItems = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			cateItems.add(new CateItem(row));
		}

		return cateItems;
	}

	public CateItem getCateItem(int cateItemId) {
		SecSql sql = new SecSql();

		sql.append("SELECT * ");
		sql.append("FROM cateItem ");
		sql.append("WHERE 1 ");
		sql.append("AND id = ? ", cateItemId);

		return new CateItem(DBUtil.selectRow(dbConn, sql));
	}

	public int write(int cateItemId, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ? ", title);
		sql.append(", body = ? ", body);
		sql.append(", displayStatus = '1'");
		sql.append(", cateItemId = ?", cateItemId);

		/*
		 * String sql = "";
		 * 
		 * sql += String.format("INSERT INTO article "); sql +=
		 * String.format("SET regDate = NOW() "); sql +=
		 * String.format(", updateDate = NOW1() "); sql +=
		 * String.format(", title = '%s' ", title); sql +=
		 * String.format(", body = '%s' ", body); sql +=
		 * String.format(", displayStatus = '1' "); sql +=
		 * String.format(", cateItemId = '%d' ", cateItemId);
		 */

		return DBUtil.insert(dbConn, sql);
	}

	public int increaseHit(int id) {
		SecSql sql = SecSql.from("UPDATE article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, sql);
	}

	public int modify(int id, int cateItemId, String title, String body) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE article");
		secSql.append("SET updateDate = NOW()");
		secSql.append(", title = ?", title);
		secSql.append(", body = ?", body);
		secSql.append(", displayStatus = '1'");
		secSql.append(", cateItemId = ?", cateItemId);
		secSql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, secSql);
	}

	public int delete(int id) {
		SecSql secSql = new SecSql();

		secSql.append("DELETE FROM article");
		secSql.append("WHERE id = ?", id);

		return DBUtil.delete(dbConn, secSql);
	}
}