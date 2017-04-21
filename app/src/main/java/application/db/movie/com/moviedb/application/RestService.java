package application.db.movie.com.moviedb.application;

import application.db.movie.com.moviedb.rest.AllApiUrls;
import application.db.movie.com.moviedb.rest.AuthenticationInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by VutkaBilai on 4/21/17.
 * mail : la4508@gmail.com
 */

public class RestService {

  private static final String baseUrl = AllApiUrls.BASE_URL;

  private static Retrofit retrofit;

  public static Retrofit retrofit(){
    return  retrofit;
  }


  private static Retrofit.Builder builder = new Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create());


  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


  public static <S> S createService(Class<S> serviceClass){

    httpClient.addInterceptor(new AuthenticationInterceptor(AllApiUrls.AUTH_TOKEN));

    /*HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    httpClient.interceptors().add(loggingInterceptor);*/

    builder.client(httpClient.build());
    Retrofit retrofit = builder.build();

    return retrofit.create(serviceClass);
  }


  /**method to change base url for a api service**/
  public void changeBaseURl(String url){
    builder = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create());
  }
}
