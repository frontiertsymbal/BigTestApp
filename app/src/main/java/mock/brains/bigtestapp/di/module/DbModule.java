package mock.brains.bigtestapp.di.module;

import android.content.Context;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mock.brains.bigtestapp.BuildConfig;
import mock.brains.bigtestapp.briteDb.DbOpenHelper;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Module
public class DbModule {

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
        return SqlBrite.create(message -> Timber.d(message));
    }

    @Provides
    @Singleton
    DbOpenHelper provideOpenHelper(Context context) {
        return new DbOpenHelper(context);
    }
}
