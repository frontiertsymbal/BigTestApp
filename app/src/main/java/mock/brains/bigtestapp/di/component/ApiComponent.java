package mock.brains.bigtestapp.di.component;

import com.squareup.okhttp.OkHttpClient;

import mock.brains.bigtestapp.api.ApiInterface;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Used in sub-graph
 */

public interface ApiComponent {

    Retrofit retrofit();

    OkHttpClient okHttpClient();

    GsonConverterFactory gsonConverterFactory();

    ApiInterface apiInterface();

}
