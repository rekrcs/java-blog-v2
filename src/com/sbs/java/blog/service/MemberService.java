package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.List;

import com.sbs.java.blog.dao.MemberDao;
import com.sbs.java.blog.dto.Member;

public class MemberService extends Service {

	private MemberDao memberDao;

	public MemberService(Connection dbConnection) {
		this.memberDao = new MemberDao(dbConnection);
	}

	public int join(String loginId, String loginPw, String name, String nickname, String email) {
		return memberDao.join(loginId, loginPw, name, nickname, email);
	}

	public List<Member> getForPrintMembers() {
		return memberDao.getForPrintMembers();
	}

	public boolean isJoinableLoginId(String loginId) {
		return memberDao.isJoinableLoginId(loginId);
	}

	public boolean isJoinableNickname(String nickname) {
		return memberDao.isJoinableNickname(nickname);
	}

	public boolean isJoinableEmail(String email) {
		return memberDao.isJoinableEmail(email);
	}

	public int getMemberIdByLoginIdAndLoginPw(String loginId, String loginPw) {
		return memberDao.getMemberIdByLoginIdAndLoginPw(loginId, loginPw);
	}

	public Member getMemberById(int id) {
		return memberDao.getMemberById(id);
	}

	public int getTemporaryPw(int id, String pw) {
		return memberDao.getTemporaryPw(id, pw);
	}

	public int userModify(String loginId, String loginPw, String name, String nickname, String email, int id) {
		return memberDao.userModify(loginId, loginPw, name, nickname, email, id);
		
	}

	public int memberDelete(int id) {
		return memberDao.memberDelete(id);
	}

	public int successAuth(int authStatus) {
		return memberDao.successAuth(authStatus);
	}
}
