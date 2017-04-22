package application.db.movie.com.moviedb.views;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

public class ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private ViewPager mViewPager;
    private CardAdapter mAdapter ;
    private float mLastOffset;
    private boolean mScalingEnabled;

    public ShadowTransformer(ViewPager mViewPager, CardAdapter mAdapter) {
        this.mViewPager = mViewPager;
        mViewPager.addOnPageChangeListener(this);
        this.mAdapter = mAdapter;
    }


    public void enableScaling(boolean enable) {



        if (mScalingEnabled && !enable) {
            //Log.d(getClass().getSimpleName() , "scale :" +enable + "bortoman obostha "+ mScalingEnabled);
            //shrink man card
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());

            if (currentCard != null) {
                currentCard.animate().scaleY(1);
                currentCard.animate().scaleX(1);
            }
        }else if (!mScalingEnabled && enable){

            //grow the main card
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());
            if (currentCard !=  null){
                currentCard.animate().scaleX(1.1f);
                currentCard.animate().scaleY(1.1f);
            }
        }


        mScalingEnabled = enable;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        /**
         * position : Position index of the first page currently being displayed.
         * Page position+1 will be visible if positionOffset is nonzero.
         *
         * positionOffset : float: Value from [0, 1) indicating the offset from the page at position.
         * positionOffsetPixels : int: Value in pixels indicating the offset from position.
         */
        int realCurrentPosition;
        int nextPosition;
        float baseElevation = mAdapter.getBaseElevation();
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        //if we are going backward onPageScrolled receives the last position
        //instead of th current one
        if(goingLeft){

            realCurrentPosition = position + 1;
            nextPosition = position ;
            realOffset = 1 - positionOffset ;

            //Log.d(getClass().getSimpleName() , "realoffset left" + realOffset);
        }else {

            nextPosition = position +1 ;
            realCurrentPosition = position ;
            realOffset = positionOffset ;

            //Log.d(getClass().getSimpleName() , "realoffset right" + realOffset);
        }

        //avoid overscroll
        if(nextPosition > mAdapter.getCount() -1 || realCurrentPosition > mAdapter.getCount() -1){
            return ;
        }

        CardView currentCard = mAdapter.getCardViewAt(realCurrentPosition);

        //this might be null if a fragment is being used
        //and the views weren't created yet
        if(currentCard != null){
            if(mScalingEnabled){

                currentCard.setScaleX((float)(1 + 0.1 * (1 - realOffset)));
                currentCard.setScaleY((float)(1 + 0.1 * (1 - realOffset)));
            } else {
                Log.d(getClass().getSimpleName() , realOffset + "card unscaled " + "card scale x "+currentCard.getScaleX() + "scale y "+currentCard.getScaleY());
            }

            currentCard.setCardElevation((baseElevation + baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }



        CardView nextCard = mAdapter.getCardViewAt(nextPosition);

        //we might be scrolling fast enough so that next or previous card
        //we already destroyed or a fragment might not have been created yet
        if(nextCard != null){
            if (mScalingEnabled){
                nextCard.setScaleX(1.1f);
                nextCard.setScaleY(1.1f);
            }

            nextCard.setCardElevation((baseElevation + baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }


        mLastOffset = positionOffset ;


    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void transformPage(View page, float position) {

    }
}
