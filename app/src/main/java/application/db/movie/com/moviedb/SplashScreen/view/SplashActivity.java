package application.db.movie.com.moviedb.SplashScreen.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import application.db.movie.com.moviedb.R;
import application.db.movie.com.moviedb.SplashScreen.SplashMVP;
import application.db.movie.com.moviedb.SplashScreen.model.SplashModel;
import application.db.movie.com.moviedb.SplashScreen.presenter.SplashPresenter;
import application.db.movie.com.moviedb.common.ActivityFragmentStatemaintainer;
import application.db.movie.com.moviedb.mainActivity.MainActivity;
import application.db.movie.com.moviedb.utils.PreferenceUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements SplashMVP.RequiredViewOps {

  private SplashMVP.ProvidedPresenterOps mPresenter;

  // Responsible to maintain the object's integrity
  // during configurations change
  private ActivityFragmentStatemaintainer mStateMaintainer =
      new ActivityFragmentStatemaintainer(getFragmentManager(), getClass().getName());


  @BindView(R.id.splash_tv) TextView statusTv ;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setUpMvp();
    Log.d(getClass().getSimpleName(), "lifecycle_event :onCreate()");
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);


    if(!PreferenceUtils.getAccesToken(getActivityContext()).equals("")){
      goToNext(new Intent(this , MainActivity.class));
    }

  }

  @Override protected void onResume() {
    super.onResume();

    if(!PreferenceUtils.isApproved(getActivityContext()))
      mPresenter.createRequestToken();
    else
      mPresenter.createAccessToken(PreferenceUtils.getRequestToken(getActivityContext()));
  }

  @Override public Context getAppContext() {
    return getApplicationContext();
  }

  @Override public Context getActivityContext() {
    return this;
  }

  @Override public void loadWebView(Intent intent) {
    startActivity(intent);
  }

  @Override public View getViewById(int viewID) {
    return findViewById(viewID);
  }

  @Override public void goToNext(Intent intent) {
    startActivity(intent);
    finish();
  }

  /**
   * Setup Model View Presenter pattern.
   * Use a {@link ActivityFragmentStatemaintainer} to maintain the
   * Presenter and Model instances between configuration changes.
   * Could be done differently,
   * using a dependency injection for example.
   */
  @TargetApi(Build.VERSION_CODES.KITKAT) private void setUpMvp() {

    try {
      if (mStateMaintainer.isFirstTimeIn()) {

        initilize(this);
      } else {
        Log.d(getClass().getSimpleName(), " reinitializing...");
        reInitialize(this);
      }
    } catch (InstantiationException | IllegalAccessException e) {
      Log.e(getClass().getSimpleName(), "onCreate() " + e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Initialize relevant MVP Objects.
   * Creates a Presenter instance, saves the presenter in {@link ActivityFragmentStatemaintainer}
   */
  private void initilize(SplashMVP.RequiredViewOps view)
      throws InstantiationException, IllegalAccessException {

    //create the presenter
    SplashPresenter presenter = new SplashPresenter(this);

    //create the model
    SplashModel model = new SplashModel(presenter);

    //set presenter to model
    presenter.setModel(model);

    //save presenter
    /**and model to {@link ActivityFragmentStatemaintainer}**/
    mStateMaintainer.put(SplashMVP.ProvidedPresenterOps.class.getSimpleName(), presenter);
    mStateMaintainer.put(SplashMVP.ProvidedModelOps.class.getSimpleName(), model);

    //set the presenter as a interface
    //to limit communication with it
    mPresenter = presenter;
  }

  /**
   * Recovers Presenter and informs Presenter that occurred a config change.
   * If Presenter has been lost, recreates a instance
   */
  private void reInitialize(SplashMVP.RequiredViewOps view)
      throws InstantiationException, IllegalAccessException {

    mPresenter = mStateMaintainer.get(SplashMVP.ProvidedPresenterOps.class.getSimpleName());

    if (mPresenter == null) {
      initilize(view);
    } else {
      mPresenter.onConfigurationChanged(this);
    }
  }

  @Override protected void onStop() {
    super.onStop();
    Log.d(getClass().getSimpleName(), "lifecycle_event :onStop()");

    mPresenter.onConfigurationChanged(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    Log.d(getClass().getSimpleName(), "lifecycle_event :onDestroy()");
    mPresenter.onDestroy(isChangingConfigurations());
  }
}
