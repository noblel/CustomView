package com.noblel.quickindex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements IndexView.LetterTouchListener {

    private TextView mLetterTv;
    private IndexView mIndexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLetterTv = findViewById(R.id.letter_tv);
        mIndexView = findViewById(R.id.index_view);
        mIndexView.setOnLetterTouchListener(this);
    }

    @Override
    public void touch(CharSequence letter, boolean isTouch) {
        if (isTouch) {
            mLetterTv.setVisibility(View.VISIBLE);
            mLetterTv.setText(letter);
        } else {
            mLetterTv.setVisibility(View.GONE);
        }
    }
}

