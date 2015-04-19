package ch.unige.idsi.cultweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import ch.unige.idsi.cultweb.model.Museum;
import ch.unige.idsi.cultweb.model.Place.Infrastructure;

public class ApiRequest {

	private String MUSEUM_API = "http://ge.ch/ags1/rest/services/Culture/MapServer/1/query?text=Mus%C3%A9e&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=*&f=pjson";
	private String CINEMA_API = "http://ge.ch/ags1/rest/services/Culture/MapServer/1/query?text=Cin%C3%A9ma&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=*&f=pjson";
	
	private HashMap<Long, ArrayList<JSONArray>> moreInfo;
	
	ApiRequest() {
		
		moreInfo = new HashMap<Long, ArrayList<JSONArray>>();
		
		JSONArray list = new JSONArray();
		list.add("tg.com");
		
		moreInfo.put((long) 191, list);
	}
	
	public JSONArray getMuseums() throws IOException {
		
		JSONObject json = this.sendGet(MUSEUM_API);
		
		JSONArray arr = (JSONArray) json.get("features");
		
		return arr;
	}
	
	public JSONArray getCinemas() throws IOException {
		
		JSONObject json = this.sendGet(CINEMA_API);
		
		JSONArray arr = (JSONArray) json.get("features");
		
		return arr;
	}
	
	public JSONObject getInfo(String objectId, Infrastructure infrastructure) throws IOException {
		
		String api = CINEMA_API;
		if(!infrastructure.equals(Infrastructure.CINEMA)) {
			api = MUSEUM_API;
		}
			
		long oId = Long.valueOf(objectId);
		
		JSONObject res = new JSONObject();
		
		JSONObject json = this.sendGet(api);

		JSONArray arr = (JSONArray) json.get("features");
		
		int i = 0;
		int length = arr.size();
		
		for (; i < length; i++) {
			
			JSONObject obj = (JSONObject) arr.get(i);
			JSONObject attributes = (JSONObject) obj.get("attributes");
			
			JSONObject geometry = (JSONObject) obj.get("geometry");
			
			attributes.put("latitude", geometry.get("x"));
			attributes.put("longitude", geometry.get("y"));
			
			long id = (((Long) attributes.get("ID_INFRASTRUCTURE")).intValue());
		
			// Add CultWeb data to the Object
			attributes.put("links", moreInfo.get(id));
			
			if(id == oId) {
				res = attributes;
			}
		}
		
		return res;
	}
	
	private JSONObject sendGet(String url) throws IOException {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
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
		in.close();

		JSONObject o = (JSONObject) JSONValue.parse(response.toString());
		return o;
	}
}
