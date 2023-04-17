package com.study.contest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.contest.R;
import com.study.contest.model.UserRecord;
import com.study.contest.model.enums.ColumEnum;
import com.study.contest.view.EditNote;

import java.util.List;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.ViewHolder> {
    private final List<UserRecord> userRecords;
    private final Context context;
    private final Activity activity;

    public RowAdapter(List<UserRecord> userRecords, Context context, Activity activity) {
        this.userRecords = userRecords;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView noteTitle = holder.itemView.findViewById(R.id.titel_note);
        TextView noteDate = holder.itemView.findViewById(R.id.date_creation);
        noteTitle.setText(userRecords.get(position).getTitle());
        noteDate.setText(userRecords.get(position).getDate());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditNote.class);
            intent.putExtra(ColumEnum.ID.getName(), String.valueOf(userRecords.get(position).getId()));
            intent.putExtra(ColumEnum.TITLE.getName(), String.valueOf(userRecords.get(position).getTitle()));
            intent.putExtra(ColumEnum.CONTENT.getName(), String.valueOf(userRecords.get(position).getContent()));
            intent.putExtra(ColumEnum.DATE.getName(), String.valueOf(userRecords.get(position).getDate()));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return userRecords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tittle;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.titel_note);
            date = itemView.findViewById(R.id.date_creation);
        }
    }


}
