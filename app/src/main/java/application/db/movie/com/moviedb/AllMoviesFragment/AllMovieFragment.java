package application.db.movie.com.moviedb.AllMoviesFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import application.db.movie.com.moviedb.R;
import application.db.movie.com.moviedb.common.ActivityFragmentStatemaintainer;
import application.db.movie.com.moviedb.mainActivity.view.MainActivity;
import application.db.movie.com.moviedb.mainActivity.view.TabOneFragment;
import application.db.movie.com.moviedb.tabInterfaces.MovieFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class AllMovieFragment extends MovieFragment  {

  private static AllMovieFragment instance;

  private MainActivity mainActivity;

  // Responsible to maintain the object's integrity
  // during configurations change
  private ActivityFragmentStatemaintainer mStateMaintainer;

  private String name = "Movies";



  public static AllMovieFragment getInstance(TabOneFragment fragment) {

    if (instance == null) {
      instance = new AllMovieFragment();
      instance.tabOnefragment = fragment ;
    }

    return instance;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mainActivity = (MainActivity) context;

    mStateMaintainer = new ActivityFragmentStatemaintainer(mainActivity.getFragmentManager() , AllMovieFragment.class.getName());
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {


    View view = inflater.inflate(R.layout.all_movies_layout , null);

    ButterKnife.bind(view);

    mainActivity.getSupportActionBar().setTitle(name);

    return view ;
  }

  @Override public String getName() {
    return this.name;
  }
}
