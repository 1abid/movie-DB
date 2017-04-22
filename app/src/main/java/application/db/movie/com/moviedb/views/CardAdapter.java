package application.db.movie.com.moviedb.views;

import android.support.v7.widget.CardView;

/**
 * Created by VutkaBilai on 1/31/17.
 * mail : la4508@gmail.com
 */

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
