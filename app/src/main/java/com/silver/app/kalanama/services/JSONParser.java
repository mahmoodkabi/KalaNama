package com.silver.app.kalanama.services;


import android.util.Log;
import com.silver.app.kalanama.models.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 08/25/2015.
 */
public class JSONParser {
    public JSONParser() {

    }

    public String[] parseRegister(String object) throws JSONException {
        JSONArray res = new JSONArray(object);
        String[] parseRes = new String[3];
        parseRes[0] = res.get(0).toString();  //userID
        parseRes[1] = res.get(1).toString();   //Validate
        parseRes[2] = res.get(2).toString();  //ConfirmedCode
        return parseRes;
    }

    public ArrayList<Product> parseProduct(String object) {

        JSONArray obj = null;
        try {
            obj = new JSONArray(object);

        } catch (JSONException e) {
            Log.d("JSONParser => parseMT26", e.getMessage());
        }



        ArrayList<Product> arrayList = new ArrayList<Product>();
        try {
       /*     JSONArray jsonArray = obj.getJSONArray("Value");*/
            JSONObject jsonObj = null;
            for (int i = 0; i < obj.length(); i++) {
                jsonObj = obj.getJSONObject(i);

                Product item = new Product();
                item.setProductId(jsonObj.getInt("ProductId"));
                item.setTitle(jsonObj.getString("Title"));
                item.setDescription(jsonObj.getString("Description"));
                item.setScore(jsonObj.getDouble("Score"));
                item.setDescriptionURL(jsonObj.getString("DescriptionUrl"));
                item.setViewCount(jsonObj.getInt("ViewCount"));
                item.setDiscussionCount(jsonObj.getInt("DiscussionCount"));
                item.setFAQCount(jsonObj.getInt("FAQCount"));

                arrayList.add(item);
            }

        } catch (JSONException e) { // TODO Auto-generated catch block
            Log.d("parseMusic", e.getMessage());
        }
        return arrayList;
    }

}
