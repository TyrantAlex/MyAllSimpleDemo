package com.myallsimpledemo.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.example.alex.dagger.R;

public class NotificationResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_result);
        Toast.makeText(this, "NotificationResultActivity", Toast.LENGTH_SHORT).show();
    }
}
