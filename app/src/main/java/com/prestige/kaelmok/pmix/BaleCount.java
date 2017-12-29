package com.prestige.kaelmok.pmix;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.prestige.kaelmok.pmix.data.*;

public class BaleCount extends AppCompatActivity {

    private SQLiteDatabase mDb;

    private TextInputEditText mbaleLocation;
    private TextInputEditText mbaleQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bale_count);

        // Set the Edit texts to the corresponding views using findViewById
        mbaleLocation = this.findViewById(R.id.baleLocation);
        mbaleQuantity = this.findViewById(R.id.baleQuantity);

        // Create a DB helper (this will create the DB if run for the first time)
        PmixDbHelper dbHelper = new PmixDbHelper(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        mDb = dbHelper.getWritableDatabase();
    }

    public void addToBaleCount(View view) {
        String regexStr = "^[0-9]*$";
        int userId = 1007;
        // Check if any of the EditTexts are empty, return if so
        if (mbaleLocation.getText().length() == 0 || mbaleQuantity.getText().length() == 0 ) {
            Toast.makeText(this,"Nothing to Save",Toast.LENGTH_SHORT).show();

        }else if(!mbaleQuantity.getText().toString().trim().matches(regexStr)){
            Toast.makeText(this,"Quantity entered not numberic",Toast.LENGTH_SHORT).show();
        }else{
            // Add guest info to mDb
            try {
                addNewGuest(mbaleLocation.getText().toString(), mbaleQuantity.getText().toString(), userId);
                Toast.makeText(this,"Input Succeeded",Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                Toast.makeText(this,"Input Failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private long addNewGuest(String location, String bale_Quantity, int userId) {
        // Create a ContentValues instance to pass the values onto the insert query
        ContentValues cv = new ContentValues();
        // Call put to insert the name value with the key COLUMN_GUEST_NAME
        cv.put(PmixContracts.BaleCountEntry.COLUMN_LOCATION, location);
        // Call put to insert the party size value with the key COLUMN_PARTY_SIZE
        cv.put(PmixContracts.BaleCountEntry.COLUMN_BALE_QUANTITY, bale_Quantity);

        cv.put(PmixContracts.BaleCountEntry.COLUMN_USER_ID, userId);
        // Call insert to run an insert query on TABLE_NAME with the ContentValues created
        return mDb.insert(PmixContracts.BaleCountEntry.BALE_COUNT_TABLE, null, cv);
    }


}
