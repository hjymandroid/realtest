package com.hongjie.realm.injection.components;

import android.content.Context;
import android.content.res.Resources;

import dagger.Component;
import com.hongjie.realm.injection.modules.AppModule;
import com.hongjie.realm.injection.modules.DataModule;
import com.hongjie.realm.injection.modules.NetModule;
import com.hongjie.realm.injection.qualifier.AppContext;
import com.hongjie.realm.injection.scopes.PerApplication;
import com.hongjie.realm.service.ApiService;
import com.hongjie.realm.service.TopicsRepository;
import com.hongjie.realm.service.TopicsRepositoryModule;
import io.realm.Realm;

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
@PerApplication
@Component(modules={AppModule.class, NetModule.class, DataModule.class,TopicsRepositoryModule.class})
public interface AppComponent {
    @AppContext
    Context context ();
    Resources resources ();

    Realm realm ();
    ApiService apiService();
    TopicsRepository topicsRepository ();

//    CountryRepo countryRepo ();
//    CountryApi countryApi ();
}
