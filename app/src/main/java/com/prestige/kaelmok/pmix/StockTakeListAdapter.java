package com.prestige.kaelmok.pmix;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prestige.kaelmok.pmix.data.PmixContracts.*;

public class StockTakeListAdapter extends RecyclerView.Adapter<StockTakeListAdapter.StockTakeViewHolder> {

        // Holds on to the cursor to display the waitlist
        private Cursor mCursor;
        private Context mContext;

        /**
         * Constructor using the context and the db cursor
         * @param context the calling context/activity
         * @param cursor the db cursor with stocktakelist data to display
         */
        public StockTakeListAdapter(Context context, Cursor cursor) {
            this.mContext = context;
            this.mCursor = cursor;
        }

        @Override
        public StockTakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Get the RecyclerView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.stock_take_list_adapter, parent, false);
            return new StockTakeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(StockTakeViewHolder holder, int position) {
            // Move the mCursor to the position of the item to be displayed
            if (!mCursor.moveToPosition(position))
                return; // bail if returned null

            // Update the view holder with the information needed to display
            String barcode = mCursor.getString(mCursor.getColumnIndex(StockTakeEntry.COLUMN_BARCODE));
            String timestamp = mCursor.getString(mCursor.getColumnIndex(StockTakeEntry.COLUMN_TIMESTAMP));
            int userId = mCursor.getInt(mCursor.getColumnIndex(StockTakeEntry.COLUMN_USER_ID));
            // TODO (6) Retrieve the id from the cursor and
            long id = mCursor.getLong(mCursor.getColumnIndex(StockTakeEntry._ID));
            // Display the guest name
            holder.barcodeTextView.setText(barcode);
            // Display the party count
            holder.timestampTextView.setText(String.valueOf(timestamp));
            // Display User ID
            holder.userIdTextView.setText(String.valueOf(userId));
            // TODO (7) Set the tag of the itemview in the holder to the id
            holder.itemView.setTag(id);
        }


        @Override
        public int getItemCount() {
            return mCursor.getCount();
        }

        /**
         * Swaps the Cursor currently held in the adapter with a new one
         * and triggers a UI refresh
         *
         * @param newCursor the new cursor that will replace the existing one
         */
        public void swapCursor(Cursor newCursor) {
            // Always close the previous mCursor first
            if (mCursor != null) mCursor.close();
            mCursor = newCursor;
            if (newCursor != null) {
                // Force the RecyclerView to refresh
                this.notifyDataSetChanged();
            }
        }

        /**
         * Inner class to hold the views needed to display a single item in the recycler-view
         */
        class StockTakeViewHolder extends RecyclerView.ViewHolder {

            // Will display the guest name
            TextView barcodeTextView;
            // Will display the party size number
            TextView timestampTextView;
            // Display User ID
            TextView userIdTextView;

            /**
             * Constructor for our ViewHolder. Within this constructor, we get a reference to our
             * TextViews
             *
             * @param itemView The View that you inflated in
             *                 {@link StockTakeListAdapter#onCreateViewHolder(ViewGroup, int)}
             */
            public StockTakeViewHolder(View itemView) {
                super(itemView);
                barcodeTextView = (TextView) itemView.findViewById(R.id.barcode_Text_View);
                timestampTextView = (TextView) itemView.findViewById(R.id.timestamp_Text_View);
                userIdTextView = (TextView) itemView.findViewById(R.id.userID_Text_View);
            }

        }
}
