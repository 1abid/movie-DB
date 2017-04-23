package application.db.movie.com.moviedb.AllMoviesFragment.model;

import application.db.movie.com.moviedb.AllMoviesFragment.AllMoviesMVP;

/**
 * Created by VutkaBilai on 4/23/17.
 * mail : la4508@gmail.com
 */

public class AllMovieModel implements AllMoviesMVP.ProvidedModelOps {

  private AllMoviesMVP.ProvidedPresenterOps mPresenter ;

  public AllMovieModel(AllMoviesMVP.ProvidedPresenterOps mPresenter) {
    this.mPresenter = mPresenter;
  }

  @Override public void onDestroy(boolean isConfigurationChanging) {
    if(!isConfigurationChanging)
      mPresenter = null ;
  }
}
