package com.marduc812.databaseexample.example;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

     ShoplistAdapter dbHelper;
     SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new ShoplistAdapter(this);
        dbHelper.open();
        displaylistview();

        final EditText et1 = (EditText) findViewById(R.id.editText);
        final EditText et2 = (EditText) findViewById(R.id.editText2);
        Button adduser = (Button) findViewById(R.id.button);

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String item = et1.getText().toString();
                String comment = et2.getText().toString();
                dbHelper.CreateItem(item,comment);

                displaylistview();



            }
        });


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void displaylistview() {


        Cursor cursor = dbHelper.fetchAllItems();

        String[] columns = new String[] {
                ShoplistAdapter.KEY_ITEM,
                ShoplistAdapter.KEY_COMMENT
        };

        int[] to = new int[] {
                R.id.textView,
                R.id.textView2,

        };

        dataAdapter = new SimpleCursorAdapter(this, R.layout.list_row,cursor,columns,to,0);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(dataAdapter);

        Button adduser = (Button) findViewById(R.id.button);

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String ItemComment =
                        cursor.getString(cursor.getColumnIndexOrThrow("comment"));
                Toast.makeText(getApplicationContext(),
                        ItemComment, Toast.LENGTH_SHORT).show();

            }
        });

        EditText myFilter = (EditText) findViewById(R.id.editText3);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.fletchByItem(constraint.toString());
            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
