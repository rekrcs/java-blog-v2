package com.sbs.java.blog.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {
	public static boolean empty(HttpServletRequest req, String paramName) {
		String paramValue = req.getParameter(paramName);

		return empty(paramValue);
	}

	public static boolean empty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof String) {
			return ((String) obj).trim().length() == 0;
		}

		return true;
	}

	public static boolean isNum(HttpServletRequest req, String paramName) {
		String paramValue = req.getParameter(paramName);

		return isNum(paramValue);
	}

	public static boolean isNum(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Long) {
			return true;
		} else if (obj instanceof Integer) {
			return true;
		} else if (obj instanceof String) {
			try {
				Integer.parseInt((String) obj);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		return false;
	}

	public static int getInt(HttpServletRequest req, String paramName) {
		return Integer.parseInt(req.getParameter(paramName));
	}

	public static void printEx(String errName, HttpServletResponse resp, Exception e) {
		try {
			resp.getWriter()
					.append("<h1 style='color:red; font-weight:bold; text-align:left;'>[에러 : " + errName + "]</h1>");

			resp.getWriter().append("<pre style='text-align:left; font-weight:bold; font-size:1.3rem;'>");
			e.printStackTrace(resp.getWriter());
			resp.getWriter().append("</pre>");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String getString(HttpServletRequest req, String paramName) {
		return req.getParameter(paramName);
	}

	public static String getTemporaryPw() {
		Random rand = new Random();
		String temporaryPw = "";

		for (int i = 0; i < 6; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			temporaryPw += ran;
		}
		return temporaryPw;
	}

	public static String getTemporaryPwSHA256(String temporaryPw) {
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
			return temporaryPwSHA256;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}
}
