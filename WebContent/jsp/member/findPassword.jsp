<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

<!-- 비번 암호화저장 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<!-- 회원가입 중 유효하지 않은 input 방지  -->
<script>
	function submitJoinForm(form) {
		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			alert('로그인 아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}

		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

		if (form.loginPwConfirm.value.length == 0) {
			alert('로그인 비번확인을 입력해주세요.');
			form.loginPwConfirm.focus();
			return;
		}

		form.name.value = form.name.value.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}

		form.loginPw.value = '';

		form.submit();
	}
</script>

<!--body 내용-->
<section class="body-main">
	<div class="find-password-form-box con">
	<h2 style="text-align: center">비번 찾기</h2>
		<form action="doFindPassword" method="POST" class="find-form form1"
			onsubmit="submitJoinForm(this); return false;">
			<div class="form-row">
				<div class="label">로그인 아이디</div>
				<div class="input">
					<input name="loginId" type="text" placeholder="로그인 아이디를 입력해주세요." />
				</div>
			</div>
			<div class="form-row">
				<div class="label">이름</div>
				<div class="input">
					<input name="name" type="text" placeholder="이름을 입력해주세요." />
				</div>
			</div>
			<div class="form-row">
				<div class="label">이메일</div>
				<div class="input">
					<input name="email" type="email" placeholder="이메일을 입력해주세요." />
				</div>
			</div>
			<div class="form-row">
				<div class="label">전송</div>
				<div class="input">
					<input type="submit" value="비번찾기" />
				</div>
			</div>
		</form>
	</div>
	<div class="backHome">
		<a href="../home/main">홈으로 돌아가기</a>
	</div>
</section>
<%@ include file="/jsp/part/foot.jspf"%>