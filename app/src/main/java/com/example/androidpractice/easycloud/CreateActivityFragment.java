package com.example.androidpractice.easycloud;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateActivityFragment extends Fragment {

    public CreateActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create, container, false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* if(item.getItemId() == R.id.action_refresh){

            CreateDroplets cd = new CreateDroplets();
            cd.execute();
        }*/

        return super.onOptionsItemSelected(item);
    }

}
