package be.ehb.restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tv_content);
        Thread thisWillRunInTheBackground = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient mClient = new OkHttpClient();

                    Request mRequest = new Request.Builder()
                            .url("https://jsonplaceholder.typicode.com/posts")
                            .get()
                            .build();


                    Response mResponse = mClient.newCall(mRequest).execute();

                    String responseText = mResponse.body().string();
                    tvContent.setText(responseText);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thisWillRunInTheBackground.start();
    }
}