package mock.brains.bigtestapp.briteDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Singleton;

import mock.brains.bigtestapp.model.Address;
import mock.brains.bigtestapp.model.Company;
import mock.brains.bigtestapp.model.Geo;
import mock.brains.bigtestapp.model.User;
import timber.log.Timber;

@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bigTestApp.db";
    private static final int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, CREATE_TABLE_USER);
        createTable(db, CREATE_ADDRESS);
        createTable(db, CREATE_GEO);
        createTable(db, CREATE_COMPANY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        destroyDB(db);
        onCreate(db);
    }

    public void destroyDB(SQLiteDatabase db) {
        try {
            db.beginTransaction();

            db.execSQL(Db.DROP_TABLE_IF_EXIST + User.TABLE);
            db.execSQL(Db.DROP_TABLE_IF_EXIST + Address.TABLE);
            db.execSQL(Db.DROP_TABLE_IF_EXIST + Company.TABLE);
            db.execSQL(Db.DROP_TABLE_IF_EXIST + Geo.TABLE);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Timber.e(e, "destroyDB error");
        }
        db.endTransaction();
    }

    private void createTable(SQLiteDatabase db, String sql) {
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            Timber.e(e, "Error create table [ %s ]", sql);
        }
    }

    /**
     * Create table
     */
    private static final String CREATE_TABLE_USER = ""
            + Db.CREATE_TABLE + User.TABLE + "("
            + Db.COL_ID + Db.INT_NOT_NULL_PK_AUTOINCREMENT + Db.COMMA
            + User.COL_USER_ID + Db.INTEGER_NOT_NULL + Db.COMMA
            + User.COL_NAME + Db.TEXT_NOT_NULL + Db.COMMA
            + User.COL_USER_NAME + Db.TEXT_NOT_NULL + Db.COMMA
            + User.COL_EMAIL + Db.TEXT_NOT_NULL + Db.COMMA
            + User.COL_PHONE + Db.TEXT_NOT_NULL + Db.COMMA
            + User.COL_WEB_SITE + Db.TEXT_NOT_NULL
            + ")";

    private static final String CREATE_ADDRESS = ""
            + Db.CREATE_TABLE + Address.TABLE + "("
            + Db.COL_ID + Db.INT_NOT_NULL_PK_AUTOINCREMENT + Db.COMMA
            + Address.COL_USER_ID + Db.INTEGER_NOT_NULL + Db.COMMA
            + Address.COL_STREET + Db.TEXT_NOT_NULL + Db.COMMA
            + Address.COL_SUITE + Db.TEXT_NOT_NULL + Db.COMMA
            + Address.COL_CITY + Db.TEXT_NOT_NULL + Db.COMMA
            + Address.COL_ZIP_CODE + Db.TEXT_NOT_NULL
            + ")";

    private static final String CREATE_GEO = ""
            + Db.CREATE_TABLE + Geo.TABLE + "("
            + Db.COL_ID + Db.INT_NOT_NULL_PK_AUTOINCREMENT + Db.COMMA
            + Geo.COL_ADDRESS_ID + Db.INTEGER_NOT_NULL + Db.COMMA
            + Geo.COL_LAT + Db.REAL_NOT_NULL + Db.COMMA
            + Geo.COL_LNG + Db.REAL_NOT_NULL
            + ")";

    private static final String CREATE_COMPANY = ""
            + Db.CREATE_TABLE + Company.TABLE + "("
            + Db.COL_ID + Db.INT_NOT_NULL_PK_AUTOINCREMENT + ", "
            + Company.COL_USER_ID + Db.INTEGER_NOT_NULL + Db.COMMA
            + Company.COL_NAME + Db.TEXT_NOT_NULL + Db.COMMA
            + Company.COL_CATCH_PHRASE + Db.TEXT_NOT_NULL + Db.COMMA
            + Company.COL_BS + Db.TEXT_NOT_NULL
            + ")";
}
