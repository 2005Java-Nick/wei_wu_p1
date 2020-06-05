package com.revature.trms.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.dao.FileDownloadDAO;
import com.revature.trms.struct.Token;

/**
 * Servlet implementation class FileDownloadServlet
 */
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileDownloadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-disposition", "attachment; filename=sample.pdf");

		Token token = new Token();
		token.token = request.getParameter("Token");
		token.token = token.token.replace(' ', '+');
		int fileID = Integer.parseInt(request.getParameter("FileID"));
		String fileName = request.getParameter("FileName");
		System.out.println(fileID + "|||" + fileName);

		byte[] buf = new byte[100 * 1024];
		InputStream input = FileDownloadDAO.downloadFile(fileID, fileName, token);
		OutputStream output = response.getOutputStream();
		response.setContentLength(input.available());

		for (int length = 0; (length = input.read(buf)) > 0;) {
			output.write(buf, 0, length);
			System.out.println(length);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
