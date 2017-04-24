package application.db.movie.com.moviedb.views;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by abidhasan on 2/22/17.
 */

public class AutoScroller extends Scroller {

    private double scrollFactor = 1 ;

    public AutoScroller(Context context) {
        super(context);
    }

    public AutoScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public void setScrollDuraionFactor(double scrollFactor){
        this.scrollFactor = scrollFactor ;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy ,int duration) {
        super.startScroll(startX, startY, dx, dy , (int) (duration * scrollFactor));
    }
}
