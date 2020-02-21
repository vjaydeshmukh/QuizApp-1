package com.example.myquizapp;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";

    //interface to callback activity to inform about click
    // so that it can perform functionality after item has been clicked
    //this interface will be implemented in callback class(MainActivity)
    interface OnRecyclerClickListener {
        void onItemClick(View view, int position);

    }

    private final OnRecyclerClickListener listener;
    private final GestureDetectorCompat gestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, final OnRecyclerClickListener listener) {
        this.listener = listener;
        //create instance of gesture detector also creating anonymous class that extends SimpleOnGestureListener
        //so that we can override methods we are interested in
        gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: starts");
                //motion event passed as parameter they check to see which view is underneath it
                // by using co-ordinates on the screen that were tapped
                // checks what is underneath the x,y cordinates of tap
                View childview = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childview != null && listener != null) {
                    Log.d(TAG, "onSingleTapUp: calling listener.onItemClick");
                    listener.onItemClick(childview, recyclerView.getChildAdapterPosition(childview));
                }
                return true;
            }

//            @Override
//            public void onLongPress(MotionEvent e) {
//                Log.d(TAG, "onLongPress: starts");
//                View childview = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                if (childview != null && listener != null) {
//                    Log.d(TAG, "onLongPress: calling listener.onItemClick");
//                    listener.onItemLongClick(childview, recyclerView.getChildAdapterPosition(childview));
//                }
//            }
        });
    }

    // to detect any sort of touch activity on recycler view
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");
        //In iterecept touch event pass the event to gesture and let it help us
        //to call the appropriate methods for the events that we wanna handle
        if (gestureDetector != null) {
            boolean result = gestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: returned " + result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent: returned: false");
            return false;
        }
    }
}


