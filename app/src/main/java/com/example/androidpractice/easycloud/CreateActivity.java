package com.example.androidpractice.easycloud;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    EditText name;
    Button create;
    String regionValue, retrieveRegionValue, dataCenterValue;
    String sizeValue;
    String imageValue;
    String nameStr;
    ArrayAdapter<String> adapter;
    Spinner region, dataCenter;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("TOKEN");
        Log.i("token retrived", token);

        region = (Spinner) findViewById(R.id.region_spinner);
        dataCenter = (Spinner) findViewById(R.id.dataCenter_spinner);

        //Log.v("REGION################# ", region.toString());
        if (region != null) {
            region.setOnItemSelectedListener(new OnItemSelectedListener() {
                                                 @Override
                                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                     String tmpRegionValue = String.valueOf(region.getSelectedItem());

                                                     Log.v("@@@@@@@@@@@@@@@@@@@@@@@", tmpRegionValue);

                                                     if (tmpRegionValue.contentEquals("New York")) {

                                                         List<String> nycArr = new ArrayList<String>();
                                                         nycArr.add("1");
                                                         nycArr.add("2");
                                                         nycArr.add("3");

                                                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_spinner_item, nycArr);
                                                         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                         adapter.notifyDataSetChanged();
                                                         dataCenter.setAdapter(adapter);
                                                     }
                                                     if (tmpRegionValue.contentEquals("San Francisco")) {

                                                         List<String> sfArr = new ArrayList<String>();
                                                         sfArr.add("1");


                                                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_spinner_item, sfArr);
                                                         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                         adapter.notifyDataSetChanged();
                                                         dataCenter.setAdapter(adapter);
                                                     }
                                                     if (tmpRegionValue.contentEquals("Amsterdam")) {

                                                         List<String> amsterdamArr = new ArrayList<String>();

                                                         amsterdamArr.add("2");
                                                         amsterdamArr.add("3");

                                                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_spinner_item, amsterdamArr);
                                                         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                         adapter.notifyDataSetChanged();
                                                         dataCenter.setAdapter(adapter);
                                                     }
                                                     if (tmpRegionValue.contentEquals("Singapore")) {

                                                         List<String> sgpArr = new ArrayList<String>();
                                                         sgpArr.add("1");

                                                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_spinner_item, sgpArr);
                                                         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                         adapter.notifyDataSetChanged();
                                                         dataCenter.setAdapter(adapter);
                                                     }
                                                     if (tmpRegionValue.contentEquals("London")) {

                                                         List<String> lonArr = new ArrayList<String>();
                                                         lonArr.add("1");


                                                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_spinner_item, lonArr);
                                                         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                         adapter.notifyDataSetChanged();
                                                         dataCenter.setAdapter(adapter);
                                                     }
                                                     if (tmpRegionValue.contentEquals("Frankfurt")) {

                                                         List<String> frankArr = new ArrayList<String>();

                                                         frankArr.add("1");


                                                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_spinner_item, frankArr);
                                                         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                         adapter.notifyDataSetChanged();
                                                         dataCenter.setAdapter(adapter);
                                                     }
                                                     if (tmpRegionValue.contentEquals("Toronto")) {

                                                         List<String> torArr = new ArrayList<String>();

                                                         torArr.add("1");


                                                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_spinner_item, torArr);
                                                         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                         adapter.notifyDataSetChanged();
                                                         dataCenter.setAdapter(adapter);
                                                     }
                                                     if (tmpRegionValue.contentEquals("Banglore")) {

                                                         List<String> bangArr = new ArrayList<String>();

                                                         bangArr.add("1");


                                                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateActivity.this, android.R.layout.simple_spinner_item, bangArr);
                                                         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                         adapter.notifyDataSetChanged();
                                                         dataCenter.setAdapter(adapter);
                                                     }
                                                 }

                                                 @Override
                                                 public void onNothingSelected(AdapterView<?> parent) {

                                                 }
                                             }

            );
        }

        create = (Button) findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          name = (EditText) findViewById(R.id.editTextName);
                                          nameStr = name.getText().toString();


                                          Spinner regionSpinner = (Spinner) findViewById(R.id.region_spinner);
                                          retrieveRegionValue = regionSpinner.getSelectedItem().toString();

                                          Spinner dataCenterSpinner = (Spinner)findViewById(R.id.dataCenter_spinner);
                                          dataCenterValue = dataCenterSpinner.getSelectedItem().toString();

                                          if(retrieveRegionValue.equals("New York")){

                                              regionValue = "nyc"+dataCenterValue;
                                          }
                                          if(retrieveRegionValue.equals("San Francisco")){

                                              regionValue = "sfo"+dataCenterValue;

                                          }
                                          if(retrieveRegionValue.equals("Amsterdam")){

                                              regionValue = "ams"+dataCenterValue;
                                          }
                                          if(retrieveRegionValue.equals("Singapore")){

                                              regionValue = "sgp"+dataCenterValue;
                                          }
                                          if(retrieveRegionValue.equals("London")){

                                              regionValue = "lon"+dataCenterValue;
                                          }
                                          if(retrieveRegionValue.equals("Frankfurt")){

                                              regionValue = "fra"+dataCenterValue;
                                          }
                                          if(retrieveRegionValue.equals("Toronto")){

                                              regionValue = "tor"+dataCenterValue;
                                          }
                                          if(retrieveRegionValue.equals("Banglore")){

                                              regionValue = "blr"+dataCenterValue;
                                          }

                                          //Log.v(" RESULT**********************",regionValue);


                                          Spinner sizeSpinner = (Spinner) findViewById(R.id.size_spinner);
                                          sizeValue = sizeSpinner.getSelectedItem().toString();


                                          Spinner imageSpinner = (Spinner) findViewById(R.id.image_spinner);
                                          imageValue = imageSpinner.getSelectedItem().toString();




                                          // int result = createDroplets(name,regionValue,sizeValue,imageValue);

                                          Droptlets d = new Droptlets();
                                          d.execute(token);

                                          Intent i = new Intent(CreateActivity.this, MainActivity.class);
                                          Bundle bundle = new Bundle();
                                          bundle.putString("TOKEN", token);
                                          i.putExtra("TOKEN", token);
                                          startActivity(i);

                                      }
                                  }

        );

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    class Droptlets extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String dropletsListJsonStr = "";
            StringBuilder sb = new StringBuilder();
            int HttpResult = 0;

            try {

                final String CREATE_DROPLET_BASE_URL = "https://api.digitalocean.com/v2/droplets";

                Uri builtUri = Uri.parse(CREATE_DROPLET_BASE_URL).buildUpon().build();

                URL url = new URL(builtUri.toString());


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", "Bearer "+params[0]);

                urlConnection.connect();

                Map<String, String> hmap = new HashMap<>();
                hmap.put("name", nameStr);

                hmap.put("region", regionValue);
                hmap.put("size", sizeValue);
                hmap.put("image", imageValue);
                hmap.put("ssh_keys", null);
                hmap.put("backups", "false");
                hmap.put("ipv6", "true");
                hmap.put("user_data", null);
                hmap.put("private_networking", null);

                JSONObject jparam = new JSONObject(hmap);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jparam.toString());
                out.close();

                HttpResult = urlConnection.getResponseCode();

                Log.v("RESPONSE CODE ", String.valueOf(HttpResult));
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();


                    Log.v("TO_STRING", sb.toString());

                } else {
                    Log.v("RESPONSE MSG ", urlConnection.getResponseMessage());
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

            return null;


        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }
    }
}
