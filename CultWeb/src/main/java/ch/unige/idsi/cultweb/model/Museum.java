package ch.unige.idsi.cultweb.model;

import java.util.List;

public class Museum extends Place {
	
	public Museum(long id, String name, String contact, String town, String address, String url, long latitude, long longitude) {
		super(id, name, contact, town, address, url, Infrastructure.MUSEUM, latitude, longitude);
	}
	
	public Museum(long id, String name, String contact, String town, String address, String url, long latitude, long longitude, List<Recommendation> recommendations) {
		super(id, name, contact, town, address, url, Infrastructure.MUSEUM, latitude, longitude, recommendations);
	}
	
	
}
