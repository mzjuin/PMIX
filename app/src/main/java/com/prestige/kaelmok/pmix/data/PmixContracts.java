package com.prestige.kaelmok.pmix.data;

import android.provider.BaseColumns;

public class PmixContracts {

    // Table Columns of Stock take with barcode scanner
    public static final class StockTakeEntry implements BaseColumns{
        public static final String TABLE_NAME = "stocktakelist";
        public static final String COLUMN_BARCODE = "barcode";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_USER_ID = "userId";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

    // Table Columns of Bale Count
    public static final class BaleCountEntry implements BaseColumns{
        public static final String BALE_COUNT_TABLE = "balecountlist";
        public static final String COLUMN_LOCATION = "baleLocation";
        public static final String COLUMN_BALE_QUANTITY = "baleQuantity";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_USER_ID = "userId";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
