<%@ page import="com.sbs.java.blog.dto.ArticleReply"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	ArticleReply articleReply = (ArticleReply) request.getAttribute("articleReply");
%>

<style>

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
</style>


<!--body 내용-->
<section class="body-main flex-grow-1">
	<div class="body-main-name">
		<div class="title">댓글 수정</div>
	</div>


	<!-- 댓글 출력 -->

	<div class="reply-container">
		<div class="reply-box">
			<div class="reply-header" style="margin-bottom: 10px;">
				<span style="margin-right: 20px">홍길동</span><span><%=articleReply.getRegDate().substring(0, 10)%></span>
			</div>
			<div class="reply-body" style="font-size: 1.2rem"><%=articleReply.getBody()%></div>
		</div>
	</div>
	
	
	<!-- 댓글 입력폼 -->
	<div class="write-form-box con">
		<form action="doModifyReply?id=<%=articleReply.getId()%>&articleId=<%=articleReply.getArticleId()%>" method="post" class="write-form form1">

			<div class="form-row">
				<div class="input">
					<textarea name="body" placeholder="수정할 내용을 입력해주세요."></textarea>
				</div>
			</div>
			<div class="form-row">
				<div class="label"></div>
				<div class="input flex flex-jc-e">
					<button type="submit">댓글수정</button>
				</div>
			</div>
		</form>
	</div>

</section>
</div>

<div class="backHome">
	<a href="detail?id=<%=articleReply.getArticleId()%>">게시물로 돌아가기</a>
</div>
<!--footer 파트-->
<footer class="article-footer">
	<div class="footer-container flex flex-ai-c flex-jc-c">
		<div>⊙ Blog built by Youn</div>
	</div>
</footer>
</body>
</html>