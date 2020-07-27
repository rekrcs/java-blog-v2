<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<script>
	/* 여기서 부터 */
	var onBeforeUnloadSetted = false;
	var onBeforeUnload = function(e) {
		return '떠나시겠습니까?'; // 요새 브라우저는 이 메시지가 아닌 자체의 메세지가 나옵니다.
	};

	function applyOnBeforeUnload() {
		if (onBeforeUnloadSetted)
			return;
		$(window).bind('beforeunload', onBeforeUnload); // 떠날 때 실행되는 함수를 등록
		onBeforeUnloadSetted = true;
	}

	function removeOnBeforeUnload() {
		$(window).unbind('beforeunload', onBeforeUnload); // 떠날 때 실행되는 함수를 해제
		onBeforeUnloadSetted = false;
	}
	/* 여기까지는 라이브러리 */
	
	applyOnBeforeUnload();
	var submitWriteFormDone = false;

	function submitWriteForm(form) {
		if (submitWriteFormDone) {
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

		removeOnBeforeUnload();
		form.submit();
		submitWriteFormDone = true;
	}

// 	function WriteForm__init() {
// 		// 폼의 특정 요소를 건드리(?)면, 그 이후 부터 외부로 이동하는 것에 참견하는 녀석을 작동시킨다.
// 		$('form.write-form input, form.write-form textarea').keyup(function() {
// 			applyOnBeforeUnload();
// 		});
// 	}

// 	WriteForm__init();
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
<section class="body-main flex-grow-1">
	<div class="write-form-box con">
		<form action="doWrite" method="post"
			onsubmit="submitWriteForm(this); return false;"
			class="write-form form1">
			<input type="hidden" name="body">
			<div class="form-row">
				<div class="label">카테고리 선택</div>
				<div class="input">
					<select name="cateItemId">

						<c:forEach items="${cateItems}" var="cateItem">
							<option value="${cateItem.id}">${cateItem.name}</option>
						</c:forEach>

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
					<script type="text/x-template"></script>
					<div class="toast-editor"></div>
				</div>
			</div>
			<div class="form-row">
				<div class="label">전송</div>
				<div class="input">
					<input type="submit" value="완료" />
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