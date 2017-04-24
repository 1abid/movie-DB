package application.db.movie.com.moviedb.tabInterfaces;

import android.support.v4.app.Fragment;
import android.view.View;
import application.db.movie.com.moviedb.mainActivity.view.TabOneFragment;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public abstract class MovieFragment extends Fragment implements View.OnClickListener {

  public TabOneFragment tabOnefragment ;

  @Override public void onClick(View v) {

  }



  public abstract String getName();
}
