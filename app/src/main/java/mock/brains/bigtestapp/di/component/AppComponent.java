package mock.brains.bigtestapp.di.component;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Singleton;

import dagger.Component;
import mock.brains.bigtestapp.di.module.ApiModule;
import mock.brains.bigtestapp.di.module.AppModule;
import mock.brains.bigtestapp.di.module.DbModule;
import mock.brains.bigtestapp.rx.RxBus;

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        DbModule.class
})
public interface AppComponent extends ApiComponent {

    void inject(Application application);

    Context context();
    SharedPreferences sharedPreferences();
    RxBus rxBus();
    BriteDatabase briteDatabase();
}

