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

</style>

<!-- 비번 암호화저장 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<!-- 회원가입 중 유효하지 않은 input 방지  -->
<script>
	var joinFormSubmitted = false;

	function submitJoinForm(form) {
		if (joinFormSubmitted) {
			alert('처리 중입니다.');
			return;
		}

		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('아이디를 입력해주세요.');
			form.loginId.focus();

			return;
		}

		if (form.loginId.value.indexOf(' ') != -1) {
			alert('아이디를 영문소문자와 숫자의 조합으로 입력해주세요.')
			form.loginId.focus();

			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPw.focus();

			return;
		}

		form.loginPwReal.value = sha256(form.loginPw.value);
		// 		form.loginPw.value = '';

		form.submit();
		joinFormSubmitted = true;
	}
</script>

<!--body 내용-->
<div class="body-container flex con-small">
	<section class="body-main">
		<div class="member-join-box con">
			<form action="doJoin" method="post" class="join-form form1"
				onsubmit="submitJoinForm(this); return false;">
				<input type="hidden" name="loginPwReal">
				<div class="form-row">
					<div class="label">아이디</div>
					<div class="input">
						<input name="loginId" type="text" placeholder="아이디를 입력해 주세요" />
					</div>
				</div>
				<div class="form-row">
					<div class="label">이름</div>
					<div class="input">
						<input name="name" type="text" placeholder="이름을 입력해 주세요" />
					</div>
				</div>
				<div class="form-row">
					<div class="label">email</div>
					<div class="input">
						<input name="email" type="email" placeholder="이메일을 입력해 주세요" />
					</div>
				</div>
				<div class="form-row">
					<div class="label">닉네임</div>
					<div class="input">
						<input name="nickName" type="text" placeholder="닉네임을 입력해 주세요" />
					</div>
				</div>
				<div class="form-row">
					<div class="label">비번</div>
					<div class="input">
						<input name="loginPw" type="password" placeholder="비번을 입력해 주세요" />
					</div>
				</div>
				<div class="form-row">
					<div class="label">비번확인</div>
					<div class="input">
						<input name="loginPwConfirm" type="password"
							placeholder="비번을 한번더 입력해 주세요" />
					</div>
				</div>
				<div class="form-row">
					<div class="label">회원가입</div>
					<div class="input">
						<input value="전송" type="submit" /> <a href="../article/list">취소</a>
					</div>
				</div>
			</form>
		</div>

	</section>
	<%@ include file="/jsp/part/foot.jspf"%>