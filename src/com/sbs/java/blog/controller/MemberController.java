package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.service.ArticleService;

public class MemberController extends Controller {

	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "join":
			return doActionJoin();
		case "doJoin":
			return doActionDoJoin();
		case "login":
			return doActionLogin();
		case "doLogin":
			return doActionDoLogin();
		case "doLogout":
			return doActionDoLogout();
		}
		return "";

	}

	private String doActionDoLogout() {
//		HttpSession session = req.getSession();

		session.removeAttribute("loginedMemberId");
		return "html:<script> alert('로그아웃 되었습니다.'); location.replace('../home/main'); </script>";
	}

	private String doActionDoLogin() {
//		List<Member> members = memberService.getForPrintMembers();
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");
//		HttpSession session = req.getSession();

		// 시작
		int loginedMemberId = memberService.getMemberIdByLoginIdAndLoginPw(loginId, loginPw);

		if (loginedMemberId == -1) {
			return String.format("html:<script> alert('일치하는 정보가 없습니다.'); history.back(); </script>");
		}

		session.setAttribute("loginedMemberId", loginedMemberId);

		return String.format("html:<script> alert('로그인 되었습니다.'); location.replace('../home/main'); </script>");
		// 끝
//		if (session.getAttribute("loginedMemberId") != null) {
//			return "html:<script> alert('로그아웃후에 이용해 주세요'); location.replace('../home/main'); </script>";
//		} else {
//			for (Member member : members) {
//				if (member.getLoginId().equals(loginId) && member.getLoginPw().equals(loginPwReal)) {
//					session.setAttribute("loginedMemberId", member.getId());
//
//					return "html:<script> alert('" + member.getLoginId()
//							+ "님이 로그인 됐습니다.'); location.replace('../home/main'); </script>";
//				}
//			}
//		}
//		return "html:<script> alert('아이디와 비번을 확인해 주세요'); location.replace('login'); </script>";
	}

	private String doActionLogin() {
		return "member/login.jsp";
	}

	private String doActionDoJoin() {
//		List<Member> members = memberService.getForPrintMembers();

		String loginId = req.getParameter("loginId");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPwReal");
//		String loginPwReal = req.getParameter("loginPwReal");
//		String loginPw = req.getParameter("loginPw");
//		String loginPwConfirm = req.getParameter("loginPwConfirm");

		// 시작
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

		return String.format("html:<script> alert('%s님 환영합니다.'); location.replace('../home/main'); </script>", name);
	}
	// 끝
//
//	for(
//
//	Member member:members)
//	{
//		if (member.getLoginId().equals(loginId)) {
//			return "html:<script> alert('이미 존재하는 아이디 입니다.'); location.replace('join'); </script>";
//		}
//
//		if (member.getNickname().equals(nickname)) {
//			return "html:<script> alert('이미 존재하는 닉네임 입니다.'); location.replace('join'); </script>";
//		}
//
//		if (member.getEmail().equals(email)) {
//			return "html:<script> alert('이미 존재하는 이메일 입니다.'); location.replace('join'); </script>";
//		}
//	}
//
//		if (!loginPw.equals(loginPwConfirm)) {
//			return "html:<script> alert('비번과 비번 확인이 일치 하지 않습니다.'); location.replace('join'); </script>";
//
//		} else {
//	int id = memberService.join(loginId, name, nickname, loginPwReal,
//			email);return"html:<script> alert('"+id+"번 회원님이 가입 했습니다.'); location.replace('..home/main'); </script>";
//		}
//	}

	private String doActionJoin() {
		return "member/join.jsp";
	}

	@Override
	public String getControllerName() {
		return "member";
	}

}
