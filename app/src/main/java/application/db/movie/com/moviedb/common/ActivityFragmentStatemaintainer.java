package application.db.movie.com.moviedb.common;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by VutkaBilai on 12/3/16.
 * mail : la4508@gmail.com
 */

public class ActivityFragmentStatemaintainer {


    protected final String TAG = getClass().getSimpleName();


    private final String mStateMaintainerTag;
    private final WeakReference<FragmentManager> mFragmentManeger;
    private StateMngFragment mStateMaintainerFrag;
    private boolean isRecreating ;


    public ActivityFragmentStatemaintainer( FragmentManager frgmentManager, String mStateMaintainerTag) {
        this.mFragmentManeger = new WeakReference<FragmentManager>(frgmentManager);
        this.mStateMaintainerTag = mStateMaintainerTag;
    }



    /**
     * Creates the Fragment responsible to maintain the objects.
     * @return  true: fragment just created
     */
    public boolean isFirstTimeIn(){

        try{
            mStateMaintainerFrag = (StateMngFragment) mFragmentManeger.get().findFragmentByTag(mStateMaintainerTag);

            if(mStateMaintainerFrag == null){

                Log.d(TAG, "no saved fragment found to retainted " + mStateMaintainerFrag);
                mStateMaintainerFrag = new StateMngFragment();

                mFragmentManeger.get().beginTransaction().add(mStateMaintainerFrag , mStateMaintainerTag).commit();
                isRecreating = false ;

                return true;
            }else {
                Log.d(TAG, "saved fragment found , retaining fragment " + mStateMaintainerFrag);
                isRecreating = true;

                return false;
            }
        }catch (NullPointerException e){
            Log.e(TAG , "error "+e.getMessage());
            return  false;
        }

    }


    /**
     * Return <strong>true</strong> it the current Activity was recreated at least one time
     * @return  If the Activity was recreated
     */
    public boolean wasRecreated() { return isRecreating; }


    /**
     * Insert the object to be preserved.
     * @param key   object's TAG
     * @param obj   object to maintain
     */
    public void put(String key, Object obj) {
        mStateMaintainerFrag.put(key, obj);
    }


    /**
     * Insert the object to be preserved.
     * @param obj   object to maintain
     */
    public void put(Object obj) {
        put(obj.getClass().getName(), obj);
    }

    /**
     * Recovers the object saved
     * @param key   Object's TAG
     * @param <T>   Object type
     * @return      Object saved
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key)  {
        return mStateMaintainerFrag.get(key);

    }

    /**
     * Checks the existence of a given object
     * @param key   Key to verification
     * @return      true: Object exists
     */
    public boolean hasKey(String key) {
        return mStateMaintainerFrag.get(key) != null;
    }




    /**
     * Fragment resposible to preserve objects.
     * Instantiated only once. Uses a hashmap to save objs
     */
    public static class StateMngFragment extends Fragment{

        private HashMap<String , Object> data = new HashMap<>();


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Grants that the fragment will be preserved
            setRetainInstance(true);
        }




        /**
         * Insert objects on the hashmap
         * @param key   Reference key
         * @param obj   Object to be saved
         */
        public void put(String key , Object obj){
            data.put(key ,obj);
        }

        /**
         * Insert the object to be preserved.
         * @param obj   object to maintain
         */
        public void put(Object obj) {
            put(obj.getClass().getName(), obj);
        }


        /**
         * Recovers the object saved
         * @param key   Object's TAG
         * @param <T>   Object type
         * @return      Object saved
         */

        public <T> T get(String key){
            return (T) data.get(key);
        }
    }
}
