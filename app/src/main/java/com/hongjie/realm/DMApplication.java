package com.hongjie.realm;

import android.app.Application;
import android.content.res.Resources;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import com.hongjie.realm.injection.components.AppComponent;
import com.hongjie.realm.injection.components.DaggerAppComponent;
import com.hongjie.realm.injection.modules.AppModule;
import com.hongjie.realm.service.ApiService;
import com.hongjie.realm.service.TopicsRepository;
import io.realm.Realm;


/**
 * Created by hongjiedong on 6/15/16.
 */
public class DMApplication extends Application {

    private static DMApplication sInstance = null;

    private static AppComponent sAppComponent = null;

    @Override
    public void onCreate () {
        super.onCreate();

        sInstance = this;
        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                      .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                      .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                      .build());
        }
        sAppComponent = DaggerAppComponent.builder()
                                          .appModule(new AppModule(this))
                                          .build();
    }

    public static DMApplication getInstance () {
        return sInstance;
    }

    public static AppComponent getAppComponent () {
        return sAppComponent;
    }

    public static Realm getRealm () {
        return sAppComponent.realm();
    }

    public static Resources getRes () {
        return sInstance.getResources();
    }


    public static ApiService getApiService () {
        return sAppComponent.apiService();
    }

    public static TopicsRepository getTopicsRepository() {
        return sAppComponent.topicsRepository();
    }
}
