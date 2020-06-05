package com.revature.trms.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.security.Encryption;
import com.revature.trms.dao.LoginDAO;
import com.revature.trms.struct.Token;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

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
		 * TuitionReimbursementForm test = new TuitionReimbursementForm();
		 * test.setAddress("testtt"); test.setFirstName("firstnametest");
		 * TuitionReimbursementForm test2 = new TuitionReimbursementForm();
		 * test2.setAddress("testtt2222"); test2.setFirstName("firstnametest22222");
		 * List<TuitionReimbursementForm> oblist = new
		 * ArrayList<TuitionReimbursementForm>(); oblist.add(test); oblist.add(test2);
		 * 
		 * PrintWriter writer = response.getWriter(); ObjectMapper mapper = new
		 * ObjectMapper();
		 * 
		 * Map<String, Object> theMap = new LinkedHashMap<String, Object>();
		 * theMap.put("obj1", oblist); String jsonReturn =
		 * mapper.writeValueAsString(theMap); writer.write(jsonReturn);
		 */
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Token aToken = new Token();
		/*
		 * StringBuilder buffer = new StringBuilder(); BufferedReader reader =
		 * request.getReader(); String line; while ((line = reader.readLine()) != null)
		 * { buffer.append(line); } String data = buffer.toString();
		 * 
		 * // If the String is not empty, parses the payload into a map Map<String,
		 * Object> jsonMap = null; if (!data.isEmpty()) { ObjectMapper mapper = new
		 * ObjectMapper(); jsonMap = mapper.readValue(data, Map.class);
		 * 
		 * System.out.println("test1"); System.out.println(jsonMap.get("password"));
		 * System.out.println("test2"); }
		 */

		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		JSONObject jObj = new JSONObject(sb.toString());

		String username = (String) jObj.get("username");
		String password = (String) jObj.get("password");
//........................................................
		System.out.println(username);
		System.out.println(password);
		if (LoginDAO.checkPassword_NoEncryption(username, password)) {
			aToken.token = Encryption.encryptString(username + password);
			LoginDAO.setToken(username, password, aToken);
			System.out.println(aToken.token);
			PrintWriter writer = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();

			Map<String, Object> theMap = new LinkedHashMap<String, Object>();
			theMap.put("token", aToken.token);
			String jsonReturn = mapper.writeValueAsString(theMap);
			writer.write(jsonReturn);
			// mapper.writeValue(writer, theMap);

		} else {

		}

	}
}
