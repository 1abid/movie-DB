package application.db.movie.com.moviedb.AllMoviesFragment.presenter;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import application.db.movie.com.moviedb.AllMoviesFragment.AllMoviesMVP;
import application.db.movie.com.moviedb.R;
import java.lang.ref.WeakReference;

/**
 * Created by VutkaBilai on 4/23/17.
 * mail : la4508@gmail.com
 */

public class AllMoviewPresenter implements AllMoviesMVP.ProvidedPresenterOps , AllMoviesMVP.RequiredPresenterOps {


  private WeakReference<AllMoviesMVP.RequiredViewOps> mView;

  private AllMoviesMVP.ProvidedModelOps mModel;

  public AllMoviewPresenter(AllMoviesMVP.RequiredViewOps view) {

    this.mView = new WeakReference<AllMoviesMVP.RequiredViewOps>(view);

  }

  public void setModel(AllMoviesMVP.ProvidedModelOps model){
    this.mModel = model;
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

  @Override public void onConfigurationChanged(AllMoviesMVP.RequiredViewOps view) {
      setView(view);
  }

  @Override public void setView(AllMoviesMVP.RequiredViewOps view) {
    this.mView = new WeakReference<AllMoviesMVP.RequiredViewOps>(view);
  }

  @Override public boolean onCreateOptionMenu(Menu menu) {
    MenuInflater inflater = new MenuInflater(getActivityContext());
    inflater.inflate(R.menu.main_menu, menu);

    return true;
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

      return getView().getActivityContext();


    } catch (NullPointerException e) {
      e.printStackTrace();

      return null;
    }
  }


  public AllMoviesMVP.RequiredViewOps getView() throws NullPointerException{
    if(mView != null)
      return mView.get() ;
    else
      throw new NullPointerException("View is unavailable");
  }
}
