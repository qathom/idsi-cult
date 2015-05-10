package ch.unige.idsi.cultweb.model;

import org.json.simple.JSONObject;

public class Recommendation {
	
	private long id;
	private String name;
	private String url;
	
	public Recommendation(long id, String name, String url) {
		this.setId(id);
		this.name = name;
		this.setUrl(url);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public JSONObject toJSON() {
		
		JSONObject json = new JSONObject();
		json.put("name", this.name);
		json.put("url", this.url);
		
		return json;
	}

}
