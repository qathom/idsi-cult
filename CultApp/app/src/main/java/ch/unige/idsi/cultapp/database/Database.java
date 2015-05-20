package ch.unige.idsi.cultapp.database;

import java.util.ArrayList;

import ch.unige.idsi.cultapp.model.Place;
import ch.unige.idsi.cultapp.model.Place.Infrastructure;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Database {

	/**
	 * DB version
	 */
	private static final int VERSION_BDD = 1;

	/**
	 * Places table-name
	 */
	private static final String NOM_BDD = "places.db";

	private SQLiteDatabase bdd;

	private DatabaseHelper dbHelper;

	/**
	 * @constructor
	 * @param context
	 */
	public Database(Context context) {
		dbHelper = new DatabaseHelper(context, NOM_BDD, null, VERSION_BDD);
	}

	/**
	 * Opens the database in read mode
	 */
	public void openr() {
		bdd = dbHelper.getReadableDatabase();
	}

	/**
	 * Opens the database in write mode
	 */
	public void openw() {
		bdd = dbHelper.getWritableDatabase();
	}

	/**
	 * Closes the database
	 */
	private void close() {
		bdd.close();
	}

	/**
	 * Returns the Database Object
	 * @return
	 */
	public SQLiteDatabase getBDD() {
		return bdd;
	}

	/**
	 * Get all places (museums and cinemas)
	 * @param type
	 * @return
	 */
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
		this.close();

		return places;
	}

	/**
	 * Get a Place Object according to the given UID
	 * @param uid
	 * @return
	 */
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
		this.close();

		return place;
	}

	/**
	 * Checks if a place is present in the Database
	 * @param p
	 * @return
	 */
	public boolean exists(Place p) {

		boolean exists = false;

		this.openr();

		Cursor c = bdd.rawQuery("SELECT * FROM " + dbHelper.TABLE_PLACES + " WHERE " + dbHelper.COL_UID + "=" + p.getId(), null);

		if(c.getCount() > 0) {
			exists = true;
		}
		c.close();
		this.close();

		return exists;
	}

	/**
	 * Sets a place as a favorite
	 * @param uid
	 * @param isFavorite
	 */
	public void setFavorite(int uid, boolean isFavorite) {
		
		this.openw();

		ContentValues values = new ContentValues();

		values.put(dbHelper.COL_UID, uid);
		values.put(dbHelper.COL_IS_FAVORITE, isFavorite);
		
		String where = dbHelper.COL_UID + "=" + uid;
		
		bdd.update(dbHelper.TABLE_PLACES, values, where, null);
		this.close();
	}

	/**
	 * Inserts a place in the Database
	 * @param place
	 * @return
	 */
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
			this.close();

		} else {
			updatePlace(place);
		}

		return canBeInserted;
	}

	/**
	 * Updates a place in the Database
	 * @param place
	 */
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
		this.close();
	}
}
