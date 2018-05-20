package com.realm.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.alex.dagger.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解析json测试
 * Created by shen on 2018/5/4.
 */

public class JsonParseActivity extends Activity {


    private String json = "window.__jsonp_callback([\"{\\\"attributes\\\":[{\\\"name\\\":\\\"mattr01v1\\\",\\\"value\\\":{\\\"type\\\":\\\"Integer\\\",\\\"mode\\\":\\\"literal\\\",\\\"value\\\":123}}],\\\"interactions\\\":[],\\\"components\\\":[{\\\"identifier\\\":\\\"testc003\\\",\\\"attributes\\\":[],\\\"interactions\\\":[]},{\\\"identifier\\\":\\\"testc002\\\",\\\"attributes\\\":[],\\\"interactions\\\":[]},{\\\"identifier\\\":\\\"testc001\\\",\\\"attributes\\\":[],\\\"interactions\\\":[]}]}\"])";

    private String jsonHeader = "window.__jsonp_callback(";

    private String jsonTail = ")\"";

    private void startParse(){
        String jsonSub = json.substring(jsonHeader.length(), json.length() - jsonTail.length()+1);
        Log.d("sqs", jsonSub);
        try {
            JSONArray jsonArray = new JSONArray(jsonSub);
            if (jsonArray != null && jsonArray.length() != 0) {
                String string = jsonArray.getString(0);
                Log.d("sqs", string);
                JSONObject jsonObject = new JSONObject(string);
                Log.d("sqs", "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parse);
        Button btn = (Button) findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startParse();
            }
        });
    }
}
