package sigma.telkomgroup.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.drawer.NavDrawerItem;

/**
 * Created by biting on 16/03/16.
 */

public class AdapterNavigation extends RecyclerView.Adapter<AdapterNavigation.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public AdapterNavigation(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drawer_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        String tit = current.getTitle();
        holder.title.setText(tit);
        if (tit.trim().equals("News")) {
            holder.image.setImageResource(R.drawable.menu_news);
        } else if (tit.trim().equals("Rundown")) {
            holder.image.setImageResource(R.drawable.menu_rundown);
        } else if (tit.trim().equals("Venue")) {
            holder.image.setImageResource(R.drawable.menu_venue);
        } else if (tit.trim().equals("Content")) {
            holder.image.setImageResource(R.drawable.menu_content);
        } else if (tit.trim().equals("Selfie")) {
            holder.image.setImageResource(R.drawable.menu_selfie);
        } else if (tit.trim().equals("Shoutbox")) {
            holder.image.setImageResource(R.drawable.menu_shoutbox);
        } else if (tit.trim().equals("Feedback")) {
            holder.image.setImageResource(R.drawable.menu_quisioner);
        } else if (tit.trim().equals("Logout")) {
            holder.image.setImageResource(R.drawable.menu_setting);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.imageMenu);
        }
    }
}
