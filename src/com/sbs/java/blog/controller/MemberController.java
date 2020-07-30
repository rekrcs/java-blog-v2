package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.service.MailService;
import com.sbs.java.blog.util.Util;


public class MemberController extends Controller {

	private MailService mailService;

	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "join":
			return actionJoin();
		case "doJoin":
			return actionDoJoin();
		case "login":
			return actionLogin();
		case "doLogin":
			return actionDoLogin();
		case "doLogout":
			return actionDoLogout();
		case "findPassword":
			return actionFindPassword();
		case "doFindPassword":
			return actionDoFindPassword();
		case "findId":
			return actionFindId();
		case "doFindId":
			return actionDoFindId();
		case "myPage":
			return actionMyPage();
		case "userModify":
			return actionUserModify();
		case "doUserModify":
			return actionDoUserModify();
		case "userDelete":
			return actionUserDelete();
		case "doUserDelete":
			return actionDoUserDelete();
		case "doubleCheckPassword":
			return actionDoubleCheckPassword();
		case "doDoubleCheckPassword":
			return actionDoDoubleCheckPassword();
		case "doAuthMail":
			return actionDoAuthMail();
		}
		return "";

	}

	private String actionDoAuthMail() {
		String code = req.getParameter("code");
		String authCode = (String) session.getAttribute("code");
		String loginId = req.getParameter("loginId");
		

		if (authCode.equals(code)) {
			int num = memberService.successAuth(1, loginId);
			return String.format("html:<script> alert('인증이 완료 되었습니다.'); window.close(); </script>");
		}
		return String.format("html:<script> alert('일치하는 정보가 없습니다.'); window.close(); </script>");
	}

	private String actionDoDoubleCheckPassword() {
		String loginPw = req.getParameter("loginPwReal");
		int loginedMemberId = (int) session.getAttribute("loginedMemberId");

		Member member = memberService.getMemberById(loginedMemberId);
		if (member.getLoginPw().equals(loginPw)) {
			return "member/userModify.jsp";
		}
		return String.format("html:<script> alert('일치하는 정보가 없습니다.'); history.back(); </script>");
	}

	private String actionDoubleCheckPassword() {
		return "member/doubleCheckPassword.jsp";
	}

	private String actionDoUserDelete() {
		String loginId = req.getParameter("loginId");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPwReal");
		int loginedMemberId = (int) session.getAttribute("loginedMemberId");

		Member member = memberService.getMemberById(loginedMemberId);

		if (member.getLoginId().equals(loginId) && member.getEmail().equals(email) && member.getName().equals(name)
				&& member.getNickname().equals(nickname) && member.getLoginPw().equals(loginPw)) {

			int deleteId = memberService.memberDelete(loginedMemberId);
			session.removeAttribute("loginedMemberId");
			return String.format("html:<script> alert('%s님 탈퇴됬습니다.'); location.replace('../home/main'); </script>",
					name);
		}

		return String.format("html:<script> alert('일치하는 정보가 없습니다.'); history.back(); </script>");
	}

	private String actionUserDelete() {
		return "member/userDelete.jsp";
	}

	private String actionDoUserModify() {
		String loginId = req.getParameter("loginId");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPwReal");
		int loginedMemberId = (int) session.getAttribute("loginedMemberId");
		boolean isJoinableLoginId = memberService.isJoinableLoginId(loginId);

		memberService.userModify(loginId, loginPw, name, nickname, email, loginedMemberId);

		return String.format("html:<script> alert('%s님 정보가 수정 되었습니다.'); location.replace('../home/main'); </script>",
				name);
	}

	private String actionUserModify() {
		return "member/userModify.jsp";
	}

	private String actionMyPage() {
		int loginedMemberId = (int) session.getAttribute("loginedMemberId");

		List<Article> articles = articleService.getForPrintArticlesByMemberId(loginedMemberId);
		req.setAttribute("articles", articles);

		int totalCount = articleService.getForPrintListArticlesCountByMemberId(loginedMemberId);
		req.setAttribute("totalCount", totalCount);
		return "member/myPage.jsp";
	}

	private String actionDoFindId() {
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		List<Member> members = memberService.getForPrintMembers();

		for (Member member : members) {
			if (name.equals(member.getName()) && email.equals(member.getEmail())) {

				boolean sendMailDone = mailService.findId(email, name + "님의 아이디 입니다.",
						"아이디 : " + member.getLoginId()) == 1;
				return String.format(
						"html:<script> alert('%s님 아이디가 이메일로 발송되었습니다.'); location.replace('login'); </script>", name);
			}
		}
		return String.format("html:<script> alert('일치하는 정보가 없습니다.'); history.back(); </script>");
	}

	private String actionFindId() {
		return "member/findId.jsp";
	}

	private String actionDoFindPassword() {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String temporaryPw = Util.getAuthCode();
		String temporaryPwSHA256 = Util.getTemporaryPwSHA256(temporaryPw);

		List<Member> members = memberService.getForPrintMembers();

		for (Member member : members) {
			if (loginId.equals(member.getLoginId()) && name.equals(member.getName())
					&& email.equals(member.getEmail())) {
				int memberId = member.getId();

				int num = memberService.getTemporaryPw(memberId, temporaryPwSHA256);

				boolean sendMailDone = mailService.findPassword(email, name + "님 임시 비밀번호 입니다.",
						"임시비번 : " + temporaryPw + "\n로그인후에 반드시 비번을 변경해 주세요") == 1;
				return String.format(
						"html:<script> alert('%s님 임시비번이 이메일로 발송되었습니다.'); location.replace('login'); </script>", name);
			}
		}
		return String.format("html:<script> alert('일치하는 정보가 없습니다.'); history.back(); </script>");
	}

	private String actionFindPassword() {
		return "member/findPassword.jsp";
	}

	private String actionDoLogout() {
		session.removeAttribute("loginedMemberId");
		
		String redirectUri = Util.getString(req, "redirectUri", "../home/main");
		
		return "html:<script> alert('로그아웃 되었습니다.'); location.replace('" + redirectUri + "'); </script>";
	}

	private String actionDoLogin() {
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");

		int loginedMemberId = memberService.getMemberIdByLoginIdAndLoginPw(loginId, loginPw);

		if (loginedMemberId == -1) {
			return String.format("html:<script> alert('일치하는 정보가 없습니다.'); history.back(); </script>");
		}

		Member member = memberService.getMemberById(loginedMemberId);

		if (member.getMailAuthStatus() == 0) {
			return String.format("html:<script> alert('이메일 인증후에 로그인 가능합니다.'); history.back(); </script>");
		}
		session.setAttribute("loginedMemberId", loginedMemberId);

		String redirectUri = Util.getString(req, "redirectUri", "../home/main");

		return String.format("html:<script> alert('로그인 되었습니다.'); location.replace('" + redirectUri + "'); </script>");
	}

	private String actionLogin() {
		return "member/login.jsp";
	}

	private String actionDoJoin() {
		String loginId = req.getParameter("loginId");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPwReal");

		boolean isJoinableLoginId = memberService.isJoinableLoginId(loginId);

		if (isJoinableLoginId == false) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 아이디 입니다.'); history.back(); </script>", loginId);
		}

		boolean isJoinableNickname = memberService.isJoinableNickname(nickname);

		if (isJoinableNickname == false) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 닉네임 입니다.'); history.back(); </script>", nickname);
		}

		boolean isJoinableEmail = memberService.isJoinableEmail(email);

		if (isJoinableEmail == false) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 이메일 입니다.'); history.back(); </script>", email);
		}

		memberService.join(loginId, loginPw, name, nickname, email);

		String code = Util.getAuthCode();

		session.setAttribute("code", code);

		String body = "";
		body += "로그인을 위해서는 인증이 필요합니다. 아래의 링크를 클릭해 주세요";
//		body += String.format("\nhttps://brg.my.iu.gy/blog/s/member/doAuthMail?code=%s&loginId=%s", code, loginId);
		body += String.format("\nhttp://localhost:8081/blog/s/member/doAuthMail?code=%s&loginId=%s", code, loginId);
		boolean sendMailDone = mailService.send(email, name + "님 가입을 환영합니다.", body) == 1;
		return String.format(
				"html:<script> alert('%s님 환영합니다. 이메일 인증후에 로그인 가능 합니다.'); location.replace('../home/main'); </script>",
				name);
	}

	private String actionJoin() {
		return "member/join.jsp";
	}

	@Override
	public String getControllerName() {
		return "member";
	}

}
