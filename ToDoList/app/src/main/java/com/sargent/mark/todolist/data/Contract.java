package com.sargent.mark.todolist.data;

import android.provider.BaseColumns;

/**
 * Created by mark on 7/4/17.
 */

public class Contract {

    public static class TABLE_TODO implements BaseColumns{
        public static final String TABLE_NAME = "todoitems";

        public static final String COLUMN_NAME_DESCRIPTION = "description";
        //added category for differentiating diffrent work
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_DUE_DATE = "duedate";
        //status impplies the status of the work - done or pending
        public static final String COLUMN_STATUS = "status";
    }
}
