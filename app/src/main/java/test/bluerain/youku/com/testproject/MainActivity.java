package test.bluerain.youku.com.testproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import test.bluerain.youku.com.testproject.ui.GameFlowTagView;
import test.bluerain.youku.com.testproject.ui.GameTagGradview;

public class MainActivity extends AppCompatActivity {
    private Button mButtonChange;
    private TextView mTextViewTest;
    private GameFlowTagView mGameFlowTagView;

    private GameTagGradview mGameTagGradview;

    private List<String> mData = new ArrayList<>();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();


    }

    private void initview() {
        mTextViewTest = (TextView) findViewById(R.id.id_txv_test);

        String text = "click me <a href = \"http://www.baidu.com\"> baidu </a> " + "\n"
                + "asdfasdfsdfadf" +
                "asdfasdfadf" +
                "asdfasdfa" +
                "\n" +
                "asdfadsf" +
                "asdf" +
                "" +
                "adf" +
                "afdadfadf" +
                "\n";
        mTextViewTest.setText(Html.fromHtml(text));
        mTextViewTest.setMovementMethod(LinkMovementMethod.getInstance());


        mButtonChange = (Button) findViewById(R.id.id_btn_change_text_main);
        mButtonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        for (int i = 0; i < 33; i++) {
            mData.add(i + "Item");
        }
        mGameFlowTagView = (GameFlowTagView) findViewById(R.id.id_tag_view);
        mGameFlowTagView.setGameFlowTagViewAdapter(new GameFlowTagView.GameFlowTagViewAdapter<String>() {
            @Override
            public View convert(LayoutInflater inflater, String data, int position) {
                View view = inflater.inflate(R.layout.item_flow_tag, null);
                TextView textView = (TextView) view.findViewById(R.id.id_txv_game_detail_recomm_guid_tag);
                textView.setText(data);
                return view;
            }
        });
        mGameFlowTagView.setData(mData);
        mGameFlowTagView.setOnItemClickListener(new GameFlowTagView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "position : " + position + "", Toast.LENGTH_SHORT).show();
            }
        });


        //tag gradview
        mGameTagGradview = (GameTagGradview) findViewById(R.id.id_tag_grad_view);
        mGameTagGradview.setGameTagGradViewAdapter(new GameTagGradview.GameTagGradViewAdapter<String>() {
            @Override
            protected View convertView(String data, int position) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_flow_tag, null);
                TextView textView = (TextView) view.findViewById(R.id.id_txv_game_detail_recomm_guid_tag);
                textView.setText(data);
                return view;
            }

        });
        mGameTagGradview.setData(mData);

        //tag gradview end
    }

}
