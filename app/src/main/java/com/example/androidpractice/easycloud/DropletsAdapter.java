package com.example.androidpractice.easycloud;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by anuj on 6/4/2016.
 */

    public class DropletsAdapter extends RecyclerView.Adapter<DropletsAdapter.MyViewHolder> {

        private List<DropletModel> dropletList;
        private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView title, region;
            public ImageView image;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                region = (TextView) view.findViewById(R.id.region);
                image = (ImageView) view.findViewById(R.id.imageview);
                title.setOnClickListener(this);
                region.setOnClickListener(this);
                image.setOnClickListener(this);
            }


            @Override
            public void onClick(View v) {
                Context context = itemView.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", String.valueOf(dropletList.get(getAdapterPosition()).getTitle()));
                intent.putExtra("region", String.valueOf(dropletList.get(getAdapterPosition()).getRegion()));

                context.startActivity(intent);

            }
        }


        public DropletsAdapter(Context context,List<DropletModel> dropletList) {
            this.context = context;
            this.dropletList = dropletList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.droplet_list_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DropletModel dropletDescription = dropletList.get(position);


            String[] changeRegionValue = dropletDescription.getRegion().split("/");
            String res = "";

            for (int i =1;i< changeRegionValue.length-4;i++){
                    if(i != changeRegionValue.length-5){
                        res = res+ changeRegionValue[i]+"/";
                    }
                else{
                        res = res + changeRegionValue[i];
                    }

            }

            Log.v(" RESULT", res);
            holder.title.setText(dropletDescription.getTitle());
            holder.region.setText(res);

            //holder.region.setText(dropletDescription.getRegion());
            Picasso.with(context).load(dropletList.get(position).getImage()).resize(100,100).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return dropletList.size();
        }
    }


