<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>
<script>
	var submitModifyReplyFormDone = false;

	function submitModifyReplyForm(form) {
		if (submitModifyReplyFormDone) {
			alert('처리중입니다.');
			return;
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
		submitModifyReplyFormDone = true;
	}
</script>

<style>

/* cus */
.modify-reply-form-box {
	margin-top: 30px;
}

/*댓글 출력*/
.reply-container>.reply-box {
	/* 	margin: 10px 10px 0 10px; */
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
	<div class="body-main-name">댓글 수정</div>


	<!-- 댓글 출력 -->

	<div class="reply-container">
		<div class="reply-box">
			<div class="reply-header" style="margin-bottom: 10px;">
				<span style="margin-right: 20px">${articleReply.extra.memberName}</span><span>${articleReply.regDate.substring(0, 10)}</span>
			</div>
		</div>
	</div>


	<!-- 댓글 입력폼 -->
	<div class="modify-reply-form-box con">
		<form action="doModifyReply" method="POST"
			class="modify-reply-form form1"
			onsubmit="submitModifyReplyForm(this); return false;">
			<input type="hidden" name="id" value="${articleReply.id}"> <input
				type="hidden" name="articleId" value="${articleReply.articleId}"><input
				type="hidden" name="body">
			<div class="form-row">
				<div class="input">
					<script type="text/x-template">${articleReply.bodyForXTemplate}</script>
					<div data-toast-editor-height="300" class="toast-editor"></div>
				</div>
			</div>
			<div class="form-row">
				<div class="input flex">
					<input type="submit" value="댓글 수정" /> <input class="cancel"
						type="button" value="취소" />
				</div>
			</div>
		</form>
	</div>

</section>
</div>

<div class="backHome">
	<a href="detail?id=${articleReply.articleId}">게시물로 돌아가기</a>
</div>
<!--footer 파트-->
<footer class="article-footer">
	<div class="footer-container flex flex-ai-c flex-jc-c">
		<div>⊙ Blog built by Youn</div>
	</div>
</footer>
</body>
</html>