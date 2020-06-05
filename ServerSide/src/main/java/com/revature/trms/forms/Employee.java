package com.revature.trms.forms;

import java.util.ArrayList;
import java.util.List;

public class Employee extends FormsParent {
	private List<Integer> permissionLevel;
	private String reimbursementAvaliable;
	private String reimbursementPending;

	public Employee() {
		super();
		permissionLevel = new ArrayList<Integer>();
	}

	public String getReimbursementAvaliable() {
		return reimbursementAvaliable;
	}

	public void setReimbursementAvaliable(String reimbursementAvaliable) {
		this.reimbursementAvaliable = reimbursementAvaliable;
	}

	public String getReimbursementPending() {
		return reimbursementPending;
	}

	public void setReimbursementPending(String reimbursementPending) {
		this.reimbursementPending = reimbursementPending;
	}

	public List<Integer> getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel.add(permissionLevel);
	}
}
