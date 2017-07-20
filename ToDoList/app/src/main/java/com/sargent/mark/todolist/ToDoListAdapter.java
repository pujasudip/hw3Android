package com.sargent.mark.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sargent.mark.todolist.data.Contract;
import com.sargent.mark.todolist.data.DBHelper;
import com.sargent.mark.todolist.data.ToDoItem;

import java.util.ArrayList;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemHolder> {
    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "todolistadapter";

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public interface ItemClickListener {
        void onItemClick(int pos, String description, String category, String duedate, long id);
    }

    public ToDoListAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descr;
        TextView cate;
        TextView due;
        //String of category and status is retrieved from the cursor and assigned to these variable
        TextView sta;
        String category;
        String duedate;
        String description;
        String status;
        long id;
        CheckBox cb;

        //here category and checkbox were retrieved
        ItemHolder(View view) {
            super(view);
            descr = (TextView) view.findViewById(R.id.description);
            cate = (TextView) view.findViewById(R.id.category);
            due = (TextView) view.findViewById(R.id.dueDate);
            sta = (TextView) view.findViewById(R.id.tv_status);
            view.setOnClickListener(this);
            cb = (CheckBox) view.findViewById(R.id.cb_item);
        }

        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));

            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));
            //cursor will assigned the value to the category of the currently pointed row
            category = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY));
            //cursor will assingned the value to the category of the currently pointed row
            status = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_STATUS));
            Log.d(TAG, "status: " + status);
            Log.d(TAG, "t/f: " + (status=="Done"));
            descr.setText(description);
            cate.setText(category);
            due.setText(duedate);

            /* implemented try catch block for checking wether the checkbox is checked so checkbox
            could be updated according to the database
             */
            try{
                if(status.equals("Done")){
                    cb.setChecked(true);
                    sta.setText("Done");
                } else {
                    cb.setChecked(false);
                    sta.setText("Pending");
                }
            } catch (NullPointerException e){
                cb.setChecked(false);
                sta.setText("Pending");
            }

            //checkbox is set to clicklistener so user can update the list if it is been its been intended for some change
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//updateTodoList is update on the basis of checkbox boolean and inserted in database as done or pending

                    //object of dbhelper is referenced grabbing the context
                    DBHelper helper = new DBHelper(v.getContext());
                    //variable of SQLiteDAtabase if created to handle write-ablity
                    SQLiteDatabase db = helper.getWritableDatabase();
                    MainActivity.updateTodoList(db, id, cb.isChecked());
                }
            });
            holder.itemView.setTag(id);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, description, category, duedate, id);
        }
    }

}
