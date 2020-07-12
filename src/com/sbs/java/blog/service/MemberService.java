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

	public int join(String loginId, String name, String nickName, String loginPw) {
		return memberDao.join(loginId, name, nickName, loginPw);
	}

	public List<Member> getForPrintMembers() {
		return memberDao.getForPrintMembers();
	}

}
