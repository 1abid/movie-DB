package application.db.movie.com.moviedb.mainActivity.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import application.db.movie.com.moviedb.R;
import application.db.movie.com.moviedb.SplashScreen.SplashMVP;
import application.db.movie.com.moviedb.SplashScreen.model.SplashModel;
import application.db.movie.com.moviedb.SplashScreen.presenter.SplashPresenter;
import application.db.movie.com.moviedb.common.ActivityFragmentStatemaintainer;
import application.db.movie.com.moviedb.mainActivity.MainActivityMVP;
import application.db.movie.com.moviedb.mainActivity.model.MainactivityModel;
import application.db.movie.com.moviedb.mainActivity.presenter.MainActivityPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.roughike.bottombar.BottomBar;

public class MainActivity extends AppCompatActivity implements MainActivityMVP.RequiredViewOps {

    private MainActivityMVP.ProvidedPresenterOps mPresenter ;


    // Responsible to maintain the object's integrity
    // during configurations change
    private ActivityFragmentStatemaintainer mStateMaintainer =
        new ActivityFragmentStatemaintainer(getFragmentManager(), getClass().getName());

    @BindView(R.id.bottomBar) BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpMvp();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT) private void setUpMvp() {
        try {
            if (mStateMaintainer.isFirstTimeIn()) {

                initilize(this);
            } else {
                Log.d(getClass().getSimpleName(), " reinitializing...");
                reInitialize(this);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e(getClass().getSimpleName(), "onCreate() " + e);
            throw new RuntimeException(e);
        }
    }

    @Override public Context getAppContext() {
        return getApplicationContext();
    }

    @Override public Context getActivityContext() {
        return getActivityContext();
    }


    /**
     * Initialize relevant MVP Objects.
     * Creates a Presenter instance, saves the presenter in {@link ActivityFragmentStatemaintainer}
     */
    private void initilize(MainActivityMVP.RequiredViewOps view)
        throws InstantiationException, IllegalAccessException {

        //create the presenter
        MainActivityPresenter presenter = new MainActivityPresenter(this);

        //create the model
        MainactivityModel model = new MainactivityModel(presenter);

        //set presenter to model
        presenter.setModel(model);

        //save presenter
        /**and model to {@link ActivityFragmentStatemaintainer}**/
        mStateMaintainer.put(MainActivityMVP.ProvidedPresenterOps.class.getSimpleName(), presenter);
        mStateMaintainer.put(MainActivityMVP.ProvidedModelOps.class.getSimpleName(), model);

        //set the presenter as a interface
        //to limit communication with it
        mPresenter = presenter;
    }

    /**
     * Recovers Presenter and informs Presenter that occurred a config change.
     * If Presenter has been lost, recreates a instance
     */
    private void reInitialize(MainActivityMVP.RequiredViewOps view)
        throws InstantiationException, IllegalAccessException {

        mPresenter = mStateMaintainer.get(MainActivityMVP.ProvidedPresenterOps.class.getSimpleName());

        if (mPresenter == null) {
            initilize(view);
        } else {
            mPresenter.onConfigurationChanged(this);
        }
    }

    @Override protected void onStop() {
        super.onStop();
        Log.d(getClass().getSimpleName(), "lifecycle_event :onStop()");

        mPresenter.onConfigurationChanged(this);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(), "lifecycle_event :onDestroy()");
        mPresenter.onDestroy(isChangingConfigurations());
    }
}
