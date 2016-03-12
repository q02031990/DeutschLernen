package tiengduc123.com.deutschlernen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
/*

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import tiengduc123.com.deutschlernen.qLib.qInternet;
import tiengduc123.com.deutschlernen.qLib.qDevice;

public class ListVideo extends AppCompatActivity {

    ListView lv;
    ArrayList<VideoObj> mang;
    ArrayList<String> videoIds;
    String category;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(tiengduc123.com.deutschlernen.R.layout.activity_list_video);
        Toolbar toolbar = (Toolbar) findViewById(tiengduc123.com.deutschlernen.R.id.toolbar);
        setSupportActionBar(toolbar);
        String cate = getCate();
        if (cate.equals("D")) {
            cate = "DSH";
        }
        getSupportActionBar().setTitle("Deutsch Lernen: Niveaus " + cate);

        com.github.clans.fab.FloatingActionButton myFab = (com.github.clans.fab.FloatingActionButton) findViewById(tiengduc123.com.deutschlernen.R.id.fab_Share);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                qDevice a = new qDevice(ListVideo.this);
                a.shareForFriend();
            }
        });

        com.github.clans.fab.FloatingActionButton FabDownload = (com.github.clans.fab.FloatingActionButton) findViewById(tiengduc123.com.deutschlernen.R.id.fab_Download);
        FabDownload.setVisibility(View.INVISIBLE);

        com.github.clans.fab.FloatingActionButton SendVideo = (com.github.clans.fab.FloatingActionButton) findViewById(tiengduc123.com.deutschlernen.R.id.fab_SendVideo);
        SendVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(ListVideo.this, SendVideo.class);
                startActivity(it);
            }
        });

        /*AdView mAdView = (AdView) findViewById(tiengduc123.com.deutschlernen.R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        lv = (ListView) findViewById(tiengduc123.com.deutschlernen.R.id.listView);

        mang = new ArrayList<VideoObj>();

        if (!isOnline()) {
            Toast.makeText(getApplicationContext(), "You have to connect Internet", Toast.LENGTH_LONG).show();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Random rand = new Random();
                int n = rand.nextInt(999999999);
                int m = rand.nextInt(999999999);
                new docJSON().execute("http://tiengduc123.com/app/youtube.php?language=ge&list=1&category=" + getCate() + "&n=" + String.valueOf(n) + "&m=" + String.valueOf(m));
            }
        });
    }

    public void shareForFriend() {
        String message = "Deutsch Lernen Mit Video.<br /> https://play.google.com/store/apps/details?id=tiengduc123.com.deutschlernen";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(share, "Share to your Friend"));
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(tiengduc123.com.deutschlernen.R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case tiengduc123.com.deutschlernen.R.id.MenuShare:
                shareForFriend();
                /*Toast.makeText(getApplicationContext(),"This is MenuShare", Toast.LENGTH_LONG).show();*/
                break;

            case tiengduc123.com.deutschlernen.R.id.MenuSendVideo:
                Intent it = new Intent(ListVideo.this, SendVideo.class);
                startActivity(it);
                /*Toast.makeText(getApplicationContext(),"This is MenuSendVideo", Toast.LENGTH_LONG).show();*/
                break;
            case R.id.MenuAppsTiengDuc123:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://search?q=pub:TiengDuc123"));
                startActivity(intent);
                break;

            case tiengduc123.com.deutschlernen.R.id.Impressum:
                Intent Impressum = new Intent(ListVideo.this, Impressum.class);
                startActivity(Impressum);
                break;

            case tiengduc123.com.deutschlernen.R.id.MenuAbout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About Us");
                builder.setMessage(Html.fromHtml("<p>See more at, \n <a href=\"http://Tiengduc123.com\">Http://www.Tiengduc123.com</a>.</p>"));
                AlertDialog alert = builder.create();
                alert.show();
        }
        return false;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public String getCate() {
        Bundle bu = getIntent().getExtras();
        if (bu != null) {
            return category = bu.getString("category");
        }

        return "A";
    }
/*
    public void onBackPressed() {
        Intent it = new Intent(ListVideo.this, MainActivity.class);
        finish();
        startActivity(it);

    }*/

    public void ChuyenManHinh(String key, int position) {
        Intent it = new Intent(ListVideo.this, VideoPlay.class);
        it.putExtra("key", key);
        it.putExtra("position", position);
        it.putExtra("category", getCate());
        it.putExtra("VideoIDs", videoIds);
        it.putExtra("Mang", mang);
        startActivity(it);
    }

    class docJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            //Toast.makeText(getApplicationContext(), docNoiDung_Tu_URL(params[0]),Toast.LENGTH_LONG).show();
            qInternet _qInternet = new qInternet();
            return _qInternet.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                mang.clear();// xoa sach mang
                JSONArray mangJson = new JSONArray(s);
                videoIds = new ArrayList<String>();
                for (int i = 0; i < mangJson.length(); i++) {
                    JSONObject sp = mangJson.getJSONObject(i);
                    mang.add(new VideoObj(
                            sp.getString("nameVideo"),
                            sp.getString("timeVideo"),
                            sp.getString("keyID")
                    ));

                    videoIds.add(sp.getString("keyID"));
                }
                //Toast.makeText(getApplicationContext(), "" + mangJson.length(), Toast.LENGTH_LONG).show();



                //adapter.clear();
                adapter = new ListAdapter(
                        getApplicationContext(),
                        tiengduc123.com.deutschlernen.R.layout.activity_list_video,
                        mang
                );


                lv.setAdapter(adapter);


                //load lai list
                adapter.notifyDataSetChanged();
                lv.invalidateViews();
                lv.refreshDrawableState();

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ChuyenManHinh(mang.get(position).key, position);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mang.size() > 1) {
                Toast.makeText(getApplicationContext(), mang.size() + " Videos were loaded", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please wait or Try to open www.TiengDuc123.com!", Toast.LENGTH_LONG).show();
                Intent it = new Intent(ListVideo.this, ListVideo.class);
                it.putExtra("category", getCate());
                startActivity(it);
            }
        }
    }
}
