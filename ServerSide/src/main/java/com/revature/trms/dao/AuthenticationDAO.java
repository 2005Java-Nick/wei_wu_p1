package com.revature.trms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revature.trms.struct.Token;
import com.revature.util.ConnectionFactory;

public class AuthenticationDAO {
	private static final String GET_EMPLOYEE_ID = "select employee.id " + "from employee "
			+ "inner join user_account on user_account.employee_id = employee.id "
			+ "inner join session_token on user_account.id = session_token.user_id "
			+ "where session_token.user_token = ?";

	public static int getEmployeeID(Token token) {

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(GET_EMPLOYEE_ID);
			preparedStatement.setString(1, token.token);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getInt("id"));
				return rs.getInt("id");

			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;

		}

		return -1;
	}
}
