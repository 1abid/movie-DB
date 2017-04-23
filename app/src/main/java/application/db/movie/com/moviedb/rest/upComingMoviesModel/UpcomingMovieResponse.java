package application.db.movie.com.moviedb.rest.upComingMoviesModel;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by vutka bilai on 12/19/16.
 * mail : la4508@gmail.com
 */

public class UpcomingMovieResponse {

    @SerializedName("page")
    private int page;


    @SerializedName("total_pages")
    private int totalpages;

    @SerializedName("total_results")
    private int totalResults;


    @SerializedName("results")
    private ArrayList<UpcomingMovie> results;





    public UpcomingMovieResponse(int page, int totalpages, int totalResults, ArrayList<UpcomingMovie> results) {
        this.page = page;
        this.totalpages = totalpages;
        this.totalResults = totalResults;
        this.results = results;
    }


    public int getPage() {
        return page;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public ArrayList<UpcomingMovie> getResults() {
        return results;
    }

    public int getTotalResults() {
        return totalResults;
    }


    class MovieReleaseDates{

        @SerializedName("maximum")
        private String maxDate;


        @SerializedName("minimum")
        private String minDate;


        public MovieReleaseDates(String maxDate, String minDate) {
            this.maxDate = maxDate;
            this.minDate = minDate;
        }


        public String getMaxDate() {
            return maxDate;
        }

        public String getMinDate() {
            return minDate;
        }
    }

}
