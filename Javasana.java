/**
 * 
 */
package asana;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Christopher Downard
 *
 */
public class Javasana {
	public String apiVersion = "1.0";
	public String url = "https://app.asana.com/api/";
	public String apiUrl = url + apiVersion;
	public String apiKey;
	public String bauth;
	// Lists for holding the workspaces, projects, tasks
	public ArrayList<JSONObject> workspaces;
	public ArrayList<JSONObject> projects;
	
	// Constructor
	public Javasana(String key) {
		apiKey = key;
		bauth = basicAuth(apiKey);
		workspaces = new ArrayList<JSONObject>();
		projects = new ArrayList<JSONObject>();
	}

	// All methods related to Workspaces
	public JSONObject getWorkspaces() {
		String url = apiUrl + "/workspaces";
		String result = getData(url);
		
		JSONObject json = convertToJSON(result);
		
		// update the workspaces field
		try {
			JSONArray data = json.getJSONArray("data");
						
			for (int i = 0; i < data.length(); i++) {
				String d = data.get(i).toString();
				workspaces.add(new JSONObject(d));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		
		return json;
	}
	
	public JSONObject getWorkspaceRecord(String workspaceId) {
		String url = apiUrl + "/workspaces/" + workspaceId;
		String result = getData(url);
		
		JSONObject json = convertToJSON(result);
		
		return json;
	}
	
	// All methods related to Projects
	public JSONObject getProjects() {		
		String url = apiUrl + "/projects";	
		String result = getData(url);
		//System.out.println("Asana returned the following: " + result);
		
		JSONObject json = convertToJSON(result);
		
		return json;
	}
	
	public JSONObject getProjectTasks(String projectId) {
		String url = apiUrl + "/projects/" + projectId + "/tasks";
		String result = getData(url);
		
		JSONObject json = convertToJSON(result);
		
		return json;
	}
	
	public JSONObject getProjectRecord(String projectId) {
		String url = apiUrl + "/projects/" + projectId;
		String result = getData(url);
		
		JSONObject json = convertToJSON(result);
		
		return json;
	}
	
	public JSONObject createProject() {
		
		JSONObject json = null;
		
		return json;
	}
	// All methods related to Users
			
	// Utility Methods
	private String getData(String RESTurl) {
		String data = null;
		try { 
			URL url = new URL(RESTurl);
			HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();
			
			urlConn.setRequestMethod("GET");
			urlConn.setAllowUserInteraction(false);
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty("Content-type", "text/json");
			urlConn.setRequestProperty("accept", "text/json");
			urlConn.setRequestProperty("authorization",  "Basic " + bauth);
						
			int responseCode = urlConn.getResponseCode();
			
			if (responseCode == 200) {
				// handle an OK response
				System.out.println("Response Code: 200");
				
				InputStream inStream = urlConn.getInputStream();
				data = convertStreamToString(inStream);
			} else {
				// handle all other responses
				System.out.println("Something has gone wrong. Response code was: " + responseCode);
				data = "ERROR:" + responseCode;
			}
			
		} catch (MalformedURLException e) {
			System.out.println("The url is not valid for listing a project's tasks: " + url.toString());
		} catch (IOException e) {
			System.out.println("An IOException has occurred: " + e.getMessage());
			e.printStackTrace();
		}
		
		return data;
	}

	private String basicAuth(String key) {
		key = key + ":";
		byte[] byteKey = key.getBytes();
		String auth = DatatypeConverter.printBase64Binary(byteKey);
		return auth;
	}
	
	private String convertStreamToString(InputStream inputStream) {

		try { 
			return new Scanner(inputStream).useDelimiter("\\A").next();
		} catch (NoSuchElementException e) {
			return "";
		}
	}
	
	private JSONObject convertToJSON(String result) {
		JSONObject json;
		try {
			json = new JSONObject(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			json = null;
		}
		return json;
	}
}
