package com.catlerina.android.dailytasks.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.catlerina.android.dailytasks.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.catlerina.android.dailytasks.date";
    private static final String ARG_TASK_DATE = "date";
    private DatePicker taskDateDP;

    public static DatePickerFragment newInstance(Date date) {

        DatePickerFragment fragment = new DatePickerFragment();
        if (date != null) {
            Bundle args = new Bundle();
            args.putSerializable(ARG_TASK_DATE, date);
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        taskDateDP = (DatePicker) LayoutInflater.from(getActivity())
                .inflate(R.layout.task_date_picker, null);


        if (getArguments() != null) {
            Date taskDate = (Date) getArguments().getSerializable(ARG_TASK_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(taskDate);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            taskDateDP.init(year, month, day, null);
        }


        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(taskDateDP)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = taskDateDP.getYear();
                        int month = taskDateDP.getMonth();
                        int day = taskDateDP.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();

                        if (getTargetFragment() != null) {
                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_DATE, date);
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        }

                    }
                })
                .create();
    }
}
