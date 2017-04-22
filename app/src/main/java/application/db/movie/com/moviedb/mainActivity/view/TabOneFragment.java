package application.db.movie.com.moviedb.mainActivity.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import application.db.movie.com.moviedb.AllMoviesFragment.AllMovieFragment;
import application.db.movie.com.moviedb.R;
import application.db.movie.com.moviedb.common.OnBackPressListener;
import application.db.movie.com.moviedb.tabInterfaces.MovieFragment;
import java.util.ArrayList;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class TabOneFragment extends Fragment implements OnBackPressListener {

  public static ArrayList<MovieFragment> FRAGMENTS ;
  public static int CURRENT_INDEX = 0 ;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FRAGMENTS = new ArrayList<>();

    FRAGMENTS.add(AllMovieFragment.getInstance(this));
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragments_holder,null);


    replaceFragment(FRAGMENTS.get(0));
    return view;
  }

  /**
   * replace holder with given fragment
   * @param fragment
   */
  private void replaceFragment(MovieFragment fragment){
    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    transaction.replace(R.id.fragment_content, fragment);
    transaction.commit();
  }

  @Override public boolean onBackPressed() {
    this.goToBack();
    return false;
  }

  /**
   * show previous fragment
   */
  public void goToBack(){

  }


  /**
   * show next fragment
   */
  public void goTonext(){

  }

  public void showExitDialog() {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

    // set the title of the Alert Dialog
    alertDialogBuilder.setTitle("Exit Application?");


    // set dialog message
    alertDialogBuilder.setMessage("Click yes to exit!")
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            // if yes is clicked, close
            // current activity
            getActivity().finish();
          }
        }).setNegativeButton("No",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            // if no is clicked, just close
            // the dialog box and do nothing
            dialog.cancel();
          }
        });
    alertDialogBuilder.show();
  }
}
