package com.revature.trms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.forms.SubmissionForm;
import com.revature.trms.struct.Token;
import com.revature.util.ConnectionFactory;

public class MySubmissionsDAO {
	private static final String GET_MY_SUBMISSIONS = "select t.*" + " from records_tuition_reimbursement t"
			+ " inner join reimbursement_tracker on reimbursement_records_id = t.id"
			+ " inner join employee on employee.id = reimbursement_tracker.employee_id"
			+ " inner join user_account on user_account.employee_id = employee.id"
			+ " inner join session_token on session_token.user_id = user_account.id"
			+ " where session_token.user_token = ?";

	private static final String GET_MY_ATTACHMENTS = "select *" + " from records_tuition_reimbursement_attachments"
			+ " where records_tuition_reimbursement_id = ?";
	private static final String GET_MY_MESSAGES = "select rm.*" + " from reimbursement_messages rm"
			+ " where reimbursement_records_id = ?";
	private static final String GET_EMPLOYEE_INFO = "select *" + " from employee"
			+ " inner join user_account on user_account.employee_id = employee.id"
			+ " inner join session_token on session_token.user_id = user_account.id"
			+ " where session_token.user_token = ?";

	public static List<SubmissionForm> getMySubmissions(Token token) {
		List<SubmissionForm> SubFormList = new ArrayList<SubmissionForm>();

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(GET_MY_SUBMISSIONS);
			preparedStatement.setString(1, token.token);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int formID = rs.getInt("id");

				SubmissionForm SubForm = new SubmissionForm();

				PreparedStatement preparedStatementEmpoyeeInfo = conn.prepareStatement(GET_EMPLOYEE_INFO);
				preparedStatementEmpoyeeInfo.setString(1, token.token);
				ResultSet rsEmpoyeeInfo = preparedStatementEmpoyeeInfo.executeQuery();
				if (rsEmpoyeeInfo.next()) {
					SubForm.setFirstName(rsEmpoyeeInfo.getString("first_name"));
					SubForm.setMidName(rsEmpoyeeInfo.getString("mid_name"));
					SubForm.setLastName(rsEmpoyeeInfo.getString("last_name"));
					SubForm.setEmail(rsEmpoyeeInfo.getString("email"));
					SubForm.setPhone(rsEmpoyeeInfo.getString("phone"));
					SubForm.setAddress(rsEmpoyeeInfo.getString("address"));
					SubForm.setAddressTwo(rsEmpoyeeInfo.getString("addresstwo"));
					SubForm.setCity(rsEmpoyeeInfo.getString("city"));
					SubForm.setState(rsEmpoyeeInfo.getString("state"));
					SubForm.setZip(rsEmpoyeeInfo.getString("zip"));
				}

				SubForm.setFormID(formID);
				SubForm.setSubmissionDate(rs.getString("submission_date"));
				SubForm.setEventDate(rs.getString("event_date"));
				SubForm.setEventLocation(rs.getString("event_location"));
				SubForm.setEventDiscription(rs.getString("event_discription"));
				SubForm.setEventCost(rs.getString("event_cost"));
				SubForm.setGradingFormat(rs.getString("event_grading_format"));
				SubForm.setCutOffGrade(rs.getString("event_cutOff_grade"));
				SubForm.setEventType(rs.getString("event_type"));
				SubForm.setJustification(rs.getString("event_justification"));
				SubForm.setWorkTimeOff(rs.getString("estimated_timeoff"));
				SubForm.setOptionalComments(rs.getString("optional_comments"));
				SubForm.setOverride_amount(rs.getString("override_amount"));
				SubForm.setOverride_amount(rs.getString("override_amount"));
				SubForm.setExceedsAvaliableAmount(rs.getBoolean("exceeds_avaliable_amount"));
				SubForm.setSupervisorApproval(rs.getString("supervisor_approval"));
				SubForm.setDepartmentHeadAprroval(rs.getString("department_head_approval"));
				SubForm.setBenefitsCoordApproval(rs.getString("benefits_coordinator_approval"));
				SubForm.setSubmitterMessageUpdated(rs.getString("submitter_message_updated"));
				SubForm.setAdminMessageUpdated(rs.getString("admin_message_updated"));
				SubForm.setStatus(rs.getString("status"));

				PreparedStatement preparedStatement2 = conn.prepareStatement(GET_MY_ATTACHMENTS);
				preparedStatement2.setInt(1, formID);
				ResultSet rs2 = preparedStatement2.executeQuery();
				while (rs2.next()) {
					SubForm.setFileID(rs2.getInt("id"));
					SubForm.setFileName(rs2.getString("file_name"));
				}

				PreparedStatement preparedStatement3 = conn.prepareStatement(GET_MY_MESSAGES);
				preparedStatement3.setInt(1, formID);
				ResultSet rs3 = preparedStatement3.executeQuery();
				while (rs3.next()) {
					SubForm.setMessages(rs3.getString("message"));
				}
				SubFormList.add(SubForm);
			}
			return SubFormList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
}
