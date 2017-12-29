package com.prestige.kaelmok.pmix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Toolbar myToolbar = findViewById(R.id.menuToolbar);
        setSupportActionBar(myToolbar);

        Button stocktakeButton = findViewById(R.id.stocktakeButton);
        Button viewdataButton = findViewById(R.id.viewdataButton);
        Button baleCountActivityButton = findViewById(R.id.baleCountActivityButton);
        Button viewBaleCountActivityButton = findViewById(R.id.viewBaleCountActivityButton);

        stocktakeButton.setOnClickListener(this);
        viewdataButton.setOnClickListener(this);
        baleCountActivityButton.setOnClickListener(this);
        viewBaleCountActivityButton.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.stocktakeButton:
                startActivity(new Intent(Menu.this, StockTake.class));
                break;
            case R.id.viewdataButton:
                startActivity(new Intent(Menu.this, ViewStockTakeList.class));
                break;
            case R.id.baleCountActivityButton:
                startActivity(new Intent(Menu.this, BaleCount.class));
                break;
            case R.id.viewBaleCountActivityButton:
                startActivity(new Intent(Menu.this, ViewBaleCount.class));
                break;
        }
    }
}
