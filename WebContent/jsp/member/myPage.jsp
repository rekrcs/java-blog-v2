<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	List<Article> articles = (List<Article>) request.getAttribute("articles");
%>
<style>
.userbox {
	font-size: 1.2rem;
	text-align: center;
}

.userWrite {
	margin-top: 20px;
}

.userModify {
	margin-top: 50px;
}

.userModify>a {
	color: blue;
}

.userModify>a:hover {
	color: red;
}

.userDelete {
	margin: 30px 0;
}

.userDelete>a {
	color: blue;
}
.userDelete>a:hover {
	color: red;
}
/*홈으로 돌아가기*/
.backHome {
	text-align: center;
	border-top: 1px solid rgba(0, 0, 0, .2);
	border-bottom: 1px solid rgba(0, 0, 0, .2);
	padding: 20px 0;
	margin-top: 50px;
	width: 70%;
	margin-right: auto;
	margin-left: auto;
	font-weight: bold;
}

.backHome>a:hover {
	color: #008d62;
}
</style>

<!--body 내용-->
<section class="body-main">
	<div class="myPage-box con">
		<h2 style="text-align: center">My Page</h2>
		<div class="userbox">
			<div class="userInf">
				<div class="userName">
					이름 :
					<%=loginedMember.getName()%></div>
				<div class="userNickname">
					닉네임 :
					<%=loginedMember.getNickname()%></div>
				<div class="email">
					이메일 :
					<%=loginedMember.getEmail()%></div>
			</div>


			<div class="userWrite">
				<div class="userArticle">내가쓴 글 : ${totalCount}</div>
				<div class="userArticle">내가쓴 글 리스트</div>
				<table border="1" align="center">
					<tr align="center" bgcolor="skybule">
						<th>카테고리</th>
						<th>제목</th>
					</tr>
					<%
						for (Article article : articles) {
					%>
					<tr>
						<td><%=article.getExtra().get("cateItemName")%></td>
						<td><a
							href="${pageContext.request.contextPath}/s/article/detail?id=<%=article.getId()%>"><%=article.getTitle()%></a></td>
					</tr>
					<%
						}
					%>
				</table>
				<div class="userModify">
					<a href="doubleCheckPassword">[개인정보 수정]</a>
				</div>

				<div class="userDelete">
					<a onclick="if ( confirm('탈퇴하시겠습니까?') == false ) return false;" href="userDelete">[회원탈퇴]</a>
				</div>
			</div>
		</div>

	</div>
	<div class="backHome">
		<a href="../home/main">홈으로 돌아가기</a>
	</div>
</section>
<%@ include file="/jsp/part/foot.jspf"%>