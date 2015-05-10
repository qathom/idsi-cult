package ch.unige.idsi.cultapp.util;

import ch.unige.idsi.cultapp.listener.OnLocationListener;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class ContextLocation implements LocationListener {

	private OnLocationListener listener;

	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
	private static final long MIN_TIME_BW_UPDATES = 1;

	private Location location;

	private LocationManager locationManager;

	public Location getLocation(Context context) {

		try {

			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);

			// getting GPS status
			boolean isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			boolean isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (isGPSEnabled == false && isNetworkEnabled == false) {
				// no network provider is enabled

			} else {

				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {

					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						}
					}
				} else if (isNetworkEnabled) {
					location = null;
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	public void registerListener(OnLocationListener listener) {
		this.listener = listener;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		listener.onLocationChanged(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
}
