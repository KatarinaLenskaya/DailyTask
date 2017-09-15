package com.catlerina.android.dailytasks;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.catlerina.android.dailytasks.fragments.TaskFragment;

import java.util.UUID;

public class TaskActivity extends SingleFragmentActivity {

    private static final String EXTRA_TASK_ID =
            "com.catlerina.android.dailytasks.task.id";

    @Override
    protected Fragment createFragment() {
        getIntent().getSerializableExtra(EXTRA_TASK_ID);
        return TaskFragment.newInstance((UUID)getIntent().getSerializableExtra(EXTRA_TASK_ID));
    }

    public static Intent newIntent(Context context, UUID taskId){
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }
}
