package application.db.movie.com.moviedb.application;

import android.app.Application;
import application.db.movie.com.moviedb.utils.FontsOverride;

/**
 * Created by VutkaBilai on 4/21/17.
 * mail : la4508@gmail.com
 */

public class MovieDbApp extends Application {

  @Override public void onCreate() {
    super.onCreate();

    FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/DroidSans.ttf");
    FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/DroidSans.ttf");
    FontsOverride.setDefaultFont(this, "SERIF", "fonts/DroidSans.ttf");
    FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/DroidSans.ttf");
  }
}
