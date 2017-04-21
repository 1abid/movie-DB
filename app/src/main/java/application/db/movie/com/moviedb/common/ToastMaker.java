package application.db.movie.com.moviedb.common;


import android.content.Context;
import android.widget.Toast;

/**
 * Created by vutka bilai on 12/28/16.
 * mail : la4508@gmail.com
 */

public class ToastMaker {

    public static final Toast makeToast(Context context , String msg){

        return  Toast.makeText(context , msg , Toast.LENGTH_SHORT);
    }
}
