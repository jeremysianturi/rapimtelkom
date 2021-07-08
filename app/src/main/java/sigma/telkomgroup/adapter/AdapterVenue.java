package sigma.telkomgroup.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sigma.telkomgroup.R;
import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.controller.ControllerVenueZoom;
import sigma.telkomgroup.model.ModelVenue;

/**
 * Created by biting on 17/03/16.
 */

public class AdapterVenue extends RecyclerView.Adapter<AdapterVenue.MyViewHolder> {
    private ModelVenue model;
    private ArrayList<ModelVenue> dataSet;
    public AdapterVenue(ArrayList<ModelVenue> listModel) {
        this.dataSet = listModel;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivBerita;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvCaption);
//            this.tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            this.ivBerita = (ImageView) itemView.findViewById(R.id.imagePOI);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), " On CLick one" +getPosition(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid_venue, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Context context = holder.ivBerita.getContext(); //<----- Add this line
        model = dataSet.get(position);
        TextView tv_title = holder.tvTitle;
//        TextView tv_desc = holder.tvDesc;
        ImageView ivBerita = holder.ivBerita;
        tv_title.setText(model.getVenue_title());
//        makeTextViewResizable(tv_desc, 2, model.getDesc(), false);
//        tv_desc.setText(model.getDesc());
        downloadImage(context, model.getVenue_image(), ivBerita);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Intent intent = new Intent(c, ControllerVenueZoom.class);
                intent.putExtra(ConstantUtil.VENUE.TAG_VENUE_TITLE, dataSet.get(position).getVenue_title());
                intent.putExtra(ConstantUtil.VENUE.TAG_IMAGE, dataSet.get(position).getVenue_image());
                intent.putExtra(ConstantUtil.VENUE.TAG_VENUE_CAPTION, dataSet.get(position).getVenue_caption());
                c.startActivity(intent);
            }
        });

    }
    private void downloadImage(Context context, String url, ImageView image) {
        Picasso.get()
                .load(url)
                .error(R.drawable.news_icon)
//                .fit()
                .into(image);
    }

    @Override
    public int getItemCount() {
        if (dataSet.size() == 0) {
            return 0;
        } else {
            return dataSet.size();
        }
    }
}

//public class AdapterVenue extends ArrayAdapter<ModelVenue> {
//
//    Typeface font,fontbold;
//    ArrayList<ModelVenue> venueList;
//    LayoutInflater vi;
//    int Resource;
//    ViewHolder holder;
//
//    public AdapterVenue(Context context, int resource, ArrayList<ModelVenue> objects) {
//        super(context, resource, objects);
//        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Resource = resource;
//        venueList = objects;
//
//        font = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.otf");
//        fontbold = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-SemiBold.otf");
//
//    }
//
//    @Override
//    public View getView(int position, final View convertView, ViewGroup parent) {
//        // convert view = design
//        View v = convertView;
//        if (v == null) {
//            holder = new ViewHolder();
//            v = vi.inflate(Resource, null);
//            holder.imageview = (ImageView) v.findViewById(R.id.imagePOI);
//            holder.tvName = (TextView) v.findViewById(R.id.textPOITitle);
//            holder.tvName.setTypeface(fontbold);
//            v.setTag(holder);
//        } else {
//            holder = (ViewHolder) v.getTag();
//        }
//        holder.imageview.setImageResource(R.drawable.loading);
//        new DownloadImageTask(holder.imageview).execute(venueList.get(position).getVenue_image());
//        holder.tvName.setText(venueList.get(position).getVenue_title());
//
//        return v;
//
//    }
//
//    static class ViewHolder {
//        public ImageView imageview;
//        public TextView tvName;
//    }
//
//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }
//}
