package com.yulin.ivan.vtech.activities.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yulin.ivan.vtech.R;

public class MainActivity extends AppCompatActivity implements MainView {
    private EditText inputEditText;
    private TextView remainingTextView;
    private Button addBtn;
    private RecyclerView tasksRecyclerView;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);
        findViews();
        setViews();
    }

    private void setViews() {
        setTasksRecyclerView();
        setAddBtn();
        inputEditText.setOnEditorActionListener((v, actionId, event) -> onEditorAction(actionId));
    }

    private boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            mPresenter.addTask();
            return true;
        }
        return false;
    }

    private void setAddBtn() {
        addBtn.setOnClickListener(mPresenter);
    }

    private void setTasksRecyclerView() {
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(new TasksListAdapter(mPresenter));
    }

    private void findViews() {
        inputEditText = findViewById(R.id.input);
        remainingTextView = findViewById(R.id.remaining_message);
        tasksRecyclerView = findViewById(R.id.tasks_list);
        addBtn = findViewById(R.id.add_btn);
    }

    @Override
    public String getInput() {
        return inputEditText.getText().toString();
    }

    @Override
    public void onTaskAdded() {
        inputEditText.getText().clear();
        updateRemainingTasks();
    }

    @Override
    public void updateRemainingTasks() {
        TasksListAdapter tasksListAdapter = (TasksListAdapter) tasksRecyclerView.getAdapter();
        int totalTasks;
        if (tasksListAdapter != null) {
            totalTasks = tasksListAdapter.getItemCount();
            int doneTasks = tasksListAdapter.getDoneItemCount();

            String sb = (totalTasks - doneTasks) +
                    " remaining out of " +
                    totalTasks +
                    " tasks";

            remainingTextView.setText(sb);
        }
    }

    @Override
    public TasksListAdapter getListAdapter() {
        return (TasksListAdapter) tasksRecyclerView.getAdapter();
    }

}
