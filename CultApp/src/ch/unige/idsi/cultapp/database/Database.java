package ch.unige.idsi.cultapp.database;

import java.util.ArrayList;

import ch.unige.idsi.cultapp.model.Place;
import ch.unige.idsi.cultapp.model.Place.Infrastructure;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "places.db";

	private SQLiteDatabase bdd;

	private DatabaseHelper dbHelper;

	public Database(Context context) {
		dbHelper = new DatabaseHelper(context, NOM_BDD, null, VERSION_BDD);
	}

	public void openr() {
		bdd = dbHelper.getReadableDatabase();
	}

	public void openw() {
		bdd = dbHelper.getWritableDatabase();
	}

	public void close() {
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public ArrayList<Place> getAll(Infrastructure type) {

		this.openr();

		ArrayList<Place> places = new ArrayList<Place>();

		Cursor c = bdd.rawQuery("SELECT * FROM " + dbHelper.TABLE_PLACES + " WHERE " + dbHelper.COL_INFRASTRUCTURE + "= \"" + type + "\"", null);
		if (c.moveToFirst()) {
			do {
				
				long uid = (long) c.getInt(1);
				String name = c.getString(2);
				String contact = c.getString(3);
				String town = c.getString(3);
				String address = c.getString(3);
				String url = c.getString(3);
				Infrastructure infrastructure = Infrastructure.valueOf(c.getString(4));

				places.add(new Place(uid, name, contact, town,
						address, url, infrastructure, 0, 0));
			} while (c.moveToNext());
		}
		c.close();

		return places;
	}
	
	public void addFavorite(long uid, String name) {
		
		this.openw();

		ContentValues values = new ContentValues();

		values.put(dbHelper.COL_UID, uid);
		values.put(dbHelper.COL_NAME, name);

		bdd.insert(dbHelper.TABLE_PLACES, null, values);
	}
	
	public void deleteFavorite(long uid) {
//		bdd.delete(table, whereClause, whereArgs);
	}

	public void insertPlace(Place place) {

		this.openw();

		ContentValues values = new ContentValues();

		values.put(dbHelper.COL_UID, place.getId());
		values.put(dbHelper.COL_NAME, place.getName());
		values.put(dbHelper.COL_CONTACT, place.getContact());
		values.put(dbHelper.COL_TOWN, place.getTown());
		values.put(dbHelper.COL_ADDRESS, place.getAddress());
		values.put(dbHelper.COL_URL, place.getUrl());
		values.put(dbHelper.COL_INFRASTRUCTURE, place.getInfrastructure()
				.toString());
		values.put(dbHelper.COL_LATITUDE, place.getLatitude());
		values.put(dbHelper.COL_LONGITUDE, place.getLongitude());

		bdd.insert(dbHelper.TABLE_PLACES, null, values);

		// this.close();
	}

	public int getUid(long uid) {

		int id = -1;

		try {

			Cursor c = null;

			c = bdd.rawQuery("SELECT " + dbHelper.COL_UID + " FROM "
					+ dbHelper.TABLE_PLACES + " WHERE " + dbHelper.COL_UID
					+ "=" + uid, null);

			if (c != null && c.moveToFirst()) {
				id = c.getInt(c.getColumnIndexOrThrow(dbHelper.COL_UID));
				c.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public void updateLivre(Place place) {

		ContentValues values = new ContentValues();

		// return bdd.update(maBaseSQLite.TABLE_PLACES, values, COL_ID + " = "
		// +id, null);
	}

}
