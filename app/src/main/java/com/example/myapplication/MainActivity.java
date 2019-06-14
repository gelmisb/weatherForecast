package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private EditText city;
    private Button submit;

    private ArrayList<HashMap<String, String>> contactList;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = findViewById(R.id.cityText);
        submit = findViewById(R.id.submit);
        contactList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lv = (ListView) findViewById(R.id.list);

        new GetResponse().execute();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private class GetResponse extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }


//        api.openweathermap.org/data/2.5/forecast/daily?q=dublin,units=metric&cnt=16&appidae3723984918e29156906ffa2182bf02

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://jsonp.afeld.me/?url=https%3A%2F%2Fapi.openweathermap.org%2Fdata%2F2.5%2Fforecast%2Fdaily%3Fq%3DDublin%26mode%3Djson%26units%3Dmetric%26cnt%3D7%26appid%3Dae3723984918e29156906ffa2182bf02";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("list");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {

                        Log.i("!!!!!!!!!", jsonObj.getString("message") + " ");
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("dt");
                        JSONObject temp = c.getJSONObject("temp");

                        Log.i("Day1", temp.get("day") + " ");
                        Log.i("Day2", temp.get("min") + " ");
                        Log.i("Day3", temp.get("max") + " ");
                        Log.i("Day4", temp.get("night") + " ");
                        Log.i("Day5", temp.get("eve") + " ");
                        Log.i("Day6", temp.get("morn") + " ");




                        String name = c.getString("cnt");
                        String email = c.getString("list");
                        String address = c.getString("country");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
                    R.layout.item_layout, new String[]{ "list","name"},
                    new int[]{R.id.id, R.id.name});
            lv.setAdapter(adapter);
        }
    }
}
