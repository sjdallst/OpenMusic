package org.mhacks.openmusic.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;
import org.mhacks.openmusic.R;
import org.mhacks.openmusic.utils.database.Database;
import org.mhacks.openmusic.ui.adapters.BaseAdapter;
import org.mhacks.openmusic.ui.animators.RecyclerViewItemSlideAnimator;
import org.mhacks.openmusic.utils.LogUtils;
import org.mhacks.openmusic.utils.NetworkUtils;


@EFragment(R.layout.recyclerview_fragment)
public abstract class RecyclerViewFragment extends Fragment {

	@Bean
	NetworkUtils mNetworkUtils;
	@Bean
	Database mDatabase;

	@ViewById(android.R.id.button1)
	FloatingActionButton mFloatingActionButton;
	@ViewById(android.R.id.empty)
	LinearLayout mEmptyViewLayout;
	@ViewById(android.R.id.progress)
	ProgressBar mProgressBar;
	@ViewById(android.R.id.list)
	RecyclerView mRecyclerView;
	@ViewById(android.R.id.primary)
	SwipeRefreshLayout mSwipeRefreshLayout;
	@ViewById(android.R.id.custom)
	TextView mEmptyTextView;

	@DrawableRes(R.drawable.ic_all_done)
	Drawable mEmptyDrawable;
	@DrawableRes(R.drawable.ic_cloud_off)
	Drawable mErrorDrawable;
	String mEmptyText;
	@StringRes(R.string.generic_error_message)
	String mErrorText;
	@StringRes(R.string.generic_retry_message)
	String mRetryText;

	private OnFragmentInteractionListener mOnFragmentInteractionListener;
	private View.OnClickListener mEmptyViewOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			retry();
		}
	};

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			mOnFragmentInteractionListener = (OnFragmentInteractionListener) context;
		}
		catch (ClassCastException exception) {
			LogUtils.error(exception);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mRecyclerView == null ||
				mRecyclerView.getAdapter() == null ||
				mRecyclerView.getAdapter().getItemCount() <= 0) {
			retry();
		}
		else {
			mProgressBar.setVisibility(View.GONE);
			mSwipeRefreshLayout.setVisibility(View.VISIBLE);
		}
	}

	@AfterViews
	public void onViewCreated() {
		mEmptyTextView.setOnClickListener(mEmptyViewOnClickListener);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setItemAnimator(new RecyclerViewItemSlideAnimator());
		mSwipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light
		);
		mSwipeRefreshLayout.setOnRefreshListener(
				new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						retry();
					}
				});
		init();
		((BaseAdapter) mRecyclerView.getAdapter()).setItemClickListeners(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						onRecyclerViewItemClick(mRecyclerView.getChildAdapterPosition(view));
					}
				},
				new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View view) {
						return onRecyclerViewItemLongClick(mRecyclerView.getChildAdapterPosition(view));
					}
				});
	}

	@UiThread
	protected void setLoading() {
		mEmptyViewLayout.setVisibility(View.GONE);
		mFloatingActionButton.setVisibility(View.GONE);
		if (mSwipeRefreshLayout.getVisibility() == View.VISIBLE) {
			mSwipeRefreshLayout.setRefreshing(true);
		} else {
			mProgressBar.setVisibility(View.VISIBLE);
		}
	}

	@UiThread
	protected void setLoadingFailed() {
		if (mSwipeRefreshLayout.getVisibility() == View.VISIBLE) {
			mSwipeRefreshLayout.setRefreshing(false);
			Snackbar.make(
					mSwipeRefreshLayout,
					getResources().getString(
							R.string.generic_error_message),
					Snackbar.LENGTH_SHORT)
					.show();
		} else {
			mProgressBar.setVisibility(View.GONE);
			mEmptyTextView.setText(mErrorText + '\n' + mRetryText);
			mEmptyTextView.setCompoundDrawablesWithIntrinsicBounds(null, mErrorDrawable, null, null);
			mEmptyViewLayout.setVisibility(View.VISIBLE);
			mFloatingActionButton.setVisibility(View.GONE);
		}
	}

	@UiThread
	protected void setLoadingSucceeded() {
		mFloatingActionButton.setVisibility(View.VISIBLE);
		if (mRecyclerView.getAdapter().getItemCount() == 0) {
			mEmptyTextView.setText(mEmptyText);
			mEmptyTextView.setCompoundDrawablesWithIntrinsicBounds(null, mEmptyDrawable, null, null);
			mEmptyViewLayout.setVisibility(View.VISIBLE);
			mSwipeRefreshLayout.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.GONE);
			return;
		}
		mEmptyTextView.setVisibility(View.GONE);
		if (mSwipeRefreshLayout.getVisibility() == View.VISIBLE) {
			mSwipeRefreshLayout.setRefreshing(false);
		} else {
			mProgressBar.setVisibility(View.GONE);
			mSwipeRefreshLayout.setVisibility(View.VISIBLE);
		}
	}

	private void retry() {
		if (mNetworkUtils.isConnected()) {
			setLoading();
			fetch();
			return;
		}
		setLoadingFailed();
	}

	protected void setEmptyText(int resId) {
		mEmptyText = getContext().getResources().getString(resId);
	}

	protected void setRecyclerViewAdapter(BaseAdapter adapter) {
		mRecyclerView.setAdapter(adapter);
	}

	protected void setTitle(int resId) {
		mOnFragmentInteractionListener.onFragmentUpdateTitle(resId);
	}

	protected void switchFragment(Fragment fragment) {
		mOnFragmentInteractionListener.onFragmentSwitchFragment(fragment);
	}

	protected abstract void init();
	protected abstract void fetch();

	protected abstract void onRecyclerViewItemClick(int position);
	protected abstract boolean onRecyclerViewItemLongClick(int position);

	public interface OnFragmentInteractionListener {
		void onFragmentUpdateTitle(int resId);
		void onFragmentSwitchFragment(Fragment fragment);
	}
}
