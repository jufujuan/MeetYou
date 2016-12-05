package com.zrj.dllo.meetyou.find.listfind;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/12/3.
 * recyclerview 的行布局之间的间隔
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view) != 0) {
            outRect.top = space;
            outRect.left=space;
        }
    }
}