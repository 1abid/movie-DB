package application.db.movie.com.moviedb.allMoviesFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import application.db.movie.com.moviedb.mainActivity.MainActivityMVP;

/**
 * Created by VutkaBilai on 4/24/17.
 * mail : la4508@gmail.com
 */

public class AllMovieMVP {
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
    void onConfigurationChanged(AllMovieMVP.RequiredViewOps view);
    void setView(AllMovieMVP.RequiredViewOps view);

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
   * Required View methods available to Presenter.
   * A passive layer, responsible to show data
   * and receive user interactions
   * (presenter -> view)
   */
  public interface ProvidedViewPagerPresenterOps{
    void onDestroy(boolean isChangingConfigurations);
    void onConfigurationChanged(AllMovieMVP.RequiredViewOps view);
    void setView(AllMovieMVP.RequiredViewOps view);

  }

  /**
   * required Presenter operation available
   * to model (model -> presenter)
   */
  public interface RequiredViewpagerPresenterOps{
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
