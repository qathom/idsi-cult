package ch.unige.idsi.cultapp.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Place {

	private int id;
	private String name;
	private String contact;
	private String town;
	private String address;
	private String url;
	private Infrastructure infrastructure;
	private double latitude;
	private double longitude;
	private List<Recommendation> recommendations;
	
	private boolean checked = false;

	public enum Infrastructure {
		MUSEUM, CINEMA
	}

	public Place(int id, String name, String contact, String town, String address, String url,
			Infrastructure infrastructure, double latitude, double longitude) {
		this.id = id;
		this.name = name;
		this.setContact(contact);
		this.town = town;
		this.address = address;
		this.url = url;
		this.infrastructure = infrastructure;
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}
	
	public Place(int id, String name, String contact, String town, String address, String url,
			Infrastructure infrastructure, double latitude, double longitude, boolean isChecked) {
		this.id = id;
		this.name = name;
		this.setContact(contact);
		this.town = town;
		this.address = address;
		this.url = url;
		this.infrastructure = infrastructure;
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setChecked(isChecked);
	}
	
	public Place(int id, String name, String contact, String town, String address, String url,
			Infrastructure infrastructure, double latitude, double longitude, List<Recommendation> recommendations) {
		this.id = id;
		this.name = name;
		this.setContact(contact);
		this.town = town;
		this.address = address;
		this.url = url;
		this.infrastructure = infrastructure;
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.recommendations = recommendations;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getTown() {
		return this.town;
	}

	public String getAddress() {
		return this.address;
	}

	public String getUrl() {
		return this.url;
	}

	public Infrastructure getInfrastructure() {
		return this.infrastructure;
	}


	public List<Recommendation> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public JSONObject toJSON() throws JSONException {
		
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("name", this.name);
		json.put("contact", this.contact);
		json.put("town", this.town);
		json.put("address", this.address);
		json.put("url", this.url);
		json.put("infrastructure", this.infrastructure);
		json.put("latitude", this.latitude);
		json.put("longitude", this.longitude);
		
//		String jsonText = JSONValue.toJSONString(this.recommendations);
//		JSONArray arr = (JSONArray) JSONValue.parse(jsonText);
		
		json.put("recommendations", new JSONArray());
		
		return json;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
