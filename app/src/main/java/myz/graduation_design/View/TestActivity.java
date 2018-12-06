package myz.graduation_design.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void startThread(){
        new Thread(){
            @Override
            public void run() {
                super.run();
            }
        }.start();
    }

    private void startRunnable(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        }).start();


    }
}
