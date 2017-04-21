package application.db.movie.com.moviedb.common;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VutkaBilai on 4/22/17.
 * mail : la4508@gmail.com
 */

public class APIError {

  @SerializedName("status_message")
  String statusMsg;
  @SerializedName("error_message")
  String errmsg;
  @SerializedName("success")
  boolean success ;
  @SerializedName("status_code")
  int statusCode ;

  public APIError() {
  }

  public String getStatusMsg() {
    return statusMsg;
  }

  public void setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
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
