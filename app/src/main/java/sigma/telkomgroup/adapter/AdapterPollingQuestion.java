package sigma.telkomgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.model.ModelPolling;

/**
 * Created by biting on 10/03/16.
 */
public class AdapterPollingQuestion extends BaseAdapter  {

    private Context mContext;
    private List<ModelPolling> mListInfo, filterd;
    private ModelPolling model;
    private TextView question;

    public AdapterPollingQuestion(Context context, List<ModelPolling> list) {
        this.mContext = context;
        this.mListInfo = list;

        this.filterd = this.mListInfo;
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
            convertView = inflater.inflate(R.layout.item_list_question_polling, null);
        }

        question = (TextView) convertView.findViewById(R.id.textQuestionPolling);
        question.setText(model.getQuestion());

        return convertView;
    }
}
