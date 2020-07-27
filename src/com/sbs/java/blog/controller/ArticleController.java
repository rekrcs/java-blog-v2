package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.Util;

public class ArticleController extends Controller {
	public ArticleController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	public void beforeAction() {
		super.beforeAction();
		// 이 메서드는 게시물 컨트롤러의 모든 액션이 실행되기 전에 실행된다.
		// 필요없다면 지워도 된다.
	}

	public String doAction() {
		switch (actionMethodName) {
		case "list":
			return doActionList();
		case "detail":
			return doActionDetail();
		case "doWrite":
			return doActionDoWrite();
		case "write":
			return doActionWrite();
		case "modify":
			return doActionModify();
		case "doModify":
			return doActionDoModify();
		case "doDelete":
			return doActionDoDelete();
		case "doReply":
			return doActionReply();
		case "doDeleteReply":
			return doActionDodeleteReply();
		case "modifyReply":
			return doActionModifyReply();
		case "doModifyReply":
			return doActionDoModifyReply();
		}

		return "";
	}

	private String doActionDoModifyReply() {
		int id = Integer.parseInt(req.getParameter("id"));
		int articleId = Integer.parseInt(req.getParameter("articleId"));
		String body = req.getParameter("body");

		int modifyId = articleService.modifyReply(id, body);

		return "html:<script> alert('댓글이 수정 되었습니다.'); location.replace('./detail?id=" + articleId + "'); </script>";

	}

	private String doActionModifyReply() {
//		HttpSession session = req.getSession();
		int id = Util.getInt(req, "id");
		int articleId = Util.getInt(req, "articleId");

		if (session.getAttribute("loginedMemberId") == null) {
			return "html:<script> alert('본인만 수정 가능 합니다. 로그인 후 이용하세요'); location.replace('detail?id=" + articleId
					+ "'); </script>";
		}

		int memberId = Util.getInt(req, "memberId");
		int loginedMemberId = (int) session.getAttribute("loginedMemberId");
		if (loginedMemberId != memberId) {
			return "html:<script> alert('본인만 수정 가능 합니다'); location.replace('detail?id=" + articleId + "'); </script>";
		}

		ArticleReply articleReply = articleService.getArticleReplyForPrint(id);
		req.setAttribute("articleReply", articleReply);

		return "article/modifyReply.jsp";
	}

	private String doActionDodeleteReply() {
//		HttpSession session = req.getSession();
		int id = Util.getInt(req, "id");
		int articleId = Util.getInt(req, "articleId");

		if (session.getAttribute("loginedMemberId") == null) {
			return "html:<script> alert('본인만 삭제 가능 합니다. 로그인 후 이용하세요'); location.replace('detail?id=" + articleId
					+ "'); </script>";
		}

		int memberId = Util.getInt(req, "memberId");
		int loginedMemberId = (int) session.getAttribute("loginedMemberId");

		if (loginedMemberId != memberId) {
			return "html:<script> alert('본인만 삭제 가능 합니다'); location.replace('detail?id=" + articleId + "'); </script>";
		}

		int deleteReplyId = articleService.deleteReply(id);

		return "html:<script> alert('댓글이 삭제되었습니다.'); location.replace('./detail?id=" + articleId + "'); </script>";
	}

	private String doActionReply() {
//		HttpSession session = req.getSession();
		int articleId = Util.getInt(req, "id");
		if (session.getAttribute("loginedMemberId") == null) {
			return "html:<script> alert('로그인 후 댓글 쓰기가 가능 합니다.'); location.replace('detail?id=" + articleId
					+ "'); </script>";
		}
		int memberId = (int) session.getAttribute("loginedMemberId");
		String body = req.getParameter("body");
		int id = articleService.replyWrite(body, articleId, memberId);

		return "html:<script>location.replace('./detail?id=" + articleId + "');</script>";
	}

	private String doActionDoDelete() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		Map<String, Object> getCheckRsDeleteAvailableRs = articleService.getCheckRsDeleteAvailable(id, loginedMemberId);

		if (Util.isSuccess(getCheckRsDeleteAvailableRs) == false) {
			return "html:<script> alert('" + getCheckRsDeleteAvailableRs.get("msg") + "'); history.back(); </script>";
		}

