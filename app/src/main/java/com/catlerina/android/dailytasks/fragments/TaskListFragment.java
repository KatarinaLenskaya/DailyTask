package com.catlerina.android.dailytasks.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.catlerina.android.dailytasks.R;
import com.catlerina.android.dailytasks.adapters.TaskListAdapter;
import com.catlerina.android.dailytasks.model.Task;
import com.catlerina.android.dailytasks.model.TaskKeeper;

import java.util.List;

public class TaskListFragment extends Fragment{

    private RecyclerView taskListRV;
    private FloatingActionButton addTaskFAB;
    private TaskListAdapter adapter;

    public static TaskListFragment newInstance(){
        return new TaskListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        taskListRV = (RecyclerView) view.findViewById(R.id.rv_task_list);
        taskListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskListAdapter(TaskKeeper.getInstance().getTasks(), getActivity());
        taskListRV.setAdapter(adapter);

        addTaskFAB = (FloatingActionButton) view.findViewById(R.id.fab_add_task);
        addTaskFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        TaskKeeper taskKeeper = TaskKeeper.getInstance();
        List<Task> tasks = taskKeeper.getTasks();
        if (adapter == null) {
            adapter = new TaskListAdapter(tasks, getActivity());
            taskListRV.setAdapter(adapter);
        } else {
            adapter.setData(tasks);
            adapter.notifyDataSetChanged();
        }
    }
}
