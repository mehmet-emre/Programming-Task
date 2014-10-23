package programmingTask;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {
	/*
	 * SoundCloud API
	 */
	public static String SC_CLIENT_ID;
	public static String SC_CLIENT_SECRET_ID;
	public static String SC_REDIRECT_URI_STRING;
	public static String SC_TOKEN_STRING;
	public static String SC_LOGIN_USERNAME;
	public static String SC_LOGIN_PASSWORD;
	public static String SC_FILE_NAME;
	/*
	 * AWS Java API
	 */
	public static String AWS_BUCKET_NAME;
	
	public static void loadProperties() throws IOException{
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream("config.properties");
			prop.load(input);
	 
			SC_CLIENT_ID = prop.getProperty("SC_CLIENT_ID");
			SC_CLIENT_SECRET_ID= prop.getProperty("SC_CLIENT_SECRET_ID");
			SC_REDIRECT_URI_STRING = prop.getProperty("SC_REDIRECT_URI_STRING");
			SC_TOKEN_STRING = prop.getProperty("SC_TOKEN_STRING");
			SC_LOGIN_USERNAME = prop.getProperty("SC_LOGIN_USERNAME");
			SC_LOGIN_PASSWORD = prop.getProperty("SC_LOGIN_PASSWORD");
			SC_FILE_NAME = prop.getProperty("SC_FILE_NAME");
			AWS_BUCKET_NAME = prop.getProperty("AWS_BUCKET_NAME");
	 
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
