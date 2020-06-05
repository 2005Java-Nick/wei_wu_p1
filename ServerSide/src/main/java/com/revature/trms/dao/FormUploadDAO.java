package com.revature.trms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.revature.trms.forms.TuitionReimbursementForm;
import com.revature.trms.struct.Token;
import com.revature.util.ConnectionFactory;

public class FormUploadDAO {

	private static final String UPLOAD_FORM_TUITION_REIMBURSE = "INSERT INTO records_tuition_reimbursement"
			+ " (submission_date, event_date, event_location, event_discription, event_cost,"
			+ " event_grading_format,event_cutOff_grade, event_type, event_justification, estimated_timeoff, optional_comments,"
			+ " status) values (?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id";
	private static final String UPLOAD_FORM_TUITION_REIMBURSE_ATTACHMENTS = "INSERT INTO records_tuition_reimbursement_attachments"
			+ " (records_tuition_reimbursement_id, file_name, file_size, content_type, file_data) values (?,?,?,?,?)";

	private static final String INSERT_REIMBURSEMENT_TRACKER = "INSERT INTO reimbursement_tracker"
			+ " (employee_id,reimbursement_records_id) values (?,?)";

	private static final String UPDATE_BALANCE = "update reimbursement_account" + " set reimbursement_pending = ?"
			+ " where reimbursement_account.employee_id = ?";
	private static String GET_REIMBURSEMENT_ACCOUNT_INFO = "select reimbursement_avaliable, reimbursement_pending"
			+ " from reimbursement_account" + " inner join employee on employee.id = reimbursement_account.employee_id"
			+ " inner join user_account on user_account.employee_id = employee.id"
			+ " inner join session_token on session_token.user_id = user_account.id"
			+ " where session_token.user_token = ?";

	public static boolean uploadForm(TuitionReimbursementForm aForm, Token token) {
		Connection conn = ConnectionFactory.getConnection();
		int employeeID = AuthenticationDAO.getEmployeeID(token);
		System.out.println(employeeID);
		if (!(employeeID >= 0)) {
			return false;
		}

		try {
			long ra = 0;
			long rp = 0;
			long rc = Long.parseLong(aForm.getEventCost());
			long updatedPending = 0;
			int eventPercent = Integer.parseInt(aForm.getEventType());
			PreparedStatement preparedStatementAccount = conn.prepareStatement(GET_REIMBURSEMENT_ACCOUNT_INFO);
			preparedStatementAccount.setString(1, token.token);
			ResultSet rsAccount = preparedStatementAccount.executeQuery();
			if (rsAccount.next()) {
				ra = rsAccount.getLong("reimbursement_avaliable");
				rp = rsAccount.getLong("reimbursement_pending");
			}
			if ((rc * (eventPercent / 100)) > (ra - rp)) {
				updatedPending = rp + (ra - rp);
			} else {
				updatedPending = rp + (rc * (eventPercent / 100));
			}
			PreparedStatement preparedStatementAccountBalanceUpdate;
			preparedStatementAccountBalanceUpdate = conn.prepareStatement(UPDATE_BALANCE);
			preparedStatementAccountBalanceUpdate.setLong(1, updatedPending);
			preparedStatementAccountBalanceUpdate.setInt(2, employeeID);
			preparedStatementAccountBalanceUpdate.executeUpdate();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		PreparedStatement preparedStatement;
		int last_inserted_id = 0;
		try {

			preparedStatement = conn.prepareStatement(UPLOAD_FORM_TUITION_REIMBURSE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, formatter.format(date));
			preparedStatement.setString(2, aForm.getEventDate());
			preparedStatement.setString(3, aForm.getEventLocation());
			preparedStatement.setString(4, aForm.getEventDiscription());
			preparedStatement.setLong(5, Long.parseLong(aForm.getEventCost()));
			preparedStatement.setString(6, aForm.getGradingFormat());
			preparedStatement.setString(7, aForm.getCutOffGrade());
			preparedStatement.setString(8, aForm.getEventType());
			preparedStatement.setString(9, aForm.getJustification());
			preparedStatement.setString(10, aForm.getWorkTimeOff());
			preparedStatement.setString(11, aForm.getOptionalComments());
			preparedStatement.setString(12, "new_submission");
			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				last_inserted_id = rs.getInt(1);
				System.out.println(last_inserted_id);
			}
			System.out.println(last_inserted_id);
			preparedStatement = conn.prepareStatement(UPLOAD_FORM_TUITION_REIMBURSE_ATTACHMENTS);
			for (int i = 0; i < aForm.getFiles().size(); i++) {
				preparedStatement.setInt(1, last_inserted_id);
				preparedStatement.setString(2, aForm.getFiles().get(i).getSubmittedFileName());
				preparedStatement.setLong(3, aForm.getFiles().get(i).getSize());
				preparedStatement.setString(4, aForm.getFiles().get(i).getContentType());
				preparedStatement.setBinaryStream(5, aForm.getFiles().get(i).getInputStream());
				preparedStatement.executeUpdate();
			}
			// System.out.println(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("upload fail");

		}

		try {
			preparedStatement = conn.prepareStatement(INSERT_REIMBURSEMENT_TRACKER);
			preparedStatement.setInt(1, employeeID);
			preparedStatement.setInt(2, last_inserted_id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("upload completed");
		return true;
	}

	/*
	 * private static final String CREATE_UserACCOUNT =
	 * "INSERT INTO uploadtest (file_data) values (?)";
	 * 
	 * public static boolean uploadFile(InputStream inputStream) {
	 * 
	 * Connection conn = ConnectionFactory.getConnection(); PreparedStatement
	 * preparedStatement; try { // conn.setAutoCommit(false); preparedStatement =
	 * conn.prepareStatement(CREATE_UserACCOUNT); System.out.println("inserting");
	 * System.out.println(inputStream.toString());
	 * preparedStatement.setBinaryStream(1, inputStream);
	 * preparedStatement.executeUpdate(); System.out.println(inputStream); //
	 * conn.commit(); // conn.setAutoCommit(true);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); System.out.println("Error");
	 * 
	 * } System.out.println("returnedr"); return true; }
	 */
}
