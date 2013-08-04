package com.Almaarik.badr_api;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlaceItemsAdaptor extends BaseAdapter {
	
	private List<Place> _placeInfo;
	private Context _context;
	
	public PlaceItemsAdaptor(List<Place> place, Context context){
		this._placeInfo = place;
		this._context = context;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return _placeInfo.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = LayoutInflater.from(_context);
		
		View placeView = inflator.inflate(R.layout.places_item, null);
		TextView txtName = (TextView)placeView.findViewById(R.id.name);
		TextView txtAddress = (TextView)placeView.findViewById(R.id.address);
		
		Place thisPlace = _placeInfo.get(position);
		
		
		txtName.setText(thisPlace.getName());
		txtAddress.setText(thisPlace.getAaddress());
		
		return placeView;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _placeInfo.size();
	}
}
