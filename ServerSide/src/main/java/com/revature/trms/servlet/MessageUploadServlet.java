package com.revature.trms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.dao.MessageUploadDAO;
import com.revature.trms.struct.Token;

/**
 * Servlet implementation class MessageUploadServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 40, // 2MB
		maxFileSize = 1024 * 1024 * 50, // 10MB
		maxRequestSize = 1024 * 1024 * 700) // 50MB
public class MessageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageUploadServlet() {
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
		/*
		 * StringBuilder sb = new StringBuilder(); BufferedReader br =
		 * request.getReader(); String str; while ((str = br.readLine()) != null) {
		 * sb.append(str); } System.out.println(sb.toString());
		 */
		/*
		 * response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		 * response.setHeader("Access-Control-Allow-Headers", "Content-Type"); String
		 * message = request.getParameter("Message"); System.out.println(message); Token
		 * token = new Token(); token.token = request.getParameter("Token");
		 * System.out.println(token.token);
		 */

		int formID = Integer.parseInt(request.getParameter("FormID"));
		String message = request.getParameter("Message");
		Token token = new Token();
		token.token = request.getParameter("Token");
		token.token.replace(' ', '+');
		MessageUploadDAO.UploadMessaget(formID, message, token);
		/*
		 * String message = request.getParameter("message");
		 * System.out.println(message); System.out.println(message); int formID =
		 * Integer.parseInt(request.getParameter("FormID")); Token token = new Token();
		 * token.token = request.getParameter("Token"); System.out.println(token.token);
		 * token.token = token.token.replace(' ', '+'); System.out.println(token.token);
		 * MessageUploadDAO.UploadMessaget(formID, message, token);
		 */
	}

}
