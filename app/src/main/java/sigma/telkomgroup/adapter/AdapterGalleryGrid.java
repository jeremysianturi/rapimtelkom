package sigma.telkomgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.model.ModelGallery;

/**
 * Created by Biting on 7/28/2017.
 */

public class AdapterGalleryGrid extends BaseAdapter {
    private List<ModelGallery> mDataList;
    private ModelGallery model;
    private Context mContext;

    public AdapterGalleryGrid(Context context, List<ModelGallery> dataList) {
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        model = mDataList.get(position);

        // inflating list view layout if null
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.item_grid_gallery, null);
        }

        ImageView mImageView = (ImageView) view.findViewById(R.id.grid_image);

        mImageView.setVisibility(View.VISIBLE);

        if (model.getNamaGallery() != null) {
            String imgURL = model.getNamaGallery();
            //new DownLoadImageTask(mImageView).execute(imgURL);
            downloadImage(mContext, imgURL, mImageView);
//            mImageView.setBackground(null);
        } else {
            mImageView.setImageResource(R.drawable.rapim_logo);
        }

        return view;
    }

    private void downloadImage(Context context, String url, ImageView image) {
        Picasso.get()
                .load(url)
                .error(R.drawable.logo_rapim)
                .into(image);
    }
}
