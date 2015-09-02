package com.silver.app.kalanama;


import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.widget.ListView;
import com.silver.app.kalanama.CustomControl.LazyAdapter;
import com.silver.app.kalanama.CustomControl.TabListener;
import com.silver.app.kalanama.utility.ActivityBase;




public class MainActivity extends ActivityBase {

    static final String URL = "http://91.98.120.196/KalaNamaServer/music.xml";
    static final String KEY_PRODUCTID = "ProductId";
    static final String KEY_TITLE = "Title";
    static final String KEY_DESCRIPTION = "Description";
    static final String KEY_SCORE = "Score";
    static final String KEY_DescriptionUrl = "DescriptionUrl";
    static final String KEY_ViewCount = "ViewCount";
    static final String KEY_DiscussionCount = "DiscussionCount";
    static final String KEY_FAQCount = "FAQCount";
    static final String KEY_FAQCount1 = "FAQCount1";
    ListView list;
    LazyAdapter adapter;

    // Declaring our tabs and the corresponding fragments.
    ActionBar.Tab bmwTab = null, fordTab = null, toyotaTab = null;
    Fragment bmwFragmentTab = new MostDiscussedFragment();
    Fragment toyotaFragmentTab = new MostPopularFragment();
    Fragment fordFragmentTab = new RecentDiscussionsFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);



// Asking for the default ActionBar element that our platform supports.
        // ActionBar actionBar = getActionBar();

        // Screen handling while hiding ActionBar icon.
        actionBar.setDisplayShowHomeEnabled(false);

        // Screen handling while hiding Actionbar title.
        actionBar.setDisplayShowTitleEnabled(false);

        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Setting custom tab icons.
        bmwTab = actionBar.newTab().setIcon(R.drawable.favorite);
        toyotaTab = actionBar.newTab().setIcon(R.drawable.question);
        fordTab = actionBar.newTab().setIcon(R.drawable.eye);


        // Setting tab listeners.
        bmwTab.setTabListener(new TabListener(bmwFragmentTab));
        toyotaTab.setTabListener(new TabListener(toyotaFragmentTab));
        fordTab.setTabListener(new TabListener(fordFragmentTab));

        // Adding tabs to the ActionBar.
        actionBar.addTab(bmwTab);
        actionBar.addTab(toyotaTab);
        actionBar.addTab(fordTab);



    }
}
