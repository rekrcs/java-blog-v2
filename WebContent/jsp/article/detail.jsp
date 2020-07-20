<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page import="com.sbs.java.blog.dto.CateItem"%>
<%@ page import="com.sbs.java.blog.dto.ArticleReply"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	Article article = (Article) request.getAttribute("article");
	List<ArticleReply> articleReplies = (List<ArticleReply>) request.getAttribute("articleReplies");
	int totalPage = (int) request.getAttribute("totalPage");
	int paramPage = (int) request.getAttribute("page");
%>
<!-- 하이라이트 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/highlight.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/styles/default.min.css">

<!-- 하이라이트 라이브러리, 언어 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/css.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/javascript.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/xml.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/php-template.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.1.1/languages/sql.min.js"></script>

<!-- 코드 미러 라이브러리 추가, 토스트 UI 에디터에서 사용됨 -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.min.css" />

<!-- 토스트 UI 에디터, 자바스크립트 코어 -->
<script
	src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.js"></script>

<!-- 토스트 UI 에디터, 신택스 하이라이트 플러그인 추가 -->
<script
	src="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight-all.min.js"></script>

<!-- 토스트 UI 에디터, CSS 코어 -->
<link rel="stylesheet"
	href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />


<style>
.body-main-name {
	margin-top: 30px;
}
.title {
	font-size: 3rem;
}
/* 수정 삭제 버튼 시작 */
.option-box {
	display: flex;
	justify-content: flex-end;
	color: black;
	margin: 50px 0 100px;
	font-size: 1.2rem;
	font-weight: bold;
}

.option-box>span:nth-child(2) {
	margin: 0 3px;
}

.option-box>span>a:hover {
	color: red;
}

/*댓글*/
/* lib */
.form1 {
	display: block;
	width: 100%;
}

.form1 .form-row {
	align-items: center;
	display: flex;
}

.form1 .form-row>.input {
	flex-grow: 1;
}

.form1 .form-row>.input>input, .form1 .form-row>.input>textarea {
	display: block;
	width: 100%;
	box-sizing: border-box;
	padding: 10px;
}

.form1 .form-row>.input>textarea {
	height: 70px;
}

@media ( max-width : 700px ) {
	.form1 .form-row {
		display: block;
	}
}

@media ( max-width : 700px ) {
	.form1 .form-row>.input>.reply-btn {
		width: 100%;
	}
}

@media ( min-width : 701px ) {
	.form1 .form-row>.input>.reply-btn {
		width: 10%;
	}
}
/* cus */
.write-form-box {
	margin: 20px 0 30px;
}

/*홈으로 돌아가기*/
.backHome {
	text-align: center;
	border-top: 1px solid rgba(0, 0, 0, .2);
	border-bottom: 1px solid rgba(0, 0, 0, .2);
	padding: 20px 0;
	margin-top: 50px;
	width: 50%;
	margin-right: auto;
	margin-left: auto;
	font-weight: bold;
}

.backHome>a:hover {
	color: #008d62;
}

/*테이블 정렬*/
td {
	padding-left: 10px;
}

th {
	background-color: #368bcc;
	color: white;
}

/*댓글 출력*/
.reply-container>.reply-box {
	margin: 10px 10px 0 10px;
	border: 1px solid rgba(0, 0, 0, .2);
	word-break: break-all;
}

.reply-container>.reply-option-box>div a {
	color: blue;
}

.reply-container>.reply-option-box>div a:hover {
	color: red;
}

/* 댓글 박스 */
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

<!--카테고리 이름 받기-->
<%
	String cateName = null;
	for (CateItem cateItem : cateItems) {
		if (cateItem.getId() == article.getCateItemId()) {
			cateName = cateItem.getName();
			break;
		}
	}
%>

