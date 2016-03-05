package mock.brains.bigtestapp.di.module;

import dagger.Module;
import dagger.Provides;
import mock.brains.bigtestapp.api.ApiInterface;
import mock.brains.bigtestapp.api.ApiManager;
import mock.brains.bigtestapp.util.annotation.Cycles;
import mock.brains.bigtestapp.util.annotation.Lifecycle;

@Module
public class ApiManagerModule {

    @Provides
    @Lifecycle(Cycles.ACTIVITY)
    ApiManager provideApiManager(ApiInterface apiInterface) {
        return new ApiManager(apiInterface);
    }
}


