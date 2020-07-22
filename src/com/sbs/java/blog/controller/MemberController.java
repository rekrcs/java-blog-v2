package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.Util;
import com.sbs.java.mail.service.MailService;

public class MemberController extends Controller {

	private MailService mailService;

	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp, MailService mailService) {
		super(dbConn, actionMethodName, req, resp);
		this.mailService = mailService;
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
		case "findPassword":
			return doActionFindPassword();
		case "doFindPassword":
			return doActionDoFindPassword();
		case "findId":
			return doActionFindId();
		case "doFindId":
			return doActionDoFindId();
		case "myPage":
			return doActionMyPage();
		case "userModify":
			return doActionUserModify();
		case "doUserModify":
			return doActionDoUserModify();
		case "userDelete":
			return doActionUserDelete();
		case "doUserDelete":
			return doActionDoUserDelete();
		}
		return "";

	}

	private String doActionDoUserDelete() {
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

	private String doActionUserDelete() {
		return "member/userDelete.jsp";
	}

	private String doActionDoUserModify() {
		String loginId = req.getParameter("loginId");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String nickname = req.getParameter("nickname");
		String loginPw = req.getParameter("loginPwReal");
		int loginedMemberId = (int) session.getAttribute("loginedMemberId");
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
		memberService.userModify(loginId, loginPw, name, nickname, email, loginedMemberId);

		return String.format("html:<script> alert('%s님 정보가 수정 되었습니다.'); location.replace('../home/main'); </script>",
				name);
	}

	private String doActionUserModify() {
		return "member/userModify.jsp";
	}

	private String doActionMyPage() {
		int loginedMemberId = (int) session.getAttribute("loginedMemberId");

		List<Article> articles = articleService.getForPrintArticlesByMemberId(loginedMemberId);
		req.setAttribute("articles", articles);

		int totalCount = articleService.getForPrintListArticlesCountByMemberId(loginedMemberId);
		req.setAttribute("totalCount", totalCount);
		return "member/myPage.jsp";
	}

	private String doActionDoFindId() {
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

	private String doActionFindId() {
		return "member/findId.jsp";
	}

	private String doActionDoFindPassword() {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String temporaryPw = Util.getTemporaryPw();
		String temporaryPwSHA256 = Util.getTemporaryPwSHA256(temporaryPw);

		List<Member> members = memberService.getForPrintMembers();

		for (Member member : members) {
			if (loginId.equals(member.getLoginId()) && name.equals(member.getName())
					&& email.equals(member.getEmail())) {
				int memberId = member.getId();

				int num = memberService.getTemporaryPw(memberId, temporaryPwSHA256);

//				getGmailForTemporaryPw(name, temporaryPw, email);
				boolean sendMailDone = mailService.findPassword(email, name + "님 임시 비밀번호 입니다.",
						"임시비번 : " + temporaryPw + "\n로그인후에 반드시 비번을 변경해 주세요") == 1;
				return String.format(
						"html:<script> alert('%s님 임시비번이 이메일로 발송되었습니다.'); location.replace('login'); </script>", name);
			}
		}
		return String.format("html:<script> alert('일치하는 정보가 없습니다.'); history.back(); </script>");
	}

	private String doActionFindPassword() {
		return "member/findPassword.jsp";
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
		
		String redirectUrl = Util.getString(req, "redirectUrl", "../home/main");

		return String.format("html:<script> alert('로그인 되었습니다.'); location.replace('" + redirectUrl + "'); </script>");
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
//		gmailSend(name, email);

		boolean sendMailDone = mailService.send(email, name + "님 가입을 환영합니다.", "반갑습니다.!!") == 1;
		return String.format("html:<script> alert('%s님 환영합니다.'); location.replace('../home/main'); </script>", name);
	}

	// 끝
//	private void gmailSend(String name, String email) {
//		String user = ""; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
//		String password = ""; // 패스워드
//		// SMTP 서버 정보를 설정한다.
//		Properties prop = new Properties();
//		prop.put("mail.smtp.host", "smtp.gmail.com");
//		prop.put("mail.smtp.port", 465);
//		prop.put("mail.smtp.auth", "true");
//		prop.put("mail.smtp.ssl.enable", "true");
//		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//
//		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(user, password);
//			}
//		});
//
//		try {
//			MimeMessage message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(user));
//
//			// 수신자메일주소
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//
//			// Subject
//			message.setSubject(name + "님 가입환영합니다."); // 메일 제목을 입력
//
//			// Text
//			message.setText("blog에 가입하셨습니다."); // 메일 내용을 입력
//
//			// send the message
//			Transport.send(message); //// 전송
//			System.out.println("message sent successfully...");
//		} catch (AddressException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	private void getGmailForTemporaryPw(String name, String temporaryPw, String email) {
//		String user = ""; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
//		String password = ""; // 패스워드
//
//		// SMTP 서버 정보를 설정한다.
//		Properties prop = new Properties();
//		prop.put("mail.smtp.host", "smtp.gmail.com");
//		prop.put("mail.smtp.port", 465);
//		prop.put("mail.smtp.auth", "true");
//		prop.put("mail.smtp.ssl.enable", "true");
//		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//
//		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(user, password);
//			}
//		});
//
//		try {
//			MimeMessage message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(user));
//
//			// 수신자메일주소
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//
//			// Subject
//			message.setSubject(name + "님 임시비빌번호입니다."); // 메일 제목을 입력
//
//			// Text
//			message.setText("임시비번 : " + temporaryPw + "\n로그인후에 반드시 비번을 변경해 주세요"); // 메일 내용을 입력
//
//			// send the message
//			Transport.send(message); //// 전송
//			System.out.println("message sent successfully...");
//		} catch (AddressException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

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
