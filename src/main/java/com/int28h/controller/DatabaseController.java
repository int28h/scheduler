package com.int28h.controller;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.int28h.component.ScheduledTasks;

@RestController
public class DatabaseController {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
    Connection connection = null;
        
    /*@RequestMapping("/showEmailsJSON")
    public String showEmailsJSON() {
    	JsonArrayBuilder emails = Json.createArrayBuilder();
    	
		connect();    	
    	String sqlSelect = "SELECT email_id, email FROM emails";
		
		try (Connection connection = this.connection;
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sqlSelect)) {
			while(rs.next()) {
				JsonObjectBuilder email = Json.createObjectBuilder();
				email.add("email_id", Integer.toString(rs.getInt("email_id")));
				email.add("email", rs.getString("email"));
				emails.add(email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return emails.build().toString();
    }*/
	
	@RequestMapping("/showEmailsXML")
    public String showEmailsXML() throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		connect();    	
    	String sql = "SELECT email_id, email FROM emails";
		
		try (Connection connection = this.connection;
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql)) {
			while(rs.next()) {
				Element emails = doc.createElement("emails");
				doc.appendChild(emails);
				
				Element emailId = doc.createElement("emailId");
				emailId.appendChild(doc.createTextNode(Integer.toString(rs.getInt("email_id"))));
				emails.appendChild(emailId);
				
				Element email = doc.createElement("email");
				email.appendChild(doc.createTextNode(rs.getString("email")));
				emails.appendChild(emailId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		
		return writer.getBuffer().toString();
	}
	
	@RequestMapping("/deleteMail")
    public void deleteMail(@RequestParam(value="email", defaultValue="null") String email) {
		connect();    	
    	String sql = "DELETE FROM emails WHERE email = ?";
		try (Connection connection = this.connection;
				PreparedStatement prStatement = connection.prepareStatement(sql)){
					prStatement.setString(1, email);
					prStatement.executeUpdate();
				} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/addMail")
    public void addMail(@RequestParam(value="email", defaultValue="null") String email) {
		connect();    	
    	String sql = "INSERT INTO emails (email) VALUES (?)";
		try (Connection connection = this.connection;
				PreparedStatement prStatement = connection.prepareStatement(sql)){
					prStatement.setString(1, email);
					prStatement.executeUpdate();
				} catch (SQLException e) {
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
}
