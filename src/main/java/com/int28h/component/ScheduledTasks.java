package com.int28h.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    
    Connection connection = null;

    //@Scheduled("0 0 17 * * MON-FRI")
	@Scheduled(fixedRate = 10000)
    public void sendEmails() {
		connect();
		
		
		RestTemplate restTemplate = new RestTemplate();
        String emailSendResult = restTemplate.getForObject("http://localhost:8080/sendMail?email=" + email, String.class);
        log.info(emailSendResult);
        
        try {
        	if (connection != null) {
                connection.close();
                log.info("Connection to SQLite has been closed.");
            }
        } catch (SQLException ex) {
        	log.info("Connection to SQLite hasn't been closed.");
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
}