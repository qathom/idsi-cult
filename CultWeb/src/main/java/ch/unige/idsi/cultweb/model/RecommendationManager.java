package ch.unige.idsi.cultweb.model;

import java.util.ArrayList;
import java.util.List;

public class RecommendationManager {
	
	private List<Recommendation> fullList = new ArrayList<Recommendation>();
	
	public RecommendationManager() {

		Recommendation r1 = new Recommendation(432, "tg", "http://omg.lol");
		this.fullList.add(r1);
		
		Recommendation r2 = new Recommendation(432, "tg2", "http://omg2.lol");
		this.fullList.add(r2);
		
		Recommendation r3 = new Recommendation(432, "tg2", "http://omg2.lol");
		this.fullList.add(r3);
	}
	
	public List<Recommendation> get(int id) {

		List<Recommendation> list = new ArrayList<Recommendation>();
		
		for(Recommendation r : this.fullList) {
			if(r.getId() == id) {
				list.add(r);
			}
		}
		
		return list;
	}
	
}
