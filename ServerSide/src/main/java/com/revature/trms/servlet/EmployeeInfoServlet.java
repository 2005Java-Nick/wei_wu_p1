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
import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.forms.Employee;
import com.revature.trms.struct.Token;

/**
 * Servlet implementation class EmployeeInfoServlet
 */
public class EmployeeInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeInfoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Token token = new Token();
		token.token = request.getParameter("Token");
		token.token = token.token.replace(' ', '+');
		System.out.println(token.token);

		Employee emp = EmployeeDAO.getMyEmployee(token);

		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> theMap = new LinkedHashMap<String, Object>();
		System.out.println("This My Employee Ran");
		theMap.put("EmployeeInfo", emp);
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
