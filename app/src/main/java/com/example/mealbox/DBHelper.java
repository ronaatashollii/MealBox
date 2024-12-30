package com.example.mealbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MealBox1.db";
    private static final int DATABASE_VERSION = 5;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_PRODUCTS = "products";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PASSWORD = "password";

    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_PRODUCT_COUNT = "product_count";
    private static final String COLUMN_TOTAL_AMOUNT = "total_amount";
    private static final String COLUMN_ADDRESS = "addres";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_SURNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT UNIQUE, "
                + COLUMN_PHONE + " TEXT, "
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);


        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_PRODUCT_NAME + " TEXT PRIMARY KEY, "
                + COLUMN_PRICE + " REAL)";
        db.execSQL(CREATE_PRODUCTS_TABLE);


        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_PHONE + " TEXT)";
        db.execSQL(CREATE_ORDERS_TABLE);


        addProduct(db, "Chicken Burger", 5.00);
        addProduct(db, "BBQ Burger", 3.50);
        addProduct(db, "Beef Burger", 7.00);
        addProduct(db, "Chicken Sandwich", 4.50);
        addProduct(db, "Chicken Spinner", 2.00);
        addProduct(db, "Crispy Onion Burger", 2.50);
        addProduct(db, "Double Crispy Onion", 3.00);
        addProduct(db, "Giant Beef", 4.00);
        addProduct(db, "Mega Cheese Burger", 5.50);
        addProduct(db, "Golden Burger", 6.00);

    }
    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDERS, null, null, null, null, null, null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }


    public boolean addOrder(int productCount, double totalAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_COUNT, productCount);
        values.put(COLUMN_TOTAL_AMOUNT, totalAmount);

        long result = db.insert(TABLE_ORDERS, null, values);
        db.close();
        return result != -1;
    }





    private void addProduct(SQLiteDatabase db, String productName, double price) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_PRICE, price);
        db.insert(TABLE_PRODUCTS, null, values);
    }


    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PRODUCTS, null, null, null, null, null, null);
    }


    public boolean addUser(String name, String surname, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SURNAME, surname);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }


    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, null, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
    }


    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);


        int rowsAffected = db.update(TABLE_USERS, values, COLUMN_EMAIL + "=?", new String[]{email});
        db.close();

        return rowsAffected > 0;
    }


    public boolean isUserExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);

        boolean exists = cursor != null && cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean addOrder(String name, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_PHONE, phone);


        long result = db.insert(TABLE_ORDERS, null, values);
        db.close();
        return result != -1;
    }


}
