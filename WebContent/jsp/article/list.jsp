<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	List<Article> articles = (List<Article>) request.getAttribute("articles");
	int totalPage = (int) request.getAttribute("totalPage");
	int paramPage = (int) request.getAttribute("page");
	String cateItemName = (String) request.getAttribute("cateItemName");
%>

<style>
.page-box {
	margin-bottom: 15px;
}

.page-box>ul>li>a {
	padding: 0 10px;
	text-decoration: underline;
	color: #787878;
}

.page-box>ul>li:hover>a {
	color: black;
	
}

.page-box>ul>li.current>a {
	color: red;
}
</style>

<!--body 내용-->
<section class="body-main">
	<div class="body-main-name">
		<h3><%=cateItemName%>
			(${totalCount})
		</h3>
	</div>
	<%
		for (Article article : articles) {
	%>
	<div class="article">
		<div class="article-header">
			<div class="article-info">
				<span class="artice-date"><i><%=article.getRegDate().substring(0, 10)%></i></span>
				<span class="article-writer"><i>by <%=article.getExtra().get("writer")%></i></span>
			</div>
			<a
				href="${pageContext.request.contextPath}/s/article/detail?id=<%=article.getId()%>"
				class="article-title"><%=article.getTitle()%></a>
		</div>
		<div class="article-body"><%=article.getBody()%></div>
	</div>
	<%
		}
	%>
	<div class="con page-box">
		<ul class="flex flex-jc-c">
			<%
				for (int i = 1; i <= totalPage; i++) {
			%>
			<li class="<%=i == paramPage ? "current" : ""%>"><a
				href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=<%=i%>"
				class="block"><%=i%></a></li>
			<%
				}
			%>
		</ul>
	</div>

	<div class="con search-box flex flex-jc-c">

		<form action="${pageContext.request.contextPath}/s/article/list">
			<input type="hidden" name="page" value="1" /> <input type="hidden"
				name="cateItemId" value="${param.cateItemId}" /> <input
				type="hidden" name="searchKeywordType" value="title" /> <input
				type="text" name="searchKeyword" value="${param.searchKeyword}" />
			<button type="submit">검색</button>
		</form>

	</div>
</section>
<%@ include file="/jsp/part/foot.jspf"%>