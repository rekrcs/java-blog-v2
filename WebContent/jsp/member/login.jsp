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
.find-inf>a {
	color:blue;
}

.find-inf>a:hover {
	color:red;
}
</style>

<!-- 비번 암호화저장 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<!-- 회원가입 중 유효하지 않은 input 방지  -->
<script>
	function submitLoginForm(form) {
		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			alert('로그인 아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}

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
	<div class="login-form-box con">
	<h2 style="text-align: center">로그인</h2>
		<form action="doLogin" method="post" class="login-form form1"
			onsubmit="submitLoginForm(this); return false;">
			<input type="hidden" name="redirectUri" value="${param.afterLoginRedirectUri}" />
			<input type="hidden" name="loginPwReal" />
			<div class="form-row">
				<div class="label">로그인 아이디</div>
				<div class="input">
					<input name="loginId" type="text" placeholder="로그인 아이디를 입력해주세요." />
				</div>
			</div>
			<div class="form-row">
				<div class="label">로그인 비번</div>
				<div class="input">
					<input name="loginPw" type="password" placeholder="로그인 비번을 입력해주세요." />
				</div>
			</div>
			<div class="form-row">
				<div class="label">전송</div>
				<div class="input">
					<input type="submit" value="로그인" />
				</div>
			</div>
		</form>
		
		<div class="flex flex-jc-e">
		<div class="find-inf"><a href="findId">[아이디찾기]</a></div>
		<span style="padding:0 5px"></span>
		<div class="find-inf"><a href="findPassword">[비번찾기]</a></div>
		</div>
	</div>
	<div class="backHome">
		<a href="../home/main">홈으로 돌아가기</a>
	</div>
</section>
<%@ include file="/jsp/part/foot.jspf"%>