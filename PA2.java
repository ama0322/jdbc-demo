/**
 * WI20 JDBC Demo
 */

/**
 * This Java program exemplifies the basic usage of JDBC.
 * Requirements:
 * (1) JDK 1.6+.
 * (2) SQLite3.
 * (3) SQLite3 JDBC jar (https://bitbucket.org/xerial/sqlitejdbc/downloads/sqlite-jdbc-3.8.7.jar).
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PA2 {
	public static void main(String[] args) {
		Connection conn = null; // Database connection.
		try {
			// Load the JDBC class.
			Class.forName("org.sqlite.JDBC");
			// Get the connection to the database.
			// - "jdbc" : JDBC connection name prefix.
			// - "sqlite" : The concrete database implementation
			// (e.g., sqlserver, postgresql).
			// - "pa2.db" : The name of the database. In this project,
			// we use a local database named "pa2.db". This can also
			// be a remote database name.
			conn = DriverManager.getConnection("jdbc:sqlite:pa2.db");
			System.out.println("Opened database successfully.");
			
			System.out.println("Example 1");

			Statement stmt = conn.createStatement();
			
			ResultSet rset = stmt.executeQuery("sElEcT * FroM Flight LIMIT 5;");

			while(rset.next()){
				System.out.println(rset.getString("airline") +  "   " + rset.getString("origin"));
			}

			System.out.println("---------------------");
			System.out.println("Example 2");

			PreparedStatement pstmt = conn.prepareStatement(
				"SELECT * FROM Flight WHERE Origin = ?;");
			
			pstmt.setString(1, "Barcelona, Spain");
			rset = pstmt.executeQuery();

			while(rset.next()){
				System.out.println(rset.getString(1) + ' ' + rset.getString(2));
			}

			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException("There was a runtime problem!", e);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				throw new RuntimeException("Cannot close the connection!", e);
			}
		}
	}
	
}
