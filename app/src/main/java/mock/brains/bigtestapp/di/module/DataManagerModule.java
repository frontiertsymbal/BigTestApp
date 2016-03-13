package mock.brains.bigtestapp.di.module;

import com.squareup.sqlbrite.BriteDatabase;

import dagger.Module;
import dagger.Provides;
import mock.brains.bigtestapp.api.ApiInterface;
import mock.brains.bigtestapp.api.ApiManager;
import mock.brains.bigtestapp.briteDb.DbManager;
import mock.brains.bigtestapp.util.annotation.Cycles;
import mock.brains.bigtestapp.util.annotation.Lifecycle;

@Module
public class DataManagerModule {

    @Provides
    @Lifecycle(Cycles.ACTIVITY)
    ApiManager provideApiManager(ApiInterface apiInterface) {
        return new ApiManager(apiInterface);
    }

    @Provides
    @Lifecycle(Cycles.ACTIVITY)
    DbManager provideDbManager(BriteDatabase briteDatabase) {
        return new DbManager(briteDatabase);
    }
}


