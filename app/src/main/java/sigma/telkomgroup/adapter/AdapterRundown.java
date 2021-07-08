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
import sigma.telkomgroup.model.ModelRundown;

/**
 * Created by biting on 10/03/16.
 */
public class AdapterRundown extends BaseAdapter  {

    Typeface font,fontbold;
    private Context mContext;
    private List<ModelRundown> mListInfo, filterd;
    private ModelRundown model;

    public AdapterRundown(Context context, List<ModelRundown> list) {
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
            convertView = inflater.inflate(R.layout.item_list_rundown, null);
        }



        // set text
//        TextView txtNumber = (TextView) convertView.findViewById(R.id.textRundownNumber);
//        txtNumber.setText(model.getNomor());
//        txtNumber.setTypeface(fontbold);
        TextView txtContent = (TextView) convertView.findViewById(R.id.textRundownContent);
        txtContent.setText(model.getRundown_name());
        txtContent.setTypeface(fontbold);

        TextView txtTime = (TextView) convertView.findViewById(R.id.textRundownTime);
        TextView tvNo = (TextView) convertView.findViewById(R.id.tvNo);
        tvNo.setText(String.valueOf(pos+1));
        txtTime.setText(model.getRundown_time());
        txtTime.setTypeface(fontbold);

//        TextView txtDesc = (TextView) convertView.findViewById(R.id.textRundownDesc);
//        txtDesc.setText(model.getRundown_desc());
//        txtDesc.setTypeface(font);

        return convertView;
    }
}
