package application.db.movie.com.moviedb.allMoviesFragment.model;

import android.util.Log;
import application.db.movie.com.moviedb.allMoviesFragment.AllMovieMVP;
import application.db.movie.com.moviedb.allMoviesFragment.presenter.AllMoviePresnter;
import application.db.movie.com.moviedb.application.RestService;
import application.db.movie.com.moviedb.rest.AllApiUrls;
import application.db.movie.com.moviedb.rest.TMDBApiInterface;
import application.db.movie.com.moviedb.rest.upComingMoviesModel.UpcomingMovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by VutkaBilai on 4/24/17.
 * mail : la4508@gmail.com
 */

public class AllMovieModel implements AllMovieMVP.ProvidedModelOps {

  private AllMovieMVP.ProvidedPresenterOps mPresenter;

  private AllMovieMVP.RequiredViewpagerPresenterOps mViewPagerPrenter;

  public AllMovieModel(AllMovieMVP.ProvidedPresenterOps mPresenter) {
    this.mPresenter = mPresenter;
  }

  public void setmViewPagerPrenter(AllMovieMVP.RequiredViewpagerPresenterOps prenter) {
    this.mViewPagerPrenter = prenter;
  }

  @Override public void onDestroy(boolean isConfigurationChanging) {
    if (!isConfigurationChanging) mPresenter = null;
  }

  @Override public void loadUpComingMovies() {


    TMDBApiInterface apiInterface = RestService.createService(TMDBApiInterface.class);

    Call<UpcomingMovieResponse> call = apiInterface.getUpcomingMovieList(AllApiUrls.TMDB_API_KEY);

    Log.i(getClass().getSimpleName() , "request "+call.request().toString());

    call.enqueue(new Callback<UpcomingMovieResponse>() {
      @Override public void onResponse(Call<UpcomingMovieResponse> call,
          Response<UpcomingMovieResponse> response) {

        if(response.isSuccessful())
          mViewPagerPrenter.showUpcomingMoviesSlider(response.body().getResults());
      }

      @Override public void onFailure(Call<UpcomingMovieResponse> call, Throwable t) {

      }
    });
  }
}
