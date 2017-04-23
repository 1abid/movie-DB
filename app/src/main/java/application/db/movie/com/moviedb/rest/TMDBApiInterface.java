package application.db.movie.com.moviedb.rest;

import application.db.movie.com.moviedb.SplashScreen.model.AccessTokenResponse;
import application.db.movie.com.moviedb.SplashScreen.model.RequestTokenResponse;
import application.db.movie.com.moviedb.rest.upComingMoviesModel.UpcomingMovieResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by VutkaBilai on 4/21/17.
 * mail : la4508@gmail.com
 */

public interface TMDBApiInterface {

  //create request_token
  @POST("auth/request_token")
  Call<RequestTokenResponse> createRequestToken(@Query("api_key") String apiKey ,
      @Query("access_token") String accessToken ,
      @Header("Authorization") String authHeader);

  @POST("auth/access_token")
  Call<AccessTokenResponse> createAccessToken(@Query("api_key") String apiKey ,
      @Query("access_token") String accessToken ,
      @Header("Authorization") String authHeader,@Body AccessTokenResponse.RequestToken requestBody);

  //get upcoming movie list
  @GET("movie/upcoming")
  Call<UpcomingMovieResponse> getUpcomingMovieList(@Query("api_key") String apiKey);
}
