package be.ehb.restapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import be.ehb.restapi.model.Entitys;
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

                    //raw payload data
                    String responseText = mResponse.body().string();
                    //convert to json
                    JSONArray postsJSONArray = new JSONArray(responseText);
                    int postJSONArrayLenth = postsJSONArray.length();
                    ArrayList<Entitys> parseObjects = new ArrayList<>(postJSONArrayLenth);

                    for(int i = 0; i < postJSONArrayLenth; i++){
                        JSONObject temp = postsJSONArray.getJSONObject(i);

                        Entitys currentPost = new Entitys(
                                temp.getLong("userId"),
                                temp.getLong("id"),
                                temp.getString("title"),
                                temp.getString("body")
                        );

                        parseObjects.add(currentPost);

                    }

                    tvContent.setText(parseObjects.toString());


                                                //
                } catch (IOException e) {       // "Exception" kan ook maar dan heb je geen idee wat er mis is
                                                // en de tweede "catch (JSONException e)" is dan niet nodig
                                                //
                    e.printStackTrace();    //this will crash the app
                } catch (JSONException e) {
                    throw new RuntimeException(e); //this will crash the app
                }

            }
        });
        thisWillRunInTheBackground.start();
    }
}