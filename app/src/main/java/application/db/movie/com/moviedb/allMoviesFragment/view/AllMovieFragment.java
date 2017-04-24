package application.db.movie.com.moviedb.allMoviesFragment.view;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import application.db.movie.com.moviedb.R;
import application.db.movie.com.moviedb.allMoviesFragment.AllMovieMVP;
import application.db.movie.com.moviedb.allMoviesFragment.model.AllMovieModel;
import application.db.movie.com.moviedb.allMoviesFragment.presenter.AllMoviePresnter;
import application.db.movie.com.moviedb.allMoviesFragment.presenter.ViewPagerPresenter;
import application.db.movie.com.moviedb.common.ActivityFragmentStatemaintainer;
import application.db.movie.com.moviedb.mainActivity.view.MainActivity;
import application.db.movie.com.moviedb.mainActivity.view.TabOneFragment;
import application.db.movie.com.moviedb.tabInterfaces.MovieFragment;
import application.db.movie.com.moviedb.views.MyAutoScrollViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by VutkaBilai on 4/24/17.
 * mail : la4508@gmail.com
 */

public class AllMovieFragment extends MovieFragment implements AllMovieMVP.RequiredViewOps{

  private static AllMovieFragment instance;

  private MainActivity mainActivity;

  private Unbinder unbinder;

  @BindView(R.id.viewPager) MyAutoScrollViewPager mViewPager;

  // Responsible to maintain the object's integrity
  // during configurations change
  private ActivityFragmentStatemaintainer mStateMaintainer;


  private AllMovieMVP.ProvidedPresenterOps mPresenter ;
  private AllMovieMVP.ProvidedViewPagerPresenterOps mViewpagerPresenter ;

  private View rootView ;

  private ProgressDialog progressDialog;
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

    setUpMvp();

    rootView = inflater.inflate(R.layout.all_movies_layout, null);

    unbinder = ButterKnife.bind(this, rootView);

    mainActivity.getSupportActionBar().setTitle(getName());

    createPDialog();

    return rootView ;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mViewpagerPresenter.loadUpcomingMovies();
  }

  @Override public String getName() {
    return "Movies";
  }

  @Override public Context getAppContext() {
    return mainActivity.getAppContext();
  }

  @Override public Context getActivityContext() {
    return mainActivity.getActivityContext();
  }

  @Override public View getView(int id) {
    return rootView.findViewById(id);
  }

  @Override public void showPDialog() {
    if(progressDialog != null)
      progressDialog.show();
  }

  @Override public void hidePDialog() {
    if(progressDialog.isShowing()){
      progressDialog.hide();
    }
  }

  private void createPDialog(){
    progressDialog = new ProgressDialog(mainActivity , R.style.AppTheme_Dark_Dialog);

    progressDialog.setMessage(getActivityContext().getString(R.string.loading));
    progressDialog.setIndeterminate(true);
    progressDialog.setCancelable(false);

  }

  @TargetApi(Build.VERSION_CODES.KITKAT) private void setUpMvp() {
    try {
      if (mStateMaintainer.isFirstTimeIn()) {

        initilize(this);
        Log.i(getClass().getSimpleName(), "initializing..."+AllMovieFragment.class.getSimpleName());
      } else {
        Log.i(getClass().getSimpleName(), " reinitializing..."+AllMovieFragment.class.getSimpleName());
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
  private void initilize(AllMovieMVP.RequiredViewOps view)
      throws java.lang.InstantiationException, IllegalAccessException {

    //create the presenter
    AllMoviePresnter presenter = new AllMoviePresnter(view);

    //create the model
    AllMovieModel model = new AllMovieModel(presenter);

    //set presenter to model
    presenter.setModel(model);

    ViewPagerPresenter viewPagerPresenter = new ViewPagerPresenter(view , model);

    model.setmViewPagerPrenter(viewPagerPresenter);

    //save presenter
    /**and model to {@link ActivityFragmentStatemaintainer}**/
    mStateMaintainer.put(AllMovieMVP.ProvidedPresenterOps.class.getSimpleName(), presenter);
    mStateMaintainer.put(AllMovieMVP.ProvidedModelOps.class.getSimpleName(), model);
    mStateMaintainer.put(AllMovieMVP.ProvidedViewPagerPresenterOps.class.getSimpleName() , viewPagerPresenter);

    //set the presenter as a interface
    //to limit communication with it
    mPresenter = presenter;
    mViewpagerPresenter = viewPagerPresenter ;
  }

  /**
   * Recovers Presenter and informs Presenter that occurred a config change.
   * If Presenter has been lost, recreates a instance
   */
  private void reInitialize(AllMovieMVP.RequiredViewOps view)
      throws java.lang.InstantiationException, IllegalAccessException {

    mPresenter = mStateMaintainer.get(AllMovieMVP.ProvidedPresenterOps.class.getSimpleName());
    mViewpagerPresenter = mStateMaintainer.get(AllMovieMVP.ProvidedViewPagerPresenterOps.class.getSimpleName());

    if (mPresenter == null) {
      initilize(view);
    } else {
      mPresenter.onConfigurationChanged(this);
      mViewpagerPresenter.onConfigurationChanged(this);
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();

    unbinder.unbind();
  }

  @Override public void onStop() {
    super.onStop();

    mPresenter.onConfigurationChanged(this);
    mViewpagerPresenter.onConfigurationChanged(this);
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    mPresenter.onDestroy(mainActivity.isChangingConfigurations());
    mPresenter.onDestroy(mainActivity.isChangingConfigurations());
  }
}
