package ch.unige.idsi.cultapp.util;

/**
 * Constants must be edited in case of changes (website name for the API,etc)
 */
public class Constants {

	public static String BASE_URL = "http://ge.ch/ags1/";
	public static String MUSEUM_API = BASE_URL + "rest/services/Culture/MapServer/1/query?text=Mus%C3%A9e&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=*&f=pjson";
	public static String CINEMA_API = BASE_URL + "rest/services/Culture/MapServer/1/query?text=Cin%C3%A9ma&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=*&f=pjson";
	
	public static String INTENT_OBJECT_ID = "intent_object_id";
	public static String INTENT_OBJECT_NAME = "intent_object_name";
}
