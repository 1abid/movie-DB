package application.db.movie.com.moviedb.allMoviesFragment.model;

import application.db.movie.com.moviedb.allMoviesFragment.AllMovieMVP;

/**
 * Created by VutkaBilai on 4/24/17.
 * mail : la4508@gmail.com
 */

public class AllMovieModel implements AllMovieMVP.ProvidedModelOps {

  private AllMovieMVP.ProvidedPresenterOps mPresenter ;

  public AllMovieModel(AllMovieMVP.ProvidedPresenterOps mPresenter) {
    this.mPresenter = mPresenter;
  }

  @Override public void onDestroy(boolean isConfigurationChanging) {
    if(!isConfigurationChanging)
      mPresenter = null ;
  }
}
