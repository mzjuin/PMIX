package com.prestige.kaelmok.pmix;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import com.opencsv.CSVWriter;
import com.prestige.kaelmok.pmix.data.PmixContracts;
import com.prestige.kaelmok.pmix.data.PmixDbHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ViewStockTakeList extends AppCompatActivity{

    private StockTakeListAdapter mAdapter;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "stocktakelist";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_take_list);

        RecyclerView stocktakelistRecyclerView;

        // Set local attributes to corresponding views
        stocktakelistRecyclerView = this.findViewById(R.id.all_guests_list_view);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        stocktakelistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a DB helper (this will create the DB if run for the first time)
        PmixDbHelper dbHelper = new PmixDbHelper(this);

        // Make DB readable
        mDb = dbHelper.getReadableDatabase();

        // Get all guest info from the database and save in a cursor
        Cursor cursor = getAllStockTakeList();

        // Create an adapter for that cursor to display the data
        mAdapter = new StockTakeListAdapter(this, cursor);

        // Add a divider for each stock take
        stocktakelistRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // Link the adapter to the RecyclerView
        stocktakelistRecyclerView.setAdapter(mAdapter);

        // Create a new ItemTouchHelper with a SimpleCallback that handles both LEFT and RIGHT swipe directions
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                long id = (long) viewHolder.itemView.getTag();
                removeStock(id);
                mAdapter.swapCursor(getAllStockTakeList());
            }
        }).attachToRecyclerView(stocktakelistRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu){
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_export:
                exportCsv();
                return true;

            case R.id.action_remove_all:
                removeAll();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    // Query the mDb and get all guests from the table
    private Cursor getAllStockTakeList() {
        return mDb.query(
                PmixContracts.StockTakeEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PmixContracts.StockTakeEntry.COLUMN_TIMESTAMP
        );
    }

    // Remove certain stock
    private boolean removeStock(long id){
        return mDb.delete(PmixContracts.StockTakeEntry.TABLE_NAME,
                PmixContracts.StockTakeEntry._ID + "=" + id, null) > 0;
    }

    // Remove all data from database
    public void removeAll(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove All Data");
        builder.setMessage("Do you really want to proceed?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDb.delete(PmixContracts.StockTakeEntry.TABLE_NAME, null, null);
                mAdapter.swapCursor(getAllStockTakeList());
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });

        builder.show();
    }

    public void exportCsv() {
        File dbFile = getDatabasePath("stocktakelist.db");
        PmixDbHelper dbhelper = new PmixDbHelper(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");

        boolean isDirectoryCreated = exportDir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated= exportDir.mkdir();
        }
        if(isDirectoryCreated) {
            File file = new File(exportDir, "stocktake-barcode.csv");
            try {
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                SQLiteDatabase db = dbhelper.getReadableDatabase();
                Cursor curCSV = db.rawQuery("SELECT * FROM stocktakelist", null);
                String arrStrColumn[] = {curCSV.getColumnName(1), curCSV.getColumnName(2), curCSV.getColumnName(3)
                        , curCSV.getColumnName(4)};
                csvWrite.writeNext(arrStrColumn);

                while (curCSV.moveToNext()) {
                    //Which column you want to exprort
                    String arrStr[] = {curCSV.getString(1), curCSV.getString(2), curCSV.getString(3)
                            , curCSV.getString(4)};

                    csvWrite.writeNext(arrStr);
                }

                Toast.makeText(this,"Export Succeeded",Toast.LENGTH_SHORT).show();
                csvWrite.close();
                curCSV.close();

            } catch (Exception sqlEx) {
                Toast.makeText(this,"Export Failed",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
