package ch.unige.idsi.cultweb.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import ch.unige.idsi.cultweb.model.Location;

/**
 * The role of DataRequest is to execute HTTP requests or read data stored in our cache filesystem
 * If LOCAL_ONLY is true, then it reads local data, otherwise it makes a request to the GE - API
 */
public class DataRequest {

	private String MUSEUM_API = "http://ge.ch/ags1/rest/services/Culture/MapServer/1/query?text=Mus%C3%A9e&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=*&f=pjson";
	private String CINEMA_API = "http://ge.ch/ags1/rest/services/Culture/MapServer/1/query?text=Cin%C3%A9ma&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=*&f=pjson";
	
	private boolean LOCAL_ONLY = true;
	
	/**
	 * Retrieves museums
	 * @return
	 * @throws IOException
	 */
	public JSONObject getMuseums() throws IOException {
		
		JSONObject json = new JSONObject();
		
		if(this.LOCAL_ONLY) {
			json = this.readFile("museums.json");
		} else {
			json = this.sendGet(MUSEUM_API);
		}
		
		return json;
	}
	
	/**
	 * Retrieves cinemas
	 * @return
	 * @throws IOException
	 */
	public JSONObject getCinemas() throws IOException {
		
		JSONObject json = new JSONObject();
		
		if(this.LOCAL_ONLY) {
			json = this.readFile("cinemas.json");
		} else {
			json = this.sendGet(CINEMA_API);
		}
		return json;
	}
	
	/**
	 * Retrieves the Geo Data from the Google Maps s' API
	 * @param address
	 * @return
	 * @throws IOException
	 */
	public Location getLocation(String address) throws IOException {
		
		JSONObject json = this.sendGet("http://maps.google.com/maps/api/geocode/json?address="+address+" Genève, Switzerland&sensor=false");
		
		JSONArray results = (JSONArray) json.get("results");
		JSONObject res = (JSONObject) results.get(0);
		JSONObject geometry = (JSONObject) res.get("geometry");
		JSONObject location = (JSONObject) geometry.get("location");

		double lat = (Double) location.get("lat");
		double lng = (Double) location.get("lng");

		return new Location(lat, lng);
	}
	
	/**
	 * Execute a GET HTTP Request
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private JSONObject sendGet(String url) throws IOException {
		
		url = url.replace(" ", "%20");
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
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
	
	/**
	 * Reads a file from resources folder
	 * By mkyong: http://www.mkyong.com/java/java-read-a-file-from-resources-folder/
	 * @param filename
	 * @return
	 * @throws IOException 
	 */
	private JSONObject readFile(String fileName) throws IOException {

		StringBuilder result = new StringBuilder("");
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    try {
	        String line = br.readLine();

	        while (line != null) {
	        	result.append(line);
	        	result.append(System.lineSeparator());
	            line = br.readLine();
	        }
	    } finally {
	        br.close();
	    }
	 
		return (JSONObject) JSONValue.parse(result.toString());
	}
}
