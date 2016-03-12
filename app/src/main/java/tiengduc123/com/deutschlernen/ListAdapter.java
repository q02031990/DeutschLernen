package tiengduc123.com.deutschlernen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dell on 12/3/2015.
 */
public class ListAdapter extends ArrayAdapter<VideoObj> {
    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }


    public ListAdapter(Context context, int resource, List<VideoObj> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(tiengduc123.com.deutschlernen.R.layout.activity_video_item, null);
        }

        VideoObj p = getItem(position);

        if (p != null) {
            // Anh xa + Gan gia tri
            TextView nameVideo = (TextView) v.findViewById(tiengduc123.com.deutschlernen.R.id.txtNameVideo);
            nameVideo.setText(p.nameVideo);

            TextView timeVideo = (TextView) v.findViewById(tiengduc123.com.deutschlernen.R.id.txtTimeVideo);
            timeVideo.setText("Time :" + p.timeVideo);

            ImageView img = (ImageView) v.findViewById(tiengduc123.com.deutschlernen.R.id.imageView);
            Picasso.with(getContext()).load("http://img.youtube.com/vi/" + p.key + "/0.jpg").into(img);
        }

        return v;
    }
}