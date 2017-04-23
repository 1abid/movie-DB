package application.db.movie.com.moviedb.AllMoviesFragment.view;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import application.db.movie.com.moviedb.AllMoviesFragment.AllMoviesMVP;
import application.db.movie.com.moviedb.AllMoviesFragment.model.AllMovieModel;
import application.db.movie.com.moviedb.AllMoviesFragment.presenter.AllMoviewPresenter;
import application.db.movie.com.moviedb.R;
import application.db.movie.com.moviedb.common.ActivityFragmentStatemaintainer;
import application.db.movie.com.moviedb.mainActivity.view.MainActivity;
import application.db.movie.com.moviedb.mainActivity.view.TabOneFragment;
import application.db.movie.com.moviedb.tabInterfaces.MovieFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class AllMovieFragment extends MovieFragment implements AllMoviesMVP.RequiredViewOps {

  private static AllMovieFragment instance;

  private MainActivity mainActivity;

  // Responsible to maintain the object's integrity
  // during configurations change
  private ActivityFragmentStatemaintainer mStateMaintainer;

  private String name = "Movies";

  private AllMoviesMVP.ProvidedPresenterOps mPresenter ;

  @BindView(R.id.viewPager) ViewPager mViewPager ;
  private ProgressDialog pDialog ;


  private Unbinder unbinder;

  public static AllMovieFragment getInstance(TabOneFragment fragment) {

    if (instance == null) {
      instance = new AllMovieFragment();
      instance.tabOnefragment = fragment;
    }

    return instance;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mainActivity = (MainActivity) context;

    mStateMaintainer = new ActivityFragmentStatemaintainer(mainActivity.getFragmentManager(),
        AllMovieFragment.class.getName());
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    setUpMVP();

    View view = inflater.inflate(R.layout.all_movies_layout, null);

    unbinder = ButterKnife.bind(this, view);

    mainActivity.getSupportActionBar().setTitle(name);


    return view;
  }

  @TargetApi(Build.VERSION_CODES.KITKAT) private void setUpMVP() {

    try {
      if (mStateMaintainer.isFirstTimeIn()) {

        initilize(this);
      } else {
        Log.d(getClass().getSimpleName(), " reinitializing...");
        reInitialize(this);
      }
    } catch (java.lang.InstantiationException | IllegalAccessException e) {
      Log.e(getClass().getSimpleName(), "onCreate() " + e);
      throw new RuntimeException(e);
    }
  }


  /**
   * Initialize relevant MVP Objects.
   * Creates a Presenter instance, saves the presenter in {@link ActivityFragmentStatemaintainer}
   */
  private void initilize(AllMoviesMVP.RequiredViewOps view)
      throws java.lang.InstantiationException, IllegalAccessException {

    //create the presenter
    AllMoviewPresenter presenter = new AllMoviewPresenter(this);

    //create the model
    AllMovieModel model = new AllMovieModel(presenter);

    //set presenter to model
    presenter.setModel(model);

    //save presenter
    /**and model to {@link ActivityFragmentStatemaintainer}**/
    mStateMaintainer.put(AllMoviesMVP.ProvidedPresenterOps.class.getSimpleName(), presenter);
    mStateMaintainer.put(AllMoviesMVP.ProvidedModelOps.class.getSimpleName(), model);

    //set the presenter as a interface
    //to limit communication with it
    mPresenter = presenter;
  }

  /**
   * Recovers Presenter and informs Presenter that occurred a config change.
   * If Presenter has been lost, recreates a instance
   */
  private void reInitialize(AllMoviesMVP.RequiredViewOps view)
      throws java.lang.InstantiationException, IllegalAccessException {

    mPresenter = mStateMaintainer.get(AllMoviesMVP.ProvidedPresenterOps.class.getSimpleName());

    if (mPresenter == null) {
      initilize(view);
    } else {
      mPresenter.onConfigurationChanged(this);
    }
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }

  @Override public String getName() {
    return this.name;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public Context getAppContext() {
    return mainActivity.getAppContext();
  }

  @Override public Context getActivityContext() {
    return mainActivity;
  }

  @Override public void onStop() {
    super.onStop();

    mPresenter.onConfigurationChanged(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();

    mPresenter.onDestroy(mainActivity.isChangingConfigurations());
  }
}
