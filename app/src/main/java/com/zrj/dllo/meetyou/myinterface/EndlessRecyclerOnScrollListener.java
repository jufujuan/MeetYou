package com.zrj.dllo.meetyou.myinterface;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.zrj.dllo.meetyou.tools.LogUtils;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/12/8.
 * 实现recylerview上拉加载的滚动监听
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private int previousTotal = 0;
    private boolean loading = false;//判断是否是加载状态
    int visibleItemCount, totalItemCount;
    int[] firstVisibleItems;

    private int currentPage = 1;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public EndlessRecyclerOnScrollListener(StaggeredGridLayoutManager mStaggeredGridLayoutManager) {
        this.mStaggeredGridLayoutManager = mStaggeredGridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();

        totalItemCount = mStaggeredGridLayoutManager.getItemCount();
        firstVisibleItems = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(new int[]{0, 1});


        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        LogUtils.d(firstVisibleItems[0]+"第一个位置");
        LogUtils.d(totalItemCount+"总数");
        LogUtils.d(visibleItemCount+"显示的总数\n");
        LogUtils.d(loading+"状态");
        LogUtils.d("------------------");
        if (!loading && (totalItemCount - visibleItemCount) <=firstVisibleItems[0]&&totalItemCount>=10) {
            LogUtils.d("走进了加载更多");
            currentPage++;
            onLoadMore(totalItemCount-1);
            loading = true;
        }
    }

    public abstract void onLoadMore(int endPosition);

}
