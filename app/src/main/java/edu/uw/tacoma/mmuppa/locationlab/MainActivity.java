package edu.uw.tacoma.mmuppa.locationlab;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private List<String> mItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItems = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.list);
        final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItems);
        listView.setAdapter(itemsAdapter);


        final LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, location.toString());
                mItems.add(location.toString());
                itemsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        Button start = (Button) findViewById(R.id.start_location);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0, 0, locationListener);
            }
        });

        Button stop = (Button) findViewById(R.id.stop_location);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.removeUpdates(locationListener);
            }
        });

        //locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,
          //      locationListener, null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
