package com.revature.trms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.trms.forms.SubmissionForm;
import com.revature.trms.struct.Token;
import com.revature.util.ConnectionFactory;

public class AdminFormDAO {
	private static final String GET_MY_SUBMISSIONS = "select *" + " from records_tuition_reimbursement"
			+ " where supervisor_id = ? or department_head_id = ? or benefits_coordinator_id = ?";

	private static final String GET_MY_ATTACHMENTS = "select *" + " from records_tuition_reimbursement_attachments"
			+ " where records_tuition_reimbursement_id = ?";
	private static final String GET_MY_MESSAGES = "select rm.*" + " from reimbursement_messages rm"
			+ " where reimbursement_records_id = ?";
	private static final String GET_EMPLOYEE_INFO = "select e2.*" + " from employee e2"
			+ " inner join reimbursement_tracker on reimbursement_tracker.employee_id = e2.id"
			+ " inner join records_tuition_reimbursement on records_tuition_reimbursement.id = reimbursement_tracker.reimbursement_records_id"
			+ " where records_tuition_reimbursement.id = ?";

	public static List<SubmissionForm> getAdminSubmissions(Token token) {
		List<SubmissionForm> SubFormList = new ArrayList<SubmissionForm>();

		Connection conn = ConnectionFactory.getConnection();

		int employeeID = AuthenticationDAO.getEmployeeID(token);
		System.out.println(employeeID);
		if (!(employeeID >= 0)) {
		}

		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(GET_MY_SUBMISSIONS);
			preparedStatement.setInt(1, employeeID);
			preparedStatement.setInt(2, employeeID);
			preparedStatement.setInt(3, employeeID);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int formID = rs.getInt("id");

				SubmissionForm SubForm = new SubmissionForm();

				PreparedStatement preparedStatementEmpoyeeInfo = conn.prepareStatement(GET_EMPLOYEE_INFO);
				preparedStatementEmpoyeeInfo.setInt(1, formID);
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
