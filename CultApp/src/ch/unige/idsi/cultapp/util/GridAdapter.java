package ch.unige.idsi.cultapp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.unige.idsi.cultapp.R;
import ch.unige.idsi.cultapp.model.Place;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * @author Anthony Meizoso
 */
public class GridAdapter extends ArrayAdapter<Place> {

	private Context context;
	private int layoutResourceId;

	private ArrayList<Place> data = new ArrayList<Place>();

	private OnClickListener onCheckedChanged = null;

	public GridAdapter(Context context, int layoutResourceId,
			ArrayList<Place> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	public void registerCheckListener(OnClickListener onCheckedChanged) {
		this.onCheckedChanged = onCheckedChanged;
	}

	private Comparator<Place> compareAscendants = new Comparator<Place>() {

		public int compare(Place p1, Place p2) {
			return p1.getName().compareTo(p2.getName());
		}
	};

	private Comparator<Place> compareFavorites = new Comparator<Place>() {

		public int compare(Place p1, Place p2) {
			boolean b1 = p1.isChecked();
			boolean b2 = p2.isChecked();
			if (b1 && !b2) {
				return -1;
			}
			if (!b1 && b2) {
				return +1;
			}
			return 0;
		}
	};

	public void sortAscendants() {
		Collections.sort(this.data, compareAscendants);
		this.notifyDataSetChanged();
	}

	public void sortFavorites() {
		Collections.sort(this.data, compareFavorites);
		this.notifyDataSetChanged();
	}

	public List<Place> getItems() {
		return data;
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
			holder.checkBox = (CheckBox) row.findViewById(R.id.item_checkbox);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		Place item = data.get(position);
		holder.txtTitle.setText(item.getName());
		
		holder.checkBox.setOnClickListener(null);
		holder.checkBox.setOnClickListener(onCheckedChanged);
		
		holder.checkBox.setChecked(item.isChecked());
		holder.checkBox.setId(item.getId());

		return row;
	}

	static class RecordHolder {
		TextView txtTitle;
		CheckBox checkBox;
	}
}