package com.example.androidpractice.easycloud;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<DropletModel> dropletList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DropletsAdapter mAdapter;
    String dropletsListJsonStr = "";
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        token = i.getExtras().getString("TOKEN");
        Log.i("token retrived", token);

        CreateDroplets cd = new CreateDroplets();
        cd.execute(token);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this, CreateActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("TOKEN", token);
                intent.putExtra("TOKEN", token);
                startActivity(intent);


            }
        });


        Log.v(" ------------", dropletsListJsonStr);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mAdapter = new DropletsAdapter(getApplicationContext(), dropletList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                query = query.toLowerCase();

                final List<DropletModel> filterArr = new ArrayList<>();

                for (int i = 0; i < dropletList.size(); i++) {

                    final String title = dropletList.get(i).getTitle().toLowerCase();
                    if (title.contains(query)) {

                        filterArr.add(dropletList.get(i));
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mAdapter = new DropletsAdapter(getApplicationContext(), filterArr);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                return true;

            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_refresh) {
            dropletList.clear();
            CreateDroplets cd = new CreateDroplets();
            cd.execute(token);
        }

        if (id == R.id.action_logout) {
            Intent intent = new Intent(MainActivity.this,LauncherActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    class CreateDroplets extends AsyncTask<String, Void, List<DropletModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected List<DropletModel> doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();


            try {

                final String DROPLETSLIST_BASE_URL = "https://api.digitalocean.com/v2/droplets?page=1&per_page=50";

                Uri builtUri = Uri.parse(DROPLETSLIST_BASE_URL).buildUpon().build();

                URL url = new URL(builtUri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                String test =token;
                Log.v("token ***",token);
                urlConnection.setRequestProperty("Authorization", "Bearer "+params[0]);
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    dropletsListJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    dropletsListJsonStr = null;
                }
                dropletsListJsonStr = buffer.toString();
                Log.v("LOG_TAG", dropletsListJsonStr);
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

                dropletsListJsonStr = null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            try {
                return getDropletDetailsFromJson(dropletsListJsonStr,token);

            } catch (Exception e) {

            }

            return null;

        }

        @Override
        protected void onPostExecute(List<DropletModel> data) {
            super.onPostExecute(data);


            mAdapter.notifyDataSetChanged();
        }
    }

    private List<DropletModel> getDropletDetailsFromJson(String dropletJsonString, String token) throws JSONException {

        final String droplets_list = "droplets";
        final String display_name = "name";
        final String display_status = "status";
        final String display_memory = "memory";
        final String display_id = "id";
        final String display_image = "image";
        final String display_distribution = "distribution";
        final String display_disk = "disk";
        final String display_region = "region";
        final String display_slug = "slug";
        final String display_created = "created_at";
        final String display_networks = "networks";
        final String display_v4 = "v4";
        final String display_v6 = "v6";
        final String display_ip4 = "ip_address";
        final String display_ip6 = "ip_address";

        String title, status, id, distribution, region, created;
        int memory, disk;
        JSONObject regionObject, image, networkObj;
        DropletModel dm;


        JSONObject dropletListJson = new JSONObject(dropletJsonString);
        JSONArray dropletListArray = dropletListJson.getJSONArray(droplets_list);


        final String imagePath[] = {
                "http://s33.postimg.org/pjz0lqmnj/1464929915_ubuntu.png",
                "http://s33.postimg.org/4qm1jq0j3/Cent_OS.png",
                "http://s32.postimg.org/rzj9r4mph/debian_logo.png",
                "http://s32.postimg.org/cs3qq5bsl/fedora.png"

        };

        for (int i = 0; i < dropletListArray.length(); i++) {

            JSONObject dropletDetailJson = dropletListArray.getJSONObject(i);
            title = dropletDetailJson.getString(display_name);
            status = dropletDetailJson.getString(display_status);
            memory = dropletDetailJson.getInt(display_memory);
            id = dropletDetailJson.getString(display_id);
            disk = dropletDetailJson.getInt(display_disk);
            created = dropletDetailJson.getString(display_created);

            regionObject = dropletDetailJson.getJSONObject(display_region);
            region = regionObject.getString(display_slug);


            image = dropletDetailJson.getJSONObject(display_image);
            distribution = image.getString(display_distribution);

            networkObj = dropletDetailJson.getJSONObject(display_networks);
            JSONArray v4 = networkObj.getJSONArray(display_v4);
            JSONArray v6 = networkObj.getJSONArray(display_v6);

            JSONObject ipvObj = v4.getJSONObject(0);
            String ip4 = ipvObj.getString(display_ip4);

            JSONObject ipv6Obj = v6.getJSONObject(0);
            String ip6 = ipv6Obj.getString(display_ip6);

            Log.v(" IP$@@@@@@@@@@@@@@@@ ",ip4);
            Log.v(" IP$@@@@@@@@@@@@@@@@ ",ip6);
            String description = id + "/ " + memory + "MB Memory/ " + disk + "GB Disk/ " + region + "/" + status + "/" + token  +"/" + created +
                    "/" + ip4 + "/" +ip6;

            Log.v(" MAINACTIVITY********* ",description);

            if(distribution.equals("Ubuntu")){
                dropletList.add(new DropletModel(imagePath[0],title,description));
            }else
            if(distribution.equals("CentOS")){
                dropletList.add(new DropletModel(imagePath[1],title,description));
            }else
            if(distribution.equals("Fedora")){
                dropletList.add(new DropletModel(imagePath[3],title,description));
            }
            else{
                dropletList.add(new DropletModel(imagePath[2],title,description));
            }

        }

        Log.v("DROPLETLIST ", String.valueOf(dropletList.size()));

        return dropletList;

    }

}
