package sigma.telkomgroup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import java.util.List;

import sigma.telkomgroup.R;
import sigma.telkomgroup.model.ModelChoice;

/**
 * Created by biting on 10/03/16.
 */
public class AdapterPollingChoice extends BaseAdapter  {

    private Context mContext;
    private List<ModelChoice> mListInfo, filterd;
    private ModelChoice model;
    private RadioButton choice;

    public AdapterPollingChoice(Context context, List<ModelChoice> list) {
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
            convertView = inflater.inflate(R.layout.item_list_polling_choice, null);
        }

        choice = (RadioButton) convertView.findViewById(R.id.radioButton);
        choice.setText(model.getChoice_list());

        return convertView;
    }
}
