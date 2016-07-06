package com.example.androidpractice.easycloud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivityFragment extends Fragment {

    String dropletsDetailsStr;
    String[] splitString;
    String[] dropletsStr;
    String[] dropletsDetailsStrSplit;
    TextView textviewSwitch;
    Switch switchOnOff;
    String region,title;
    String tmpStatus, token, created, ip4, ip6;
    ArrayAdapter adapter;

    public DetailActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_detail, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        dropletsStr = region.split("/");
        Log.v("OPTIONS************", token);
        if(item.getItemId() == R.id.action_resize){
            Intent resizeIntent = new Intent(getActivity(),ResizeActivity.class);
            resizeIntent.putExtra("dropletID", dropletsStr[0]);
            resizeIntent.putExtra("TOKEN", token);
            startActivity(resizeIntent);

        }

        if(item.getItemId() == R.id.action_rename){

            Intent renameIntent = new Intent(getActivity(),RenameActivity.class);
            renameIntent.putExtra("dropletId", dropletsStr[0]);
            renameIntent.putExtra("dropletName", title);
            renameIntent.putExtra("TOKEN", token);
            startActivity(renameIntent);

        }

        if(item.getItemId() == R.id.action_enable_backups){

            Intent enableBackupsIntent = new Intent(getActivity(),EnableBackupsActivity.class);
            enableBackupsIntent.putExtra("dropletId",dropletsStr[0]);
            enableBackupsIntent.putExtra("TOKEN", token);
            startActivity(enableBackupsIntent);

        }

        if (item.getItemId() == R.id.action_disable_backups){

            Intent disableBackupsIntent = new Intent(getActivity(),DisableBackupsActivity.class);
            disableBackupsIntent.putExtra("dropletId", dropletsStr[0]);
            disableBackupsIntent.putExtra("TOKEN", token);
            startActivity(disableBackupsIntent);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dropletDetailsView =  inflater.inflate(R.layout.fragment_detail, container, false);

        Intent i = getActivity().getIntent();
        title = i.getExtras().getString("title");
        region = i.getExtras().getString("region");

        String[] tmpDescriptionArr = region.split("/");

        String tmpRegion = tmpDescriptionArr[3];


        tmpStatus = tmpDescriptionArr[4];

        Log.v(" TRY ###############",tmpStatus);
        token = tmpDescriptionArr[5];
        created = tmpDescriptionArr[6];
        ip4 = tmpDescriptionArr[7];
        ip6 = tmpDescriptionArr[8];

        TextView dropletDetailsTextView = (TextView)dropletDetailsView.findViewById(R.id.valueTitle);
        dropletDetailsTextView.setText(title);


        TextView dropletDetailsTextView1 = (TextView)dropletDetailsView.findViewById(R.id.valueID);
        dropletDetailsTextView1.setText(tmpDescriptionArr[0]);

        TextView dropletDetailsTextView2 = (TextView)dropletDetailsView.findViewById(R.id.valueMemory);
        dropletDetailsTextView2.setText(tmpDescriptionArr[1]);

        TextView dropletDetailsTextView3 = (TextView)dropletDetailsView.findViewById(R.id.valueDisk);
        dropletDetailsTextView3.setText(tmpDescriptionArr[2]);

        TextView dropletDetailsTextView4 = (TextView)dropletDetailsView.findViewById(R.id.valueRegion);

        if(tmpRegion.contentEquals(" ams2") || tmpRegion.contentEquals(" ams3")){
            dropletDetailsTextView4.setText(" Amsterdam");
        }
        if(tmpRegion.contentEquals(" nyc1") || tmpRegion.contentEquals(" nyc2") || tmpRegion.contentEquals(" nyc3")){
            dropletDetailsTextView4.setText(" New York");
        }
        if(tmpRegion.contentEquals(" sfo1")){
            dropletDetailsTextView4.setText(" San Francisco");
        }
        if(tmpRegion.contentEquals(" sgp1")){
            dropletDetailsTextView4.setText(" Singapore");
        }
        if(tmpRegion.contentEquals(" lon1")){
            dropletDetailsTextView4.setText(" London");
        }
        if(tmpRegion.contentEquals(" blr1")){
            dropletDetailsTextView4.setText(" Banglore");
        }
        if(tmpRegion.contentEquals(" tor1")){
            dropletDetailsTextView4.setText(" Toronto");
        }
        if(tmpRegion.contentEquals(" fra1")){
            dropletDetailsTextView4.setText(" Frankfurt");
        }


        TextView dropletDetailsTextView5 = (TextView)dropletDetailsView.findViewById(R.id.valueCreated);
        dropletDetailsTextView5.setText(tmpDescriptionArr[6]);

        TextView dropletDetailsTextView6 = (TextView)dropletDetailsView.findViewById(R.id.valueIPv4);
        dropletDetailsTextView6.setText(tmpDescriptionArr[7]);

        TextView dropletDetailsTextView7 = (TextView)dropletDetailsView.findViewById(R.id.valueIPv6);
        dropletDetailsTextView7.setText(tmpDescriptionArr[8]);

        Button deleteButton = (Button)dropletDetailsView.findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());

                        builder.setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.delete_red)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                deleteDroplet dd = new deleteDroplet();
                                dd.execute(token);

                                Intent deleteIntent = new Intent(getActivity(), MainActivity.class);
                                /*Bundle bundle = new Bundle();
                                bundle.putString("TOKEN", token);*/
                                deleteIntent.putExtra("TOKEN", token);
                                startActivity(deleteIntent);
                            }

                        })

                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .show();
            }
        });

        dropletsDetailsStrSplit = region.split("/");

        switchOnOff = (Switch)dropletDetailsView.findViewById(R.id.switch1);

        if(tmpStatus.contentEquals("active")){
            switchOnOff.setChecked(true);
            switchOnOff.setTextColor(Color.GREEN);
        }
        else {
            switchOnOff.setChecked(false);
            switchOnOff.setTextColor(Color.GRAY);
        }


        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Intent switchOnIntent = new Intent(getActivity(), SwitchOnActivity.class);
                    switchOnIntent.putExtra("dropletID", dropletsDetailsStrSplit[0]);
                    switchOnIntent.putExtra("TOKEN", token);
                    startActivity(switchOnIntent);

                } else {
                    Intent switchOffIntent = new Intent(getActivity(),SwitchOffActivity.class);
                    switchOffIntent.putExtra("dropletID",dropletsDetailsStrSplit[0]);
                    switchOffIntent.putExtra("TOKEN",token);
                    startActivity(switchOffIntent);
                }
            }
        });


        return dropletDetailsView;
    }



    class deleteDroplet extends AsyncTask<String,Void,String[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {

            splitString = region.split("/");

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            int HttpResult = 0;

            try {

                final String DELETE_DROPLET_BASE_URL = "https://api.digitalocean.com/v2/droplets/"+splitString[0];

                Uri builtUri = Uri.parse(DELETE_DROPLET_BASE_URL).buildUpon().build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("DELETE");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", "Bearer "+params[0]);

                urlConnection.connect();

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
