<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<script>
	var submitModifyFormDone = false;

	function submitModifyForm(form) {
		if (submitModifyFormDone) {
			alert('처리중입니다.');
			return;
		}

		form.title.value = form.title.value.trim();

		if (form.title.value.length == 0) {
			alert('제목을 입력해주세요.');
			form.title.focus();

			return false;
		}

		var editor = $(form).find('.toast-editor').data('data-toast-editor');

		var body = editor.getMarkdown();
		body = body.trim();

		if (body.length == 0) {
			alert('내용을 입력해주세요.');
			editor.focus();

			return false;
		}

		form.body.value = body;

		form.submit();
		submitModifyFormDone = true;
	}
</script>

<style>
/* cus */
.write-form-box {
	margin-top: 30px;
}
</style>

<!--body 내용-->
<section class="body-main flex-grow-1">
	div class="modify-reply-form-box con">
	<form action="doModifyReply" method="POST"
		class="modify-reply-form form1"
		onsubmit="submitModifyReplyForm(this); return false;">
		<input type="hidden" name="redirectUri" value="${param.redirectUri}">
		<input type="hidden" name="id" value="${articleReply.id}"> <input
			type="hidden" name="body">
		<div class="form-row">
			<div class="label">게시물 번호</div>
			<div class="input">${article.id}</div>
		</div>
		<div class="form-row">
			<div class="label">게시물 제목</div>
			<div class="input">${article.title}</div>
		</div>
		<div class="form-row">
			<div class="label">번호</div>
			<div class="input">${articleReply.id}</div>
		</div>
		<div class="form-row">
			<div class="label">날짜</div>
			<div class="input">${articleReply.regDate}</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
				<script type="text/x-template">${articleReply.bodyForXTemplate}</script>
				<div class="toast-editor"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label">수정</div>
			<div class="input">
				<input type="submit" value="수정" /> <a href="list">취소</a>
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