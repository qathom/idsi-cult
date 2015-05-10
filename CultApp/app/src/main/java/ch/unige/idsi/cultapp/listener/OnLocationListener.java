package ch.unige.idsi.cultapp.listener;

import android.location.Location;

/**
 * 
 * @author Anthony Meizoso
 * 
 *         Interface used to refresh the GPS location (position) of the user
 * 
 */
public interface OnLocationListener {
	public void onLocationChanged(Location location);
}