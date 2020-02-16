package com.example.bihu.myRecycler;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerItem extends RecyclerView.ItemDecoration {
    private int space;
    private Paint paint;
    public MyRecyclerItem(int space, Paint paint){
        this.space=space;
        this.paint=paint;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        if (parent.getChildPosition(view) != 0) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = 0;
            outRect.top = space;
        }else{
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = 0;
            outRect.top = space/2;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = parent.getChildCount();
        for (int i = 0; i < itemCount; i++) {
             View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
             int left = child.getLeft();
             int top = child.getBottom()+ layoutParams.bottomMargin;
             int right = child.getRight();
             int bottom = top + space;
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
