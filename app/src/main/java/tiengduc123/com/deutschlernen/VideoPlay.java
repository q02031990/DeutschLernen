package tiengduc123.com.deutschlernen;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
/*
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;*/
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import java.util.ArrayList;

public class VideoPlay extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    public static final String API_KEY = "AIzaSyCPc8rPMgkXlmvQrI0VXL5x3IHl4s6iai8";
    public static final String VIDEO_ID = "KQ77Ers65tA";


    String VideoID;
    ListView lv;
    ArrayList<VideoObj> mang;
    String category;
    ListAdapter adapter;
    LinearLayout ln;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer _YouTubePlayer;
    int position;
    ArrayList<String> videoIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(tiengduc123.com.deutschlernen.R.layout.activity_video_play);


        com.github.clans.fab.FloatingActionButton myFab = (com.github.clans.fab.FloatingActionButton) findViewById(tiengduc123.com.deutschlernen.R.id.fab_Share);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareForFriend();
            }
        });


        com.github.clans.fab.FloatingActionButton FabDownload = (com.github.clans.fab.FloatingActionButton) findViewById(tiengduc123.com.deutschlernen.R.id.fab_Download);
        FabDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "http://tiengduc123.com/app/getDirectLinkYouTube.php?VideoID=" + VideoID;
                Toast.makeText(getApplicationContext(), "Download is available only for Premium Account. Link will be show on your WebBrowser. ", Toast.LENGTH_LONG).show();
                Intent getWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(getWebPage);
            }
        });

        com.github.clans.fab.FloatingActionButton SendVideo = (com.github.clans.fab.FloatingActionButton) findViewById(tiengduc123.com.deutschlernen.R.id.fab_SendVideo);
        SendVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(VideoPlay.this, SendVideo.class);
                startActivity(it);
            }
        });

        /*AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        ln = (LinearLayout) findViewById(tiengduc123.com.deutschlernen.R.id.lil);
        ln.setAlpha(1.0f);
        lv = (ListView) findViewById(tiengduc123.com.deutschlernen.R.id.listView);


        //  The view com.android.internal.policy.impl.PhoneWindow$DecorView{429aea70 I.E..... R......D 0,0-1080,1920} has visibility "INVISIBLE".

        if (!isOnline()) {
            Toast.makeText(getApplicationContext(), "You have to connect Internet", Toast.LENGTH_LONG).show();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSON().execute("");
            }
        });

        youTubePlayerView = (YouTubePlayerView) findViewById(tiengduc123.com.deutschlernen.R.id.youtubeplayerview);
        youTubePlayerView.setAlpha(1.0f);
        youTubePlayerView.initialize(API_KEY, this);
    }

    public void shareForFriend() {
        String message = "Deutsch Lernen Mit Video. \n https://play.google.com/store/apps/details?id=tiengduc123.com.deutschlernen";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(share, "Share to your Friend"));
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void getListVideoIDs(){
        Bundle bu = getIntent().getExtras();
        videoIds = bu.getStringArrayList("VideoIDs");
    }

    public ArrayList<VideoObj> getMang(){
        ArrayList<VideoObj> VideoObjs;
        VideoObjs = (ArrayList<VideoObj>) getIntent().getSerializableExtra("Mang");
        return VideoObjs;
    }

    /*public String getCate() {
        Bundle bu = getIntent().getExtras();
        if (bu != null) {
            return category = bu.getString("category");
        }

        return "A";
    }*/

    class docJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            //Toast.makeText(getApplicationContext(), docNoiDung_Tu_URL(params[0]),Toast.LENGTH_LONG).show();
            /*qInternet _qInternet = new qInternet();
            return _qInternet.docNoiDung_Tu_URL(params[0]);*/
            return "a";
        }

        @Override
        protected void onPostExecute(String s) {
            videoIds = new ArrayList<String>();
            mang = new ArrayList<VideoObj>();
            mang = getMang();
            for(VideoObj v : mang){
                videoIds.add(v.key);
            }
            //adapter.clear();
            adapter = new ListAdapter(
                    getApplicationContext(),
                    tiengduc123.com.deutschlernen.R.layout.activity_list_video,
                    mang
            );


            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "" + mang.get(position).key, Toast.LENGTH_SHORT).show();
                    //youTubePlayerView.(mang.get(position).key);
                    youTubePlay(_YouTubePlayer, mang.get(position).key, position);
                }
            });


            if (mang.size() > 1) {
                //Toast.makeText(getApplicationContext(), mang.size() + " Videos were loaded", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please wait or Try to open www.TiengDuc123.com!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onInitializationFailure(Provider provider,
                                        YouTubeInitializationResult result) {

    }

    public int getPosition() {
        Bundle bu = getIntent().getExtras();
        if (bu != null) {
            position = bu.getInt("position");
            return position;
        }
        return 0;
    }

    public void youTubePlay(YouTubePlayer player, String keyID, int position) {
        _YouTubePlayer = player;
        _YouTubePlayer.loadVideos(videoIds, position,0);
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        _YouTubePlayer = player;
        if (!wasRestored) {
            Bundle bu = getIntent().getExtras();
            if (bu != null) {
                String keyID = bu.getString("key");
                VideoID = keyID;
                /*_YouTubePlayer.loadPlaylist("PLPep59R1T0u4OxcUxJ3gAmpr892Q1NkJf");*/

                // ham nay de tao ra 1 list play tu dong
                // nap videoID vao list va bo vao ham nay + them voi vi tri can play la no chay luon
                /*loadVideos(List<String> videoIds, int startIndex, int timeMillis)
                Loads and plays a list of videos.*/
                if(videoIds == null){
                    getListVideoIDs();
                }

                _YouTubePlayer.loadVideos(videoIds, getPosition(),0);
                _YouTubePlayer.setFullscreen(true);
                VideoID = keyID;
                /*
                _YouTubePlayer.loadVideo(keyID);*/
            } else {
                VideoID = VIDEO_ID;
                _YouTubePlayer.loadVideo(VIDEO_ID);
            }

        }
    }
}
