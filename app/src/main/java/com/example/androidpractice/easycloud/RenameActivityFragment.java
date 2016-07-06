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
import android.widget.EditText;

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
public class RenameActivityFragment extends Fragment {

    String dropletID;
    String dropletName;
    Button renameButton;
    String renameStr,token;
    EditText newName;

    public RenameActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View renameView = inflater.inflate(R.layout.fragment_rename, container, false);

        Intent renameIntent = getActivity().getIntent();
        dropletID = renameIntent.getExtras().getString("dropletId");
        dropletName = renameIntent.getExtras().getString("dropletName");
        token = renameIntent.getExtras().getString("TOKEN");

        EditText editTextRename = (EditText)renameView.findViewById(R.id.editTextRename);
        editTextRename.setText(dropletName);

        renameButton = (Button)renameView.findViewById(R.id.buttonRename);
        renameButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                newName = (EditText)renameView.findViewById(R.id.editTextRename);
                renameStr = newName.getText().toString();

                Log.v("RENAME STR ",renameStr);


                RenameDroplets rd = new RenameDroplets();
                rd.execute(token);

                Intent i = new Intent(getActivity(),MainActivity.class);
                i.putExtra("TOKEN",token);
                startActivity(i);
            }
        });


        return renameView;
    }


    class RenameDroplets extends AsyncTask<String, Void, String[]>{

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

                final String RENAME_DROPLET_BASE_URL = "https://api.digitalocean.com/v2/droplets/"+dropletID+"/actions";

                Uri builtUri = Uri.parse(RENAME_DROPLET_BASE_URL).buildUpon().build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", "Bearer "+params[0]);

                urlConnection.connect();

                Map<String, String> hmap = new HashMap<>();

                hmap.put("type", "rename");
                hmap.put("name", renameStr);

                JSONObject jparam = new JSONObject(hmap);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jparam.toString());
                out.close();

                HttpResult = urlConnection.getResponseCode();

                Log.v("RENAME RESPONSE CODE ", String.valueOf(HttpResult));
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
