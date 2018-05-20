package com.noblel.slideview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<Joke> mJokes;
    private MyAdapter mAdapter;
    private SlideLayout mSlideLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.lv_main);
        mJokes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mJokes.add(new Joke("这是?" + i));
        }
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mJokes.size();
        }

        @Override
        public Object getItem(int position) {
            return mJokes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_main, null);
                viewHolder = new ViewHolder();
                viewHolder.item_content = convertView.findViewById(R.id.item_content);
                viewHolder.item_menu = convertView.findViewById(R.id.item_menu);
                viewHolder.tv_top =  viewHolder.item_menu.findViewById(R.id.tv_top);
                viewHolder.tv_no_read =  viewHolder.item_menu.findViewById(R.id.tv_no_read);
                viewHolder.tv_delete =  viewHolder.item_menu.findViewById(R.id.tv_delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            SlideLayout slideLayout = (SlideLayout) convertView;
            slideLayout.setOnStateChangeListener(new MyOnStateChangeListener());
            //复用会把所有状态都复用过来,所以要恢复默认状态
            slideLayout.closeMenu();
            //根据位置得到内容
            final Joke joke = mJokes.get(position);
            viewHolder.item_content.setText(joke.getContent());
            viewHolder.item_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Joke joke1 = mJokes.get(position);
                    Toast.makeText(MainActivity.this, joke1.getContent(), Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.tv_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "置顶：" + joke.getContent(), Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.tv_no_read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "标记未读：" + joke.getContent(), Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "删除：" + joke.getContent(), Toast.LENGTH_SHORT).show();
                    SlideLayout slideLayout = (SlideLayout) v.getParent().getParent();
                    slideLayout.closeMenu();
                    mJokes.remove(joke);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }


    class MyOnStateChangeListener implements SlideLayout.OnStateChangeListener {

        @Override
        public void onClose(SlideLayout layout) {
            if (mSlideLayout == layout) {
                mSlideLayout = null;
            }
        }

        @Override
        public void onDown(SlideLayout layout) {
            if (mSlideLayout != null && mSlideLayout != layout) {
                mSlideLayout.closeMenu();
            }
        }

        @Override
        public void onOpen(SlideLayout layout) {
            mSlideLayout = layout;
        }
    }

    static class ViewHolder {
        TextView item_content;
        LinearLayout item_menu;
        TextView tv_top;
        TextView tv_no_read;
        TextView tv_delete;
    }
}
