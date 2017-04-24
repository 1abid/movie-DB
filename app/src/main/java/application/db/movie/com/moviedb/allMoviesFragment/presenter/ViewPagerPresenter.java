package application.db.movie.com.moviedb.allMoviesFragment.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import application.db.movie.com.moviedb.Adapters.CardPagerAdapter;
import application.db.movie.com.moviedb.R;
import application.db.movie.com.moviedb.allMoviesFragment.AllMovieMVP;
import application.db.movie.com.moviedb.rest.AllApiUrls;
import application.db.movie.com.moviedb.rest.upComingMoviesModel.UpcomingMovie;
import application.db.movie.com.moviedb.views.CardAdapter;
import application.db.movie.com.moviedb.views.MyAutoScrollViewPager;
import application.db.movie.com.moviedb.views.SelectableRoundedImageView;
import application.db.movie.com.moviedb.views.ShadowTransformer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VutkaBilai on 4/24/17.
 * mail : la4508@gmail.com
 */

public class ViewPagerPresenter implements AllMovieMVP.ProvidedViewPagerPresenterOps,
    AllMovieMVP.RequiredViewpagerPresenterOps {

  private WeakReference<AllMovieMVP.RequiredViewOps> mView;
  private AllMovieMVP.ProvidedModelOps mModel;

  private ShadowTransformer mCardShadowTransformer;
  private CardPagerAdapter mCardAdapter;
  private MyAutoScrollViewPager mViewPager;
  private float mBaseElevation;

  private List<CardView> mViews;
  private List<UpcomingMovie> mData;

  public ViewPagerPresenter(AllMovieMVP.RequiredViewOps view, AllMovieMVP.ProvidedModelOps model) {
    this.mView = new WeakReference<AllMovieMVP.RequiredViewOps>(view);
    this.mModel = model;

    this.mViews = new ArrayList<>();
    this.mData = new ArrayList<>();
  }

  @Override public void onDestroy(boolean isChangingConfigurations) {
    //view should be null every time onDestroy is called
    mView = null;

    //inform model about the event
    mModel.onDestroy(isChangingConfigurations);

    //activity destroyed
    if (!isChangingConfigurations) {
      mModel = null;
    }
  }

  @Override public void onConfigurationChanged(AllMovieMVP.RequiredViewOps view) {
    setView(view);
  }

  @Override public void setView(AllMovieMVP.RequiredViewOps view) {
    this.mView = new WeakReference<AllMovieMVP.RequiredViewOps>(view);
  }

  @Override public void loadUpcomingMovies() {

    mModel.loadUpComingMovies();
  }

  @Override public float getBaseElevation() {
    return mBaseElevation;
  }

  @Override public CardView getCardViewAt(int position) {
    return mViews.get(position);
  }

  @Override public int getCount() {
    return mData.size();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    View view = LayoutInflater.from(container.getContext())
        .inflate(R.layout.up_coming_movie_item, container, false);
    container.addView(view);
    bind(mData.get(position), view);
    CardView cardView = (CardView) view.findViewById(R.id.cardView);

    if (mBaseElevation == 0) {
      mBaseElevation = cardView.getCardElevation();
    }

    cardView.setMaxCardElevation(mBaseElevation * CardAdapter.MAX_ELEVATION_FACTOR);

    mViews.set(position, cardView);
    return view;
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
    mViews.set(position, null);
  }

  public void addCarditem(ArrayList<UpcomingMovie> movies) {
    for (UpcomingMovie item : movies) {

      mViews.add(null);
      mData.add(item);
    }
  }

  @Override public Context getAppContext() {
    try {

      return getView().getAppContext();
    } catch (NullPointerException e) {
      e.printStackTrace();

      return null;
    }
  }

  @Override public Context getActivityContext() {
    try {

      return getView().getAppContext();
    } catch (NullPointerException e) {
      e.printStackTrace();

      return null;
    }
  }

  @Override public void showUpcomingMoviesSlider(ArrayList<UpcomingMovie> movies) {


    if(mData.size() == 0)
      addCarditem(movies);

    mViewPager = (MyAutoScrollViewPager) getView().getView(R.id.viewPager);

    mCardAdapter = new CardPagerAdapter(this);

    mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
    mCardShadowTransformer.enableScaling(true);

    mViewPager.setAdapter(mCardAdapter);


    mViewPager.setPageTransformer(false, mCardShadowTransformer);
    mViewPager.setOffscreenPageLimit(3);

    mViewPager.setInterval(2000);
    mViewPager.startAutoScroll();

  }

  public AllMovieMVP.RequiredViewOps getView() throws NullPointerException {
    if (mView != null) {
      return mView.get();
    } else {
      throw new NullPointerException("view is unavailable");
    }
  }

  private void bind(UpcomingMovie item, View view) {

    TextView movieVoteAvgTv = (TextView) view.findViewById(R.id.vote_avg_tv);
    TextView movieTile = (TextView) view.findViewById(R.id.movie_title_tv);
    TextView movieReleaseDateTv = (TextView) view.findViewById(R.id.release_tv);

    SelectableRoundedImageView posterTv =
        (SelectableRoundedImageView) view.findViewById(R.id.movie_poster);

    movieVoteAvgTv.setText(String.valueOf(item.getVoteAvg()));
    movieTile.setText(item.getMovieTitle());
    movieReleaseDateTv.setText(item.getReleaseDate());

    ViewTarget target = new ViewTarget<SelectableRoundedImageView, GlideDrawable>(posterTv) {

      @Override public void onResourceReady(GlideDrawable resource,
          GlideAnimation<? super GlideDrawable> glideAnimation) {
        this.view.setImageDrawable(resource.getCurrent());
      }
    };

    Glide.with(getActivityContext())
        .load(AllApiUrls.IMAGE_PATH + item.getPosterPath())
        .crossFade()
        .fitCenter()
        .into(target);
  }


}
