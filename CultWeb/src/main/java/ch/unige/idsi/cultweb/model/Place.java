package ch.unige.idsi.cultweb.model;

public class Place {
	
	private long id;
	private String name;
	private String town;
	private String address;
	private String url;
	private Infrastructure infrastructure;
	private long latitude;
	private long longitude;
	
	public enum Infrastructure { MUSEUM, CINEMA }
	
	public Place(long id, String name, String town, String address, String url, Infrastructure infrastructure, long latitude, long longitude) {
		this.id = id;
		this.name = name;
		this.town = town;
		this.address = address;
		this.url = url;
		this.infrastructure = infrastructure;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public long getId() {
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
	
	public long getLatitude() {
		return this.latitude;
	}
	
	public long getLontitude() {
		return this.longitude;
	}
	
}
