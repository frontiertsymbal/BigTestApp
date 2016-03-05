package mock.brains.bigtestapp.briteDb;

import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;

import mock.brains.bigtestapp.model.Address;
import mock.brains.bigtestapp.model.Company;
import mock.brains.bigtestapp.model.Geo;
import mock.brains.bigtestapp.model.User;

public class DbManager {

    private final BriteDatabase briteDatabase;

    public DbManager(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    public BriteDatabase getBriteDatabase() {
        return briteDatabase;
    }

    /**
     * User
     **/
    public void addUser(User user) {
        long id = briteDatabase.insert(User.TABLE, new User.Builder().build(user));
        user.set_id(id);
        addAddress(user);
        addCompany(user);
    }

    public void addAddress(User user) {
        long id = briteDatabase.insert(Address.TABLE, new Address.Builder().build(user));
        user.getAddress().set_id(id);
        addGeo(user.getAddress());
    }

    public void addGeo(Address address) {
        long id = briteDatabase.insert(Geo.TABLE, new Geo.Builder().build(address));
        address.getGeo().set_id(id);
    }

    public void addCompany(User user) {
        long id = briteDatabase.insert(Company.TABLE, new Company.Builder().build(user));
        user.getCompany().set_id(id);
    }

    /**
     * Db mapper utils
     */
    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }
}
