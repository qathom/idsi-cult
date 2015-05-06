package ch.unige.idsi.cultweb.model;

public class Cinema extends Place {
	
	public Cinema(long id, String name, String contact, String town, String address, String url, long latitude, long longitude) {
		super(id, name, contact, town, address, url, Infrastructure.CINEMA, latitude, longitude);
	}
	
	
}
