package ch.unige.idsi.cultapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DataBase Helper to init the SQLiteDatabase Object
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	static String TABLE_PLACES = "table_places";
	static String COL_ID = "id";
	static String COL_UID = "uid";
	static String COL_NAME = "name";
	static String COL_CONTACT = "contact";
	static String COL_TOWN = "town";
	static String COL_ADDRESS = "address";
	static String COL_URL = "url";
	static String COL_INFRASTRUCTURE = "infrastructure";
	static String COL_LATITUDE = "latitude";
	static String COL_LONGITUDE = "longitude";
	static String COL_IS_FAVORITE = "favorite";

	private String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + TABLE_PLACES + " (" + COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_UID + " INTEGER, "
			+ COL_NAME + " TEXT NOT NULL," + COL_CONTACT + " TEXT NOT NULL, "
			+ COL_TOWN + " TEXT NOT NULL, " + COL_ADDRESS + " TEXT NOT NULL,"
			+ "" + COL_URL + " TEXT NOT NULL, " + COL_INFRASTRUCTURE
			+ " TEXT NOT NULL, " + COL_LATITUDE + " REAL," + "" + COL_LONGITUDE
			+ " REAL ," + COL_IS_FAVORITE + " BOOLEAN NOT NULL CHECK ("
			+ COL_IS_FAVORITE + " IN (0,1)), UNIQUE(" + COL_UID
			+ ") ON CONFLICT REPLACE);";

	/**
	 * @constructor
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLE_PLACES + ";");
		onCreate(db);
	}

}