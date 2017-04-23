package application.db.movie.com.moviedb.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import application.db.movie.com.moviedb.R;
import application.db.movie.com.moviedb.rest.AllApiUrls;
import application.db.movie.com.moviedb.rest.upComingMoviesModel.MovieCardItem;
import application.db.movie.com.moviedb.rest.upComingMoviesModel.UpcomingMovie;
import application.db.movie.com.moviedb.views.CardAdapter;
import application.db.movie.com.moviedb.views.SelectableRoundedImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VutkaBilai on 4/24/17.
 * mail : la4508@gmail.com
 */

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

  private float mBaseElevation;

  private List<CardView> mViews;
  private List<MovieCardItem> mData;

  private Context context;

  public CardPagerAdapter(Context context) {
    this.context = context;

    mViews = new ArrayList<>();
    mData = new ArrayList<>();
  }

  public void addCarditem(ArrayList<UpcomingMovie> movies) {
    for (UpcomingMovie item : movies) {
      MovieCardItem card;
      String movieVote = String.valueOf(item.getVoteAvg());
      String movieTitle = item.getMovieTitle();
      String releaseDate = item.getReleaseDate();
      String length = "128m";
      String genere = "drama , action , romance , crime ";
      String movieBackDropPath = item.getBackdropPath();
      String moviePoster = item.getPosterPath();

      card = new MovieCardItem(movieVote , movieTitle , releaseDate , length , genere , movieBackDropPath , moviePoster);

      mViews.add(null);
      mData.add(card);
    }
  }

  @Override public float getBaseElevation() {
    return mBaseElevation;
  }

  @Override public CardView getCardViewAt(int position) {
    return mViews.get(position);
  }

  @Override public int getCount() {
    return mData==null ? 0 : mData.size();
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

  private void bind(MovieCardItem item, View view) {

    TextView movieVoteAvgTv = (TextView) view.findViewById(R.id.vote_avg_tv);
    TextView movieTile = (TextView) view.findViewById(R.id.movie_title_tv);
    TextView movieReleaseDateTv = (TextView) view.findViewById(R.id.release_tv);
    TextView genereTv = (TextView) view.findViewById(R.id.movie_genere_tv);

    SelectableRoundedImageView posterTv =
        (SelectableRoundedImageView) view.findViewById(R.id.movie_poster);

    movieVoteAvgTv.setText(item.getMovieAvg_vote());
    movieTile.setText(item.getMovieName());
    movieReleaseDateTv.setText(item.getMovieReleaseDate());
    genereTv.setText(item.getMoviewGenere());

    ViewTarget target = new ViewTarget<SelectableRoundedImageView, GlideDrawable>(posterTv) {

      @Override public void onResourceReady(GlideDrawable resource,
          GlideAnimation<? super GlideDrawable> glideAnimation) {
        this.view.setImageDrawable(resource.getCurrent());
      }
    };

    Glide.with(context)
        .load(AllApiUrls.IMAGE_PATH + item.getMoviePosterPath())
        .crossFade()
        .fitCenter()
        .into(target);
  }
}
