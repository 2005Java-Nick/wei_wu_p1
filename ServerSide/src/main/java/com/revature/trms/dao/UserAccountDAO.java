package com.revature.trms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revature.security.Encryption;
import com.revature.util.ConnectionFactory;

public class UserAccountDAO {
	private static final String CREATE_UserACCOUNT = "INSERT INTO user_account (username,user_password) values (?,?)";

	public static boolean createUserAccount(String username, String password) {
		String encryptedPassword = Encryption.encryptString(password);

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(CREATE_UserACCOUNT);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, encryptedPassword);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	private static final String GET_UserPASSWORD = "SELECT user_password FROM user_account WHERE user_account.username = ?";

	public static boolean checkPassword(String username, String password) {

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement;
		String storedPassword = null;
		try {
			preparedStatement = conn.prepareStatement(GET_UserPASSWORD);
			preparedStatement.setString(1, username);
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				storedPassword = result.getString("user_password");
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

	private static final String CHANGE_USER_PASSWORD = "UPDATE user_account SET user_password = ? WHERE id = ?";

	public static void changeUserPassword(int userAccountID, String password) {
		String encryptedPassword = Encryption.encryptString(password);

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(CHANGE_USER_PASSWORD);
			preparedStatement.setString(1, encryptedPassword);
			preparedStatement.setInt(2, userAccountID);
			preparedStatement.executeUpdate();

		} catch (Exception e) {

		}

	}

	private static final String GET_UserACCOUNT_ID = "SELECT id FROM user_account WHERE user_account.username = ?";

	public static int getUserAccount(String username, String password) {
		int accountID = -1;

		if (checkPassword(username, password)) {
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement preparedStatement;
			try {
				preparedStatement = conn.prepareStatement(GET_UserACCOUNT_ID);
				preparedStatement.setString(1, username);

				ResultSet result = preparedStatement.executeQuery();

				if (result.next()) {

					accountID = result.getInt("id");

				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return accountID;
	}

	private static final String USER_EXIST = "SELECT * FROM user_account WHERE user_account.username = ?";

	public static boolean checkUserExist(String username) {

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(USER_EXIST);
			preparedStatement.setString(1, username);
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				return true;
			}

		} catch (Exception e) {
			return false;
		}

		return false;
	}
}
