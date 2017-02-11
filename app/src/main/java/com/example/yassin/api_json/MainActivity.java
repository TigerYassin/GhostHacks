package com.example.yassin.api_json;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView myText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button myButton = (Button) findViewById(R.id.myButton);
        myText = (TextView) findViewById(R.id.myTextView);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONTask myJson = new JSONTask();
                myJson.execute("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt");


            }
        });





    }


    public class JSONTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... urls){


            HttpURLConnection myConnection = null;
            BufferedReader myReader = null;
            try {
                URL url = new URL(urls[0]);
                myConnection = (HttpURLConnection) url.openConnection();

                myConnection.connect();
                InputStream myInputStream = myConnection.getInputStream();
                myReader = new BufferedReader(new InputStreamReader(myInputStream));

                String line = "";
                StringBuffer myBuffer = new StringBuffer();
                while ((line = myReader.readLine()) != null) {

                    myBuffer.append(line);
                }

                String finalJSON = myBuffer.toString();
                JSONObject parentObject = new JSONObject(finalJSON);

                JSONArray parentArray = parentObject.getJSONArray("movies");

                JSONObject finalObject = parentArray.getJSONObject(0);

                String movieName = finalObject.getString("movie");
                int movieYear = finalObject.getInt("year");
                return myBuffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (myConnection != null) {
                    myConnection.disconnect();
                }
                try {
                    if (myReader != null) {
                        myReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            myText.setText(result);
        }
    }



}
