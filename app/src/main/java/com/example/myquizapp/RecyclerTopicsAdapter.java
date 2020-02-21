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

public class RecyclerTopicsAdapter extends RecyclerView.Adapter<RecyclerTopicsAdapter.TopicViewHolder> {
    private static final String TAG = "RecyclerTopicsAdapter";
    private List<Quiz> quizList;
    private Context context;

    public RecyclerTopicsAdapter(Context context, List<Quiz> quizList) {
        Log.d(TAG, "RecyclerTopicsAdapter: starts");
        this.context = context;
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by layout manager when it needs new view
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categories, parent, false);
        return new TopicViewHolder(view);
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "TopicViewHolder";
        TextView topic = null;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "TopicViewHolder: starts");
            this.topic = (TextView) itemView.findViewById(R.id.topic);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        //Called by layout manager when it wants new data in existing row
        Quiz quizItem = quizList.get(position);
        Log.d(TAG, "onBindViewHolder: " + quizItem.getQuizTopic() + "-->" + position);

        holder.topic.setText(quizItem.getQuizTopic());

    }

    @Override
    public int getItemCount() {
        return ((quizList != null) && (quizList.size() != 0) ? quizList.size() : 0);
    }

    void loadNewData(List<Quiz> newTopic) {
        quizList = newTopic;

    }

    public Quiz getQuizList(int position) {
        return ((quizList != null) && (quizList.size() != 0) ? quizList.get(position) : null);
    }

}