<!--body 내용-->
<section class="body-main flex-grow-1">
	<table border="1" width="100%">
		<tr>
			<th>번호</th>
			<td><%=article.getId()%></td>
		</tr>
		<tr>
			<th>글쓴이</th>
			<td><%=article.getExtra().get("writer")%></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td><%=article.getHit()%></td>
		</tr>
		<tr>
			<th>작성일</th>
			<td><%=article.getRegDate()%></td>
		</tr>
		<tr>
			<th>수정일</th>
			<td><%=article.getUpdateDate()%></td>
		</tr>
		<tr>
			<th>카테고리</th>
			<td><%=cateName%></td>
		</tr>
		<tr>
			<th>댓글수</th>
			<td>${totalCountForReply}</td>
		</tr>
	</table>
	<div class="body-main-name">
		<div class="title"><%=article.getTitle()%></div>
	</div>
	<div>
		<script type="text/x-template" id="origin1" style="display: none;"><%=article.getBodyForXTemplate()%></script>
		<div id="viewer1"></div>
		<script>
			var editor1__initialValue = getBodyFromXTemplate('#origin1');
			var editor1 = new toastui.Editor({
				el : document.querySelector('#viewer1'),
				initialValue : editor1__initialValue,
				viewer : true,
				plugins : [ toastui.Editor.plugin.codeSyntaxHighlight,
						youtubePlugin, replPlugin, codepenPlugin ]
			});
		</script>
	</div>

	<!-- 수정 삭제버튼-->
	<div class="option-box">
		<span class="option-modify"><a
			href="${pageContext.request.contextPath}/s/article/modify?id=${param.id}&memberId=<%=article.getMemberId()%>">수정</a></span><span></span><span
			class="option-delete"><a
			href="doDelete?id=${param.id}&memberId=<%=article.getMemberId()%>">삭제</a></span>
	</div>

	<div style="margin-top: 50px">댓글 : ${totalCountForReply}</div>

	<!-- 댓글 입력폼 -->
	<div class="write-form-box con">
		<form action="doReply?id=<%=article.getId()%>" method="post"
			class="write-form form1">
			<!-- 				<form name="kk" onsubmit="kkSubmit(); reurn false;" class="write-form form1">		 -->
			<div class="form-row">
				<div class="input">
					<textarea name="body" placeholder="댓글을 입력해주세요."></textarea>
				</div>
			</div>
			<div class="form-row">
				<div class="label"></div>
				<div class="input flex flex-jc-e">
					<!-- 						<input type="submit" value="댓글쓰기" class="reply-btn"/> -->
					<button type="submit">댓글쓰기</button>
				</div>
			</div>
		</form>
	</div>

	<!-- 댓글 출력 -->
	<%
		for (ArticleReply articleReply : articleReplies) {
	%>
	<div class="reply-container">
		<div class="reply-box">
			<div class="reply-header" style="margin-bottom: 10px;">
				<span style="margin-right: 20px"><%=articleReply.getExtra().get("writer")%></span><span><%=articleReply.getRegDate()%></span>
			</div>
			<div class="reply-body" style="font-size: 1.2rem"><%=articleReply.getBody()%></div>
		</div>
		<div class="reply-option-box flex flex-jc-e"
			style="margin: 0 10px 20px 0">
			<div class="reply-modify">
				<a
					href="modifyReply?id=<%=articleReply.getId()%>&articleId=<%=articleReply.getArticleId()%>&memberId=<%=articleReply.getMemberId()%>">수정</a>
			</div>
			<div class="reply-delete" style="margin-left: 10px">
				<a
					href="doDeleteReply?id=<%=articleReply.getId()%>&articleId=<%=articleReply.getArticleId()%>&memberId=<%=articleReply.getMemberId()%>">삭제</a>
			</div>
		</div>
	</div>
	<%
		}
	%>

	<!-- 	댓글 페이징 -->
	<div class="con page-box">
		<ul class="flex flex-jc-c">
			<%
				for (int i = 1; i <= totalPage; i++) {
			%>
			<li class="<%=i == paramPage ? "current" : ""%>"><a
				href="?id=<%=article.getId()%>&page=<%=i%>" class="block"><%=i%></a></li>
			<%
				}
			%>
		</ul>
	</div>
</section>
</div>

<div class="backHome">
	<a href="list">리스트로 돌아가기</a>
</div>
<!--footer 파트-->
<footer class="article-footer">
	<div class="footer-container flex flex-ai-c flex-jc-c">
		<div>⊙ Blog built by Youn</div>
	</div>
</footer>
</body>
</html>