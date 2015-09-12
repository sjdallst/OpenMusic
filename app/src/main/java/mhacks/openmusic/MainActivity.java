package mhacks.openmusic;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import mhacks.openmusic.fragments.EditSongFragment_;

@EActivity(R.layout.main_activity)
public class MainActivity extends AppCompatActivity {

	@ViewById(android.R.id.custom)
	Toolbar mToolbar;

	@AfterViews
	public void onViewChanged() {
		setSupportActionBar(mToolbar);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(android.R.id.primary, new EditSongFragment_())
				.commit();
	}
}
