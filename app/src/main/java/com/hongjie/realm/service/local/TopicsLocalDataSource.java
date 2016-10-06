package com.hongjie.realm.service.local;

import android.support.annotation.NonNull;

import com.hongjie.realm.DMApplication;
import com.hongjie.realm.injection.scopes.PerApplication;
import com.hongjie.realm.model.Topic;
import com.hongjie.realm.service.TopicsDataSource;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by hongjiedong on 6/17/16.
 */
@PerApplication
public class TopicsLocalDataSource implements TopicsDataSource {
    private Realm mRealm;

    public TopicsLocalDataSource () {
        mRealm = DMApplication.getRealm();
    }

    @Override
    public void getTopics (String tab, String category, @NonNull LoadTopicsCallback callback) {
        RealmResults<Topic> topicsResult = mRealm.where(Topic.class).equalTo("tab", tab).equalTo("category", category).findAll();
        callback.onTopicsLoaded(topicsResult);
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
