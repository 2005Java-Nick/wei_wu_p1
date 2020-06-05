package com.revature.trms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.security.Encryption;
import com.revature.trms.struct.Token;
import com.revature.util.ConnectionFactory;

public class LoginDAO {
	private static final String GET_UserPASSWORD = "SELECT userpass FROM user_account WHERE username = ?";

	/**
	 * Checks if username and password is correct
	 * 
	 * @param username
	 * @param password
	 * @return true if correct login details, false otherwise
	 */
	public static boolean checkPassword(String username, String password) {

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement;
		String storedPassword = null;
		try {
			preparedStatement = conn.prepareStatement(GET_UserPASSWORD);
			preparedStatement.setString(1, username);
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				storedPassword = result.getString("userpass");
				if (Encryption.authenticate(password, storedPassword)) {

					return true;
				} else {
					return false;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}

	/**
	 * Checks if username and password is correct. This method is for non-encrypted
	 * passwords
	 * 
	 * @param username
	 * @param password
	 * @return true if correct login details, false otherwise
	 */
	public static boolean checkPassword_NoEncryption(String username, String password) {

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement;
		String storedPassword = null;
		try {
			preparedStatement = conn.prepareStatement(GET_UserPASSWORD);
			preparedStatement.setString(1, username);
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				storedPassword = result.getString("userpass");
				if (password.equals(storedPassword)) {
					return true;
				} else {
					return false;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}

	private static final String ADD_TOKEN = "INSERT INTO session_token" + " "
			+ "(user_id, user_token) values ((SELECT id FROM user_account WHERE username = ? and userpass = ?),?)";

	private static final String UPDATE_TOKEN = "UPDATE session_token set user_token = ? where user_id = (SELECT id FROM user_account WHERE username = ? and userpass = ?)";

	public static boolean setToken(String username, String password, Token token) {

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement;
		try {

			preparedStatement = conn.prepareStatement(ADD_TOKEN);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, token.token);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			try {
				preparedStatement = conn.prepareStatement(UPDATE_TOKEN);
				preparedStatement.setString(1, token.token);
				preparedStatement.setString(2, username);
				preparedStatement.setString(3, password);
				preparedStatement.executeUpdate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return true;

	}

}
