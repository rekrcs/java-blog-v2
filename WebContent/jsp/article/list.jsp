<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

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
		<h3>${cateItemName}(${totalCount})</h3>
	</div>

	<c:forEach items="${articles}" var="article">
		<div class="article">
			<div class="article-header">
				<div class="article-info">
					<span class="artice-date"><i>${article.regDate.substring(0, 10)}</i></span>
					<span class="article-writer"><i>by
							${article.extra.writer}</i></span>
				</div>
				<a
					href="${pageContext.request.contextPath}/s/article/detail?id=${article.id}"
					class="article-title">${article.title}</a>
			</div>
			<div class="article-body">${article.body}</div>
		</div>
	</c:forEach>

	<div class="con page-box">
		<ul class="flex flex-jc-c">
			<c:forEach var="i" begin="1" end="${totalPage}" step="1">
				<li class="${i == cPage ? 'current' : ''}"><a
					href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${i}"
					class="block">${i}</a></li>
			</c:forEach>
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