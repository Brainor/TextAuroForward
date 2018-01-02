package com.brainor.textforward.textautoforward;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView_userID, textView_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        图标设置();
    }

    @SuppressLint("StaticFieldLeak")
    void 图标设置() {
        textView_userID = findViewById(R.id.UserID);
        textView_Password = findViewById(R.id.Password);
        textView_userID.setText(getPreferences(MODE_PRIVATE).getString("userID", ""));
        textView_Password.setText(getPreferences(MODE_PRIVATE).getString("Password", ""));
        findViewById(R.id.button).setOnClickListener(view -> {
            String userID = textView_userID.getText().toString();
            String Password = textView_Password.getText().toString();
            getPreferences(MODE_PRIVATE).edit()
                    .putString("userID", userID)
                    .putString("Password", Password)
                    .apply();

            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... userInfo) {
                    return new NetworkConnect().连接网络通(userInfo[0], userInfo[1]);
                }

                @Override
                protected void onPostExecute(String webResponse) {
                    ((TextView) findViewById(R.id.WebResponse)).setText(webResponse);
                }
            }.execute(userID, Password);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
