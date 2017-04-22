package application.db.movie.com.moviedb.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import application.db.movie.com.moviedb.SplashScreen.SplashMVP;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class MainActivityMVP {
  /**
   * Required View methods available to Presenter.
   * A passive layer, responsible to show data
   * and receive user interactions
   * (presenter -> view)
   */
  public interface RequiredViewOps{

    Context getAppContext();
    Context getActivityContext();


  }


  /**
   * Required View methods available to Presenter.
   * A passive layer, responsible to show data
   * and receive user interactions
   * (presenter -> view)
   */
  public interface ProvidedPresenterOps{
    void onDestroy(boolean isChangingConfigurations);
    void onConfigurationChanged(MainActivityMVP.RequiredViewOps view);
    void setView(MainActivityMVP.RequiredViewOps view);

  }

  /**
   * required Presenter operation available
   * to model (model -> presenter)
   */
  public interface RequiredPresenterOps{
    Context getAppContext();
    Context getActivityContext();
  }

  /**
   * Operations offered to model to communicate with presenter
   * Handles all data business logic
   * (presenter -> model)
   */

  public interface ProvidedModelOps {

    void onDestroy(boolean isConfigurationChanging);
  }
}
