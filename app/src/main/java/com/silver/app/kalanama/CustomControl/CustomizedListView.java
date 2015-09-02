package com.silver.app.kalanama.CustomControl;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.silver.app.kalanama.R;

public class CustomizedListView extends Activity {
	// All static variables
	static final String URL = "http://api.androidhive.info/music/music.xml";
	// XML node keys
	static final String KEY_PRODUCT = "Product"; // parent node
	static final String KEY_PRODUCTID = "ProductId";
	static final String KEY_TITLE = "Title";
	static final String KEY_DESCRIPTION = "Description";
	static final String KEY_SCORE = "Score";
	static final String KEY_DescriptionUrl = "DescriptionUrl";
	static final String KEY_ViewCount = "ViewCount";
	static final String KEY_DiscussionCount = "DiscussionCount";
	static final String KEY_FAQCount = "FAQCount";
	//static final String KEY_THUMB_URL = "thumb_url";
	
	ListView list;
    LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML from URL
		Document doc = parser.getDomElement(xml); // getting DOM element
		
		NodeList nl = doc.getElementsByTagName(KEY_PRODUCT);
		// looping through all song nodes <song>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_PRODUCTID, parser.getValue(e, KEY_PRODUCTID));
			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
			map.put(KEY_DESCRIPTION, parser.getValue(e, KEY_DESCRIPTION));
			map.put(KEY_ViewCount, parser.getValue(e, KEY_ViewCount));
			map.put(KEY_DiscussionCount, parser.getValue(e, KEY_DiscussionCount));
			map.put(KEY_FAQCount, parser.getValue(e, KEY_FAQCount));
			map.put(KEY_DescriptionUrl, parser.getValue(e, KEY_DescriptionUrl));

			// adding HashList to ArrayList
			songsList.add(map);
		}
		

		list=(ListView)findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, songsList);        
        list.setAdapter(adapter);
        

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
							

			}
		});		
	}	
}