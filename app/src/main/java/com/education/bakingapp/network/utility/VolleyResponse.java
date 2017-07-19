package com.education.bakingapp.network.utility;

import com.education.bakingapp.models.Recipe;

import java.util.ArrayList;


/**
 * Created by Sara on 6/3/2017.
 */

public interface VolleyResponse {


    void onResponse(String response);

    void onResponseArray(ArrayList<Recipe> lstRecipes);

    void onError(String errorMsg);

}
