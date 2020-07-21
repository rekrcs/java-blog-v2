package com.sbs.java.blog.controller;

import java.security.MessageDigest;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Member;

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
		case "findPassword":
			return doActionFindPassword();
		case "doFindPassword":
			return doActionDoFindPassword();
		}
		return "";

	}

	private String doActionDoFindPassword() {
		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String temporaryPw = getTemporaryPw();
		String temporaryPwSHA256 = "";

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(temporaryPw.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			temporaryPwSHA256 = hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		List<Member> members = memberService.getForPrintMembers();

		for (Member member : members) {
			if (loginId.equals(member.getLoginId()) && name.equals(member.getName())
					&& email.equals(member.getEmail())) {
				int memberId = member.getId();

				int num = memberService.getTemporaryPw(memberId, temporaryPwSHA256);

				getGmailForTemporaryPw(name, temporaryPw, email);

				return String.format(
						"html:<script> alert('%s님 임시비번이 이메일로 발송되었습니다.'); location.replace('../home/main'); </script>",
						name);
			}
		}
		return String.format("html:<script> alert('일치하는 정보가 없습니다.'); history.back(); </script>");
	}

	private String getTemporaryPw() {
		Random rand = new Random();
		String temporaryPw = "";

		for (int i = 0; i < 6; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			temporaryPw += ran;
		}
		return temporaryPw;
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
		gmailSend(name, email);
		return String.format("html:<script> alert('%s님 환영합니다.'); location.replace('../home/main'); </script>", name);
	}

	// 끝
	private void gmailSend(String name, String email) {
		String user = ""; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
		String password = ""; // 패스워드
		// SMTP 서버 정보를 설정한다.
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 465);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));

			// 수신자메일주소
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

			// Subject
			message.setSubject(name + "님 가입환영합니다."); // 메일 제목을 입력

			// Text
			message.setText("blog에 가입하셨습니다."); // 메일 내용을 입력

			// send the message
			Transport.send(message); //// 전송
			System.out.println("message sent successfully...");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getGmailForTemporaryPw(String name, String temporaryPw, String email) {
		String user = ""; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
		String password = ""; // 패스워드

		// SMTP 서버 정보를 설정한다.
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 465);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));

			// 수신자메일주소
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

			// Subject
			message.setSubject(name + "님 임시비빌번호입니다."); // 메일 제목을 입력

			// Text
			message.setText("임시비번 : " + temporaryPw + "\n로그인후에 반드시 비번을 변경해 주세요"); // 메일 내용을 입력

			// send the message
			Transport.send(message); //// 전송
			System.out.println("message sent successfully...");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
