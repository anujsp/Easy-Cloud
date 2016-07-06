package com.example.androidpractice.easycloud;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class ResizeActivityFragment extends Fragment {

    String dropletID, token;
    String resizeValue;
    Button resizeButton;
    private final String TOKEN = "TOKEN";

    public ResizeActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View resizeView =  inflater.inflate(R.layout.fragment_resize, container, false);

        Intent resizeIntent = getActivity().getIntent();
        dropletID = resizeIntent.getExtras().getString("dropletID");
        token = resizeIntent.getExtras().getString("TOKEN");

        resizeButton = (Button)resizeView.findViewById(R.id.buttonResize);
        resizeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Spinner resizeSpinner = (Spinner)resizeView.findViewById(R.id.resize_spinner);
                resizeValue = resizeSpinner.getSelectedItem().toString();



                ResizeDroplet rd = new ResizeDroplet();
                rd.execute(token);

                Intent i = new Intent(getActivity(),MainActivity.class);
                i.putExtra("TOKEN",token);
                startActivity(i);

            }
        });

        return resizeView;
    }

    class ResizeDroplet extends AsyncTask<String, Void, String[]>{


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

                /*Intent resizeIntent = getActivity().getIntent();
                dropletID = resizeIntent.getExtras().getString("dropletID");*/

                Log.v("ID BCKGRD ",dropletID);

                Log.v("RESIZE ******  ",resizeValue);

                final String RESIZE_DROPLET_BASE_URL = "https://api.digitalocean.com/v2/droplets/"+dropletID+"/actions";

                Uri builtUri = Uri.parse(RESIZE_DROPLET_BASE_URL).buildUpon().build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestProperty("Authorization","Bearer "+params[0]);

                urlConnection.connect();

                Map<String, String> hmap = new HashMap<>();

                hmap.put("type","resize");
                hmap.put("disk","true");
                hmap.put("size",resizeValue);

                JSONObject jparam = new JSONObject(hmap);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jparam.toString());
                out.close();

                HttpResult = urlConnection.getResponseCode();

                Log.v("RESIZE RESPONSE CODE ", String.valueOf(HttpResult));
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
