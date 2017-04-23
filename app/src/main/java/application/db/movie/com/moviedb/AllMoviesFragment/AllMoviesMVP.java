package application.db.movie.com.moviedb.AllMoviesFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Toast;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class AllMoviesMVP {

  /**
   * Required View methods available to Presenter.
   * A passive layer, responsible to show data
   * and receive user interactions
   * (presenter -> view)
   */
  public interface RequiredViewOps {

    Context getAppContext();
    Context getActivityContext();

  }

  /**
   * Required View methods available to Presenter.
   * A passive layer, responsible to show data
   * and receive user interactions
   * (presenter -> view)
   */
  public interface ProvidedPresenterOps {
    void onDestroy(boolean isChangingConfigurations);
    void onConfigurationChanged(RequiredViewOps view);
    void setView(RequiredViewOps view);
    boolean onCreateOptionMenu(Menu menu);
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
  public interface ProvidedModelOps{
    void onDestroy(boolean isConfigurationChanging);
  }
}
