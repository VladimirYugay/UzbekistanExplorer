package com.example.vladimir.uzbekistanexplorer;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Toast;

public class FABScrollBehavior extends FloatingActionButton.Behavior{

    int current = 0;

    public FABScrollBehavior(){
        super();
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        int total = coordinatorLayout.getLayoutParams().height;
        current += dyConsumed;
        if(current >= total / 6 * 5 && child.getVisibility() == View.VISIBLE){
            child.hide();
        } else {
            child.show();
        }


//
//        if(dyConsumed > coordinatorLayout.getLayoutParams().height / 6 * 5 && child.getVisibility() == View.VISIBLE){
//            child.hide();
//        }else if(dyConsumed < coordinatorLayout.getLayoutParams().height / 6 * 5 && child.getVisibility() == View.GONE){
//            child.show();
//        }
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }
}
