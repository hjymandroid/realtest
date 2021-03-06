package com.hongjie.realm.injection.modules;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import dagger.Module;
import dagger.Provides;
import com.hongjie.realm.injection.qualifier.ActivityContext;
import com.hongjie.realm.injection.scopes.PerActivity;

/* Copyright 2016 Patrick Löwenstein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */
@Module
public class ActivityModule {

    private final AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ActivityContext
    Context provideAppContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    FragmentManager provideFragmentManager() { return mActivity.getSupportFragmentManager(); }

}
