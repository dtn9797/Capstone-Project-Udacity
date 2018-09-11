package com.example.duynguyen.amashop.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class ProductGridItemDecoration extends RecyclerView.ItemDecoration {
    private int verticalPadding;
    private int horizontalPadding;

    public ProductGridItemDecoration(int verticalPadding, int horizontalPadding) {
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
//        int pos = parent.getChildLayoutPosition(view);

        outRect.left = horizontalPadding;
        outRect.right = horizontalPadding;
        outRect.bottom=verticalPadding;
        outRect.top= verticalPadding;
    }
}
