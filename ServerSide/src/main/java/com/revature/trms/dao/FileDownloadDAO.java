package com.revature.trms.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revature.trms.struct.Token;
import com.revature.util.ConnectionFactory;

public class FileDownloadDAO {
	private static final String FILE_DOWNLOAD = "Select * from records_tuition_reimbursement_attachments"
			+ " where id = ? AND file_name = ?";

	public static InputStream downloadFile(int fileID, String fileName, Token token) {

		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement preparedStatement;

		if (AuthenticationDAO.getEmployeeID(token) < 0) {
			return null;
		}
		try {

			preparedStatement = conn.prepareStatement(FILE_DOWNLOAD);
			preparedStatement.setInt(1, fileID);
			preparedStatement.setString(2, fileName);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				System.out.println("Entered Next");
				InputStream inputStream = rs.getBinaryStream("file_data");
				System.out.println(inputStream.available());
				byte[] imgBytes = rs.getBytes("file_data");
				System.out.println(imgBytes);

				return inputStream;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("NUMLL");
			return null;

		}

		return null;
	}
}
