package ch.unige.idsi.cultweb.model;

public class Museum extends Place {
	
	public Museum(long id, String name, String town, String address, String url, long latitude, long longitude) {
		super(id, name, town, address, url, Infrastructure.MUSEUM, latitude, longitude);
	}
	
	
}
