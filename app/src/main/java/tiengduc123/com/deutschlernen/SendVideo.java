package tiengduc123.com.deutschlernen;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendVideo extends Activity {
    Button send;
    EditText txtNiveaus, txtBody;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(tiengduc123.com.deutschlernen.R.layout.activity_send_video);

        txtNiveaus = (EditText) findViewById(tiengduc123.com.deutschlernen.R.id.txtNiveaus);
        txtBody = (EditText) findViewById(tiengduc123.com.deutschlernen.R.id.txtBody);
        send = (Button) findViewById(tiengduc123.com.deutschlernen.R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    public void sendEmail(){

        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(new Uri.Builder().scheme("mailto").build())
                .putExtra(Intent.EXTRA_EMAIL, new String[]{"Deutsch Lernen Mit Video <tiengduc123.com@gmail.com>"})
                .putExtra(Intent.EXTRA_SUBJECT, "[" + txtNiveaus.getText().toString().toUpperCase() + "] Share Deutsche Videos Zum Lernen" )
                .putExtra(Intent.EXTRA_TEXT, txtBody.getText())
                ;

        ComponentName emailApp = intent.resolveActivity(getPackageManager());
        ComponentName unsupportedAction = ComponentName.unflattenFromString("com.android.fallback/.Fallback");
        if (emailApp != null && !emailApp.equals(unsupportedAction))
            try {
                // Needed to customise the chooser dialog title since it might default to "Share with"
                // Note that the chooser will still be skipped if only one app is matched
                Intent chooser = Intent.createChooser(intent, "Send email with");
                startActivity(chooser);
                return;
            }
            catch (ActivityNotFoundException ignored) {
            }

        Toast
                .makeText(this, "Couldn't find an email app and account", Toast.LENGTH_LONG)
                .show();
        onBackPressed();
    }
}
