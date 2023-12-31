package com.shopdidong.admin.user.export;

import java.util.Date;
import java.util.List;

import org.supercsv.prefs.CsvPreference;
import org.supercsv.io.*;
import com.shopdidong.common.entity.User;

import java.io.IOException;
import java.text.*;
import jakarta.servlet.http.HttpServletResponse;

public class UserCsvExporter {
	
	public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd_HH-mm-ss");
		String timestamp = dateFormatter.format(new Date());
		String fileName = "users_" + timestamp + ".csv";
		
		response.setContentType("text/csv");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; fileName=" + fileName;
		response.setHeader(headerKey, headerValue);
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), 
				CsvPreference.STANDARD_PREFERENCE);
		
		String[] csvHeader = {"User ID", "E-mail", "First Name", 
				"Last Name", "Roles", "Enabled"};
		String[] fieldMapping = {"Id", "email", "firstName", 
				"lastName", "roles", "enabled"};
		
		csvWriter.writeHeader(csvHeader);

		for (User user : listUsers) {
			csvWriter.write(user, fieldMapping);
		}	
		
		csvWriter.close();
	}
}
