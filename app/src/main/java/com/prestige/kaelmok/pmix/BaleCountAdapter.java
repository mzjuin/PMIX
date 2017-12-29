package com.prestige.kaelmok.pmix;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prestige.kaelmok.pmix.data.PmixContracts;

public class BaleCountAdapter extends RecyclerView.Adapter<BaleCountAdapter.BaleCountViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;

    /**
     * Constructor using the context and the db cursor
     * @param context the calling context/activity
     * @param cursor the db cursor with stocktakelist data to display
     */
    public BaleCountAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public BaleCountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.bale_count_list_adapter, parent, false);
        return new BaleCountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaleCountViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String location = mCursor.getString(mCursor.getColumnIndex(PmixContracts.BaleCountEntry.COLUMN_LOCATION));
        String quantity = mCursor.getString(mCursor.getColumnIndex(PmixContracts.BaleCountEntry.COLUMN_BALE_QUANTITY));
        String timestamp = mCursor.getString(mCursor.getColumnIndex(PmixContracts.BaleCountEntry.COLUMN_TIMESTAMP));

        int userId = mCursor.getInt(mCursor.getColumnIndex(PmixContracts.StockTakeEntry.COLUMN_USER_ID));

        long id = mCursor.getLong(mCursor.getColumnIndex(PmixContracts.StockTakeEntry._ID));
        // Display the guest name
        holder.locationTextView.setText(location);
        // Display quantity
        holder.quantityTextView.setText(quantity);
        // Display the party count
        holder.timestampTextView.setText(String.valueOf(timestamp));
        // Display User ID
        holder.userIdTextView.setText(String.valueOf(userId));
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
    class BaleCountViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView locationTextView;

        TextView quantityTextView;
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
        public BaleCountViewHolder(View itemView) {
            super(itemView);
            locationTextView = itemView.findViewById(R.id.location_Text_View);
            quantityTextView = itemView.findViewById(R.id.quantity_Text_View);
            timestampTextView = itemView.findViewById(R.id.timestamp_Text_View);
            userIdTextView = itemView.findViewById(R.id.userID_Text_View);
        }
    }
}
