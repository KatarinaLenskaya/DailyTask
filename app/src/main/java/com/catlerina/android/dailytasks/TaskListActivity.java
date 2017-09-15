package com.catlerina.android.dailytasks;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.catlerina.android.dailytasks.fragments.TaskListFragment;

public class TaskListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return TaskListFragment.newInstance();
    }
}
