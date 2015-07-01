package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_find_loc) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String selectedLocation = prefs.getString(getString(R.string.pref_loc_key),
                    getString(R.string.pref_loc_default));

            Uri location = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", selectedLocation)
                .build();
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

            // Verify that there is an activity available that can respond to the intent
            PackageManager packageManager = getPackageManager();
            List activities = packageManager.queryIntentActivities(mapIntent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;

            if (isIntentSafe) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(this, getString(R.string.noAppForLocation),
                        Toast.LENGTH_LONG).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
