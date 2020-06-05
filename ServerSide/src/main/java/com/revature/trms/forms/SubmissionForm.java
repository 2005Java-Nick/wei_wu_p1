package com.revature.trms.forms;

import java.util.ArrayList;
import java.util.List;

public class SubmissionForm extends TuitionReimbursementForm {

	private int FormID;
	private List<String> messages;
	private List<Integer> fileID;
	private List<String> fileName;
	private String submissionDate;
	private String override_amount;
	private String override_reason;
	private boolean exceedsAvaliableAmount;
	private String supervisorApproval;
	private String departmentHeadAprroval;
	private String benefitsCoordApproval;
	private String submitterMessageUpdated;
	private String adminMessageUpdated;
	private String status;

	public SubmissionForm() {
		super();
		messages = new ArrayList<String>();
		fileID = new ArrayList<Integer>();
		fileName = new ArrayList<String>();
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages.add(messages);
	}

	public List<Integer> getFileID() {
		return fileID;
	}

	public void setFileID(Integer fileID) {
		this.fileID.add(fileID);
	}

	public List<String> getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName.add(fileName);
	}

	public String getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getOverride_amount() {
		return override_amount;
	}

	public void setOverride_amount(String override_amount) {
		this.override_amount = override_amount;
	}

	public String getOverride_reason() {
		return override_reason;
	}

	public void setOverride_reason(String override_reason) {
		this.override_reason = override_reason;
	}

	public boolean isExceedsAvaliableAmount() {
		return exceedsAvaliableAmount;
	}

	public void setExceedsAvaliableAmount(boolean exceedsAvaliableAmount) {
		this.exceedsAvaliableAmount = exceedsAvaliableAmount;
	}

	public String getSupervisorApproval() {
		return supervisorApproval;
	}

	public void setSupervisorApproval(String supervisorApproval) {
		this.supervisorApproval = supervisorApproval;
	}

	public String getDepartmentHeadAprroval() {
		return departmentHeadAprroval;
	}

	public void setDepartmentHeadAprroval(String departmentHeadAprroval) {
		this.departmentHeadAprroval = departmentHeadAprroval;
	}

	public String getBenefitsCoordApproval() {
		return benefitsCoordApproval;
	}

	public void setBenefitsCoordApproval(String benefitsCoordApproval) {
		this.benefitsCoordApproval = benefitsCoordApproval;
	}

	public String getSubmitterMessageUpdated() {
		return submitterMessageUpdated;
	}

	public void setSubmitterMessageUpdated(String submitterMessageUpdated) {
		this.submitterMessageUpdated = submitterMessageUpdated;
	}

	public String getAdminMessageUpdated() {
		return adminMessageUpdated;
	}

	public void setAdminMessageUpdated(String adminMessageUpdated) {
		this.adminMessageUpdated = adminMessageUpdated;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getFormID() {
		return FormID;
	}

	public void setFormID(int formID) {
		FormID = formID;
	}

}
