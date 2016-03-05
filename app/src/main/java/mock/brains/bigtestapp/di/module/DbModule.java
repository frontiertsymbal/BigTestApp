package mock.brains.bigtestapp.di.module;

import android.content.Context;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mock.brains.bigtestapp.BuildConfig;
import mock.brains.bigtestapp.briteDb.DbOpenHelper;
import mock.brains.bigtestapp.util.annotation.Cycles;
import mock.brains.bigtestapp.util.annotation.Lifecycle;
import rx.schedulers.Schedulers;

@Module
public class DbModule {

    private static final String TAG = DbModule.class.getSimpleName();

    @Provides
    @Singleton
    BriteDatabase provideDataBase(SqlBrite sqlBrite, DbOpenHelper helper) {
        BriteDatabase briteDatabase = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        if (BuildConfig.DEBUG) {
            briteDatabase.setLoggingEnabled(true);
        }
        return briteDatabase;
    }

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return SqlBrite.create(message -> Log.d(TAG, message));
    }

    @Provides
    @Singleton
    DbOpenHelper provideOpenHelper(Context context) {
        return new DbOpenHelper(context);
    }
}
