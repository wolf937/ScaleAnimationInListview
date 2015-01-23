package com.github.lorcan.scale;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * Created by luocan on 15/1/22.
 */
public class MainActivity extends Activity implements MyScrollView.OnScrollChangedListener {
    private ListViewInScollView listView;
    private View headLayout;
    private MyHeaderView headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        findViewById(R.id.parent_view).setOnTouchListener(this);

        MyScrollView myScrollView = (MyScrollView) findViewById(R.id.scrollView);
        myScrollView.setOnScrollChangedListener(this);
        headerView = (MyHeaderView) findViewById(R.id.headerView);
        headLayout = findViewById(R.id.header_icon_layout);

        listView = (ListViewInScollView) findViewById(R.id.listView);
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        headerView.setOnTopListener(new MyHeaderView.ScrollTopListener() {
            @Override
            public void onScrollToTop() {
                headLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScrollNotTop() {
                headLayout.setVisibility(View.INVISIBLE);
            }
        });


    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        headerView.updateHeaderView(t);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MainActivity.this);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
            textView.setText(position + "");
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(params);

            return textView;
        }
    }
}

