package com.example.mycircleview2.gallery.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public abstract class MyPageradapter extends PagerAdapter {
    static final int IGNORE_ITEM_VIEW_TYPE = AdapterView.ITEM_VIEW_TYPE_IGNORE;

    private final AdapterBean AdapterBean;
    private View currentView;
    private OnSetPrimaryItemListener onSetPrimaryItemListener;

    public MyPageradapter() {
        this(new AdapterBean());
    }

    MyPageradapter(AdapterBean AdapterBean) {
        this.AdapterBean = AdapterBean;
        AdapterBean.setViewTypeCount(getViewTypeCount());
    }

    @Override
    public void notifyDataSetChanged() {
        AdapterBean.scrapActiveViews();
        super.notifyDataSetChanged();
    }

    @Override
    public final Object instantiateItem(final ViewGroup container, final int position) {
        int viewType = getItemViewType(position);
        View view = null;
        if (viewType != IGNORE_ITEM_VIEW_TYPE) {
            view = AdapterBean.getScrapView(position, viewType);
        }
        view = getView(position, view, container);
        container.addView(view);
        return view;
    }

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        int viewType = getItemViewType(position);
        if (viewType != IGNORE_ITEM_VIEW_TYPE) {
            AdapterBean.addScrapView(view, position, viewType);
        }
    }

    @Override
    public final boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (View) object;
        if (onSetPrimaryItemListener != null) {
            onSetPrimaryItemListener.OnSetPrimaryItem(currentView);
        }
    }

    public void addOnSetPrimaryItemListener(OnSetPrimaryItemListener listener) {
        this.onSetPrimaryItemListener = listener;
    }

    public int getViewTypeCount() {
        return 1;
    }


    @SuppressWarnings("UnusedParameters") // Argument potentially used by subclasses.
    public int getItemViewType(int position) {
        return 0;
    }


    public abstract View getView(int position, View convertView, ViewGroup container);

    public interface OnSetPrimaryItemListener{
        void OnSetPrimaryItem(View view);
    }
}
