package mock.brains.bigtestapp;

import android.app.Application;

import mock.brains.bigtestapp.api.ApiInterface;
import mock.brains.bigtestapp.di.component.AppComponent;
import mock.brains.bigtestapp.di.component.DaggerAppComponent;
import mock.brains.bigtestapp.di.module.ApiModule;
import mock.brains.bigtestapp.di.module.AppModule;
import mock.brains.bigtestapp.util.Const;

public class BigTestApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    private void initInjector() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(Const.BASE_URL, ApiInterface.class))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
