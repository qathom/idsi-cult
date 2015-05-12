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
				
				int uid = c.getInt(1);
				String name = c.getString(2);
				String contact = c.getString(3);
				String town = c.getString(4);
				String address = c.getString(5);
				String url = c.getString(6);
				Infrastructure infrastructure = Infrastructure.valueOf(c.getString(7));
				double latitude = c.getDouble(8);
				double longitude = c.getDouble(9);
				boolean checked = (c.getInt(10) != 0);
				
				places.add(new Place(uid, name, contact, town,
						address, url, infrastructure, latitude, longitude, checked));
			} while (c.moveToNext());
		}
		c.close();

		return places;
	}

	public Place getPlace(int uid) {

		Place place = null;

		this.openr();

		Cursor c = bdd.rawQuery("SELECT * FROM " + dbHelper.TABLE_PLACES + " WHERE " + dbHelper.COL_UID + "=" + uid, null);

		if(c.getCount() > 0) {
			c.moveToFirst();

			String name = c.getString(2);
			String contact = c.getString(3);
			String town = c.getString(4);
			String address = c.getString(5);
			String url = c.getString(6);
			Infrastructure infrastructure = Infrastructure.valueOf(c.getString(7));
			double latitude = c.getDouble(8);
			double longitude = c.getDouble(9);
			boolean checked = (c.getInt(10) != 0);

			place = new Place(uid, name, contact, town,
					address, url, infrastructure, latitude, longitude, checked);
		}
		c.close();

		return place;
	}

	public boolean exists(Place p) {

		boolean exists = false;

		this.openr();

		Cursor c = bdd.rawQuery("SELECT * FROM " + dbHelper.TABLE_PLACES + " WHERE " + dbHelper.COL_UID + "=" + p.getId(), null);

		if(c.getCount() > 0) {
			exists = true;
		}
		c.close();

		return exists;
	}
	
	public void setFavorite(int uid, boolean isFavorite) {
		
		this.openw();

		ContentValues values = new ContentValues();

		values.put(dbHelper.COL_UID, uid);
		values.put(dbHelper.COL_IS_FAVORITE, isFavorite);
		
		String where = dbHelper.COL_UID + "=" + uid;
		
		bdd.update(dbHelper.TABLE_PLACES, values, where, null);
	}

	public boolean insertPlace(Place place) {

		boolean exists = this.exists(place);
		boolean canBeInserted = false;

		if(!exists) {

			canBeInserted = true;

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

			values.put(dbHelper.COL_IS_FAVORITE, 0);

			bdd.insert(dbHelper.TABLE_PLACES, null, values);
		} else {
			updatePlace(place);
		}

		return canBeInserted;
	}

	public void updatePlace(Place place) {

		this.openw();

		ContentValues values = new ContentValues();

		values.put(dbHelper.COL_NAME, place.getName());
		values.put(dbHelper.COL_CONTACT, place.getContact());
		values.put(dbHelper.COL_TOWN, place.getTown());
		values.put(dbHelper.COL_ADDRESS, place.getAddress());
		values.put(dbHelper.COL_URL, place.getUrl());
		values.put(dbHelper.COL_INFRASTRUCTURE, place.getInfrastructure()
				.toString());
		values.put(dbHelper.COL_LATITUDE, place.getLatitude());
		values.put(dbHelper.COL_LONGITUDE, place.getLongitude());

		bdd.update(dbHelper.TABLE_PLACES, values, dbHelper.COL_UID + " = " + place.getId(), null);
	}
}
