package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {

	Properties properties;

	String path = "config.properties";

	//constructor
	public ReadConfig() throws IOException 
	{	
		properties = new Properties();
		//to open config .properties file in input mode and load the file
		FileInputStream  fis = new FileInputStream(path);
		properties.load(fis);
	}

	public String getBrowser()
	{
		String value = properties.getProperty("browser");

		if(value!=null)
			return value;
		else
			throw new RuntimeException("url not specified in config file.");
		
	}

}