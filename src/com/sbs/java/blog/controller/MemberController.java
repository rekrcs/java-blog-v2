package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		}
		return "";

	}

	private String doActionDoJoin(HttpServletRequest req, HttpServletResponse resp) {
		List<Member> members = memberService.getForPrintMembers();

		String loginId = req.getParameter("loginId");
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

		if (!loginPw.equals(loginPwConfirm)) {
			return "html:<script> alert('비번과 비번 확인이 일치 하지 않습니다.'); location.replace('join'); </script>";

		} else {
			int id = memberService.join(loginId, name, nickName, loginPwReal);
			return "html:<script> alert('" + id + "번 회원님이 가입 했습니다.'); location.replace('join'); </script>";
		}
	}

	private String doActionJoin(HttpServletRequest req, HttpServletResponse resp) {
		return "member/join.jsp";
	}

}
