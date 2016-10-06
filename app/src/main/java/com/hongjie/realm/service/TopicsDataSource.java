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


import android.support.annotation.NonNull;

import com.hongjie.realm.model.Topic;
import io.realm.RealmResults;

/**
 * Main entry point for accessing Topics data.
 * <p>
 * For simplicity, only getTopics() and getTopic() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new Topic is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */
public interface TopicsDataSource {

    interface LoadTopicsCallback {

        void onTopicsLoaded (RealmResults<Topic> Topics);

        void onDataNotAvailable ();
    }

    interface GetTopicCallback {

        void onTopicLoaded (Topic Topic);

        void onDataNotAvailable ();
    }

    void getTopics (String tab, String category, @NonNull LoadTopicsCallback callback);

    void getTopic (@NonNull String TopicId, @NonNull GetTopicCallback callback);

    void saveTopic (@NonNull Topic Topic);

    void refreshTopics ();

    void deleteAllTopics ();

    void deleteTopic (@NonNull String TopicId);
}
