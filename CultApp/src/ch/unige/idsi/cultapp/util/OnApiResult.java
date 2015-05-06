package ch.unige.idsi.cultapp.util;

import org.json.JSONException;
import org.json.JSONObject;

public interface OnApiResult {
    void processFinish(JSONObject response) throws JSONException;
}