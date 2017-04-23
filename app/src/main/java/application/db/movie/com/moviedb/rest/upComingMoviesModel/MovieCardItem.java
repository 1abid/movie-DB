package application.db.movie.com.moviedb.rest.upComingMoviesModel;

/**
 * Created by abidhasan on 2/12/17.
 */

public class MovieCardItem {

    private String movieAvg_vote ;
    private String movieName ;
    private String movieReleaseDate ;
    private String movieLength;
    private String MoviewGenere;
    private String movieBackDroppath;
    private String moviePosterPath;

    public MovieCardItem(String movieAvg_vote, String movieName, String movieReleaseDate,
                         String movieLength, String moviewGenere, String moviePosterpath, String moviePoster) {
        this.movieAvg_vote = movieAvg_vote;
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
        this.movieLength = movieLength;
        MoviewGenere = moviewGenere;
        this.movieBackDroppath = moviePosterpath;
        this.moviePosterPath = moviePoster;
    }

    public String getMovieAvg_vote() {
        return movieAvg_vote;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieLength() {
        return movieLength;
    }

    public String getMoviewGenere() {
        return MoviewGenere;
    }

    public String getMovieBackDroppath() {
        return movieBackDroppath;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }
}
