package mock.brains.bigtestapp.briteDb;

import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;
import javax.inject.Singleton;

import mock.brains.bigtestapp.BuildConfig;
import rx.schedulers.Schedulers;

@Singleton
public class DbHelper {

    private static final String TAG = DbHelper.class.getSimpleName();
    private final BriteDatabase mDb;
    /**
     * User
     **/
    public DbHelper(DbOpenHelper sqLiteOpenHelper) {
        SqlBrite sqlBrite = SqlBrite.create(message -> Log.d(TAG, message));
        mDb = sqlBrite.wrapDatabaseHelper(sqLiteOpenHelper, Schedulers.io());
        if (BuildConfig.DEBUG) {
            mDb.setLoggingEnabled(true);
        }
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    //    public static void addUser(User user) {
//        BriteDatabase db = Db.getInstance();
//        long id = db.insert(User.TABLE, new User.Builder().build(user));
//        user.set_id(id);
//        addAddress(user);
//        addCompany(user);
//    }
//
//    public static void addAddress(User user) {
//        BriteDatabase db = Db.getInstance();
//        long id = db.insert(Address.TABLE, new Address.Builder().build(user));
//        user.getAddress().set_id(id);
//        addGeo(user.getAddress());
//    }
//
//    public static void addGeo(Address address) {
//        BriteDatabase db = Db.getInstance();
//        long id = db.insert(Geo.TABLE, new Geo.Builder().build(address));
//        address.getGeo().set_id(id);
//    }
//
//    public static void addCompany(User user) {
//        BriteDatabase db = Db.getInstance();
//        long id = db.insert(Company.TABLE, new Company.Builder().build(user));
//        user.getCompany().set_id(id);
//    }
}
