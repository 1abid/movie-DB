package application.db.movie.com.moviedb.allMoviesFragment.presenter;

import android.content.Context;
import application.db.movie.com.moviedb.allMoviesFragment.AllMovieMVP;
import application.db.movie.com.moviedb.mainActivity.MainActivityMVP;
import java.lang.ref.WeakReference;

/**
 * Created by VutkaBilai on 4/24/17.
 * mail : la4508@gmail.com
 */

public class AllMoviePresnter implements AllMovieMVP.ProvidedPresenterOps , AllMovieMVP.RequiredPresenterOps {

  private WeakReference<AllMovieMVP.RequiredViewOps> mView ;

  private AllMovieMVP.ProvidedModelOps mModel ;

  public AllMoviePresnter(AllMovieMVP.RequiredViewOps view) {
    this.mView = new WeakReference<AllMovieMVP.RequiredViewOps>(view);
  }


  public void setModel(AllMovieMVP.ProvidedModelOps model){
    this.mModel = model ;
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

  public AllMovieMVP.RequiredViewOps getView() throws NullPointerException{
    if (mView != null)
      return mView.get();
    else
      throw new NullPointerException("view is unavailable");
  }
}
