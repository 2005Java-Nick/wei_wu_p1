package com.revature.trms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.struct.Token;
import com.revature.util.ConnectionFactory;

public class ApprovalDAO {
	private static final String GET_PERMISSIONS = "select pl.*" + " from permission_levels pl"
			+ " inner join employee_permissions on employee_permissions.permission_level_id = pl.id"
			+ " inner join employee on employee.id = employee_permissions.employee_id"
			+ " inner join user_account on user_account.employee_id = employee.id"
			+ " inner join session_token on session_token.user_id = user_account.id"
			+ " where session_token.user_token = ?";
	private static final String DEPARTMENT_HEAD_APPROVAL = "update records_tuition_reimbursement "
			+ "set department_head_approval = ?, status = ?" + " where records_tuition_reimbursement.id = ?";

	private static final String SUPERVISOR_APPROVAL = "update records_tuition_reimbursement "
			+ "set supervisor_approval = ?, status = ?" + " where records_tuition_reimbursement.id = ?";

	private static final String BENEFITS_Coordinator_APPROVAL = "update records_tuition_reimbursement "
			+ "set benefits_coordinator_approval = ?, status = ?" + " where records_tuition_reimbursement.id = ?";

	public static boolean Approval(int formID, String message, Token token) {
		Connection conn = ConnectionFactory.getConnection();
		int employeeID = AuthenticationDAO.getEmployeeID(token);
		if (!(employeeID >= 0)) {
			return false;
		}

		List<String> permissionsList = new ArrayList<String>();

		try {
			PreparedStatement preparedStatementPermissions = conn.prepareStatement(GET_PERMISSIONS);
			preparedStatementPermissions.setString(1, token.token);
			ResultSet rsPermissions = preparedStatementPermissions.executeQuery();
			while (rsPermissions.next()) {
				if (rsPermissions.getString("permission_type").equalsIgnoreCase("supervisor")
						|| (rsPermissions.getString("permission_type").equalsIgnoreCase("department_head"))
						|| (rsPermissions.getString("permission_type").equalsIgnoreCase("benefits_coordinator"))) {
					permissionsList.add(rsPermissions.getString("permission_type"));
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		for (String p : permissionsList) {

			System.out.println(p);
			System.out.println(permissionsList.size());
			if (p == "supervisor") {

				try {
					System.out.println(p);
					PreparedStatement preparedStatement2 = conn.prepareStatement(SUPERVISOR_APPROVAL);
					preparedStatement2.setString(1, message);
					if (message.equalsIgnoreCase("true")) {
						preparedStatement2.setString(2, "Supervisor: Approved");
					} else if (message.equalsIgnoreCase("false")) {
						preparedStatement2.setString(2, "Supervisor: Disapproved");
					} else {
						preparedStatement2.setString(2, "Supervisor: Error");
					}
					preparedStatement2.setInt(3, formID);
					preparedStatement2.executeUpdate();

				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}

			}

		}

		for (String p : permissionsList) {
			if (p.equalsIgnoreCase("department_head")) {
				try {
					PreparedStatement preparedStatement1 = conn.prepareStatement(DEPARTMENT_HEAD_APPROVAL);
					preparedStatement1.setString(1, message);
					if (message.equalsIgnoreCase("true")) {
						preparedStatement1.setString(2, "Department Head: Approved");
					} else if (message.equalsIgnoreCase("false")) {
						preparedStatement1.setString(2, "Department Head: Disapproved");
					} else {
						preparedStatement1.setString(2, "Department Head: Error");
					}
					preparedStatement1.setInt(3, formID);
					preparedStatement1.executeUpdate();

				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		for (String p : permissionsList) {
			if (p.equalsIgnoreCase("benefits_coordinator")) {
				try {
					PreparedStatement preparedStatement2 = conn.prepareStatement(BENEFITS_Coordinator_APPROVAL);
					preparedStatement2.setString(1, message);
					if (message.equalsIgnoreCase("true")) {
						preparedStatement2.setString(2, "Benefits Coordinator: Approved");
					} else if (message.equalsIgnoreCase("false")) {
						preparedStatement2.setString(2, "Benefits Coordinator: Disapproved");
					} else {
						preparedStatement2.setString(2, "Benefits Coordinator: Error");
					}
					preparedStatement2.setInt(3, formID);
					preparedStatement2.executeUpdate();

				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		return true;
	}
}
