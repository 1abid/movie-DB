package application.db.movie.com.moviedb.rest.upComingMoviesModel;

/**
 * Created by VutkaBilai on 1/31/17.
 * mail : la4508@gmail.com
 */

public class CardItem {

    private int mTextResource;
    private int MTitleResource;

    public CardItem(int MTitleResource ,int mTextResource) {
        this.mTextResource = mTextResource;
        this.MTitleResource = MTitleResource;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return MTitleResource;
    }
}
