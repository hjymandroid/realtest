package com.hongjie.realm;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hongjie.realm.model.Topic;
import com.hongjie.realm.model.User;
import com.hongjie.realm.service.ApiService;
import com.hongjie.realm.service.TopicsDataSource;
import com.hongjie.realm.service.TopicsRepository;
import com.hongjie.realm.ui.CreateTopicFragment;
import com.hongjie.realm.ui.topic.TopicsRecyclerViewAdapter;

import java.util.HashMap;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    //private ApiService mApiService;
    private Realm mRealm;
    private EditText mEditName;
    private EditText mEditTab;
    private TopicsRepository mTopcsRepo;
    private RecyclerView mTopicsList;
    private TopicsRecyclerViewAdapter adapter;
    ApiService mApiService;
    private BottomSheetBehavior mBottomSheetBehavior;
    String category = "forum";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTopcsRepo = DMApplication.getTopicsRepository();
        mRealm = DMApplication.getRealm();
        mApiService = DMApplication.getApiService();
        Log.e(TAG, mTopcsRepo.toString());
        mEditName = (EditText) findViewById(R.id.name);
        mTopicsList = (RecyclerView) findViewById(R.id.topics);
        mTopicsList.setHasFixedSize(true);
        mTopicsList.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(0);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });
    }



    private void initAdapter () {

        RealmResults<Topic> topicsResult = mRealm.where(Topic.class).equalTo("tab", category).equalTo("category","all").findAll();
        adapter = new TopicsRecyclerViewAdapter(MainActivity.this, topicsResult);
        mTopicsList.setAdapter(adapter);
        topicsResult.addChangeListener(new RealmChangeListener<RealmResults<Topic>>() {
            @Override
            public void onChange(RealmResults<Topic> element) {
                Toast.makeText(MainActivity.this,"size is "+element.size(),Toast.LENGTH_LONG).show();
            }
        });

    }


    public void topics_reader_click (View view) {
        mTopcsRepo.getTopics(category, "all", new TopicsDataSource.LoadTopicsCallback() {
            @Override
            public void onTopicsLoaded (RealmResults<Topic> topics) {
                Log.e(TAG,"Topic loaded");
            }

            @Override
            public void onDataNotAvailable () {
                Log.e(TAG, "data not available");

            }
        });
    }

    public void modify_topic (View view) {
        RealmResults<Topic> topicsResult = mRealm.where(Topic.class).equalTo("tab", category).equalTo("category", "all").findAll();
        Topic first = topicsResult.first();
        mRealm.beginTransaction();
        first.setTitle(first.getTitle()+first.getTitle());
        mRealm.commitTransaction();
    }

    public void more_topic(View view) {
       //® mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        BottomSheetDialogFragment bottomSheetDialogFragment = new CreateTopicFragment();
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }


}
