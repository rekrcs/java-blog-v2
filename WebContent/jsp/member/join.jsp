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
	var JoinForm__validLoginId = '';
	function JoinForm__submit(form) {
		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			alert('로그인 아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}

		var idReg = /^[A-za-z0-9]/g;
		form.loginId.value = form.loginId.value.trim();

		var idval = idReg.test(form.loginId.value)
		if (!idval) {
			alert('로그인 아이디는 영문, 숫자만 가능합니다.');
			form.loginId.focus();
			return;
		}

		if (form.loginId.value.length < 4) {
			alert('로그인 아이디는 4글자 이상 가능 합니다.');
			form.loginId.focus();
			return;
		}


		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length == 0) {
			alert('로그인 비번을 입력해주세요.');
			form.loginPw.focus();
			return;
		}

		if (form.loginPw.value.length < 5) {
			alert('로그인 비번는 5글자 이상 가능 합니다.');
			form.loginPw.focus();
			return;
		}

		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

		if (form.loginPwConfirm.value.length == 0) {
			alert('로그인 비번확인을 입력해주세요.');
			form.loginPwConfirm.focus();
			return;
		}

		if (form.loginPw.value != form.loginPwConfirm.value) {
			alert('로그인 비번확인이 일치하지 않습니다.');
			form.loginPwConfirm.focus();
			return;
		}

		form.name.value = form.name.value.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}

		form.nickname.value = form.nickname.value.trim();

		if (form.nickname.value.length == 0) {
			alert('별명을 입력해주세요.');
			form.nickname.focus();
			return;
		}

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}

		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';
		form.loginPwConfirm.value = '';

		form.submit();
	}

	function JoinForm__checkLoginIdDup(input) {
		var form = input.form;

		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			return;
		}

		$.get('getLoginIdDup', {
			loginId : form.loginId.value
		}, function(data) {
			var $message = $(form.loginId).next();

			if (data.resultCode.substr(0, 2) == 'S-') {
				$message.empty().append(
						'<div style="color:green;">' + data.msg + '</div>');
				JoinForm__validLoginId = data.loginId;
			} else {
				$message.empty().append(
						'<div style="color:red;">' + data.msg + '</div>');
				JoinForm__validLoginId = '';
			}
		}, 'json');
	}
</script>

<!--body 내용-->
<section class="body-main">
	<div class="join-form-box con">
		<h2 style="text-align: center">회원가입</h2>
		<form action="doJoin" method="POST" class="join-form form1"
			onsubmit="JoinForm__submit(this); return false;">
			<input type="hidden" name="loginPwReal" />
			<div class="form-row">
				<div class="label">로그인 아이디</div>
				<div class="input">
					<input onkeyup="JoinForm__checkLoginIdDup(this);" name="loginId"
						type="text" placeholder="로그인 아이디를 입력해주세요." />
					<div class="message-msg"></div>
				</div>
			</div>
			<div class="form-row">
				<div class="label">로그인 비번</div>
				<div class="input">
					<input name="loginPw" type="password" placeholder="로그인 비번을 입력해주세요." />
				</div>
			</div>
			<div class="form-row">
				<div class="label">로그인 비번확인</div>
				<div class="input">
					<input name="loginPwConfirm" type="password"
						placeholder="로그인 비번확인을 입력해주세요." />
				</div>
			</div>
			<div class="form-row">
				<div class="label">이름</div>
				<div class="input">
					<input name="name" type="text" placeholder="이름을 입력해주세요." />
				</div>
			</div>
			<div class="form-row">
				<div class="label">닉네임</div>
				<div class="input">
					<input name="nickname" type="text" placeholder="닉네임을 입력해주세요." />
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
					<input type="submit" value="회원가입" />
				</div>
			</div>
		</form>
	</div>
	<div class="backHome">
		<a href="../home/main">홈으로 돌아가기</a>
	</div>
</section>
<%@ include file="/jsp/part/foot.jspf"%>