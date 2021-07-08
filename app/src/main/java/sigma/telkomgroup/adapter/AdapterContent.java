package sigma.telkomgroup.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.model.ModelContent;

/**
 * Created by biting on 10/03/16.
 */
public class AdapterContent extends BaseAdapter {

    Typeface font,fontbold;
    private Context mContext;
    private List<ModelContent> mListInfo, filterd;
    private ModelContent model;
    private TextView judul;

    public AdapterContent(Context context, List<ModelContent> list) {
        this.mContext = context;
        this.mListInfo = list;

        this.filterd = this.mListInfo;
        //        TextView
        font = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-SemiBold.otf");

    }

    @Override
    public int getCount() {
        return filterd.size();
    }

    @Override
    public Object getItem(int pos) {
        return filterd.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        // get selected entry
        model = filterd.get(pos);

        // inflating list view layout if null
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_list_content, null);
        }

        // set text
        judul = (TextView) convertView.findViewById(R.id.textTitleContent);

        judul.setText(model.getContent_title().trim() + ".pdf");
        judul.setTypeface(fontbold);

        return convertView;
    }
}
