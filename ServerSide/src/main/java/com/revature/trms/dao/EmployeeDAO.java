package com.revature.trms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.trms.forms.Employee;
import com.revature.trms.struct.Token;
import com.revature.util.ConnectionFactory;

public class EmployeeDAO {
	private static final String GET_EMPLOYEE_INFO = "select *" + " from employee"
			+ " inner join user_account on user_account.employee_id = employee.id"
			+ " inner join session_token on session_token.user_id = user_account.id"
			+ " where session_token.user_token = ?";
	private static String GET_REIMBURSEMENT_ACCOUNT_INFO = "select reimbursement_avaliable, reimbursement_pending"
			+ " from reimbursement_account" + " inner join employee on employee.id = reimbursement_account.employee_id"
			+ " inner join user_account on user_account.employee_id = employee.id"
			+ " inner join session_token on session_token.user_id = user_account.id"
			+ " where session_token.user_token = ?";

	private static String GET_PERMISSIONS = "select permission_level_id" + " from employee_permissions"
			+ " inner join employee on employee.id = employee_permissions.employee_id"
			+ " inner join user_account on user_account.employee_id = employee.id"
			+ " inner join session_token on session_token.user_id = user_account.id"
			+ " where session_token.user_token = ?";

	public static Employee getMyEmployee(Token token) {
		Employee emp = new Employee();
		Connection conn = ConnectionFactory.getConnection();
		try {
			PreparedStatement preparedStatementEmpoyeeInfo = conn.prepareStatement(GET_EMPLOYEE_INFO);
			preparedStatementEmpoyeeInfo.setString(1, token.token);
			ResultSet rsEmpoyeeInfo = preparedStatementEmpoyeeInfo.executeQuery();
			if (rsEmpoyeeInfo.next()) {
				emp.setFirstName(rsEmpoyeeInfo.getString("first_name"));
				emp.setMidName(rsEmpoyeeInfo.getString("mid_name"));
				emp.setLastName(rsEmpoyeeInfo.getString("last_name"));
				emp.setEmail(rsEmpoyeeInfo.getString("email"));
				emp.setPhone(rsEmpoyeeInfo.getString("phone"));
				emp.setAddress(rsEmpoyeeInfo.getString("address"));
				emp.setAddressTwo(rsEmpoyeeInfo.getString("addresstwo"));
				emp.setCity(rsEmpoyeeInfo.getString("city"));
				emp.setState(rsEmpoyeeInfo.getString("state"));
				emp.setZip(rsEmpoyeeInfo.getString("zip"));
			}

			PreparedStatement preparedStatementAccount = conn.prepareStatement(GET_REIMBURSEMENT_ACCOUNT_INFO);
			preparedStatementAccount.setString(1, token.token);
			ResultSet rsAccount = preparedStatementAccount.executeQuery();
			if (rsAccount.next()) {
				emp.setReimbursementAvaliable(rsAccount.getString("reimbursement_avaliable"));
				emp.setReimbursementPending(rsAccount.getString("reimbursement_pending"));
			}

			PreparedStatement preparedStatementPermissions = conn.prepareStatement(GET_PERMISSIONS);
			preparedStatementPermissions.setString(1, token.token);
			ResultSet rsPermissions = preparedStatementPermissions.executeQuery();
			while (rsPermissions.next()) {
				emp.setPermissionLevel(rsPermissions.getInt("permission_level_id"));
			}

			return emp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
