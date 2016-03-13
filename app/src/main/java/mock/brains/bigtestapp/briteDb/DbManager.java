package mock.brains.bigtestapp.briteDb;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;

import java.util.ArrayList;

import mock.brains.bigtestapp.model.Address;
import mock.brains.bigtestapp.model.Company;
import mock.brains.bigtestapp.model.Geo;
import mock.brains.bigtestapp.model.User;
import timber.log.Timber;

public class DbManager {

    private final BriteDatabase briteDatabase;

    public DbManager(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    public BriteDatabase getBriteDatabase() {
        return briteDatabase;
    }

    // ******************************************* User *******************************************/

    /**
     * Add user to database
     *
     * @param user
     */
    public void addUser(@NonNull User user) {
        BriteDatabase.Transaction transaction = briteDatabase.newTransaction();

        long id = briteDatabase.insert(User.TABLE, new User.Builder().build(user));
        user.set_id(id);
        user.getAddress().setUserId(id);
        user.getCompany().setUserId(id);
        addAddress(user.getAddress());
        addCompany(user.getCompany());

        transaction.markSuccessful();
        transaction.end();
    }

    public void addAddress(@NonNull Address address) {
        long id = briteDatabase.insert(Address.TABLE, new Address.Builder().build(address));
        address.set_id(id);
        address.getGeo().setAddressId(id);
        addGeo(address.getGeo());
    }

    public void addGeo(@NonNull Geo geo) {
        long id = briteDatabase.insert(Geo.TABLE, new Geo.Builder().build(geo));
        geo.set_id(id);
    }

    public void addCompany(@NonNull Company company) {
        long id = briteDatabase.insert(Company.TABLE, new Company.Builder().build(company));
        company.set_id(id);
    }

//    public void updateUser(User user) {
//        briteDatabase.update(User.TABLE, new User.Builder().build(user), User.COL_USER_ID + Db.EQUALS_ARG, String.valueOf(user.getUserId()));
//        addAddress(user);
//        addCompany(user);
//    }
//
//    public void updateAddress(User user) {
//        briteDatabase.update(Address.TABLE, new Address.Builder().build(user), Address.COL_USER_ID + Db.EQUALS_ARG, String.valueOf(user.get_id()));
//        updateGeo(user.getAddress());
//    }
//
//    public void updateGeo(Address address) {
//        briteDatabase.insert(Geo.TABLE, new Geo.Builder().build(address));
//    }
//
//    public void updateCompany(User user) {
//        briteDatabase.insert(Company.TABLE, new Company.Builder().build(user));
//    }

    /**
     * Basic isExist method for check object existing in database
     *
     * @param value      argument for database query
     * @param tableName  table name
     * @param columnName name of column in table
     * @return boolean result of querying
     */
    public boolean isExist(Object value, String tableName, String columnName) {
        int count = 0;
        try (Cursor cursor = briteDatabase.query(Db.SELECT_COUNT_ALL_AS_COUNT_ROWS_FROM
                        + tableName + Db.WHERE
                        + columnName + Db.EQUALS_ARG,
                String.valueOf(value))) {
            if (cursor.moveToFirst()) {
                count = Db.getInt(cursor, Db.COUNT_ROWS);
            }
        } catch (Exception e) {
            Timber.e(e, "isExist in table %s by %s = %s error",
                    tableName, columnName, String.valueOf(value));
        }
        return count != 0;
    }

    public boolean isUserExist(int serverUserId) {
        return isExist(serverUserId, User.TABLE, User.COL_USER_ID);
    }

    /**
     * Get user from database
     *
     * @param value      argument for database query
     * @param columnName name of column in database
     * @return returns user from database or null if user is not exist
     */
    public User getUser(Object value, String columnName) {
        User user = null;
        try (Cursor cursor = briteDatabase.query(Db.SELECT_ALL_FROM
                        + User.TABLE + Db.WHERE
                        + columnName + Db.EQUALS_ARG,
                String.valueOf(value))) {
            if (cursor.moveToFirst()) {
                user = User.MAPPER.call(cursor);
            }
        } catch (Exception e) {
            Timber.e(e, "getUser by %s = %s error", columnName, String.valueOf(value));
        }
        if (user != null) {
            user.setAddress(getAddress(user.get_id(), Address.COL_USER_ID));
            user.setCompany(getCompany(user.get_id(), Company.COL_USER_ID));
        }
        return user;
    }

    public Address getAddress(Object value, String columnName) {
        Address address = null;
        try (Cursor cursor = briteDatabase.query(Db.SELECT_ALL_FROM
                + Address.TABLE + Db.WHERE + columnName
                + Db.EQUALS_ARG, String.valueOf(value))) {
            if (cursor.moveToFirst()) {
                address = Address.MAPPER.call(cursor);
            }
        } catch (Exception e) {
            Timber.e(e, "getAddress by %s = %s error", columnName, String.valueOf(value));
        }
        if (address != null) {
            address.setGeo(getGeo(address.get_id(), Geo.COL_ADDRESS_ID));
        }
        return address;
    }

    public Geo getGeo(Object value, String columnName) {
        Geo geo = null;
        try (Cursor cursor = briteDatabase.query(Db.SELECT_ALL_FROM
                + Geo.TABLE + Db.WHERE + columnName
                + Db.EQUALS_ARG, String.valueOf(value))) {
            if (cursor.moveToFirst()) {
                geo = Geo.MAPPER.call(cursor);
            }
        } catch (Exception e) {
            Timber.e(e, "getGeo by %s = %s error", columnName, String.valueOf(value));
        }
        return geo;
    }

    public Company getCompany(Object value, String columnName) {
        Company company = null;
        try (Cursor cursor = briteDatabase.query(Db.SELECT_ALL_FROM +
                Company.TABLE + Db.WHERE + columnName
                + Db.EQUALS_ARG, String.valueOf(value))) {
            if (cursor.moveToFirst()) {
                company = Company.MAPPER.call(cursor);
            }
        } catch (Exception e) {
            Timber.e(e, "getCompany by %s = %s error", columnName, String.valueOf(value));
        }
        return company;
    }

    public QueryObservable observUser(long id) {
        ArrayList<String> tablesList = new ArrayList<>();
        tablesList.add(User.TABLE);
        tablesList.add(Address.TABLE);
        tablesList.add(Company.TABLE);
        tablesList.add(Geo.TABLE);

        String query = Db.SELECT_ALL_FROM
                + User.TABLE + " u, "
                + Address.TABLE + " a, "
                + Company.TABLE + " c, "
                + Geo.TABLE + " g "
                + Db.WHERE
                + "u." + Db.COL_ID + Db.EQUALS_ARG + Db.AND
                + "a." + Address.COL_USER_ID + Db.EQUALS + "u." + Db.COL_ID + Db.AND
                + "c." + Company.COL_USER_ID + Db.EQUALS + "u." + Db.COL_ID + Db.AND
                + "g." + Geo.COL_ADDRESS_ID + Db.EQUALS + "a." + Db.COL_ID;

        // TODO AlexTsymbal: create MAPPER with alias for all models
        return briteDatabase.createQuery(tablesList, query, String.valueOf(id));
    }
}
