<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

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

.form1 .form-row>.input>input {
	display: block;
	width: 100%;
	box-sizing: border-box;
	padding: 10px;
}

.member-join-box {
	margin-top: 30px;
}

@media ( max-width : 700px ) {
	.form1 .form-row {
		display: block;
	}
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

<!-- 비번 암호화저장 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<!-- 회원가입 중 유효하지 않은 input 방지  -->
<script>
	function submitDoubleCheckForm(form) {
		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length == 0) {
			alert('로그인 비번을 입력해주세요.');
			form.loginPw.focus();
			return;
		}

		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';

		form.submit();
	}
</script>

<!--body 내용-->
<section class="body-main">
	<div class="doubleCheckPassword-form-box con">
		<h2 style="text-align: center">비번확인</h2>
		<form action="doDoubleCheckPassword" method="POST"
			class="doubleCheckPassword-form form1"
			onsubmit="submitDoubleCheckForm(this); return false;">
			<input type="hidden" name="loginPwReal" />
			<div class="form-row">
				<div class="label">로그인 비번</div>
				<div class="input">
					<input name="loginPw" type="password" placeholder="비번을 입력해주세요." />
				</div>
			</div>
			<div class="form-row">
				<div class="label">전송</div>
				<div class="input">
					<input type="submit" value="비번확인" />
				</div>
			</div>
		</form>
	</div>
	<div class="backHome">
		<a href="../home/main">홈으로 돌아가기</a>
	</div>
</section>
<%@ include file="/jsp/part/foot.jspf"%>