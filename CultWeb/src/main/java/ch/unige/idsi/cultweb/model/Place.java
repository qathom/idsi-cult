package ch.unige.idsi.cultweb.model;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The Place is the parent Object of museums/cinemas
 *
 */
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

	public enum Infrastructure {
		MUSEUM, CINEMA
	}

	public Place(int id, String name, String contact, String town,
			String address, String url, Infrastructure infrastructure,
			double latitude, double longitude) {
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

	public Place(int id, String name, String contact, String town,
			String address, String url, Infrastructure infrastructure,
			double latitude, double longitude, List<Recommendation> recommendations) {
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

	public JSONObject toJSON() throws IOException {

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
		
		JSONArray jsonRecommendations = new JSONArray();
		
		for(Recommendation r : this.recommendations) {
			jsonRecommendations.add(r.toJSON());
		}

		json.put("recommendations", jsonRecommendations);

		return json;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
