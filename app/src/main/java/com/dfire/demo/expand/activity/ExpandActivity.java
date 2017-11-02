package com.dfire.demo.expand.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alex.dagger.R;

public class ExpandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);
        RelativeLayout rl_text = (RelativeLayout) findViewById(R.id.rl_text);
        rl_text.addView(createTextView());
        rl_text.addView(createEditTextView());
    }

    private View createTextView() {
        TextView textView = new TextView(this);
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setText("dsadsadsadsadsadsa");
        textView.setTextSize(20);
        return textView;
    }

    private View createEditTextView() {
        EditText textView = new EditText(this);
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return textView;
    }
}
