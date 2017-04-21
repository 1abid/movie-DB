package application.db.movie.com.moviedb.SplashScreen.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VutkaBilai on 4/21/17.
 * mail : la4508@gmail.com
 */

public class RequestTokenResponse {

  @SerializedName("status_message")
  String status;

  @SerializedName("request_token")
  String requestToekn;

  @SerializedName("success")
  boolean success;

  @SerializedName("status_code")
  int statusCode;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getRequestToekn() {
    return requestToekn;
  }

  public void setRequestToekn(String requestToekn) {
    this.requestToekn = requestToekn;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }
}
