package mock.brains.bigtestapp.di.module;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mock.brains.bigtestapp.api.ApiInterface;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

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
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(TIMEOUT, TimeUnit.SECONDS);
        File cacheDir = new File(context.getCacheDir(), "cached");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        okHttpClient.setCache(cache);
        return okHttpClient;
    }
}
