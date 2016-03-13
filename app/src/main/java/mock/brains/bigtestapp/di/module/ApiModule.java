package mock.brains.bigtestapp.di.module;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mock.brains.bigtestapp.api.ApiInterface;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    /**
     * 50MB cache size
     */
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    /**
     * Requests timeout
     */
    private static final int TIMEOUT = 10;
    /**
     * Base request url
     */
    private String url;
    /**
     * Api interface class
     */
    private Class<ApiInterface> apiInterface;

    public ApiModule(String url, Class<ApiInterface> apiInterface) {
        this.url = url;
        this.apiInterface = apiInterface;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory converter, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(converter)
                .build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideConverter() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(Context context) {
        return createHttpClient(context);
    }

    @Provides
    @Singleton
    ApiInterface provideApiInterface(Retrofit retrofit) {
        return retrofit.create(apiInterface);
    }

    static OkHttpClient createHttpClient(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheDir = new File(context.getCacheDir(), "cached");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(TIMEOUT, TimeUnit.SECONDS);

        httpClient.cache(cache);

        return httpClient.build();
    }
}
