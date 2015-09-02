package com.silver.app.kalanama;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import com.silver.app.kalanama.CustomControl.LazyAdapter;
import com.silver.app.kalanama.models.Product;
import com.silver.app.kalanama.services.JSONParser;
import com.silver.app.kalanama.utility.ServiceTask;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.HashMap;

public class MostPopularFragment extends Fragment {
    static final String KEY_PRODUCTID = "ProductId";
    static final String KEY_TITLE = "Title";
    static final String KEY_DESCRIPTION = "Description";
    static final String KEY_SCORE = "Score";
    static final String KEY_DescriptionUrl = "DescriptionUrl";
    static final String KEY_ViewCount = "ViewCount";
    static final String KEY_DiscussionCount = "DiscussionCount";
    static final String KEY_FAQCount = "FAQCount";
    private int lastPosition = -1;
    ListView list;
    LazyAdapter adapter;

    public MostPopularFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_most_popular, container, false);
        list = (ListView) view.findViewById(R.id.list);
        final Activity activity = getActivity();

        //setAnim(view,list);

        ServiceTask service = new ServiceTask(new ServiceTask.ServiceCallBack() {
            @Override
            public void getResult(String result, Object data) {

                JSONParser parse = new JSONParser();
                ArrayList<Product> res = parse.parseProduct(result);

                ArrayList<HashMap<String, String>> productList = new ArrayList<>();
                for (int i = 0; i < res.size(); i++) {
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    // adding each child node to HashMap key => value
                    map.put(KEY_PRODUCTID, String.valueOf(res.get(i).getProductId()));
                    map.put(KEY_TITLE, res.get(i).getTitle());
                    map.put(KEY_DESCRIPTION, res.get(i).getDescription());
                    map.put(KEY_DescriptionUrl, res.get(i).getDescriptionURL());
                    map.put(KEY_SCORE, String.valueOf(res.get(i).getScore()));
                    map.put(KEY_ViewCount, String.valueOf(res.get(i).getViewCount()));
                    map.put(KEY_DiscussionCount, String.valueOf(res.get(i).getDiscussionCount()));
                    map.put(KEY_FAQCount, String.valueOf(res.get(i).getFAQCount()));


                    // adding HashList to ArrayList
                    productList.add(map);


                    // Getting adapter by passing xml data ArrayList
                    adapter = new LazyAdapter(activity, productList);
                    list.setAdapter(adapter);
                }

            }
        });
        service.execute(
                new BasicNameValuePair("address", "User/GetProduct")
        );


        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });

        return  view;
    }

    private void setAnim(View view, ListView list) {
        View v = view;

        if (v == null) {
            LayoutInflater li = LayoutInflater.from(view.getContext());
            v = li.inflate(R.layout.list_row, null);

			/*Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
			if(position > lastPosition)
		    v.startAnimation(animation);*/
        }
        Animation anim = null;
        if (list.getPositionForView(view) > lastPosition) {
            anim = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.bottom_up);
        }
        else
        {
            anim = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.top_down);
        }
        v.startAnimation(anim);
        lastPosition = list.getPositionForView(view);
    }


}
