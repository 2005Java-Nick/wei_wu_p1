package com.revature.trms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.trms.struct.Token;
import com.revature.util.ConnectionFactory;

public class MessageUploadDAO {
	private static final String UPLOAD_MESSAGE = "insert into reimbursement_messages (reimbursement_records_id , message)"
			+ " values (?,?)";
	private static final String GET_EMPLOYEE_FIRST_AND_LAST_NAME = "select e2.first_name, e2.last_name"
			+ " from employee e2" + " where e2.id = ?";

	public static boolean UploadMessaget(int formID, String message, Token token) {
		Connection conn = ConnectionFactory.getConnection();
		int employeeID = AuthenticationDAO.getEmployeeID(token);
		String firstName = "";
		String lastName = "";

		System.out.println(employeeID);
		if (!(employeeID >= 0)) {
			return false;
		} else {
			PreparedStatement preparedStatementName;
			try {
				preparedStatementName = conn.prepareStatement(GET_EMPLOYEE_FIRST_AND_LAST_NAME);
				preparedStatementName.setInt(1, employeeID);
				ResultSet rsName = preparedStatementName.executeQuery();
				if (rsName.next()) {
					firstName = rsName.getString("first_name");
					lastName = rsName.getString("last_name");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(UPLOAD_MESSAGE);
			preparedStatement.setInt(1, formID);
			preparedStatement.setString(2, firstName + " " + lastName + ": " + message);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
