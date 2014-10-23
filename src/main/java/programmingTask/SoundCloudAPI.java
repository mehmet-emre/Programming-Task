package programmingTask;

import static programmingTask.Constants.SC_CLIENT_ID;
import static programmingTask.Constants.SC_CLIENT_SECRET_ID;
import static programmingTask.Constants.SC_FILE_NAME;
import static programmingTask.Constants.SC_LOGIN_PASSWORD;
import static programmingTask.Constants.SC_LOGIN_USERNAME;
import static programmingTask.Constants.SC_REDIRECT_URI_STRING;
import static programmingTask.Constants.SC_TOKEN_STRING;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.soundcloud.api.ApiWrapper;
import com.soundcloud.api.Request;
import com.soundcloud.api.Token;

public class SoundCloudAPI {
	
	private ApiWrapper wrapper;
	private JSONArray trackList;
	
	public JSONArray getTrackList() {
		return trackList;
	}


	public SoundCloudAPI(){
		super();
	}
	
	public void getTheTrackList(String username) throws URISyntaxException, IOException, JSONException  {

		wrapper = new ApiWrapper(SC_CLIENT_ID, SC_CLIENT_SECRET_ID, 
				new URI(SC_REDIRECT_URI_STRING), new Token(SC_TOKEN_STRING, null));
		wrapper.login(SC_LOGIN_USERNAME, SC_LOGIN_PASSWORD);

		HttpResponse response = wrapper.get(Request.to("/users/"+username+"/tracks"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		StringBuilder builder = new StringBuilder();
		for (String line = null; (line = reader.readLine()) != null;) {
		    builder.append(line).append("\n");
		}
		
		JSONTokener tokener = new JSONTokener(builder.toString());
		trackList = new JSONArray(tokener);		
	}

	public void downloadTrack(String download_url) throws IOException, JSONException{
			
		HttpResponse resp = wrapper.get(new Request(download_url));
		BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(), "UTF-8"));
		StringBuilder builder = new StringBuilder();
		for (String line = null; (line = reader.readLine()) != null;) {
		    builder.append(line).append("\n");
		}
		JSONTokener tokener = new JSONTokener(builder.toString());
		JSONObject jsonObj = new JSONObject(tokener);
		
		String location = jsonObj.getString("location");
		
		BufferedInputStream in = null;
	    FileOutputStream fout = null;
	    try {
	        in = new BufferedInputStream(new URL(location).openStream());
	        fout = new FileOutputStream(SC_FILE_NAME);
	
	        final byte data[] = new byte[1024];
	        int count;
	        while ((count = in.read(data, 0, 1024)) != -1) {
	            fout.write(data, 0, count);
	        }
	    } finally {
	        if (in != null) {
	            in.close();
	        }
	        if (fout != null) {
	            fout.close();
	        }
	    }

	}
	
}
