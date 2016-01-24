package cn.bleu.slidedetailsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bleu.widget.slidedetails.SlideDetailsLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SlideDetailsLayout slideDetailsLayout = (SlideDetailsLayout) findViewById(R.id.slidedetails);
        final ListView frontListView = (ListView) findViewById(R.id.slidedetails_front);
        frontListView.setAdapter(getListAdapter());
        frontListView.addFooterView(getLayoutInflater().inflate(R.layout.slidedetails_marker_default_layout,
                                                                null));

        final WebView webView = (WebView) findViewById(R.id.slidedetails_behind);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("http://www.cnbleu.com");

            }
        });


//        final ListView behindListView = (ListView) findViewById(R.id.slidedetails_behind);
//        behindListView.setAdapter(getListAdapter());
//
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                slideDetailsLayout.smoothToOpen(true);
//            }
//        }, 500);
    }

    private ListAdapter getListAdapter() {
        List<String> datas = new ArrayList<>();
        Map<String, String> map;
        for (int i = 0; i < 50; i++) {
//            map = new HashMap<>();
//            map.put("key", "datadatadatadatadatadatadatadatadatadatadatadata::: " + i);
            datas.add("data: " + i);
        }

        return new ArrayAdapter<String>(this, R.layout.layout_list_item, android.R.id.text1, datas);
//        return new ArrayAdapter<String>(this,
//                                datas,
//                                android.R.layout.activity_list_item,
//                                new String[]{"key"},
//                                new int[]{android.R.id.text1});
    }
}
