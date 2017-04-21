package application.db.movie.com.moviedb.SplashScreen.model;

import application.db.movie.com.moviedb.SplashScreen.SplashMVP;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class SplashModel implements SplashMVP.ProvidedModelOps {

  private SplashMVP.ProvidedPresenterOps mPresenter;

  public SplashModel(SplashMVP.ProvidedPresenterOps mPresenter) {
    this.mPresenter = mPresenter;
  }

  @Override public void onDestroy(boolean isConfigurationChanging) {
    if(!isConfigurationChanging)
      mPresenter = null ;
  }
}
