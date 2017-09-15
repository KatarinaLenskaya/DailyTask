package com.catlerina.android.dailytasks.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.catlerina.android.dailytasks.R;
import com.catlerina.android.dailytasks.TaskActivity;
import com.catlerina.android.dailytasks.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TASK_VIEW_TYPE = 0;
    private static final int HEADER_VIEW_TYPE = 1;

    private TaskCollection taskCollection;
    private Context context;

    public TaskListAdapter(List<Task> tasks, Context context) {
        this.context = context;
        setData(tasks);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TASK_VIEW_TYPE:
                View taskView = LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);
                return new TaskHolder(taskView);

            case HEADER_VIEW_TYPE:
                View headerView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
                return new HeaderHolder(headerView);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TASK_VIEW_TYPE:
                Task task = (Task) taskCollection.get(position);
                ((TaskHolder) holder).bind(task);
                break;
            case HEADER_VIEW_TYPE:
                String header = (String) taskCollection.get(position);
                ((HeaderHolder) holder).bind(header);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return taskCollection.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object o = taskCollection.get(position);
        if (o instanceof String) return HEADER_VIEW_TYPE;
        else return TASK_VIEW_TYPE;
    }

    public void setData(List<Task> tasks){
        taskCollection = new TaskCollection(tasks);
    }



    /**
     * view holder for task list item
     */
    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Task task;

        private TextView titleTV;
        private TextView dateTV;
        private CheckBox isDoneCB;

        TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            titleTV = (TextView) itemView.findViewById(R.id.tv_task_title);
            dateTV = (TextView) itemView.findViewById(R.id.tv_task_date_time);
            isDoneCB = (CheckBox) itemView.findViewById(R.id.cb_is_task_done);
            isDoneCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    task.setDone(isChecked);
                }
            });
        }

        void bind(Task bindTask) {
            task = bindTask;
            titleTV.setText(task.getTaskTitle());
            Date date = task.getTaskDate();
            if(date != null )
                dateTV.setText(SimpleDateFormat.getDateInstance().format(task.getTaskDate()));
            else dateTV.setText("");
            isDoneCB.setChecked(task.isDone());
        }

        @Override
        public void onClick(View v) {
            Intent intent = TaskActivity.newIntent(context, task.getId());
            context.startActivity(intent);
        }
    }

    /**
     * view holder for date type list item (for example "today", "tomorrow" etc.)
     */
    private class HeaderHolder extends RecyclerView.ViewHolder {
        String header;
        private TextView headerTV;

        HeaderHolder(View itemView) {
            super(itemView);

            headerTV = (TextView) itemView;
        }

        void bind(String bindHeader) {
            header = bindHeader;
            headerTV.setText(header);
        }
    }

    /**
     * class that represent collection of list item data
     * including date types.
     * there might be better solution for this issue...
     */
    private class TaskCollection {
        private ArrayList<Object> adapterTasks;


        TaskCollection(List<Task> tasks) {
            adapterTasks = new ArrayList<>();
            formAdapterTasks(tasks);
        }

        private void formAdapterTasks(List<Task> tasks) {
            List<Task> overdue, today, tomorrow, thisWeek, nextWeek, thisMonth, nextMonth, later, noDate;
            overdue = new ArrayList<>();
            today = new ArrayList<>();
            tomorrow = new ArrayList<>();
            thisWeek = new ArrayList<>();
            nextWeek = new ArrayList<>();
            thisMonth = new ArrayList<>();
            nextMonth = new ArrayList<>();
            later = new ArrayList<>();
            noDate = new ArrayList<>();

            for (Task task : tasks) {
                switch (getDateType(task.getTaskDate())) {
                    case OVERDUE:
                        overdue.add(task);
                        continue;
                    case TODAY:
                        today.add(task);
                        continue;
                    case TOMORROW:
                        tomorrow.add(task);
                        continue;
                    case THIS_WEEK:
                        thisWeek.add(task);
                        continue;
                    case NEXT_WEEK:
                        nextWeek.add(task);
                        continue;
                    case THIS_MONTH:
                        thisMonth.add(task);
                        continue;
                    case NEXT_MONTH:
                        nextMonth.add(task);
                        continue;
                    case LATER:
                        later.add(task);
                        continue;
                    case NO_DATE:
                        noDate.add(task);
                        continue;
                }
            }
            addToRecyclerTasks(overdue, DateType.OVERDUE);
            addToRecyclerTasks(today, DateType.TODAY);
            addToRecyclerTasks(tomorrow, DateType.TOMORROW);
            addToRecyclerTasks(thisWeek, DateType.THIS_WEEK);
            addToRecyclerTasks(nextWeek, DateType.NEXT_WEEK);
            addToRecyclerTasks(thisMonth, DateType.THIS_MONTH);
            addToRecyclerTasks(nextMonth, DateType.NEXT_MONTH);
            addToRecyclerTasks(later, DateType.LATER);
            addToRecyclerTasks(noDate, DateType.NO_DATE);
        }

        private DateType getDateType(Date date) {
            if (date == null) return DateType.NO_DATE;

            Calendar todayDate = Calendar.getInstance();
            Calendar inputDate = Calendar.getInstance();
            inputDate.setTime(date);
            if (inputDate.get(Calendar.YEAR) == todayDate.get(Calendar.YEAR)) {
                if (inputDate.get(Calendar.DAY_OF_YEAR) - todayDate.get(Calendar.DAY_OF_YEAR) < 0)
                    return DateType.OVERDUE;
                if (inputDate.get(Calendar.DAY_OF_YEAR) == todayDate.get(Calendar.DAY_OF_YEAR))
                    return DateType.TODAY;
                if (inputDate.get(Calendar.DAY_OF_YEAR) - todayDate.get(Calendar.DAY_OF_YEAR) == 1)
                    return DateType.TOMORROW;
                if (inputDate.get(Calendar.WEEK_OF_YEAR) == todayDate.get(Calendar.WEEK_OF_YEAR))
                    return DateType.THIS_WEEK;
                if (inputDate.get(Calendar.WEEK_OF_YEAR) - todayDate.get(Calendar.WEEK_OF_YEAR) == 1)
                    return DateType.NEXT_WEEK;
                if (inputDate.get(Calendar.MONTH) == todayDate.get(Calendar.MONTH))
                    return DateType.THIS_MONTH;
                if (inputDate.get(Calendar.MONTH) - todayDate.get(Calendar.MONTH) == 1)
                    return DateType.NEXT_MONTH;
            }
            return DateType.LATER;
        }

        private void addToRecyclerTasks(List<Task> t, DateType type) {
            if (!t.isEmpty()) {
                adapterTasks.add(context.getResources().getString(type.getStringResId()));
                adapterTasks.addAll(t);
            }
        }

        Object get(int position) {
            return adapterTasks.get(position);
        }

        int size() {
            return adapterTasks.size();
        }
    }

    private enum DateType {
        OVERDUE(R.string.date_type_overdue),
        TODAY(R.string.date_type_today),
        TOMORROW(R.string.date_type_tomorrow),
        THIS_WEEK(R.string.date_type_this_week),
        NEXT_WEEK(R.string.date_type_next_week),
        THIS_MONTH(R.string.date_type_this_month),
        NEXT_MONTH(R.string.date_type_next_month),
        LATER(R.string.date_type_later),
        NO_DATE(R.string.date_type_no_date);

        private int stringResId;

        DateType(@StringRes int stringResId) {
            this.stringResId = stringResId;
        }

        int getStringResId() {
            return stringResId;
        }
    }
}
