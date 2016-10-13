package com.nikolaykul.weatherapp.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemSpaceDecoration extends RecyclerView.ItemDecoration {
    final private Side mSide;
    final private int mOffset;

    public ItemSpaceDecoration(Side side, int offset) {
        super();
        mSide = side;
        mOffset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        addOffsets(outRect);
    }

    private void addOffsets(Rect rect) {
        if (null == rect) return;
        switch (mSide) {
            case LEFT:
                rect.left = mOffset;
                break;
            case TOP:
                rect.top = mOffset;
                break;
            case RIGHT:
                rect.right = mOffset;
                break;
            case BOTTOM:
                rect.bottom = mOffset;
                break;
        }
    }

    public enum Side {
        LEFT, TOP, RIGHT, BOTTOM
    }
}
