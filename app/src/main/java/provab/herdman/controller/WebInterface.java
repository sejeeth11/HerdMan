package provab.herdman.controller;

import org.json.JSONException;

/**
 * Created by ptblr-1021 on 6/10/15.
 */

public interface WebInterface {

    public void getResponse(String response, int flag) throws JSONException;

    public void failureResponse(int statusCode) throws JSONException;
}
