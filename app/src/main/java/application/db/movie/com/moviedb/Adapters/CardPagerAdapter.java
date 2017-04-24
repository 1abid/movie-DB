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
import application.db.movie.com.moviedb.allMoviesFragment.AllMovieMVP;
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



  private AllMovieMVP.ProvidedViewPagerPresenterOps mViewpagerPresnter;

  public CardPagerAdapter(AllMovieMVP.ProvidedViewPagerPresenterOps pagerPresenterOps) {

    mViewpagerPresnter = pagerPresenterOps ;
  }



  @Override public float getBaseElevation() {
    return mViewpagerPresnter.getBaseElevation();
  }

  @Override public CardView getCardViewAt(int position) {
    return mViewpagerPresnter.getCardViewAt(position);
  }

  @Override public int getCount() {
    return mViewpagerPresnter.getCount();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    return mViewpagerPresnter.instantiateItem(container , position);
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    mViewpagerPresnter.destroyItem(container , position , object);
  }


}
