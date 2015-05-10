package ch.unige.idsi.cultapp.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import ch.unige.idsi.cultapp.listener.OnApiResult;

import android.os.AsyncTask;

public class ApiThread extends AsyncTask<String, Void, JSONObject> {
	
	private OnApiResult delegate = null;
	private String url;
	
	public ApiThread(String url, OnApiResult delegate) {
		this.url = url;
		this.delegate = delegate;
	}

	protected JSONObject doInBackground(String... urls) {
		
		JSONObject res = new JSONObject();
		
		try {
			
			// Default error
			res.put("error", "NO_INTERNET_ACCESS");
			
			url = url.replace(" ", "%20");

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			res = new JSONObject(response.toString());
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	protected void onPostExecute(JSONObject json) {
		try {
			delegate.processFinish(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}