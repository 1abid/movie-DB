package application.db.movie.com.moviedb.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import application.db.movie.com.moviedb.R;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class PreferenceUtils {

  public static boolean isApproved(Context context){
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    return preferences.getBoolean(context.getString(R.string.approved_access_token_key) ,false);
  }

  public static void setIsApproved(Context context , boolean approval){
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context) ;

    preferences.edit().putBoolean(context.getString(R.string.approved_access_token_key) , approval).apply();
  }


  public static String getRequestToken(Context context){

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

    return preferences.getString(context.getString(R.string.request_token_key),"");
  }


  public static void saveRequestToken(Context context , String requestToken){
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

    preferences.edit().putString(context.getString(R.string.request_token_key) , requestToken).apply();
  }
}
