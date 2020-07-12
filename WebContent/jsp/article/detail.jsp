<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page import="com.sbs.java.blog.dto.CateItem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	Article article = (Article) request.getAttribute("article");
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
.title {
	font-size: 3rem;
}
/* 수정 삭제 버튼 시작 */
.option-box {
	display: flex;
	justify-content: flex-end;
	color: black;
	margin-top: 30px;
	font-size: 1.2rem;
	font-weight: bold;
}

.option-box>span:nth-child(2) {
	margin: 0 3px;
}

.option-box>span>a:hover {
	color: red;
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
	padding-left:10px;
}

th {
	background-color: #368bcc;
	color:white;
}
</style>

<!--카테고리 이름 받기-->
<%
	String cateName = null;
	for (CateItem cateItem : cateItems) {
		if(cateItem.getId() == article.getCateItemId()) {
			cateName = cateItem.getName();
			break;
		}
	}
%>

<!--body 내용-->
<div class="body-container flex con-small">
	<section class="body-main flex-grow-1">
		<div class="body-main-name">
			<div class="title"><%=article.getTitle()%></div>
		</div>
		<table border="1" width="100%">
			<tr>
				<th>번호</th>
				<td><%=article.getId()%></td>
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
		</table>
		<div>
			<script type="text/x-template" id="origin1" style="display: none;"><%=article.getBody()%></script>
			<div id="viewer1"></div>
			<script>
				var editor1__initialValue = $('#origin1').html().trim();
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
				href="${pageContext.request.contextPath}/s/article/modify?id=${param.id}">수정</a></span><span></span><span
				class="option-delete"><a href="delete?id=${param.id}">삭제</a></span>
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