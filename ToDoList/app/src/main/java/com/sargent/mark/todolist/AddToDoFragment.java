package com.sargent.mark.todolist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by mark on 7/4/17.
 */

public class AddToDoFragment extends DialogFragment{

    private EditText toDo;
    private DatePicker dp;
    private Button add;
    private final String TAG = "addtodofragment";
    //got spinner here
    private Spinner spinner;
    public String mDefault = "0";

    public AddToDoFragment() {
    }

    //To have a way for the activity to get the data from the dialog
    //added category in this interface
    public interface OnDialogCloseListener {
        void closeDialog(int year, int month, int day, String description, String category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_adder, container, false);
        toDo = (EditText) view.findViewById(R.id.toDo);
        dp = (DatePicker) view.findViewById(R.id.datePicker);
        add = (Button) view.findViewById(R.id.add);
        //got spinner id from xml
        spinner = (Spinner) view.findViewById(R.id.spn_todolist);

        Context context = getContext();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.todoArray, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dp.updateDate(year, month, day);

    //added code to get the string from the spinner here
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogCloseListener activity = (OnDialogCloseListener) getActivity();
                activity.closeDialog(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), toDo.getText().toString(), spinner.getSelectedItem().toString());
                AddToDoFragment.this.dismiss();
                Log.d(TAG, "test12:" + toDo.getText().toString());
            }
        });

        return view;
    }
}



