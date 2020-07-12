<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

<!-- 글쓰는 중 유효하지 않은 input 방지  -->
<script>
	var writeFormSubmitted = false;

	function submitWriteForm(form) {
		if (writeFormSubmitted) {
			alert('처리 중입니다.');
			return;
		}

		form.title.value = form.title.value.trim();
		if (form.title.value.length == 0) {
			alert('제목을 입력해주세요.');
			form.title.focus();

			return;
		}

		form.body.value = form.body.value.trim();
		if (form.body.value.length == 0) {
			alert('내용을 입력해주세요.');
			form.body.focus();

			return;
		}

		form.submit();
		writeFormSubmitted = true;
	}
</script>

<style>
/* lib */
.form1 {
	display: block;
	width: 100%;
}

.form1 .form-row {
	align-items: center;
	display: flex;
}
</style>
<%="<style>.form1 .form-row:not(:first-child) { margin-top : 10px; }</style>"%>
<style>
.form1 .form-row>.label {
	width: 100px;
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

.form1 .form-row>.input>select {
	padding: 10px;
}

.form1 .form-row>.input>textarea {
	height: 500px;
}

@media ( max-width : 700px ) {
	.form1 .form-row {
		display: block;
	}
}

/* cus */
.write-form-box {
	margin-top: 30px;
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
</style>

<!--body 내용-->
<div class="body-container flex con-small">
	<section class="body-main flex-grow-1">
		<div class="write-form-box con">
			<form action="doModify?id=${param.id}" method="POST" class="write-form form1">
				<div class="form-row">
					<div class="label">카테고리 선택</div>
					<div class="input">
						<select name="cateItemId">
							<%
								for (CateItem cateItem : cateItems) {
							%>
							<option value="<%=cateItem.getId()%>"><%=cateItem.getName()%></option>
							<%
								}
							%>

						</select>
					</div>
				</div>
				<div class="form-row">
					<div class="label">제목</div>
					<div class="input">
						<input name="title" type="text" placeholder="제목을 입력해주세요." />
					</div>
				</div>
				<div class="form-row">
					<div class="label">내용</div>
					<div class="input">
						<textarea name="body" placeholder="내용을 입력해주세요."></textarea>
					</div>
				</div>
				<div class="form-row">
					<div class="label">전송</div>
					<div class="input">
						<input type="submit" value="전송" /> <a href="list">취소</a>
					</div>
				</div>
			</form>
		</div>
	</section>
</div>

<div class="backHome">
	<a href="../home/main">홈으로 돌아가기</a>
</div>
<!--footer 파트-->
<footer class="article-footer">
	<div class="footer-container flex flex-ai-c flex-jc-c">
		<div>⊙ Blog built by Youn</div>
	</div>
</footer>
</body>
</html>