package com.int28h.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.int28h.component.ScheduledTasks;

@RestController
public class DatabaseController {
private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    
    Connection connection = null;
    
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
