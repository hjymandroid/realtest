package com.hongjie.realm.service;


import dagger.Module;
import dagger.Provides;
import com.hongjie.realm.injection.scopes.PerApplication;
import com.hongjie.realm.service.local.TopicsLocalDataSource;
import com.hongjie.realm.service.webservices.TopicRemoteDataSource;
import io.realm.Realm;

/**
 * This is used by Dagger to inject the required arguments into the {@link TopicsRepository}.
 */
@Module
public class TopicsRepositoryModule {

    @Provides
    @PerApplication
    TopicsLocalDataSource provideTopicsLocalDataSource () {
        return new TopicsLocalDataSource();
    }

    @Provides
    @PerApplication
    TopicRemoteDataSource provideTopicsRemoteDataSource (ApiService apiService, Realm realm) {
        return new TopicRemoteDataSource(apiService, realm);
    }

    @Provides
    @PerApplication
    TopicsRepository provideTopicsRepository (TopicsLocalDataSource localDataSource, TopicRemoteDataSource remoteDataSource) {
        return new TopicsRepository(localDataSource, remoteDataSource);
    }
}
