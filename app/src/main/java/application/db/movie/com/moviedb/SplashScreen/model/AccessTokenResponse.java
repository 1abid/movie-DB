package application.db.movie.com.moviedb.SplashScreen.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class AccessTokenResponse {

  @SerializedName("status_message")
  String statusMsg;

  @SerializedName("access_token")
  String accessToken;

  @SerializedName("success")
  boolean success;

  @SerializedName("status_code")
  int statusCode;

  @SerializedName("account_id")
  String accountId;

  public String getStatusMsg() {
    return statusMsg;
  }

  public void setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
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

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public static class RequestToken{
    String request_token;

    public RequestToken(String request_token) {
      this.request_token = request_token;
    }


    public String getRequest_token() {
      return request_token;
    }
  }


}
