package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import com.sbs.java.blog.dao.MemberDao;
import com.sbs.java.blog.dto.Member;

public class MemberService extends Service {
	private AttrService attrService;
	private MemberDao memberDao;

	public MemberService(Connection dbConnection, AttrService attrService) {
		this.memberDao = new MemberDao(dbConnection);
		this.attrService = attrService;
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

	public int successAuth(int authStatus, int id) {
		return memberDao.successAuth(authStatus, id);
	}

	public boolean isValidModifyPrivateAuthCode(int actorId, String authCode) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__modifyPrivateAuthCode");

		return authCodeOnDB.equals(authCode);
	}

	public String genModifyPrivateAuthCode(int actorId) {
		String authCode = UUID.randomUUID().toString();
		attrService.setValue("member__" + actorId + "__extra__modifyPrivateAuthCode", authCode);

		return authCode;
	}
	
	public boolean isValidEmailAuthCode(int actorId, String authCode) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__emailAuthCode");

		return authCodeOnDB.equals(authCode);
	}

	public String genEmailAuthCode(int actorId) {
		String authCode = UUID.randomUUID().toString();
		attrService.setValue("member__" + actorId + "__extra__emailAuthCode", authCode);

		return authCode;
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}
}
