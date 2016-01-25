package cn.bleu.slidedetailsdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        frontListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "clicked: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("data: " + i);
        }

        final View footView = getLayoutInflater()
                .inflate(R.layout.slidedetails_marker_default_layout, null);
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDetailsLayout.smoothOpen(false);
            }
        });

        frontListView.addFooterView(footView);
        frontListView.setAdapter(new Adapter(datas));

        final WebView webView = (WebView) findViewById(R.id.slidedetails_behind);
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                public void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
        }

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

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
//                slideDetailsLayout.smoothOpen(true);
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

    private class Adapter extends BaseAdapter {

        private List<String> datas;

        Adapter(List<String> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return null == datas ? 0 : datas.size();
        }

        @Override
        public String getItem(int position) {
            return null == datas ? null : datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = getLayoutInflater().inflate(R.layout.layout_list_item, null);
            }
            final TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
            textView.setText(getItem(position));
            return convertView;
        }
    }
}
