package com.TestAutomation.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader {
	
	public Properties getProperties(String propPath) throws IOException{
		
		FileInputStream inputstream=null;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(propPath));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
}
