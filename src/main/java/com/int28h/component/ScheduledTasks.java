package com.int28h.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    
    Connection connection = null;
    
    ArrayList<String> emails = new ArrayList<>();

	@Scheduled(cron = "0 0 17 * * MON-FRI")	
    public void sendEmails() {
    	connect();
		getEmails();		
		
		if(!emails.isEmpty()){
			for(String email : emails) {
				RestTemplate restTemplate = new RestTemplate();
		        String emailSend = restTemplate.getForObject("http://localhost:8080/sendMail?email=" + email, String.class);
		        log.info(emailSend);
			}
			emails.clear();
		}
        
        disconnect();
    }
	
	private void getEmails() {
		String sqlSelect = "SELECT email FROM emails";
		
		try (Connection connection = this.connection;
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sqlSelect)) {
			while(rs.next()) {
				emails.add(rs.getString("email"));
			}
			log.info("Emails were received from the database.");
		} catch (SQLException e) {			
			log.error("Emails weren't received from the database.");
			e.printStackTrace();
		}
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