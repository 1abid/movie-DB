package application.db.movie.com.moviedb.views;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Created by abidhasan on 2/22/17.
 */

public class MyAutoScrollViewPager extends ViewPager {

    public static final int DEFAULT_INTERVAL = 1500;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;


    /**
     * do nothing after reaching the first or last item
     */
    public static final int SLIDE_MODE_NONE = 0;

    /**
     * cycle after reaching last or first item
     */
    public static final int SLIDE_MODE_CYCLE = 1;


    /**
     * inform parent about the event after reaching first or last item
     */
    public static final int SLIDE_MODE_INFORM_PARENT = 3;


    private long interval = DEFAULT_INTERVAL;

    private int direction = RIGHT;

    private boolean isCycle = true;

    private boolean stopScrollWhenTouch = true;

    private int cycleMode = SLIDE_MODE_CYCLE;

    private boolean isBorderAnimation = true;

    private double swipeScrollFactor = 1.0;

    private double autoScrollFactor = 1.0;

    private Handler handler;

    private boolean isAutoScroll = false;
    private boolean isStopByTouch = false;
    private float touchX = 0f, downX = 0f;
    private AutoScroller scroller = null;

    public static final int SCROLL_WHT = 0;


    public MyAutoScrollViewPager(Context context) {
        super(context);
        init();
    }

