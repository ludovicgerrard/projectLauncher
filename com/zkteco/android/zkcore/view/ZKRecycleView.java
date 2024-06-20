package com.zkteco.android.zkcore.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.zkteco.android.zkcore.R;

public class ZKRecycleView extends LinearLayout {
    private boolean hasMore;
    /* access modifiers changed from: private */
    public boolean isLoadMore;
    /* access modifiers changed from: private */
    public boolean isRefresh;
    /* access modifiers changed from: private */
    public LinearLayout loadMoreLayout;
    private TextView loadMoreText;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RefreshLoadListener mRefreshLoadListener;
    /* access modifiers changed from: private */
    public SwipeRefreshLayout mSwipeRefreshLayout;
    /* access modifiers changed from: private */
    public boolean pullRefreshEnable;
    private boolean pushRefreshEnable;

    public interface RefreshLoadListener {
        void onLoadMore();

        void onRefresh();
    }

    public ZKRecycleView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ZKRecycleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.hasMore = true;
        this.isRefresh = false;
        this.isLoadMore = false;
        this.pullRefreshEnable = true;
        this.pushRefreshEnable = true;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_rv_pull_load, (ViewGroup) null);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.core_srl_pull);
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setColorSchemeResources(17170453, 17170451, 17170457);
        this.mSwipeRefreshLayout.setOnRefreshListener(new SRLayoutOnRefresh());
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.core_rv_content);
        this.mRecyclerView = recyclerView;
        recyclerView.setVerticalScrollBarEnabled(true);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mRecyclerView.addOnScrollListener(new OnScrollRv());
        this.mRecyclerView.setOnTouchListener(new oTouchRv());
        this.loadMoreLayout = (LinearLayout) inflate.findViewById(R.id.core_ll_load);
        this.loadMoreText = (TextView) inflate.findViewById(R.id.core_tv_load);
        this.loadMoreLayout.setVisibility(8);
        addView(inflate);
    }

    private class OnScrollRv extends RecyclerView.OnScrollListener {
        private OnScrollRv() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            int i3;
            int i4;
            super.onScrolled(recyclerView, i, i2);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int itemCount = layoutManager.getItemCount();
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                i4 = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                i3 = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                if (i3 == -1) {
                    i3 = gridLayoutManager.findLastVisibleItemPosition();
                }
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                i4 = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                i3 = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (i3 == -1) {
                    i3 = linearLayoutManager.findLastVisibleItemPosition();
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] iArr = new int[staggeredGridLayoutManager.getSpanCount()];
                staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(iArr);
                i3 = findMax(iArr);
                i4 = staggeredGridLayoutManager.findFirstVisibleItemPositions(iArr)[0];
            } else {
                i4 = 0;
                i3 = 0;
            }
            if (i4 != 0 && i4 != -1) {
                ZKRecycleView.this.setSwipeRefreshEnable(false);
            } else if (ZKRecycleView.this.getPullRefreshEnable()) {
                ZKRecycleView.this.setSwipeRefreshEnable(true);
            }
            if (ZKRecycleView.this.getPushRefreshEnable() && !ZKRecycleView.this.isRefresh() && ZKRecycleView.this.isHasMore() && i3 == itemCount - 1 && !ZKRecycleView.this.isLoadMore()) {
                if (i > 0 || i2 > 0) {
                    ZKRecycleView.this.setIsLoadMore(true);
                    ZKRecycleView.this.loadMore();
                }
            }
        }

        private int findMax(int[] iArr) {
            int i = iArr[0];
            for (int i2 : iArr) {
                if (i2 > i) {
                    i = i2;
                }
            }
            return i;
        }
    }

    private class oTouchRv implements View.OnTouchListener {
        private oTouchRv() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return ZKRecycleView.this.isRefresh || ZKRecycleView.this.isLoadMore;
        }
    }

    private class SRLayoutOnRefresh implements SwipeRefreshLayout.OnRefreshListener {
        private SRLayoutOnRefresh() {
        }

        public void onRefresh() {
            if (!ZKRecycleView.this.isRefresh()) {
                ZKRecycleView.this.setIsRefresh(true);
                ZKRecycleView.this.refresh();
            }
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mRecyclerView.setLayoutManager(layoutManager);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return this.mRecyclerView.getLayoutManager();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            this.mRecyclerView.setAdapter(adapter);
        }
    }

    public void setPullRefreshEnable(boolean z) {
        this.pullRefreshEnable = z;
        setSwipeRefreshEnable(z);
    }

    public void setPushRefreshEnable(boolean z) {
        this.pushRefreshEnable = z;
    }

    public void setPullLoadMoreCompleted() {
        this.isRefresh = false;
        setRefreshing(false);
        this.isLoadMore = false;
        this.loadMoreLayout.animate().translationY((float) this.loadMoreLayout.getHeight()).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).start();
    }

    public boolean getPullRefreshEnable() {
        return this.pullRefreshEnable;
    }

    public void setSwipeRefreshEnable(boolean z) {
        this.mSwipeRefreshLayout.setEnabled(z);
    }

    public void setRefreshing(final boolean z) {
        this.mSwipeRefreshLayout.post(new Runnable() {
            public void run() {
                if (ZKRecycleView.this.pullRefreshEnable) {
                    ZKRecycleView.this.mSwipeRefreshLayout.setRefreshing(z);
                }
            }
        });
    }

    public boolean getPushRefreshEnable() {
        return this.pushRefreshEnable;
    }

    public void refresh() {
        RefreshLoadListener refreshLoadListener = this.mRefreshLoadListener;
        if (refreshLoadListener != null) {
            refreshLoadListener.onRefresh();
        }
    }

    public void loadMore() {
        if (this.mRefreshLoadListener != null && this.hasMore) {
            this.loadMoreLayout.animate().translationY(0.0f).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
                public void onAnimationStart(Animator animator) {
                    ZKRecycleView.this.loadMoreLayout.setVisibility(0);
                }
            }).start();
            invalidate();
            this.mRefreshLoadListener.onLoadMore();
        }
    }

    public void setRefreshLoadListener(RefreshLoadListener refreshLoadListener) {
        this.mRefreshLoadListener = refreshLoadListener;
    }

    public boolean isLoadMore() {
        return this.isLoadMore;
    }

    public void setIsLoadMore(boolean z) {
        this.isLoadMore = z;
    }

    public boolean isRefresh() {
        return this.isRefresh;
    }

    public void setIsRefresh(boolean z) {
        this.isRefresh = z;
    }

    public boolean isHasMore() {
        return this.hasMore;
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }
}
