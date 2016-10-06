/*
 * Copyright 2016 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hongjie.realm.ui.topic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongjie.realm.R;
import com.hongjie.realm.model.Topic;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class TopicsRecyclerViewAdapter extends RealmRecyclerViewAdapter<Topic, TopicsRecyclerViewAdapter.MyViewHolder> {


    public TopicsRecyclerViewAdapter (Context context, OrderedRealmCollection<Topic> data) {
        super(context, data, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.topic_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, int position) {
        Topic obj = getData().get(position);
        holder.data = obj;
        holder.title.setText(obj.getTitle());
        holder.content.setText(obj.getContent());
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView title;
        public TextView content;
        public Topic data;

        public MyViewHolder (View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick (View v) {
            return true;
        }
    }
}