package cn.bleu.slidedetailsdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.bleu.slidedetailsdemo.fragment.ListFragment;
import cn.bleu.widget.slidedetails.SlideDetailsLayout;

public class MainActivity extends AppCompatActivity implements ISlideCallback {

    private SlideDetailsLayout mSlideDetailsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlideDetailsLayout = (SlideDetailsLayout) findViewById(R.id.slidedetails);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.slidedetails_front, new ListFragment()).commit();

//        final ListView frontListView = (ListView) findViewById(R.id.slidedetails_front);
//        frontListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "clicked: " + position, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        List<String> datas = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            datas.add("data: " + i);
//        }
//
//        final View footView = getLayoutInflater()
//                .inflate(R.layout.slidedetails_marker_default_layout, null);
//        footView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                slideDetailsLayout.smoothOpen(false);
//            }
//        });
//
//        frontListView.addFooterView(footView);
//        frontListView.setAdapter(new Adapter(datas));

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

    @Override
    public void openDetails(boolean smooth) {
        mSlideDetailsLayout.smoothOpen(smooth);
    }

    @Override
    public void closeDetails(boolean smooth) {
        mSlideDetailsLayout.smoothClose(smooth);
    }

//    private class Adapter extends BaseAdapter {
//
//        private List<String> datas;
//
//        Adapter(List<String> datas) {
//            this.datas = datas;
//        }
//
//        @Override
//        public int getCount() {
//            return null == datas ? 0 : datas.size();
//        }
//
//        @Override
//        public String getItem(int position) {
//            return null == datas ? null : datas.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (null == convertView) {
//                convertView = getLayoutInflater().inflate(R.layout.layout_list_item, null);
//            }
//            final TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
//            textView.setText(getItem(position));
//            return convertView;
//        }
//    }
}
