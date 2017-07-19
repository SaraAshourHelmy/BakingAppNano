package com.education.bakingapp.network.utility;

import android.content.Context;
import android.util.Log;

import com.education.bakingapp.models.Recipe;
import com.education.bakingapp.network.volley.NetworkRequest;

import java.util.ArrayList;


/**
 * Created by Sara on 7/17/2017.
 */

public class NetworkAPIs {

    public static void getRecipes(Context context, String url,
                                  final VolleyResponse volleyResponse) {

        NetworkRequest.requestAPI(context, url, new VolleyResponse() {
            @Override
            public void onResponse(String response) {

                ArrayList<Recipe> lstRecipes = Parsing.parseRecipes(response);
                volleyResponse.onResponseArray(lstRecipes);
            }

            @Override
            public void onResponseArray(ArrayList<Recipe> lstRecipes) {

            }

            @Override
            public void onError(String errorMsg) {

                volleyResponse.onError(errorMsg);
            }
        });
    }

}
