package com.revature.trms.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

public class TuitionReimbursementForm extends FormsParent {

	private String eventLocation;
	private String eventDiscription;
	private String eventCost;
	private String eventType;
	private String justification;
	private String workTimeOff;
	private String gradingFormat;
	private String cutOffGrade;
	private String eventDate;
	private String optionalComments;

	private List<Part> files;

	public TuitionReimbursementForm() {
		super();
		files = new ArrayList<Part>();
	}

	public List<Part> getFiles() {
		return files;
	}

	public void addFile(Part data) {
		files.add(data);
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public String getEventDiscription() {
		return eventDiscription;
	}

	public void setEventDiscription(String eventDiscription) {
		this.eventDiscription = eventDiscription;
	}

	public String getEventCost() {
		return eventCost;
	}

	public void setEventCost(String eventCost) {
		this.eventCost = eventCost;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getWorkTimeOff() {
		return workTimeOff;
	}

	public void setWorkTimeOff(String workTimeOff) {
		this.workTimeOff = workTimeOff;
	}

	public String getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public String getCutOffGrade() {
		return cutOffGrade;
	}

	public void setCutOffGrade(String cutOffGrade) {
		this.cutOffGrade = cutOffGrade;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getOptionalComments() {
		return optionalComments;
	}

	public void setOptionalComments(String optionalComments) {
		this.optionalComments = optionalComments;
	}
}
