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
import sigma.telkomgroup.model.ModelHistory;

public class AdapterHistory extends BaseAdapter {

    Typeface font,fontbold;
    private Context mContext;
    private List<ModelHistory> mListInfo;
    private ModelHistory model;

    private TextView tvDate;
    private TextView tvQuestion;
    private TextView tvDirektorat;

    public AdapterHistory(Context context, List<ModelHistory> list) {
        this.mContext = context;
        this.mListInfo = list;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.otf");
        fontbold = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-SemiBold.otf");

    }

    @Override
    public int getCount() {
        return mListInfo.size();
    }

    @Override
    public Object getItem(int pos) {
        return mListInfo.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        // get selected entry
        model = mListInfo.get(pos);

        // inflating list view layout if null
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_list_histori, null);
        }

        // set text
        tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        tvQuestion = (TextView) convertView.findViewById(R.id.tvQuestion);
        tvDirektorat = (TextView) convertView.findViewById(R.id.tvDirektori);

        tvDate.setText(model.getDate());
        tvQuestion.setText(model.getQuestion());
        tvDirektorat.setText(model.getDirektorat());

//        judul.setTypeface(fontbold);

        return convertView;
    }
}
