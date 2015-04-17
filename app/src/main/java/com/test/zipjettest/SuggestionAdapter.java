package com.test.zipjettest;

import android.app.Activity;
import android.location.Location;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.test.zipjettest.Network.ConnectionFactory;
import com.test.zipjettest.Network.JsonParser;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kavya, 17-04-2015.
 */
public class SuggestionAdapter extends ArrayAdapter<String> {
    private List<String> suggestions;
    private List<PlaceItem> parsedList;

    private ConnectionFactory connectionFactory;
    private JsonParser jp;
    private GPSTracker gps;

    public SuggestionAdapter(Activity context) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        suggestions = new ArrayList<>();

        connectionFactory = new ConnectionFactory();
        jp = new JsonParser();
        gps = new GPSTracker(getContext());
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public String getItem(int index) {
        return suggestions.get(index);
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {

        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            final Filter.FilterResults filterResults = new Filter.FilterResults();

            if (constraint != null) {
                // Parse JSON Data
                try {
                    HttpsURLConnection connection = connectionFactory.getConnectionForBaseUrl(constraint.toString());
                    parsedList = jp.parseStreamList(connection.getInputStream());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                // Sort by distance
                sortByDist();

                suggestions.clear();
                for (int i = 0; i < parsedList.size(); i++) {
                    suggestions.add(parsedList.get(i).getName() + "("
                            + parsedList.get(i).getCountry() + ")");
                }
                // Assign the values to FilterResults object
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      Filter.FilterResults results) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };

    public void sortByDist() {
        final Location loc = new Location("");

        //get current location
        final Location curLoc = gps.getLocation();
        if (curLoc == null) {
            Log.e("ERROR", " GPS ERROR ");
            return;
        }

        Collections.sort(parsedList, new Comparator<PlaceItem>() {
            @Override
            public int compare(PlaceItem o1, PlaceItem o2) {
                // distance of o1 with curLoc
                loc.setLatitude(o1.getLatitude());
                loc.setLongitude(o1.getLongitude());
                Float d1 = loc.distanceTo(curLoc);

                // distance of o2 with curLoc
                loc.setLatitude(o2.getLatitude());
                loc.setLongitude(o2.getLongitude());
                Float d2 = loc.distanceTo(curLoc);

                return d1.compareTo(d2);
            }
        });
    }
}
