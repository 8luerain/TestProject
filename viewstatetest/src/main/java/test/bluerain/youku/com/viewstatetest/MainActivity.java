package test.bluerain.youku.com.viewstatetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mData.add(i + "Itme ...");
        }
    }

    private void initView() {

        mListView = (ListView) findViewById(R.id.id_lv_main);
        mListView.setAdapter(new ListViewAdapter());

    }

    private class ListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_listview_main, null);
                ItemViewHolder holder = new ItemViewHolder();
                holder.mTextView = (TextView) convertView.findViewById(R.id.id_txv_item_listview_main);
                convertView.setTag(holder);
            }
            ItemViewHolder holder = (ItemViewHolder) convertView.getTag();
            holder.mTextView.setText((String) getItem(position));
            return convertView;
        }

        class ItemViewHolder {
            public TextView mTextView;
        }
    }
}
