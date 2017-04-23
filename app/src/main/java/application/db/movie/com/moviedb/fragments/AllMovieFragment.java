package application.db.movie.com.moviedb.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import application.db.movie.com.moviedb.Adapters.CardPagerAdapter;
import application.db.movie.com.moviedb.R;
import application.db.movie.com.moviedb.application.RestService;
import application.db.movie.com.moviedb.mainActivity.view.MainActivity;
import application.db.movie.com.moviedb.mainActivity.view.TabOneFragment;
import application.db.movie.com.moviedb.rest.AllApiUrls;
import application.db.movie.com.moviedb.rest.TMDBApiInterface;
import application.db.movie.com.moviedb.rest.upComingMoviesModel.UpcomingMovie;
import application.db.movie.com.moviedb.rest.upComingMoviesModel.UpcomingMovieResponse;
import application.db.movie.com.moviedb.tabInterfaces.MovieFragment;
import application.db.movie.com.moviedb.utils.PreferenceUtils;
import application.db.movie.com.moviedb.views.MyAutoScrollViewPager;
import application.db.movie.com.moviedb.views.ShadowTransformer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by VutkaBilai on 4/24/17.
 * mail : la4508@gmail.com
 */

public class AllMovieFragment extends MovieFragment {

  private static AllMovieFragment instance;

  private MainActivity mainActivity;

  private Unbinder unbinder;

  ShadowTransformer mCardShadowTransformer;
  CardPagerAdapter mCardAdapter;
  @BindView(R.id.viewPager) MyAutoScrollViewPager mViewPager;

  private ArrayList<UpcomingMovie> upcomingMovies = new ArrayList<>();

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
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.all_movies_layout, null);

    unbinder = ButterKnife.bind(this, view);

    mainActivity.getSupportActionBar().setTitle(getName());

    loadUpComingMovies();

    mCardAdapter = new CardPagerAdapter(getContext());
    mViewPager.setAdapter(mCardAdapter);

    mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
    mCardShadowTransformer.enableScaling(true);



    mViewPager.setPageTransformer(false, mCardShadowTransformer);
    mViewPager.setOffscreenPageLimit(3);

    mViewPager.setInterval(2000);
    mViewPager.startAutoScroll();

    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public String getName() {
    return "Movies";
  }

  private void loadUpComingMovies() {

    TMDBApiInterface apiInterface = RestService.createService(TMDBApiInterface.class,
        PreferenceUtils.getAccesToken(mainActivity));

    Call<UpcomingMovieResponse> call =
        apiInterface.getUpcomingMovieList(AllApiUrls.TMDB_API_KEY);

    call.enqueue(new Callback<UpcomingMovieResponse>() {
      @Override public void onResponse(Call<UpcomingMovieResponse> call,
          Response<UpcomingMovieResponse> response) {

        if(response.isSuccessful()) {
          upcomingMovies = response.body().getResults();
          mCardAdapter.addCarditem(upcomingMovies);
          mCardAdapter.notifyDataSetChanged();


        }

      }

      @Override public void onFailure(Call<UpcomingMovieResponse> call, Throwable t) {
        Log.d("response failed", t.getMessage());
      }
    });
  }
}
