package org.mhacks.openmusic.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import org.mhacks.openmusic.ui.itemviews.ItemView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseAdapter<T, V extends View & ItemView.Binder<T>> extends
		RecyclerView.Adapter<ItemView<T, V>> {

	private OnClickListener mOnClickListener;
	private OnLongClickListener mOnLongClickListener;

	protected List<T> mItems = new ArrayList<>();

	@Override
	public final ItemView<T, V> onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ItemView<>(onCreateItemView(parent, viewType));
	}

	protected abstract V onCreateItemView(ViewGroup parent, int viewType);

	@Override
	public final void onBindViewHolder(final ItemView<T, V> viewHolder, int position) {
		viewHolder.view.bind(mItems.get(position));
		viewHolder.view.setOnClickListener(mOnClickListener);
		viewHolder.view.setOnLongClickListener(mOnLongClickListener);
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	public void addAll(Collection<T> collection) {
		clear();
		mItems.addAll(collection);
		notifyItemRangeInserted(0, mItems.size());
	}

	public void clear() {
		int size = mItems.size();
		mItems.clear();
		notifyItemRangeRemoved(0, size);
	}

	public T getItem(int position) {
		return mItems.get(position);
	}

	public void setItemClickListeners(
			OnClickListener onClickListener,
			OnLongClickListener onLongClickListener) {
		mOnClickListener = onClickListener;
		mOnLongClickListener = onLongClickListener;
	}
}
