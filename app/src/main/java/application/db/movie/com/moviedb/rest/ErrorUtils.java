package application.db.movie.com.moviedb.rest;

import application.db.movie.com.moviedb.application.RestService;
import application.db.movie.com.moviedb.common.APIError;
import java.io.IOException;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class ErrorUtils {

  public static APIError parseError(Response<?> response){
    Converter<ResponseBody , APIError> converter =
        RestService.retrofit().responseBodyConverter(APIError.class , new Annotation[0]);

    APIError apiError ;
    try {
      apiError = converter.convert(response.errorBody());
    }catch (IOException e){
      apiError = new APIError();
    }

    return apiError;
  }


}
