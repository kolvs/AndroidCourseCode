package com.source.ui.materialDesign;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.source.ui.R;

public class BottomNavigationViewActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);

        mTextMessage = findViewById(R.id.message);
        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, mTextMessage.getText(), Snackbar.LENGTH_LONG).show();
            }
        });
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //导航栏菜单点击监听
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.navigation_dashboard) {
                mTextMessage.setText(R.string.title_dashboard);
            } else if (item.getItemId() == R.id.navigation_home) {
                mTextMessage.setText(R.string.title_home);
            } else if (item.getItemId() == R.id.navigation_notifications) {
                mTextMessage.setText(R.string.title_notifications);
            }
            return false;
        }
    };
}
