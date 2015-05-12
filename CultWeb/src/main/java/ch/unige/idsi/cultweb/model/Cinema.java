package ch.unige.idsi.cultweb.model;

import java.util.List;

public class Cinema extends Place {
	
	public Cinema(int id, String name, String contact, String town, String address, String url, double latitude, double longitude) {
		super(id, name, contact, town, address, url, Infrastructure.CINEMA, latitude, longitude);
	}
	
	public Cinema(int id, String name, String contact, String town, String address, String url, double latitude, double longitude, List<Recommendation> recommendations) {
		super(id, name, contact, town, address, url, Infrastructure.CINEMA, latitude, longitude, recommendations);
	}
}
