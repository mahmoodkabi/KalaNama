package com.silver.app.kalanama.CustomControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.silver.app.kalanama.CustomControl.CustomizedListView;
import com.silver.app.kalanama.CustomControl.ImageLoader;
import com.silver.app.kalanama.R;
import com.silver.app.kalanama.utility.ServiceTask;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView description = (TextView)vi.findViewById(R.id.description); //description
        TextView eye_count = (TextView)vi.findViewById(R.id.view_eye_count); // view_eye_count
        TextView comment_count = (TextView)vi.findViewById(R.id.comment_count); // comment_count
        TextView faq_count = (TextView)vi.findViewById(R.id.faq_count); // faq_count
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        
        // Setting all values in listview
        title.setText(song.get(CustomizedListView.KEY_TITLE));
        description.setText(song.get(CustomizedListView.KEY_DESCRIPTION));
        eye_count.setText(song.get(CustomizedListView.KEY_ViewCount));
        comment_count.setText(song.get(CustomizedListView.KEY_DiscussionCount));
        faq_count.setText(song.get(CustomizedListView.KEY_FAQCount));
        imageLoader.DisplayImage(song.get(CustomizedListView.KEY_DescriptionUrl), thumb_image);
        return vi;
    }
}