package ch.unige.idsi.cultapp.util;

import java.util.ArrayList;

import ch.unige.idsi.cultapp.R;
import ch.unige.idsi.cultapp.model.Place;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author manish.s
 * 
 */
public class GridAdapter extends ArrayAdapter<Place> {
	
	Context context;
	int layoutResourceId;
	
	ArrayList<Place> data = new ArrayList<Place>();

	public GridAdapter(Context context, int layoutResourceId,
			ArrayList<Place> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.item_title);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		Place item = data.get(position);
		holder.txtTitle.setText(item.getName());
		return row;

	}

	static class RecordHolder {
		TextView txtTitle;
	}
}