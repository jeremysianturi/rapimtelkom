package sigma.telkomgroup.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sigma.telkomgroup.R;
import sigma.telkomgroup.model.ModelNews;

/**
 * Created by biting on 10/03/16.
 */
public class AdapterNews extends BaseAdapter  {

    private Context mContext;
    Typeface font,fontbold;
    private List<ModelNews> mListInfo, filterd;
    private ModelNews model;
    private TextView title;
    private TextView date;
//    private TextView conte;
private ImageView icon;
    private TextView tvNo;

    public AdapterNews(Context context, List<ModelNews> list) {
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


//        CardView card = (CardView) convertView.findViewById(R.id.cardout);
//        card.setCardElevation(0);

        String tgl = model.getNews_date();
        Calendar cal = Calendar.getInstance();
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(tgl);
            cal.setTime(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String fdate = new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime());
        // inflating list view layout if null
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_list_news, null);
        }

        title = (TextView) convertView.findViewById(R.id.textNewsTitle);
        title.setTypeface(fontbold);
        date = (TextView) convertView.findViewById(R.id.textNewsDate);
        tvNo = (TextView) convertView.findViewById(R.id.tvNo);
        tvNo.setText(String.valueOf(pos+1));
        date.setTypeface(font);
//        conte = (TextView) convertView.findViewById(R.id.textNewsContent);
//        conte.setTypeface(font);
//        icon = (ImageView) convertView.findViewById(R.id.imageNewLogo);

        title.setText(model.getNews_title());
        //Log.d("hasil model adapter", model.getNews_title());
        date.setText(fdate);
//        conte.setText(model.getNews_content());

//        if(model.getNews_status().equals("0")){
//            icon.setImageResource(R.drawable.new_icon);
//            Log.d("status1", model.getNews_status());
//        } else {
//            icon.setVisibility(View.GONE);
//            Log.d("status", model.getNews_status());
//        }

        return convertView;
    }
}
