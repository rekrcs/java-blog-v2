package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class MemberDao extends Dao {
	private Connection dbConn;

	public MemberDao(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public int join(String loginId, String loginPw, String name, String nickname, String email) {
		SecSql secSql = new SecSql();

		secSql.append("INSERT INTO `member`");
		secSql.append("SET regDate = NOW()");
		secSql.append(", updateDate = NOW()");
		secSql.append(", loginId = ? ", loginId);
		secSql.append(", loginPw = ? ", loginPw);
		secSql.append(", name = ? ", name);
		secSql.append(", email = ? ", email);
		secSql.append(", nickname = ? ", nickname);

		return DBUtil.insert(dbConn, secSql);
	}

	public List<Member> getForPrintMembers() {
		SecSql secSql = new SecSql();

		secSql.append("SELECT *");
		secSql.append("FROM `member`");

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<Member> members = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			members.add(new Member(row));
		}

		return members;
	}

	public boolean isJoinableLoginId(String loginId) {
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		return DBUtil.selectRowIntValue(dbConn, sql) == 0;
	}

	public boolean isJoinableNickname(String nickname) {
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM `member`");
		sql.append("WHERE nickname = ?", nickname);

		return DBUtil.selectRowIntValue(dbConn, sql) == 0;
	}

	public boolean isJoinableEmail(String email) {
		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM `member`");
		sql.append("WHERE email = ?", email);

		return DBUtil.selectRowIntValue(dbConn, sql) == 0;
	}

	public int getMemberIdByLoginIdAndLoginPw(String loginId, String loginPw) {
		SecSql sql = SecSql.from("SELECT id");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		sql.append("AND loginPw = ?", loginPw);

		return DBUtil.selectRowIntValue(dbConn, sql);
	}

	public Member getMemberById(int id) {
		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE id = ?", id);

		return new Member(DBUtil.selectRow(dbConn, sql));
	}

	public int getTemporaryPw(int id, String pw) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE member");
		secSql.append("SET loginPw = ?", pw);
		secSql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, secSql);
	}

	public int userModify(String loginId, String loginPw, String name, String nickname, String email, int id) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE member");
		secSql.append("SET loginId = ?", loginId);
		secSql.append(", loginPw = ?", loginPw);
		secSql.append(", name = ?", name);
		secSql.append(", nickname = ?", nickname);
		secSql.append(", email = ?", email);
		secSql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, secSql);
	}

	public int memberDelete(int id) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE member");
		secSql.append("SET delStatus = 1");
		secSql.append(", nickname = '탈퇴회원'");
		secSql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, secSql);
	}

	public int successAuth(int authStatus, int id) {
		SecSql secSql = new SecSql();

		secSql.append("UPDATE member");
		secSql.append("SET mailAuthStatus = ?", authStatus);
		secSql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, secSql);
	}

	public Member getMemberByLoginId(String loginId) {
		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		return new Member(DBUtil.selectRow(dbConn, sql));
	}

}
