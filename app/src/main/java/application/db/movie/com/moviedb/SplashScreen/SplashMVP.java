package application.db.movie.com.moviedb.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by VutkaBilai on 4/21/17.
 * mail : la4508@gmail.com
 */

public class SplashMVP {

  /**
   * Required View methods available to Presenter.
   * A passive layer, responsible to show data
   * and receive user interactions
   * (presenter -> view)
   */
  public interface RequiredViewOps{

    Context getAppContext();
    Context getActivityContext();

    void loadWebView(Intent intent);
    View getViewById(int viewID);

  }


  /**
   * Required View methods available to Presenter.
   * A passive layer, responsible to show data
   * and receive user interactions
   * (presenter -> view)
   */
  public interface ProvidedPresenterOps{
    void onDestroy(boolean isChangingConfigurations);
    void onConfigurationChanged(RequiredViewOps view);
    void setView(RequiredViewOps view);

    void CreateRequestToken();
    void createAccessToken(String requestToekn);


  }

  /**
   * required Presenter operation available
   * to model (model -> presenter)
   */
  public interface RequiredPresenterOps{
    Context getAppContext();
    Context getActivityContext();

    void showUpdate(String msg);
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
