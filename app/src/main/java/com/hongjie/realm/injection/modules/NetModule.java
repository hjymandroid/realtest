package com.hongjie.realm.injection.modules;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import dagger.Module;
import dagger.Provides;

import com.hongjie.realm.BuildConfig;
import com.hongjie.realm.injection.scopes.PerApplication;
import com.hongjie.realm.model.RealmString;
import com.hongjie.realm.model.RealmStringMapEntry;
import com.hongjie.realm.model.Topic;
import com.hongjie.realm.model.User;
import com.hongjie.realm.model.parse_util.RealmStringListTypeAdapter;
import com.hongjie.realm.model.parse_util.RealmStringMapEntryListTypeAdapter;
import com.hongjie.realm.service.ApiService;

import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

@Module
public class NetModule {

    private static final String ENDPOINT = "http://api.doum.in";

    static class DMListObjectDeserializer implements JsonDeserializer<List<?>> {
        @Override
        public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            json = json.getAsJsonObject().get("result");
            if (json.isJsonArray()) {
                JsonArray array = json.getAsJsonArray();
                Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                List list = new ArrayList<>();
                for (int i = 0; i < array.size(); i++) {
                    JsonElement element = array.get(i);
                    Object item = context.deserialize(element, itemType);
                    list.add(item);
                }
                return list;
            } else {
                return Collections.EMPTY_LIST;
            }
        }
    }

    static class DMSingleObjectDeserializer<T> implements JsonDeserializer<T> {
        @Override
        public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
                throws JsonParseException {
            // Get the "content" element from the parsed JSON
            JsonElement content = je.getAsJsonObject().get("result");
            if (content == null) {
                content = je.getAsJsonObject();
            }
            // Deserialize it. You use a new instance of Gson to avoid infinite recursion
            // to this deserializer
            return new Gson().fromJson(content, type);

        }
    }

    @Provides
    @PerApplication
    static Gson provideGson() {
        Gson realmGson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        })
                .registerTypeHierarchyAdapter(List.class, new DMListObjectDeserializer())
                .registerTypeAdapter(Topic.class, new DMSingleObjectDeserializer<Topic>())
                .registerTypeAdapter(User.class, new DMSingleObjectDeserializer<User>())
                .registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {
                }.getType(), RealmStringListTypeAdapter.INSTANCE)
                .registerTypeAdapter(new TypeToken<RealmList<RealmStringMapEntry>>() {
                }.getType(), RealmStringMapEntryListTypeAdapter.INSTANCE)

                .create();
        return realmGson;
    }

    @Provides
    @PerApplication
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @PerApplication
    static ApiService provideApiService(Gson gson, OkHttpClient okHttpClient) {
        OkHttpClient.Builder httpClientBuilder = okHttpClient.newBuilder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }

        return new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .callFactory(httpClientBuilder.build())
                .build().create(ApiService.class);
    }
}
