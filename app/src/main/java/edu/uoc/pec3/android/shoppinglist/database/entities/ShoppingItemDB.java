package edu.uoc.pec3.android.shoppinglist.database.entities;

import android.content.Context;
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
    public static abstract class ShoppingElementEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                COLUMN_NAME_TITLE + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


    /**
     * Method to create new element in the database
     *
     * @param productName
     */
    public void insertElement(String productName) {
        //TODO: add all the needed code to insert one item in database
    }

    /**
     * Method to get all the shopping elements of the database
     *
     * @return ShoppingItem array
     */
    public ArrayList<ShoppingItem> getAllItems() {
        ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();
        //TODO: add all the needed code to get all the database items
        return shoppingItems;
    }

    /**
     * Method to clear all the elements
     */
    public void clearAllItems() {
        //TODO: add all the needed code to clear all the database items
    }

    /**
     * Method to update a database item
     *
     * @param shoppingItem
     */
    public void updateItem(ShoppingItem shoppingItem) {
        //TODO: add the needed code to update a database item
    }

    /**
     * Method to delete one item
     *
     * @param shoppingItem
     */
    public void deleteItem(ShoppingItem shoppingItem) {
        //TODO: add all the needed code to delete a database item
    }
}
