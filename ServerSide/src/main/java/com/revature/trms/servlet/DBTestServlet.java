package com.revature.trms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.dao.DBConnectionTestDAO;

/**
 * Servlet implementation class DBTestServlet
 */
public class DBTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBTestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String message = "";
		if (DBConnectionTestDAO.Test() == true) {
			message = "Worked";
		} else {
			message = "Failed";
		}
		Map<String, Object> theMap = new LinkedHashMap<String, Object>();
		System.out.println("This My Submission Ran");
		theMap.put("SubForms", message);
		String jsonReturn = mapper.writeValueAsString(theMap);
		writer.write(jsonReturn);
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
