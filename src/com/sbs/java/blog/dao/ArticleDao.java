package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class ArticleDao extends Dao {
	private Connection dbConn;

	public ArticleDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public List<Article> getForPrintListArticles(int page, int itemsInAPage, int cateItemId) {
		SecSql sql = new SecSql();
		int limitFrom = (page - 1) * itemsInAPage;

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE displayStatus = 1");
		if (cateItemId != 0) {
			sql.append("AND cateItemId = ?", cateItemId);
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

	public List<Article> getForPrintListArticles(int page, int itemsInAPage, int cateItemId, String searchKeywordType,
			String searchKeyword) {
		SecSql sql = new SecSql();
		int limitFrom = (page - 1) * itemsInAPage;

		sql.append("SELECT A.*, M.nickname AS extra__writer");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE A.displayStatus = 1");
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

	public List<Article> getForPrintListArticles(int showArticlesInMainPage) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT A.*, M.nickname AS extra__writer");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE displayStatus = 1");
		sql.append("ORDER BY id DESC ");
		sql.append("LIMIT ?", showArticlesInMainPage);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public List<Article> getForPrintListInOrderHit(int showArticlesInMainPage) {
		SecSql sql = new SecSql();

		sql.append("SELECT A.*, M.nickname AS extra__writer");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE displayStatus = 1");
		sql.append("ORDER BY hit DESC ");
		sql.append("LIMIT ?", showArticlesInMainPage);

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

		sql.append("SELECT A.*, M.nickname AS extra__writer");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE 1 ");
		sql.append("AND A.id = ? ", id);
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

	public int write(int cateItemId, String title, String body, int memberId) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ? ", title);
		sql.append(", body = ? ", body);
		sql.append(", memberId = ? ", memberId);
		sql.append(", displayStatus = '1'");
		sql.append(", cateItemId = ?", cateItemId);

		return DBUtil.insert(dbConn, sql);
	}

	public int increaseHit(int id) {
		SecSql sql = SecSql.from("UPDATE article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, sql);
	}

	public int modifyArticle(int id, int cateItemId, String title, String body) {
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

	public int deleteArticle(int id) {
		SecSql secSql = new SecSql();

		secSql.append("DELETE FROM article");
		secSql.append("WHERE id = ?", id);

		return DBUtil.delete(dbConn, secSql);
	}

	public int deleteReply(int id) {
		SecSql secSql = new SecSql();

		secSql.append("DELETE FROM articleReply");
		secSql.append("WHERE id = ?", id);

		return DBUtil.delete(dbConn, secSql);
	}

	public int writeArticleReply(int articleId, int memberId, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO articleReply");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", body = ? ", body);
		sql.append(", articleId = ? ", articleId);
		sql.append(", displayStatus = '1'");
		sql.append(", memberId = ?", memberId);

		return DBUtil.insert(dbConn, sql);
	}

	public List<ArticleReply> getArticleReplyPage(int id, int page, int itemsInAPage) {
		SecSql sql = new SecSql();

		int limitFrom = (page - 1) * itemsInAPage;

		sql.append("SELECT A.*, M.nickname AS extra__writer");
		sql.append("FROM articleReply AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE 1 ");
		sql.append("AND articleId = ?", id);
		sql.append("ORDER BY id DESC ");
		sql.append("LIMIT ?, ? ", limitFrom, itemsInAPage);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<ArticleReply> articleReplies = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articleReplies.add(new ArticleReply(row));
		}

		return articleReplies;

	}

	public ArticleReply getArticleReplyForPrint(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT A.*, M.nickname AS extra__memberName");
		sql.append("FROM articleReply AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE 1 ");
		sql.append("AND A.id = ? ", id);

		Map<String, Object> row = DBUtil.selectRow(dbConn, sql);

		ArticleReply articleReply = new ArticleReply(row);

		return articleReply;
	}

	public int modifyReply(int id, String body) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE articleReply");
		secSql.append("SET updateDate = NOW()");
		secSql.append(", body = ?", body);
		secSql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, secSql);
	}

	public int getForPrintListReplyCount(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) AS cnt ");
		sql.append("FROM articleReply ");
		sql.append("WHERE articleId = ? ", id);

		int count = DBUtil.selectRowIntValue(dbConn, sql);
		return count;
	}

	public List<Article> getmemberForNickNames() {

		SecSql secSql = new SecSql();

		secSql.append("SELECT A.*, M.nickname AS extra__writer");
		secSql.append("FROM article AS A");
		secSql.append("INNER JOIN `member` AS M");
		secSql.append("ON A.memberId = M.id");
		secSql.append("WHERE A.displayStatus = 1");
		secSql.append("ORDER BY A.id DESC");

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);

		List<Article> articleFormemberNickNames = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articleFormemberNickNames.add(new Article(row));
		}

		return articleFormemberNickNames;
	}

	public int getTotalCateItems() {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append("FROM cateItem");
		int count = DBUtil.selectRowIntValue(dbConn, sql);
		return count;
	}

	public int getTotalCateItems(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*), id");
		sql.append("FROM article");
		sql.append("WHERE cateItemId = ?", id);
		int count = DBUtil.selectRowIntValue(dbConn, sql);
		return count;
	}

	public List<Article> getForPrintArticlesByMemberId(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT A.*, C.name AS extra__cateItemName");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN cateItem AS C");
		sql.append("ON A.cateItemId = C.id");
		sql.append("WHERE displayStatus = 1");
		sql.append("AND memberId = ?", id);
		sql.append("ORDER BY id DESC ");
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, sql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	public int getForPrintListArticlesCountByMemberId(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*)");
		sql.append("FROM article ");
		sql.append("WHERE displayStatus = 1 ");
		sql.append("AND memberId = ?", id);

		int count = DBUtil.selectRowIntValue(dbConn, sql);
		return count;
	}

	public CateItem getForPrintCateItemNameByArticleId(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT A.*, C.name AS extra__cateItemName");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `cateItem` AS C");
		sql.append("ON A.cateItemId = C.id");
		sql.append("WHERE 1 ");
		sql.append("AND A.id = ? ", id);
		sql.append("AND displayStatus = 1 ");

		return new CateItem(DBUtil.selectRow(dbConn, sql));
	}
}
