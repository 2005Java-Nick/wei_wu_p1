package com.revature.trms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.dao.ApprovalDAO;
import com.revature.trms.struct.Token;

/**
 * Servlet implementation class ApprovalServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 40, // 2MB
		maxFileSize = 1024 * 1024 * 50, // 10MB
		maxRequestSize = 1024 * 1024 * 700) // 50MB
public class ApprovalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApprovalServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int formID = Integer.parseInt(request.getParameter("FormID"));
		String approval = request.getParameter("Approval");
		Token token = new Token();
		token.token = request.getParameter("Token");
		token.token.replace(' ', '+');
		ApprovalDAO.Approval(formID, approval, token);
	}

}
