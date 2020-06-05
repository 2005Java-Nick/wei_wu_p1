package com.revature.trms.dao;

import java.sql.Connection;

import com.revature.util.ConnectionFactory;

public class DBConnectionTestDAO {
	public static boolean Test() {
		Connection conn = ConnectionFactory.getConnection();

		if (conn != null) {
			return true;
		}
		return false;
	}
}
