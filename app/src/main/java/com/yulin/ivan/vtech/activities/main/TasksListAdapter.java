package com.yulin.ivan.vtech.activities.main;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yulin.ivan.vtech.R;
import com.yulin.ivan.vtech.activities.main.TasksListAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan Y. on 2019-12-01.
 */

class TasksListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<String> tasksList;
    private MainPresenter mPresenter;
    private int doneCounter;

    TasksListAdapter(MainPresenter presenter) {
        this.tasksList = new ArrayList<>();
        mPresenter = presenter;
        doneCounter = 0;
    }

    @NonNull
    @Override
    public TasksListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksListAdapter.ViewHolder holder, int i) {
        holder.setText(tasksList.get(i));
        holder.text.setOnClickListener(mPresenter);
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    void addInput(String newTask) {
        tasksList.add(newTask);
        notifyDataSetChanged();
    }

    int getDoneItemCount() {
        return doneCounter;
    }

    void incDone() {
        doneCounter++;
    }

    void decDone() {
        doneCounter--;
    }

    void toggleTaskDone(TextView textView, boolean isDone) {
        if (isDone) {
            textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            incDone();
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            decDone();
        }
        mPresenter.updateRemainingTasks();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        TextView text;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item = itemView;
            this.text = itemView.findViewById(R.id.task_text);
        }

        void setText(String text) {
            this.text.setText(text);
        }
    }
}
