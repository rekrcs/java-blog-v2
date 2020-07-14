package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			return doActionJoin(req, resp);
		case "doJoin":
			return doActionDoJoin(req, resp);
		case "login":
			return doActionLogin(req, resp);
		case "doLogin":
			return doActionDoLogin(req, resp);
		}
		return "";

	}

	private String doActionDoLogin(HttpServletRequest req, HttpServletResponse resp) {
		List<Member> members = memberService.getForPrintMembers();
		String loginId = req.getParameter("loginId");
		String loginPwReal = req.getParameter("loginPwReal");
		HttpSession session = req.getSession();

		if (session.getAttribute("loginedMemberId") != null) {
			return "html:<script> alert('로그아웃후에 이용해 주세요'); location.replace('../home/main'); </script>";
		} else {
			for (Member member : members) {
				if (member.getLoginId().equals(loginId) && member.getLoginPw().equals(loginPwReal)) {
					session.setAttribute("loginedMemberId", member.getId());
					return "html:<script> alert('" + member.getLoginId()
							+ "님이 로그인 됐습니다.'); location.replace('../home/main'); </script>";
				}
			}
		}
		return "html:<script> alert('아이디와 비번을 확인해 주세요'); location.replace('login'); </script>";
	}

	private String doActionLogin(HttpServletRequest req, HttpServletResponse resp) {
		return "member/login.jsp";
	}

	private String doActionDoJoin(HttpServletRequest req, HttpServletResponse resp) {
		List<Member> members = memberService.getForPrintMembers();

		String loginId = req.getParameter("loginId");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String nickName = req.getParameter("nickName");
		String loginPw = req.getParameter("loginPw");
		String loginPwConfirm = req.getParameter("loginPwConfirm");
		String loginPwReal = req.getParameter("loginPwReal");

		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return "html:<script> alert('이미 존재하는 아이디 입니다.'); location.replace('join'); </script>";
			}
		}

		for (Member member : members) {
			if (member.getNickName().equals(nickName)) {
				return "html:<script> alert('이미 존재하는 닉네임 입니다.'); location.replace('join'); </script>";
			}
		}
		for (Member member : members) {
			if (member.getEmail().equals(email)) {
				return "html:<script> alert('이미 존재하는 이메일 입니다.'); location.replace('join'); </script>";
			}
		}

		if (!loginPw.equals(loginPwConfirm)) {
			return "html:<script> alert('비번과 비번 확인이 일치 하지 않습니다.'); location.replace('join'); </script>";

		} else {
			int id = memberService.join(loginId, name, nickName, loginPwReal, email);
			return "html:<script> alert('" + id + "번 회원님이 가입 했습니다.'); location.replace('join'); </script>";
		}
	}

	private String doActionJoin(HttpServletRequest req, HttpServletResponse resp) {
		return "member/join.jsp";
	}

}
