package sigma.telkomgroup.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sigma.telkomgroup.R;
import sigma.telkomgroup.model.ModelMeeting;

/**
 * Created by biting on 10/03/16.
 */
public class AdapterMyAgenda extends BaseAdapter  {

    Typeface font,fontbold;
    private Context mContext;
    private List<ModelMeeting> mListInfo, filterd;
    private ModelMeeting modelMeeting;

    public AdapterMyAgenda(Context context, List<ModelMeeting> list) {
        this.mContext = context;
        this.mListInfo = list;

        this.filterd = this.mListInfo;
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
        modelMeeting = filterd.get(pos);

        // inflating list view layout if null
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_list_agenda, null);
        }

        String tgl = modelMeeting.getAgenda_date();
        Calendar cal = Calendar.getInstance();
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(tgl);
            cal.setTime(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String fdate = new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime());

//        ImageView img = (ImageView) convertView.findViewById(R.id.imageContentAgenda);

        // set text
        TextView txtName = (TextView) convertView.findViewById(R.id.textMeetingName);
        txtName.setText(modelMeeting.getAgenda_name());
        txtName.setTypeface(fontbold);
        String temp = modelMeeting.getAgenda_name();
//        if(temp!=null ){
//            if(temp.toLowerCase().contains("raga")){
//                img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_olah_raga));
//            }else if(temp.toLowerCase().contains("rasio")){
//                img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_olah_rasio));
//            }else if(temp.toLowerCase().contains("rasa")){
//                img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_olah_rasa));
//            }else if(temp.toLowerCase().contains("ruh")){
//                img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_olah_ruh));
//            }
//        }

//        TextView txtDate = (TextView) convertView.findViewById(R.id.textMeetingTheme);
//        txtDate.setText(modelMeeting.getTema_name());
//        txtDate.setTypeface(font);

        TextView txtTime = (TextView) convertView.findViewById(R.id.textMeetingDate);
        TextView tvNo = (TextView) convertView.findViewById(R.id.tvNo);
        String nomer = String.valueOf(pos+1);
        tvNo.setText(nomer);
        txtTime.setText(fdate);
//        txtTime.setTypeface(fontbold);

//        TextView txtLocation = (TextView) convertView.findViewById(R.id.textMeetingLocation);
//        txtLocation.setText(modelMeeting.getMeeting_location());
//        txtLocation.setTypeface(font);

        return convertView;
    }
}
