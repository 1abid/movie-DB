package application.db.movie.com.moviedb.SplashScreen.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import application.db.movie.com.moviedb.R;
import application.db.movie.com.moviedb.SplashScreen.SplashMVP;
import application.db.movie.com.moviedb.SplashScreen.model.AccessTokenResponse;
import application.db.movie.com.moviedb.SplashScreen.model.RequestTokenResponse;
import application.db.movie.com.moviedb.application.RestService;
import application.db.movie.com.moviedb.mainActivity.view.MainActivity;
import application.db.movie.com.moviedb.rest.AllApiUrls;
import application.db.movie.com.moviedb.rest.TMDBApiInterface;
import application.db.movie.com.moviedb.utils.PreferenceUtils;
import java.lang.ref.WeakReference;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class SplashPresenter implements SplashMVP.ProvidedPresenterOps,SplashMVP.RequiredPresenterOps {

  private WeakReference<SplashMVP.RequiredViewOps> mView;

  private SplashMVP.ProvidedModelOps mModel;

  public SplashPresenter(SplashMVP.RequiredViewOps view) {
    mView = new WeakReference<SplashMVP.RequiredViewOps>(view);
  }

  /**
   * called by activity every time during
   * setting up MVP , only called once
   *
   * @param model
   */
  public void setModel(SplashMVP.ProvidedModelOps model) {
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

  /**
   * Sent from Activity after a configuration changes
   *
   * @param view View reference
   */
  @Override public void onConfigurationChanged(SplashMVP.RequiredViewOps view) {
    setView(view);
  }

  @Override public void setView(SplashMVP.RequiredViewOps view) {
    mView = new WeakReference<SplashMVP.RequiredViewOps>(view);
  }

  @Override public void createRequestToken() {

    showUpdate(getActivityContext().getString(R.string.request_token));

    RestService.changeBaseURl(AllApiUrls.BASE_URL_V4);

    TMDBApiInterface apiInterface = RestService.createService(TMDBApiInterface.class , AllApiUrls.AUTH_TOKEN);

    Call<RequestTokenResponse> call = apiInterface.createRequestToken(AllApiUrls.TMDB_API_KEY , AllApiUrls.AUTH_TOKEN ,AllApiUrls.AUTH_TOKEN);

    call.enqueue(new Callback<RequestTokenResponse>() {
      @Override public void onResponse(Call<RequestTokenResponse> call,
          Response<RequestTokenResponse> response) {

          if(response.isSuccessful()){
            Log.d(getClass().getSimpleName() ,"Request Token "+ response.body().getRequestToekn());

            redirectUser(response);

          }else {
            try {
              JSONObject jObjError = new JSONObject(response.errorBody().string());
              Log.d("error msg", jObjError.getString("status_message"));
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
      }

      @Override public void onFailure(Call<RequestTokenResponse> call, Throwable t) {
        Log.d("ERROR : ","something went wrong .... "+t.getMessage());
      }
    });
  }


  @Override public void createAccessToken(String requestToken) {

    showUpdate(getActivityContext().getString(R.string.create_access_token));

    RestService.changeBaseURl(AllApiUrls.BASE_URL_V4);

    TMDBApiInterface apiInterface = RestService.createService(TMDBApiInterface.class , AllApiUrls.AUTH_TOKEN);

    AccessTokenResponse.RequestToken requestTokenBody = new AccessTokenResponse.RequestToken(requestToken);


    Call<AccessTokenResponse> call = apiInterface.createAccessToken(
        AllApiUrls.TMDB_API_KEY,
        AllApiUrls.AUTH_TOKEN ,
        AllApiUrls.AUTH_TOKEN ,
        requestTokenBody );

    call.enqueue(new Callback<AccessTokenResponse>() {
      @Override public void onResponse(Call<AccessTokenResponse> call,
          Response<AccessTokenResponse> response) {

        if(response.isSuccessful()){
          Log.d(getClass().getSimpleName() ,"status "+ response.body().getStatusMsg());


          showUpdate(getActivityContext().getString(R.string.saving_access_token));

          PreferenceUtils.saveAccessToken(getActivityContext() , response.body().getAccessToken());

          getView().goToNext(new Intent(getActivityContext() , MainActivity.class));

        }else {
          PreferenceUtils.setIsApproved(getActivityContext() , false);
          PreferenceUtils.saveRequestToken(getActivityContext() , "");


          createRequestToken();
        }
      }

      @Override public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
        Log.d("ERROR : ","something went wrong .... "+t.getMessage());
      }
    });

  }

  private void redirectUser(Response<RequestTokenResponse> response) {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
        Uri.parse("https://www.themoviedb.org/auth/access?request_token="+response.body().getRequestToekn()));

    getView().loadWebView(browserIntent);

    PreferenceUtils.setIsApproved(getActivityContext() , true);
    PreferenceUtils.saveRequestToken(getActivityContext() , response.body().getRequestToekn());

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

  @Override public void showUpdate(String msg) {
    final TextView tv = (TextView) getView().getViewById(R.id.splash_tv);

    tv.setText(msg);
  }

  public SplashMVP.RequiredViewOps getView() throws NullPointerException{
    if (mView != null)
      return mView.get();
    else
      throw new NullPointerException("view is unavailable");
  }


}
