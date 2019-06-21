package com.assess15.ndk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class NDKActivity extends AppCompatActivity {

    {
        System.loadLibrary("hello-jni");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);

        TextView view = findViewById(R.id.tv);
        view.setText("nativeTest:" + nativeTest());
    }

    native int nativeTest();
}
