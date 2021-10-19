package com.qa.rough;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.TestAutomation.StepDef.TestBase;

public class Parent extends TestBase {

	public static void main(String[] args) {
		
		
		
		System.out.println(isPasswordReset("junida.keckler@slc01.com"));
		
		
	}
	
	public static boolean isPasswordReset(String email) {
		String dbName = "LMS_interface";
		String user= "lms_user"; 
		String pass= "ST20JP*otty";

		String dbURL = null;
		boolean bFlag = false;
		if (CONFIG.getProperty("appEnv").equals("QA")) {
			dbURL = "jdbc:sqlserver://2012QASQLPROD01.slc.local:1433;databasename=" + dbName
					+ ";integratedSecurity=false";
		} else if (CONFIG.getProperty("appEnv").equals("DEV")) {
			dbURL = "jdbc:sqlserver://QTSDEVSQL01.slc.local:1433;databasename=" + dbName + ";integratedSecurity=false";
		}
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dbURL, user, pass);
			stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT COUNT(*) as C1 FROM [LMS_interface].[dbo].[UserTable] WHERE Cust_ID IN (select Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email ='"
							+ clientData.get("Email") + "');");

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					if (rs.getString(i).equals("0")) {
						bFlag = false;
						break;
					} else if (rs.getString(i).equals("1")) {
						log.info("User is registered, deleting from database to re-register the user");
						connection.createStatement().executeUpdate(
								"DELETE FROM [LMS_interface].[dbo].[UserTable] where Cust_ID IN (select Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email ='"
										+ clientData.get("Email") + "');");

						log.info("User is successfully deleted from DataBase");
						bFlag = false;
						break;
					} else {
						bFlag = true;
						break;
					}

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException exp) {
			exp.printStackTrace();
		}
		return bFlag;
	}
	

}