    public MyAutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        handler = new AutoScrollHandler(this);
        setViewPagerScroller();
    }


    /**
     * start auto scroll with default delay time
     */
    public void startAutoScroll() {
        isAutoScroll = true;
        sendScrollEvntMsg((long) (interval + scroller.getDuration() / autoScrollFactor * swipeScrollFactor));
    }


    /**
     * start auto scroll, first scroll delay time is given delayTime
     */
    public void startAutoScroll(long delayTime) {
        isAutoScroll = true;
        sendScrollEvntMsg(delayTime);
    }


    /**
     * stop scrolling
     */
    public void stopAutoScroll() {
        isAutoScroll = false;
        handler.removeMessages(SCROLL_WHT);
    }

    public void setSwipeScrollDurationFactor(double factor) {
        swipeScrollFactor = factor;
    }

    public void setAutoScrollDurationFactor(double factor) {
        autoScrollFactor = factor;
    }


    /**
     * set ViewPager scroller to change animation duration when sliding
     */
    private void setViewPagerScroller() {

        try {
            Field scrollField = ViewPager.class.getDeclaredField("mScroller");
            scrollField.setAccessible(true);

            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);

            scroller = new AutoScroller(getContext(), (Interpolator) interpolatorField.get(null));
            scrollField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR !", e.getMessage());
        }
    }


    /**
     * scroll only once
     */
    public void scrollOnce() {

        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;

        if (adapter == null || (totalCount = adapter.getCount()) <= 1)
            return;

        int nextItem = (direction == LEFT) ? --currentItem : ++currentItem;

        if (nextItem < 0) {
            if (isCycle) {
                setCurrentItem(totalCount - 1, isBorderAnimation);
            }
        } else if (nextItem == totalCount) {
            if (isCycle) {
                setCurrentItem(0, isBorderAnimation);
            }
        } else {
            setCurrentItem(nextItem, isBorderAnimation);
        }
    }

    /**
     * is stopScrollWhenTouch true
     * if event is down , stop auto scroll
     * if event is up , start auto scroll
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = MotionEventCompat.getActionMasked(ev);

        if (stopScrollWhenTouch) {
            if (action == MotionEvent.ACTION_DOWN && isAutoScroll) {
                isStopByTouch = true;
                startAutoScroll();
            } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
                startAutoScroll();
            }
        }

        if (cycleMode == SLIDE_MODE_INFORM_PARENT || cycleMode == SLIDE_MODE_CYCLE) {
            touchX = ev.getX();

            if (ev.getAction() == MotionEvent.ACTION_DOWN)
                downX = touchX;

            int currentItem = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            int itemCount = adapter == null ? 0 : adapter.getCount();

            /**
             * current index is first one and slide to right or current index is last one and slide to left.<br/>
             * if slide border mode is to parent, then requestDisallowInterceptTouchEvent false.<br/>
             * else scroll to last one when current item is first one, scroll to first one when current item is last
             * one.*/

            if ((currentItem == 0 && downX <= touchX) || (currentItem == itemCount - 1 && downX >= touchX)) {
                if (cycleMode == SLIDE_MODE_INFORM_PARENT) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (itemCount > 1) {
                        setCurrentItem(itemCount - currentItem - 1, isBorderAnimation);
                    }

                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                return super.dispatchTouchEvent(ev);
            }
        }

        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    public void sendScrollEvntMsg(long delayInMill) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHT);
        handler.sendEmptyMessageDelayed(SCROLL_WHT, delayInMill);
    }


    /**
     * get auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     *
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * set auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     *
     * @param interval the interval to set
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * get auto scroll direction
     *
     * @return {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public int getDirection() {
        return (direction == LEFT) ? LEFT : RIGHT;
    }

    /**
     * set auto scroll direction
     *
     * @param direction {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * whether automatic cycle when auto scroll reaching the last or first item, default is true
     *
     * @return the isCycle
     */
    public boolean isCycle() {
        return isCycle;
    }

    /**
     * set whether automatic cycle when auto scroll reaching the last or first item, default is true
     *
     * @param isCycle the isCycle to set
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * whether stop auto scroll when touching, default is true
     *
     * @return the stopScrollWhenTouch
     */
    public boolean isStopScrollWhenTouch() {
        return stopScrollWhenTouch;
    }

    /**
     * set whether stop auto scroll when touching, default is true
     *
     * @param stopScrollWhenTouch
     */
    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
    }

    /**
     * get how to process when sliding at the last or first item
     *
     * @return the slideBorderMode {@link #SLIDE_MODE_NONE}, {@link #SLIDE_MODE_INFORM_PARENT},
     *         {@link #SLIDE_MODE_CYCLE}, default is {@link #SLIDE_MODE_NONE}
     */
    public int getSlideBorderMode() {
        return cycleMode;
    }

    /**
     * set how to process when sliding at the last or first item
     *
     * @param cycleMode {@link #SLIDE_MODE_NONE}, {@link #SLIDE_MODE_INFORM_PARENT},
     *        {@link #SLIDE_MODE_CYCLE}, default is {@link #SLIDE_MODE_NONE}
     */
    public void setSlideBorderMode(int cycleMode) {
        this.cycleMode = cycleMode;
    }

    /**
     * whether animating when auto scroll at the last or first item, default is true
     *
     * @return
     */
    public boolean isBorderAnimation() {
        return isBorderAnimation;
    }

    /**
     * set whether animating when auto scroll at the last or first item, default is true
     *
     * @param isBorderAnimation
     */
    public void setBorderAnimation(boolean isBorderAnimation) {
        this.isBorderAnimation = isBorderAnimation;
    }



    /**
     * Handler class for handling autoScroll
     * event after certain duration
     */

    private static class AutoScrollHandler extends Handler {

        private final WeakReference<MyAutoScrollViewPager> mAutoScrollViewPager;

        public AutoScrollHandler(MyAutoScrollViewPager viewPager) {
            this.mAutoScrollViewPager = new WeakReference<MyAutoScrollViewPager>(viewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case SCROLL_WHT:
                    MyAutoScrollViewPager pager = this.mAutoScrollViewPager.get();
                    if (pager != null) {
                        pager.scroller.setScrollDuraionFactor(pager.autoScrollFactor);
                        pager.scrollOnce();
                        pager.scroller.setScrollDuraionFactor(pager.swipeScrollFactor);
                        pager.sendScrollEvntMsg(pager.interval + pager.scroller.getDuration());
                    }

                default:
                    break;
            }
        }
    }
}
