package com.example.myquizapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AnsViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private List<Question> ansList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Question> ansList) {
        this.context = context;
        this.ansList = ansList;
    }

    @NonNull
    @Override
    public AnsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by layout manager when it needs new view
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_answers, parent, false);
        return new AnsViewHolder(view);
    }

    static class AnsViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "AnsViewHolder";
        TextView question = null;
        TextView ans = null;

        public AnsViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "AnsViewHolder: starts");
            this.question = (TextView) itemView.findViewById(R.id.question);
            this.ans = (TextView) itemView.findViewById(R.id.answer);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AnsViewHolder holder, int position) {
        //Called by layout manager when it wants new data in existing row
        Question ansItem = ansList.get(position);
        Log.d(TAG, "onBindViewHolder: " + ansItem.getQuestion() + "-->" + position);

        holder.question.setText(ansItem.getQuestion());
        holder.ans.setText(ansItem.getOption4());

    }

    @Override
    public int getItemCount() {
        return ((ansList != null) && (ansList.size() != 0) ? ansList.size() : 0);
    }

    void loadNewData(List<Question> newAns) {
        ansList = newAns;
    }

    public Question getAns(int position) {
        return ((ansList != null) && (ansList.size() != 0) ? ansList.get(position) : null);
    }


}

