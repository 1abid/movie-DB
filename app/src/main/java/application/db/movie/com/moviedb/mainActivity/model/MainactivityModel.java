package application.db.movie.com.moviedb.mainActivity.model;

import application.db.movie.com.moviedb.mainActivity.MainActivityMVP;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class MainactivityModel implements MainActivityMVP.ProvidedModelOps {

  private MainActivityMVP.ProvidedPresenterOps mPresenter;

  public MainactivityModel(MainActivityMVP.ProvidedPresenterOps mPresenter) {
    this.mPresenter = mPresenter;
  }

  @Override public void onDestroy(boolean isConfigurationChanging) {
    if(!isConfigurationChanging)
      mPresenter = null ;
  }
}
