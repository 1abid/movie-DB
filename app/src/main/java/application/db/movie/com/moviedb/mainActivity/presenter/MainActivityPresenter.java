package application.db.movie.com.moviedb.mainActivity.presenter;

import android.content.Context;
import application.db.movie.com.moviedb.SplashScreen.SplashMVP;
import application.db.movie.com.moviedb.mainActivity.MainActivityMVP;
import java.lang.ref.WeakReference;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class MainActivityPresenter implements MainActivityMVP.ProvidedPresenterOps , MainActivityMVP.RequiredPresenterOps {

  private WeakReference<MainActivityMVP.RequiredViewOps> mView;


  private MainActivityMVP.ProvidedModelOps mModel;

  public MainActivityPresenter(MainActivityMVP.RequiredViewOps view) {
    this.mView = new WeakReference<MainActivityMVP.RequiredViewOps>(view);
  }


  /**
   * called by activity every time during
   * setting up MVP , only called once
   *
   * @param model
   */
  public void setModel(MainActivityMVP.ProvidedModelOps model) {
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

  @Override public void onConfigurationChanged(MainActivityMVP.RequiredViewOps view) {
    setView(view);
  }

  @Override public void setView(MainActivityMVP.RequiredViewOps view) {
    this.mView = new WeakReference<MainActivityMVP.RequiredViewOps>(view);
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


  public MainActivityMVP.RequiredViewOps getView() throws NullPointerException{
    if (mView != null)
      return mView.get();
    else
      throw new NullPointerException("view is unavailable");
  }
}
