package com.example.myapplication;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.ArrayList;

public class CaldroidSampleCustomFragment extends CaldroidFragment {
<<<<<<< Updated upstream
	ArrayList<String> weather;
=======
		ArrayList<String> weather;
>>>>>>> Stashed changes
	public CaldroidSampleCustomFragment(ArrayList<String> weather){
		this.weather = weather;
	}

	@Override
	public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
		// TODO Auto-generated method stub

		return new com.example.myapplication.CaldroidSampleCustomAdapter(getActivity(), month, year,
				getCaldroidData(), extraData, weather);
	}

}