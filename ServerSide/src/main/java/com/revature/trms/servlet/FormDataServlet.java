package com.revature.trms.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.revature.trms.dao.FormUploadDAO;
import com.revature.trms.forms.TuitionReimbursementForm;
import com.revature.trms.struct.Token;

/**
 * Servlet implementation class FormDataServlet
 */

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 40, // 2MB
		maxFileSize = 1024 * 1024 * 50, // 10MB
		maxRequestSize = 1024 * 1024 * 700) // 50MB
public class FormDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FormDataServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		 * response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		 * response.setContentType("APPLICATION/OCTET-STREAM");
		 * response.setHeader("Content-disposition", "attachment; filename=sample.pdf");
		 * 
		 * System.out.println("Get Ran");
		 * 
		 * byte[] buf = new byte[100 * 1024]; InputStream input =
		 * FileDownloadDAO.uploadFile(); OutputStream output =
		 * response.getOutputStream(); response.setContentLength(input.available());
		 * 
		 * for (int length = 0; (length = input.read(buf)) > 0;) { output.write(buf, 0,
		 * length); System.out.println(length); }
		 */
		// byte[] file = FileDownloadDAO.uploadFile();
		// OutputStream os = response.getOutputStream();

		// FileInputStream in = new FileInputStream(file);
		// byte[] bytes = new byte[1024];
		// int bytesRead;
		// byte[] buf = new byte[100 * 1024];
		// int len;

		// response.getOutputStream().write(bytes, 0, bytesRead);

		// String contentType = this.getServletContext().getMimeType(file);
		// System.out.println("Content Type: " + contentType);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Token aToken = new Token();
		// gets values of text fields
		TuitionReimbursementForm aForm = new TuitionReimbursementForm();
		aForm.setFirstName(request.getParameter("firstName"));
		aForm.setMidName(request.getParameter("midName"));
		aForm.setLastName(request.getParameter("lastName"));
		aForm.setAddress(request.getParameter("address"));
		aForm.setAddressTwo(request.getParameter("addressTwo"));
		aForm.setCity(request.getParameter("city"));
		aForm.setState(request.getParameter("state"));
		aForm.setZip(request.getParameter("zip"));
		aForm.setEventLocation(request.getParameter("eventLocation"));
		aForm.setEventDiscription(request.getParameter("eventDiscription"));
		aForm.setEventCost(request.getParameter("eventCost"));
		aForm.setEventType(request.getParameter("eventType"));
		aForm.setJustification(request.getParameter("justification"));
		aForm.setWorkTimeOff(request.getParameter("workTimeOff"));
		aForm.setGradingFormat(request.getParameter("gradingFormat"));
		aForm.setCutOffGrade(request.getParameter("cutOffGrade"));
		aForm.setEventDate(request.getParameter("eventDate"));
		aForm.setOptionalComments(request.getParameter("optionalComments"));
		aToken.token = request.getParameter("Token");
		System.out.println(aToken.token);
		Collection<Part> fileParts = request.getParts();

		for (Part part : fileParts) {
			if (part.getSubmittedFileName() != null) {
				aForm.addFile(part);
				System.out.println(aForm.getFiles().get(0).getSubmittedFileName());
			}
		}
		FormUploadDAO.uploadForm(aForm, aToken);
		/*
		 * 
		 * InputStream inputStream = null; // input stream of the upload file
		 * 
		 * // obtains the upload file part in this multipart request Part filePart =
		 * request.getPart("File1"); if (filePart != null) { // prints out some
		 * information for debugging System.out.println(filePart.getName());
		 * System.out.println(filePart.getSize());
		 * System.out.println(filePart.getContentType());
		 * System.out.println(filePart.getSubmittedFileName());
		 * 
		 * // obtains input stream of the upload file inputStream =
		 * filePart.getInputStream(); } FormUploadDAO.uploadFile(inputStream);
		 */
	}

}
