/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hongjie.realm.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import com.hongjie.realm.injection.scopes.PerApplication;
import com.hongjie.realm.model.Topic;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load topics from the data sources into a cache.
 * <p/>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 * <p/>
 * By marking the constructor with {@code @Inject} and the class with {@code @Singleton}, Dagger
 * injects the dependencies required to create an instance of the TopicsRespository (if it fails, it
 * emits a compiler error). It uses {@link TopicsRepositoryModule} to do so, and the constructed
 * instance is available in {@link TopicsRepositoryComponent}.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 */
@PerApplication
public class TopicsRepository implements TopicsDataSource {

    private final TopicsDataSource mTopicsRemoteDataSource;

    private final TopicsDataSource mTopicsLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Topic> mCachedTopics;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    /**
     * By marking the constructor with {@code @Inject}, Dagger will try to inject the dependencies
     * required to create an instance of the TopicsRepository. Because {@link TopicsDataSource} is an
     * interface, we must provide to Dagger a way to build those arguments, this is done in
     * {@link TopicsRepositoryModule}.
     * <p/>
     * When two arguments or more have the same type, we must provide to Dagger a way to
     * differentiate them. This is done using a qualifier.
     * <p/>
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    TopicsRepository (@Remote TopicsDataSource topicsRemoteDataSource,
                      @Local TopicsDataSource topicsLocalDataSource) {
        mTopicsRemoteDataSource = topicsRemoteDataSource;
        mTopicsLocalDataSource = topicsLocalDataSource;
    }

    /**
     * Gets topics from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p/>
     * Note: {@link LoadTopicsCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getTopics (final String tab, final String category, @NonNull final LoadTopicsCallback callback) {
        getTopics(tab, category, callback, false);
    }

    public void getTopics (final String tab, final String category, @NonNull final LoadTopicsCallback callback, final boolean forceUpdate) {
        checkNotNull(callback);

        // Query the local storage if available. If not, query the network.
        mTopicsLocalDataSource.getTopics(tab, category, new LoadTopicsCallback() {
            @Override
            public void onTopicsLoaded (RealmResults<Topic> topics) {
                //refreshCache(topics);
                if (forceUpdate) {
                    getTopicsFromRemoteDataSource(tab, category, callback);
                } else {
                    callback.onTopicsLoaded(topics);
                }
            }

            @Override
            public void onDataNotAvailable () {
                getTopicsFromRemoteDataSource(tab, category, callback);
            }
        });
    }


    @Override
    public void saveTopic (@NonNull Topic topic) {
        checkNotNull(topic);
        mTopicsRemoteDataSource.saveTopic(topic);
        mTopicsLocalDataSource.saveTopic(topic);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedTopics == null) {
            mCachedTopics = new LinkedHashMap<>();
        }
        mCachedTopics.put(topic.getId(), topic);
    }


    /**
     * Gets topics from local data source (sqlite) unless the table is new or empty. In that case it
     * uses the network data source. This is done to simplify the sample.
     * <p/>
     * Note: {@link LoadTopicsCallback#onDataNotAvailable()} is fired if both data sources fail to
     * get the data.
     */
    @Override
    public void getTopic (@NonNull final String topicId, @NonNull final GetTopicCallback callback) {
        checkNotNull(topicId);
        checkNotNull(callback);

        // Is the topic in the local data source? If not, query the network.
        mTopicsLocalDataSource.getTopic(topicId, new GetTopicCallback() {
            @Override
            public void onTopicLoaded (Topic topic) {
                callback.onTopicLoaded(topic);
            }

            @Override
            public void onDataNotAvailable () {
                mTopicsRemoteDataSource.getTopic(topicId, new GetTopicCallback() {
                    @Override
                    public void onTopicLoaded (Topic topic) {
                        callback.onTopicLoaded(topic);
                    }

                    @Override
                    public void onDataNotAvailable () {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void refreshTopics () {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllTopics () {
        mTopicsRemoteDataSource.deleteAllTopics();
        mTopicsLocalDataSource.deleteAllTopics();

        if (mCachedTopics == null) {
            mCachedTopics = new LinkedHashMap<>();
        }
        mCachedTopics.clear();
    }

    @Override
    public void deleteTopic (@NonNull String topicId) {
        mTopicsRemoteDataSource.deleteTopic(checkNotNull(topicId));
        mTopicsLocalDataSource.deleteTopic(checkNotNull(topicId));

        mCachedTopics.remove(topicId);
    }

    private void getTopicsFromRemoteDataSource (String tab, String category, @NonNull final LoadTopicsCallback callback) {
        mTopicsRemoteDataSource.getTopics(tab, category, new LoadTopicsCallback() {
            @Override
            public void onTopicsLoaded (RealmResults<Topic> topics) {
                // refreshCache(topics);
                //refreshLocalDataSource(topics);
                callback.onTopicsLoaded(topics);
            }

            @Override
            public void onDataNotAvailable () {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache (List<Topic> topics) {
        if (mCachedTopics == null) {
            mCachedTopics = new LinkedHashMap<>();
        }
        mCachedTopics.clear();
        for (Topic topic : topics) {
            mCachedTopics.put(topic.getId(), topic);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource (List<Topic> topics) {
        mTopicsLocalDataSource.deleteAllTopics();
        for (Topic topic : topics) {
            mTopicsLocalDataSource.saveTopic(topic);
        }
    }

    @Nullable
    private Topic getTopicWithId (@NonNull String id) {
        checkNotNull(id);
        if (mCachedTopics == null || mCachedTopics.isEmpty()) {
            return null;
        } else {
            return mCachedTopics.get(id);
        }
    }
}
