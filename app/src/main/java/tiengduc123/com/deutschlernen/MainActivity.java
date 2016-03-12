package tiengduc123.com.deutschlernen;

import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.github.clans.fab.FloatingActionButton;

import tiengduc123.com.deutschlernen.qLib.qInternet;
import tiengduc123.com.deutschlernen.BuildConfig;
import tiengduc123.com.deutschlernen.qLib.qDevice;


public class MainActivity extends AppCompatActivity {

    Button btna;
    Button btnB;
    Button btnC;
    Button btnD;

    final static private String PREF_KEY_SHORTCUT_ADDED = "PREF_KEY_SHORTCUT_ADDED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(tiengduc123.com.deutschlernen.R.layout.activity_main);

        createShortcutIcon();// tao shortcut

        FloatingActionButton myFab = (FloatingActionButton) findViewById(tiengduc123.com.deutschlernen.R.id.fab_Share);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                qDevice a = new qDevice(MainActivity.this);
                a.shareForFriend();
            }
        });

        com.github.clans.fab.FloatingActionButton FabDownload = (com.github.clans.fab.FloatingActionButton) findViewById(tiengduc123.com.deutschlernen.R.id.fab_Download);
        FabDownload.setVisibility(View.INVISIBLE);


        com.github.clans.fab.FloatingActionButton SendVideo = (com.github.clans.fab.FloatingActionButton) findViewById(tiengduc123.com.deutschlernen.R.id.fab_SendVideo);
        SendVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, SendVideo.class);
                startActivity(it);
            }
        });

        /*AdView mAdView = (AdView) findViewById(tiengduc123.com.deutschlernen.R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        btna = (Button) findViewById(tiengduc123.com.deutschlernen.R.id.btn);
        btnB = (Button) findViewById(tiengduc123.com.deutschlernen.R.id.btnB);
        btnC = (Button) findViewById(tiengduc123.com.deutschlernen.R.id.btnC);
        btnD = (Button) findViewById(tiengduc123.com.deutschlernen.R.id.btnD);
        if (!isOnline()) {
            Toast.makeText(getApplicationContext(), "You need to connect to the Internet", Toast.LENGTH_LONG).show();
        }

        btna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChuyenManHinh("A");
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChuyenManHinh("B");
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChuyenManHinh("C");
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChuyenManHinh("D");
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
                break;

            case tiengduc123.com.deutschlernen.R.id.MenuSendVideo:
                Intent it = new Intent(MainActivity.this, SendVideo.class);
                startActivity(it);
                break;

            case tiengduc123.com.deutschlernen.R.id.Impressum:
                Intent Impressum = new Intent(MainActivity.this, Impressum.class);
                startActivity(Impressum);
                break;

            case R.id.MenuAppsTiengDuc123:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://search?q=pub:TiengDuc123"));
                startActivity(intent);
                break;

            case tiengduc123.com.deutschlernen.R.id.MenuUpdate:
                //check Update
                qInternet _qInternet = new qInternet();
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                String version = _qInternet.docNoiDung_Tu_URL("http://tiengduc123.com/app/checknewversion.php?version_Deutsch_Lernen_Mit_Video=1&versionCode=" + versionCode + "&versionName=" + versionName);
                Toast.makeText(getApplicationContext(), version, Toast.LENGTH_LONG).show();

                Intent getWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse("http://tiengduc123.com/app/checknewversion.php?version_Deutsch_Lernen_Mit_Video=1&versionCode=" + versionCode + "&versionName=" + versionName));
                startActivity(getWebPage);

                /*if(version != "" ){
                    Toast.makeText(getApplicationContext(), " Your app is old, you can download new app recently ", Toast.LENGTH_LONG).show();
                    Intent getWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse(version));
                    startActivity(getWebPage);
                }*/
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


    // Creates shortcut on Android widget screen
    private void createShortcutIcon() {

        // Checking if ShortCut was already added
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        boolean shortCutWasAlreadyAdded = sharedPreferences.getBoolean(PREF_KEY_SHORTCUT_ADDED, false);
        if (shortCutWasAlreadyAdded) return;

        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Deutsch Lernen");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), tiengduc123.com.deutschlernen.R.mipmap.ic_launcher));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);

        // Remembering that ShortCut was already added
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_KEY_SHORTCUT_ADDED, true);
        editor.commit();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void ChuyenManHinh(String str) {
        if (!isOnline()) {
            Toast.makeText(getApplicationContext(), "You need to connect to the Internet", Toast.LENGTH_LONG).show();
            return;
        }
        Intent it = new Intent(MainActivity.this, ListVideo.class);
        it.putExtra("category", str);
        startActivity(it);
    }
}
