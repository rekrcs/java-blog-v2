<%@ page import="java.util.List"%>
<%@ page import="com.sbs.java.blog.dto.Article"%>
<%@ page import="com.sbs.java.blog.dto.CateItem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%
	List<Article> articles = (List<Article>) request.getAttribute("articles");
%>

<style>
.cateItems-container {
	flex-wrap: wrap;
}

.body-main table tr td  a:hover {
	color: #008d62;
}
</style>
<!--body 내용-->
<section class="body-main">
	<table border="1" style="margin: 5px; width: 100%">
		<tr bgcolor="#368bcc" color="white">
			<p>
			<td colspan="3" span style="color: white">최근 게시물</td>
			</p>
		</tr>
		<tr align="center">
			<th>제목
			</td>
			<th>작성자
			</td>
			<th>날짜
			</td>
		</tr>
		<%
			for (Article article : articles) {
		%>
		<tr align="center">
			<td><a href="../article/detail?id=<%=article.getId()%>"><%=article.getTitle()%></a></td>
			<td><%=article.getExtra().get("writer")%></td>
			<td><%=article.getRegDate().substring(0, 10)%></td>
		</tr>
		<%
			}
		%>
	</table>
</section>
<%@ include file="/jsp/part/foot.jspf"%>