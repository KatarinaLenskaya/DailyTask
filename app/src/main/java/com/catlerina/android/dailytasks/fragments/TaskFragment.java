package com.catlerina.android.dailytasks.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.catlerina.android.dailytasks.R;
import com.catlerina.android.dailytasks.model.Task;
import com.catlerina.android.dailytasks.model.TaskKeeper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class TaskFragment extends Fragment {

    private static final String ARG_TASK_ID = "task_id";
    private static final String TASK_DIALOG_DATE = "task_dialog_date";
    private static final int DATE_DIALOG_REQUEST = 0;

    private EditText taskTitleET;
    private Switch isDateEnable;
    private Button dateBtn;
    private CheckBox isDoneCB;
    private Task task;


    public static TaskFragment newInstance(UUID taskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        task = TaskKeeper.getInstance().getTask((UUID) getArguments().get(ARG_TASK_ID));

        taskTitleET = (EditText) view.findViewById(R.id.et_task_title);
        taskTitleET.setText(task.getTaskTitle());
        taskTitleET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.setTaskTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        isDateEnable = (Switch) view.findViewById(R.id.swt_is_date_enable);
        isDateEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dateBtn.setEnabled(isChecked);
                if(!isChecked){
                dateBtn.setText(R.string.no_date);
                task.setTaskDate(null);}
            }
        });

        dateBtn = (Button) view.findViewById(R.id.btn_task_date);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(task.getTaskDate());
                dialog.setTargetFragment(TaskFragment.this, DATE_DIALOG_REQUEST);
                dialog.show(manager, TASK_DIALOG_DATE);
            }

        });
        Date date = task.getTaskDate();
        if (date != null) {
            isDateEnable.setChecked(true);
            DateFormat formatDate = SimpleDateFormat.getDateInstance();
            dateBtn.setText(formatDate.format(date));
            dateBtn.setEnabled(true);
        } else dateBtn.setText(R.string.no_date);

        isDoneCB = (CheckBox) view.findViewById(R.id.cb_task_done);
        isDoneCB.setChecked(task.isDone());
        isDoneCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setDone(isChecked);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK) return;
        if(requestCode == DATE_DIALOG_REQUEST){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            task.setTaskDate(date);
            DateFormat formatDate = SimpleDateFormat.getDateInstance();
            dateBtn.setText(formatDate.format(date));
        }
    }
}
