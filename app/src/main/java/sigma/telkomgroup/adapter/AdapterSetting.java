package sigma.telkomgroup.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sigma.telkomgroup.R;

/**
 * Created by biting on 25/07/16.
 */
public class AdapterSetting extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] list;
    private final Integer[] imageId;
    public AdapterSetting(Activity context,
                      String[] listMenu, Integer[] imageId) {
        super(context, R.layout.item_list_setting, listMenu);
        this.context = context;
        list = listMenu;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_list_setting, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textViewSetting);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewSetting);
        txtTitle.setText(list[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
