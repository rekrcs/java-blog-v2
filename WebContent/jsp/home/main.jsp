<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

<!--body 내용-->
<section class="body-main">
	<div class="body-main-name">인기게시물 (TOP 5)</div>

	<c:forEach items="${articleHits}" var="article">
		<div class="article">
			<div class="article-header">
				<div class="article-info">
					<span class="artice-date"><i>${article.regDate.substring(0, 10)}</i></span>
					<span class="article-writer"><i>by
							${article.extra.writer}</i></span>
				</div>
				<a href="../article/detail?id=${article.id}" class="article-title">${article.title}</a>
			</div>
			<div class="article-body">${article.body}</div>
		</div>
	</c:forEach>
</section>
<%@ include file="/jsp/part/foot.jspf"%>