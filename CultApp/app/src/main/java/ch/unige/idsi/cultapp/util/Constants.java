package ch.unige.idsi.cultapp.util;

/**
 * Constants must be edited in case of changes (website name for the API,etc)
 */
public class Constants {

	public static String LOCAL_PORT = "8585";
	public static String BASE_URL = "http://192.168.1.148:" + LOCAL_PORT + "/CultWeb/";
	public static String MUSEUM_API = BASE_URL + "api/museums/";
	public static String CINEMA_API = BASE_URL + "api/cinemas/";

	public static String INFO_MUSEM_API = BASE_URL + "api/museum/";
	public static String INFO_CINEMA_API = BASE_URL + "api/cinema/";

	public static String INTENT_OBJECT_ID = "intent_object_id";
	public static String INTENT_OBJECT_NAME = "intent_object_name";
	public static String INTENT_OBJECT_TYPE = "intent_object_type";
}
