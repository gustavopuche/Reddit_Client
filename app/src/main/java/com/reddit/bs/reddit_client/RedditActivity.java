package com.reddit.bs.reddit_client;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class RedditActivity extends AppCompatActivity {

    //	TAG for Debug
    private static final String TAG = RedditActivity.class.getSimpleName();

    // json object response url
    private String urlJsonObj = "https://api.reddit.com/r/programming/new.json";

    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;


    // Progress dialog
    private ProgressDialog pDialog;

    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;

    private GetJSONData getJSONData;

    // contacts JSONArray
    JSONArray contacts = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reddit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;

        txtResponse = (TextView) findViewById(R.id.textView);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        try {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (getJSONData != null) {
                        getJSONData.cancel(true);
                    }
                    // Calling async task to get json
                    getJSONData = (GetJSONData) new GetJSONData().execute();

                    Snackbar.make(view, "Updating ...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        listView = (ListView) findViewById(R.id.card_listView);


        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.card);

        // Calling async task to get json
        getJSONData = (GetJSONData) new GetJSONData().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"-----Click-----");
                List<Card> list = cardArrayAdapter.getCardList();
                Card elem = list.get(position);
                Log.d(TAG,elem.getUrl());
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url",elem.getUrl());
                startActivity(intent);

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reddit, menu);
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Reddit Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.reddit.bs.reddit_client/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Reddit Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.reddit.bs.reddit_client/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

//    /**
//     * Method to make json object request where json response starts wtih {
//     * */
//    private void makeJsonObjectRequest() {
//
//        //Debug
//        Log.d(TAG,"----------<<<2>--------");
//
//        showpDialog();
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://api.androidhive.info/volley/person_object.json";
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,url
//                ,null,new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d(TAG, response.toString());
//
//                //Debug
//                Log.d(TAG, "----------2.1--------");
//
//                //Debug
//                Log.d(TAG, response.toString());
//
//                try {
//                    // Parsing json object response
//                    // response will be a json object
//                    String thumbnail = response.getString("thumbnail");
//                    String date = response.getString("created");
//                    String title = response.getString("title");
//                    String author = response.getString("author");
//                    String score = response.getString("score");
//                    String comments = response.getString("num_comments");
//
//                    jsonResponse = "";
//                    jsonResponse += "Thumbnail: " + thumbnail + "\n\n";
//                    jsonResponse += "Date: " + date + "\n\n";
//                    jsonResponse += "Title: " + title + "\n\n";
//                    jsonResponse += "Author: " + author + "\n\n";
//                    jsonResponse += "Score: " + score + "\n\n";
//                    jsonResponse += "Comments: " + comments + "\n\n";
//
//
//                    //Debug
//                    Log.d(TAG, jsonResponse);
//
//                    txtResponse.setText(jsonResponse);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//
//                hidepDialog();
//            }
//
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//                hidepDialog();
//            }
//        });
//
//        //Debug
//        Log.d(TAG,"------------------3-------------------");
//
//        // Adding request to request queue
////        AppController.getInstance().addToRequestQueue(jsonObjReq);
//
//        // hide the progress dialog
//        hidepDialog();
//    }
//

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetJSONData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(RedditActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);

            Log.d("--onPreExecute","clear screen");
            // Clears Screen
            clearList();

            // Disconnects CardArrayAdapter
            listView.setAdapter(null);

            showpDialog();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlJsonObj, ServiceHandler.GET);

            Log.d("-------Response: ", ">* " + jsonStr);

            // Empty response
            jsonResponse = "";

            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);


                    String kind = jsonObj.getString("kind");

                    Log.d(TAG, "kind:::" + kind);

                    JSONObject data = jsonObj.getJSONObject("data");

                    // Getting JSON Array node
                    contacts = data.getJSONArray("children");


                    Log.d("Try--", "Num elems::: " + contacts.length());

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        JSONObject dataelem = c.getJSONObject("data");

                        String thumbnail = dataelem.getString("thumbnail");
                        String date = dataelem.getString("created");
                        String title = dataelem.getString("title");
                        String author = dataelem.getString("author");
                        String score = dataelem.getString("score");
                        String comments = dataelem.getString("num_comments");
                        String url = dataelem.getString("url");

                        double time = Double.parseDouble(date);
                        date = getDate((long) time);


                        try {
                            url = URLDecoder.decode(url,"UTF-8");

                        } catch (Exception e) {
                            Log.e(TAG, "------Encoding Error!!! ");
                            Log.e(TAG, "Exception: " + e);
                        }

                        Card card = new Card(thumbnail, date, title, author, score, comments,url);

                        try{
                            if (thumbnail.length() > 0) {

                                String imageurl = URLDecoder.decode(thumbnail, "UTF-8");
                                Log.d(TAG, "Image:: " + imageurl);
                                card.setImage(getBitmap(imageurl));
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "------Encoding Error!!! ");
                            Log.e(TAG, "Exception: " + e);
                        }


                        cardArrayAdapter.add(card);

                        jsonResponse += "Thumbnail: " + thumbnail;
                        jsonResponse += " Date: " + date;
                        jsonResponse += " Title: " + title;
                        jsonResponse += " Author: " + author;
                        jsonResponse += " Score: " + score;
                        jsonResponse += " Comments: " + comments + "\n\n";

                        Log.d("Response: ", "Data===Thumbnail" + thumbnail + " title: "
                                + title + "date:" + date + " author " +
                                author + " score " + score + " comements " + comments);

                        if (thumbnail.length() > 0) {
                            Log.d("<<<Thumbnail>>>", "Image: " + thumbnail);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            hidepDialog();

            txtResponse.setText(jsonResponse);

            listView.setAdapter(cardArrayAdapter);
            cardArrayAdapter.notifyDataSetChanged();

            RedditActivity.this.isFinishing();
        }

        private Bitmap getBitmap(String url) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                input.close();
                connection.disconnect();
                return bitmap;
            } catch (IOException ioe) {
                Log.e(TAG, "--Bitmap Exception: " + ioe);
                return null;
            }
        }

        private String getDate(long time) {
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(time * 1000);
            String date = DateFormat.format("dd-MM-yyyy", cal).toString();
            return date;
        }

    }

    private void clearList()
    {
        cardArrayAdapter.removeList();
        cardArrayAdapter.notifyDataSetChanged();
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
