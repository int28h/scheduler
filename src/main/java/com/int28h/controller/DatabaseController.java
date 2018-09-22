package com.int28h.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.int28h.component.ScheduledTasks;

@RestController
public class DatabaseController {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
	Connection connection = null;
	
	@RequestMapping("/showEmails")
	public String showEmails() {
		StringBuilder emails = new StringBuilder();
		
		connect();
		String sql = "SELECT email FROM emails";
		
		try (Connection connection = this.connection;
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql)) {
			while(rs.next()) {
				emails.append(rs.getString("email")).append("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		disconnect();
		
		return emails.toString();
	}
	
	@RequestMapping("/deleteMail")
    	public String deleteMail(@RequestParam(value="email", defaultValue="null") String email) {
		connect();
    		String sql = "DELETE FROM emails WHERE email = ?";
    	
		try (Connection connection = this.connection;
		     PreparedStatement prStatement = connection.prepareStatement(sql)){
			prStatement.setString(1, email);
			prStatement.executeUpdate();
			return ("Email " + email + " was deleted from the database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		disconnect();
		
		return ("Email " + email + " wasn't deleted from the database.");
	}
	
	@RequestMapping("/addMail")
    	public String addMail(@RequestParam(value="email", defaultValue="null") String email) {
		connect();    	
    		String sql = "INSERT INTO emails (email) VALUES (?)";
    	
		try (Connection connection = this.connection;
		     PreparedStatement prStatement = connection.prepareStatement(sql)){
			prStatement.setString(1, email);
			prStatement.executeUpdate();
			return ("Email " + email + " was added to the database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		disconnect();
		
		return ("Email " + email + " was added to the database.");
	}

	private void connect() {
		try {
			String url = "jdbc:sqlite:C:/sqlite/test.db";
            		this.connection = DriverManager.getConnection(url);            
            		log.info("Connection to SQLite has been established.");
		} catch (SQLException e) {
			log.error("Connection to SQLite hasn't been established.");
		}
	}
	
	private void disconnect() {        
		try {
        	if (connection != null) {
                connection.close();
                log.info("Connection to SQLite has been closed.");
            }
        } catch (SQLException ex) {
        	log.error("Connection to SQLite hasn't been closed.");
        }
    }
}
