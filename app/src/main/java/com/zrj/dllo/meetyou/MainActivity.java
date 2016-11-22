package com.zrj.dllo.meetyou;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.personal.PersonalFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersonalFragment fragment = new PersonalFragment();
        getSupportFragmentManager().beginTransaction().add(android.R.id.content,fragment).commit();


    }
}