		articleService.deleteArticle(id);

		return "html:<script> alert('" + id + "번 게시물이 삭제되었습니다.'); location.replace('list'); </script>";
	}

	private String doActionDoModify() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		Map<String, Object> getCheckRsModifyAvailableRs = articleService.getCheckRsModifyAvailable(id, loginedMemberId);

		if (Util.isSuccess(getCheckRsModifyAvailableRs) == false) {
			return "html:<script> alert('" + getCheckRsModifyAvailableRs.get("msg") + "'); history.back(); </script>";
		}

		int cateItemId = Util.getInt(req, "cateItemId");
		String title = Util.getString(req, "title");
		String body = Util.getString(req, "body");

		articleService.modifyArticle(id, cateItemId, title, body);

		return "html:<script> alert('" + id + "번 게시물이 수정되었습니다.'); location.replace('detail?id=" + id + "'); </script>";
	}

	private String doActionModify() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		Article article = articleService.getForPrintArticle(id, loginedMemberId);

		req.setAttribute("article", article);

		return "article/modify.jsp";
	}

	private String doActionWrite() {
		if (session.getAttribute("loginedMemberId") == null) {
			return "html:<script> alert('로그인 후 글 작성 가능 합니다.'); location.replace('../member/login'); </script>";
		}
		return "article/write.jsp";
	}

	private String doActionDoWrite() {
		String title = req.getParameter("title");
		String body = req.getParameter("body");
		int cateItemId = Util.getInt(req, "cateItemId");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		int id = articleService.write(cateItemId, title, body, loginedMemberId);

		return "html:<script> alert('" + id + "번 게시물이 생성되었습니다.'); location.replace('list'); </script>";
	}

	private String doActionDetail() {
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		articleService.increaseHit(id);

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		Article article = articleService.getForPrintArticle(id, loginedMemberId);

		req.setAttribute("article", article);

		CateItem cateItem = articleService.getForPrintCateItemNameByArticleId(id);
		req.setAttribute("cateItem", cateItem);

		int totalCountForReply = articleService.getForPrintListReplyCount(id);
		req.setAttribute("totalCountForReply", totalCountForReply);

		int page = 1;

		if (!Util.empty(req, "page") && Util.isNum(req, "page")) {
			page = Util.getInt(req, "page");
		}

		// 댓글 리스팅 페이징
		int itemsInAPage = 3;
		int totalCount = articleService.getForPrintListReplyCount(id);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);

		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("page", page);

		List<ArticleReply> articleReplies = articleService.getArticleReplyPage(id, page, itemsInAPage);
		req.setAttribute("articleReplies", articleReplies);
		return "article/detail.jsp";

	}

	private String doActionList() {
		int page = 1;

		if (!Util.empty(req, "page") && Util.isNum(req, "page")) {
			page = Util.getInt(req, "page");
		}

		int cateItemId = 0;

		if (!Util.empty(req, "cateItemId") && Util.isNum(req, "cateItemId")) {
			cateItemId = Util.getInt(req, "cateItemId");
		}

		String cateItemName = "전체";

		if (cateItemId != 0) {
			CateItem cateItem = articleService.getCateItem(cateItemId);
			cateItemName = cateItem.getName();
		}
		req.setAttribute("cateItemName", cateItemName);

		String searchKeywordType = "";

		if (!Util.empty(req, "searchKeywordType")) {
			searchKeywordType = Util.getString(req, "searchKeywordType");
		}

		String searchKeyword = "";

		if (!Util.empty(req, "searchKeyword")) {
			searchKeyword = Util.getString(req, "searchKeyword");
		}

		int itemsInAPage = 5;
		int totalCount = articleService.getForPrintListArticlesCount(cateItemId, searchKeywordType, searchKeyword);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);

		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("cPage", page);

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		List<Article> articles = articleService.getForPrintListArticles(loginedMemberId, page, itemsInAPage, cateItemId,
				searchKeywordType, searchKeyword);

		req.setAttribute("articles", articles);

		return "article/list.jsp";
	}

	@Override
	public String getControllerName() {
		return "article";
	}
}
