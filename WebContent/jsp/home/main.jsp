<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page import="com.sbs.java.blog.dto.CateItem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
//	List<Article> articles = (List<Article>) request.getAttribute("articles");
	List<Article> articleHits = (List<Article>) request.getAttribute("articleHits");
%>

<!--body 내용-->
<section class="body-main">
	<div class="body-main-name">
		<h3>인기 게시물 (TOP 5)</h3>
	</div>
	<%
		for (Article article : articleHits) {
	%>
	<div class="article">
		<div class="article-header">
			<div class="article-info">
				<span class="artice-date"><i><%=article.getRegDate().substring(0, 10)%></i></span>
				<span class="article-writer"><i>by <%=article.getExtra().get("writer")%></i></span>
			</div>
			<a href="../article/detail?id=<%=article.getId()%>"
				class="article-title"><%=article.getTitle()%></a>
		</div>
		<div class="article-body"><%=article.getBody()%></div>
	</div>
	<%
		}
	%>
</section>
<%@ include file="/jsp/part/foot.jspf"%>