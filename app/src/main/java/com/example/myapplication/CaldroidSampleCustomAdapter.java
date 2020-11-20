package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import hirondelle.date4j.DateTime;

public class CaldroidSampleCustomAdapter extends CaldroidGridAdapter {
	ArrayList<String> weather;

	public CaldroidSampleCustomAdapter(Context context, int month, int year,
									   Map<String, Object> caldroidData,
									   Map<String, Object> extraData, ArrayList<String> weather) {
		super(context, month, year, caldroidData, extraData);
		this.weather = weather;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View cellView = convertView;

		// For reuse
		if (convertView == null) {
			cellView = inflater.inflate(R.layout.custom_cell, null);
		}

		int topPadding = cellView.getPaddingTop();
		int leftPadding = cellView.getPaddingLeft();
		int bottomPadding = cellView.getPaddingBottom();
		int rightPadding = cellView.getPaddingRight();

		TextView tv1 = (TextView) cellView.findViewById(R.id.tv1);
		TextView tv2 = (TextView) cellView.findViewById(R.id.tv2);
		ImageView img = (ImageView) cellView.findViewById(R.id.imageView);

		tv1.setTextColor(Color.BLACK);

		// Get dateTime of this cell
		DateTime dateTime = this.datetimeList.get(position);
		Resources resources = context.getResources();

		// Set color of the dates in previous / next month
		if (dateTime.getMonth() != month) {
			tv1.setTextColor(resources
					.getColor(com.caldroid.R.color.caldroid_darker_gray));
		}

		boolean shouldResetDiabledView = false;
		boolean shouldResetSelectedView = false;

		// Customize for disabled dates and date outside min/max dates
		if ((minDateTime != null && dateTime.lt(minDateTime))
				|| (maxDateTime != null && dateTime.gt(maxDateTime))
				|| (disableDates != null && disableDates.indexOf(dateTime) != -1)) {

			tv1.setTextColor(CaldroidFragment.disabledTextColor);
			if (CaldroidFragment.disabledBackgroundDrawable == -1) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.disable_cell);
			} else {
				cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
			}

			if (dateTime.equals(getToday())) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.red_border_gray_bg);
			}

		} else {
			shouldResetDiabledView = true;
		}

		// Customize for selected dates
		if (selectedDates != null && selectedDates.indexOf(dateTime) != -1) {
			cellView.setBackgroundColor(resources
					.getColor(com.caldroid.R.color.caldroid_sky_blue));

			tv1.setTextColor(Color.BLACK);

		} else {
			shouldResetSelectedView = true;
		}

		if (shouldResetDiabledView && shouldResetSelectedView) {
			// Customize for today
			if (dateTime.equals(getToday())) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.red_border);
			} else {
				cellView.setBackgroundResource(com.caldroid.R.drawable.cell_bg);
			}
		}

		int difference = getToday().numDaysFrom(dateTime);


		tv1.setText("" + dateTime.getDay());
		tv2.setText("");
		tv2.setVisibility(View.VISIBLE);
		img.setImageResource(R.drawable.siro);

		if (difference >= 0 && difference < 5) {
			if (weather.get(difference).equals("01n") || weather.get(difference).equals("02n")) {
				img.setImageResource(R.drawable.hare);
			} else if (weather.get(difference).equals("03n") || weather.get(difference).equals("04n")) {
				img.setImageResource(R.drawable.kumori);
			} else {
				img.setImageResource(R.drawable.ame);
			}

		}
			tv1.setMinHeight(50);

/*		for (int count = 0; count < weather.size(); count++) {
			for (int day = 1; day < 5; day++) {
				if (weather.get(count).equals("01n") || weather.get(count).equals("02n")) {
					img.setImageResource(R.drawable.hare);
				} else if (weather.get(count).equals("03n") || weather.get(count).equals("04n")) {
					img.setImageResource(R.drawable.kumori);
				} else {
					img.setImageResource(R.drawable.ame);
				}
			}
		}
*/
			// Somehow after setBackgroundResource, the padding collapse.
			// This is to recover the padding
			cellView.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);

			// Set custom color if required
			setCustomResources(dateTime, cellView, tv1);


			return cellView;
		}
	}





