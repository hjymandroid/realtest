package com.hongjie.realm.service.webservices;

import java.util.List;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import com.hongjie.realm.model.Topic;
import com.hongjie.realm.service.ApiService;
import com.hongjie.realm.service.TopicsDataSource;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hongjiedong on 6/17/16.
 */
public class TopicRemoteDataSource implements TopicsDataSource {
    private ApiService mApi;
    private Realm mRealm;

    @Inject
    public TopicRemoteDataSource (@NonNull ApiService apiService, @NonNull Realm realm) {
        mApi = apiService;
        mRealm = realm;
    }

    @Override
    public void getTopics (final String tab, final String category, @NonNull final LoadTopicsCallback callback) {
        Call<List<Topic>> call = mApi.listTopics(tab, null, 10, 1);
        call.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse (Call<List<Topic>> call, Response<List<Topic>> response) {
                final List<Topic> topics = response.body();
                if(response.isSuccessful()) {

                    mRealm.beginTransaction();
                    mRealm.copyToRealmOrUpdate(topics);
                    mRealm.commitTransaction();
                    RealmResults<Topic> topicsResult = mRealm.where(Topic.class).equalTo("tab",tab).equalTo("category",category).findAll();
                    callback.onTopicsLoaded(topicsResult);
                } else
                    callback.onDataNotAvailable();
            }

            @Override
            public void onFailure (Call<List<Topic>> call, Throwable t) {
                Log.e("tag", t.getLocalizedMessage());
                t.printStackTrace();
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getTopic (@NonNull String TopicId, @NonNull GetTopicCallback callback) {

    }

    @Override
    public void saveTopic (@NonNull Topic Topic) {

    }

    @Override
    public void refreshTopics () {

    }

    @Override
    public void deleteAllTopics () {

    }

    @Override
    public void deleteTopic (@NonNull String TopicId) {

    }
}
