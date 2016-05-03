package edu.uoc.pec3.android.shoppinglist.database.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

import edu.uoc.pec3.android.shoppinglist.database.helper.ShoppingElementHelper;
import edu.uoc.pec3.android.shoppinglist.database.model.ShoppingItem;

/**
 * Created by Marc on 18/09/2015.
 */
public class ShoppingItemDB {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private ShoppingElementHelper dbHelper;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ShoppingItemDB(Context context) {
        // Create new helper
        dbHelper = new ShoppingElementHelper(context);
    }

    /* Inner class that defines the table contents */
    public static abstract class ShoppingElementEntry implements BaseColumns {  //S'utilitza la interfície BaseColumns per heretar el camp clau _ID que la classe Cursor utilitza.
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                COLUMN_NAME_TITLE + TEXT_TYPE + " )";
        /*
         * CREATE_TABLE entry (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT)
         */

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        /*
         * DROP TABLE IF EXIST entry
         */
    }///////////////// end class ShoppingElementEntry /////////////////////////


    /**
     * Method to create new element in the database
     *
     * @param productName
     */
    public void insertElement(String productName) {

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create the ContentValue element for insert to database.
        ContentValues values = new ContentValues();
        values.put(ShoppingElementEntry.COLUMN_NAME_TITLE, productName);
        // Insert the new row.
        db.insert(ShoppingElementEntry.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Method to get all the shopping elements of the database
     *
     * @return ShoppingItem array
     */
    public ArrayList<ShoppingItem> getAllItems() {
        ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();

        // Gets the data repository in read only mode.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Sets the column projection for simplify the query.
        String[] projection = {
                ShoppingElementEntry._ID,
                ShoppingElementEntry.COLUMN_NAME_TITLE
        };


        // Execute the query
        Cursor c = db.query(
                ShoppingElementEntry.TABLE_NAME,    // table
                projection, //columns
                null,       // selection (null = all rows)
                null,       // selectionArgs
                null,       // groupBy
                null,       // having
                null        // orderBy
                );

        if (c != null) {
            /*
             * Populate the ShoppingItem list with the data contained in cursor elements.
             */
            for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                shoppingItems.add(
                        new ShoppingItem(
                                c.getInt(c.getColumnIndex(ShoppingElementEntry._ID)),
                                c.getString(c.getColumnIndex(ShoppingElementEntry.COLUMN_NAME_TITLE))
                        )
                );
            }
            // Close the cursor.
            c.close();
        }
        // Close the db connection.
        db.close();

        // Return the list of ShoppingItems obtained with the query.
        return shoppingItems;
    }

    /**
     * Method to clear all the elements
     */
    public void clearAllItems() {

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Delete all elements of the table.
        db.delete(ShoppingElementEntry.TABLE_NAME, null, null);

        // Close the database connection.
        db.close();
    }

    /**
     * Method to update a database item
     *
     * @param shoppingItem
     */
    public void updateItem(ShoppingItem shoppingItem) {

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object and put the new ShoppingItem information in every column.
        ContentValues values = new ContentValues();
        values.put(ShoppingElementEntry._ID, shoppingItem.getId());
        values.put(ShoppingElementEntry.COLUMN_NAME_TITLE, shoppingItem.getName());

        // Sets the selection criteria with text and arguments.
        String selection = ShoppingElementEntry._ID + " LIKE ?";    //"=?"
        String[] selectionArgs = {
                String.valueOf(shoppingItem.getId())
        };

        // Update the table with the previous data.
        db.update(
                ShoppingElementEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        // Close the db connection.
        db.close();
    }

    /**
     * Method to delete one item
     *
     * @param shoppingItem
     */
    public void deleteItem(ShoppingItem shoppingItem) {

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Sets the selection text and arguments.
        String selection = ShoppingElementEntry._ID + " LIKE ?";
        String[] selectionArgs = {
            String.valueOf(shoppingItem.getId())
        };

        // Delete the selected row and close the db connection.
        db.delete(ShoppingElementEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
}
