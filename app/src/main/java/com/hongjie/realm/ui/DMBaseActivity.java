package com.hongjie.realm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.hongjie.realm.R;

/**
 * Created by hongjiedong on 6/19/16.
 */
public abstract class DMBaseActivity extends AppCompatActivity {
    @BindView(R.id.top_toolbar)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getMainContentViewId());
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    protected abstract int getMainContentViewId ();

    protected int getMainFragmentContainerViewId () {
        return R.id.content_frame;
    }

    protected void addFragment (String tag, Fragment f) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
            return;
        }
        ft.add(getMainFragmentContainerViewId(), f, tag).commit();
    }
}
