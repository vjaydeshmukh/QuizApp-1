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

public class RecyclerAdminDashboard extends RecyclerView.Adapter<RecyclerAdminDashboard.ScoreViewHolder> {
    private static final String TAG = "RecyclerAdminDashboard";
    private List<Score> scoreList;
    private Context context;


    public RecyclerAdminDashboard(Context context, List<Score> scoreList) {
        Log.d(TAG, "RecyclerTopicsAdapter: starts");
        this.context = context;
        this.scoreList = scoreList;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by layout manager when it needs new view
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_score, parent, false);
        return new ScoreViewHolder(view);
    }

    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "TopicViewHolder";
        TextView score = null;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "TopicViewHolder: starts");
            this.score = (TextView) itemView.findViewById(R.id.score);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        //Called by layout manager when it wants new data in existing row
        Score scoreItem = scoreList.get(position);
        Log.d(TAG, "onBindViewHolder: " + scoreItem.getUsername() + "-->" + position);

        holder.score.setText(scoreItem.getUsername() + ":" + scoreItem.getScore());

    }

    @Override
    public int getItemCount() {
        return ((scoreList != null) && (scoreList.size() != 0) ? scoreList.size() : 0);
    }

    void loadNewData(List<Score> newScore) {
        scoreList = newScore;
    }

    public Score getScoreList(int position) {
        return ((scoreList != null) && (scoreList.size() != 0) ? scoreList.get(position) : null);
    }

}