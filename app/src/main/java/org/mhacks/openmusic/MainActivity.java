package org.mhacks.openmusic;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.mhacks.openmusic.fragments.SongsListFragment_;

import static org.mhacks.openmusic.fragments.RecyclerViewFragment.OnFragmentInteractionListener;

@EActivity(R.layout.main_activity)
public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

	@ViewById(android.R.id.custom)
	Toolbar mToolbar;

	@AfterViews
	public void onViewChanged() {
		setSupportActionBar(mToolbar);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(android.R.id.primary, new SongsListFragment_())
				.commit();
	}

	@Override
	public void onFragmentUpdateTitle(int resId) {
		setTitle(resId);
	}

	@Override
	public void onFragmentSwitchFragment(Fragment fragment) {
		switchFragment(fragment);
	}

	private void switchFragment(Fragment fragment) {
		getSupportFragmentManager()
				.beginTransaction()
				.setCustomAnimations(
						R.anim.slide_in_left,
						R.anim.slide_out_left,
						R.anim.slide_in_right,
						R.anim.slide_out_right)
				.replace(android.R.id.primary, fragment)
				.addToBackStack(null)
				.commit();
	}
}
