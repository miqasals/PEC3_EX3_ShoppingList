package edu.uoc.pec3.android.shoppinglist;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import edu.uoc.pec3.android.shoppinglist.adapter.ShoppingItemAdapter;
import edu.uoc.pec3.android.shoppinglist.database.entities.ShoppingItemDB;
import edu.uoc.pec3.android.shoppinglist.database.model.ShoppingItem;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private ShoppingItemDB mShoppingItemDB;
    private ShoppingItemAdapter shoppingItemAdapter;
    private ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init database shopping list
        mShoppingItemDB = new ShoppingItemDB(this);

        /**
         * Check if exist a instance state saved for prevent the reinitialize the
         * database elements.
         */

        if (savedInstanceState == null){
            // If there is any instance saved, the DB will be initialized.

            // Start with an empty database
            mShoppingItemDB.clearAllItems();

            // Insert items
            insertProducts();
        }


        // Set views
        mListView = (ListView) findViewById(R.id.listView);

        // Get all the items
        shoppingItems.addAll(mShoppingItemDB.getAllItems());

        // Init adapter
        shoppingItemAdapter = new ShoppingItemAdapter(this, shoppingItems);
        mListView.setAdapter(shoppingItemAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //no inspection SimplifiableIfStatement
        if (id == R.id.action_update_list) {
            updateProducts();
            updateList();
            return true;
        } else if (id == R.id.action_init_list) {
            /**
             * This part is added for give the option of reset the database
             * without reinstaling the apk.
             */
            mShoppingItemDB.clearAllItems();
            insertProducts();
            updateList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Insert items
     */
    private void insertProducts() {
        mShoppingItemDB.insertElement("Tomatoes");
        mShoppingItemDB.insertElement("Water");
        mShoppingItemDB.insertElement("Apples");
        mShoppingItemDB.insertElement("Soup");
        mShoppingItemDB.insertElement("Coffee");
        mShoppingItemDB.insertElement("Bread");
        mShoppingItemDB.insertElement("Juice");
        mShoppingItemDB.insertElement("Pizza");
        mShoppingItemDB.insertElement("Mozzarella");
        mShoppingItemDB.insertElement("Onion");
        mShoppingItemDB.insertElement("Milk");
        mShoppingItemDB.insertElement("Eggs");
        mShoppingItemDB.insertElement("Bananas");
        mShoppingItemDB.insertElement("Toilet rolls");
        mShoppingItemDB.insertElement("Butter");
        mShoppingItemDB.insertElement("Carrots");
    }

    /**
     * Update some items
     */
    private void updateProducts() {
        if (shoppingItems.size() >= 15) {
            // Update items
            shoppingItems.get(8).setName("Cheese");
            shoppingItems.get(9).setName("Jam");
            mShoppingItemDB.updateItem(shoppingItems.get(8));
            mShoppingItemDB.updateItem(shoppingItems.get(9));
            // Delete items
            mShoppingItemDB.deleteItem(shoppingItems.get(0));
            mShoppingItemDB.deleteItem(shoppingItems.get(1));
            mShoppingItemDB.deleteItem(shoppingItems.get(2));
        }
    }

    /**
     * Update listview with database items
     */
    private void updateList() {
        // Get all the products
        shoppingItems.clear();
        shoppingItems.addAll(mShoppingItemDB.getAllItems());
        shoppingItemAdapter.notifyDataSetChanged();
    }

}
