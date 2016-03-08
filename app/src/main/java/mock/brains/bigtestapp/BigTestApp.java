package mock.brains.bigtestapp;

import android.app.Application;

import mock.brains.bigtestapp.api.ApiInterface;
import mock.brains.bigtestapp.di.component.AppComponent;
import mock.brains.bigtestapp.di.component.DaggerAppComponent;
import mock.brains.bigtestapp.di.module.ApiModule;
import mock.brains.bigtestapp.di.module.AppModule;
import mock.brains.bigtestapp.di.module.DbModule;
import mock.brains.bigtestapp.util.Const;
import timber.log.Timber;

public class BigTestApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initInjector() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(Const.BASE_URL, ApiInterface.class))
                .dbModule(new DbModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
