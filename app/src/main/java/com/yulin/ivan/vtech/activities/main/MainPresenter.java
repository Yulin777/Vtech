package com.yulin.ivan.vtech.activities.main;

import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yulin.ivan.vtech.R;

/**
 * Created by Ivan Y. on 2019-12-01.
 */

interface MainView {

    String getInput();

    void onTaskAdded();

    TasksListAdapter getListAdapter();

    void toggleTaskDone(TextView textView, boolean isDone);

    void updateRemainingTasks();
}

public class MainPresenter implements OnClickListener {
    private MainView mView;

    MainPresenter(MainView mainView) {
        mView = mainView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                addTask();
                break;

            case R.id.task_text:
                toggleTaskDone((TextView) view);
                //todo mark text
                break;
        }
    }

    private void toggleTaskDone(TextView textView) {
        boolean isDone = (textView.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0;
        TasksListAdapter listAdapter = mView.getListAdapter();

        listAdapter.toggleTaskDone(textView, !isDone);
    }

    void addTask() {
        String text = mView.getInput();
        TasksListAdapter listAdapter = mView.getListAdapter();
        listAdapter.addInput(text);
        mView.onTaskAdded();
    }

    void updateRemainingTasks() {
        mView.updateRemainingTasks();
    }
}
