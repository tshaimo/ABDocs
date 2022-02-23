package za.co.africanbank.datascience.abdocs.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class properties {
	
	public properties(String value) {
	}

	public String read(String propertyName) throws IOException{
		try {
			Properties properties = new Properties();		
			InputStream inputStream = new FileInputStream("/tmp/config/ABDocs.properties");
			properties.load(inputStream);			
			return properties.get(propertyName).toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
