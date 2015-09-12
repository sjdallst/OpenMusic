package org.mhacks.openmusic.ui.itemviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemView<T, V extends View & ItemView.Binder<T>> extends
		RecyclerView.ViewHolder {

	public V view;

	public ItemView(V itemView) {
		super(itemView);
		view = itemView;
	}

	public interface Binder<T> {
		void bind(T data);
	}
}
