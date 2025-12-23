package com.example.kursovya.ui;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class StackLayoutManager extends RecyclerView.LayoutManager {

    private static final int MAX_VISIBLE = 3;
    private static final float SCALE_STEP = 0.05f;
    private static final float TRANS_Y = 40f;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);

        int count = Math.min(getItemCount(), MAX_VISIBLE);

        for (int i = count - 1; i >= 0; i--) {
            View view = recycler.getViewForPosition(i);
            addView(view);

            measureChildWithMargins(view, 0, 0);

            int width = getWidth();
            int height = getHeight();

            int viewWidth = getDecoratedMeasuredWidth(view);
            int viewHeight = getDecoratedMeasuredHeight(view);

            int left = (width - viewWidth) / 2;
            int top = (height - viewHeight) / 2;

            layoutDecorated(
                    view,
                    left,
                    top,
                    left + viewWidth,
                    top + viewHeight
            );

            float scale = 1f - i * SCALE_STEP;
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setTranslationY(i * TRANS_Y);
        }
    }
}
