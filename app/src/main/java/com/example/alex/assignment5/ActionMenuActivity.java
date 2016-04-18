// Alex Mun
// Team 2
// ECE 4574
/*
ActionMenuActivity.java

Implements the Action Menu screen where the
user can modify settings for robots and
sensors in addition to performing specific
building tasks and operations.
 */
package com.example.alex.assignment5;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alex.assignment5.Progress;
import com.example.alex.assignment5.cloudService.cloudAPIClass;
import com.example.alex.assignment5.cloudService.cloudAPIClient;
import com.example.alex.assignment5.cloudService.nullReturnClass;
import com.example.alex.assignment5.cloudService.sensorRequest;
import com.example.alex.assignment5.cloudService.usersPostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 3/27/2016.
 */
public class ActionMenuActivity extends AppCompatActivity implements Progress.ProgressInteractionListener
{
    public final static String PROG_FRAG = "PROG";
    private Progress progFrag;
    private cloudAPIClient apiService;
    private cloudAPIClass apiClass;
    // The fragment manager inserts and removes fragments from the frame layout
    private FragmentManager fm;
    private String typeSensor;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);


        apiService = apiClass.getApiClient();

        fm = getFragmentManager();

        Fragment temp;
        temp = fm.findFragmentByTag(PROG_FRAG);
        progFrag = Progress.newInstance("a", "n");

        Intent intent = getIntent();

        // Implements an OnClickListener to transition to Progress
        ImageButton progButton = (ImageButton)findViewById(R.id.perfAction);
        progButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fm.beginTransaction().add(R.id.frag, progFrag, PROG_FRAG).commit();
            }
        });

        // Implements an OnClickListener to exit the preferences activity
        ImageButton exitButton = (ImageButton)findViewById(R.id.button_return);
        exitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        // Implements an OnClickListener to transition to the ListUsersActivity
        ImageButton usersButton = (ImageButton)findViewById(R.id.toUsers);
        usersButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToUsers(v);
            }
        });

        // Implements an OnClickListener to transition to the ListSensorsActivity
        ImageButton sensorsButton = (ImageButton)findViewById(R.id.toSensors);
        sensorsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToSensors(v);
            }
        });

        // Implements an OnClickListener to transition to the ListRobotsActivity
        ImageButton robotsButton = (ImageButton)findViewById(R.id.toRobots);
        robotsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToRobots(v);
            }
        });


    }

    public void goToUsers(View v)
    {
        Intent intent = new Intent(this, ListUsersActivity.class);
        startActivity(intent);
    }

    public void goToSensors(View v)
    {
        retrofit2.Call call = apiService.sensorCreate();

        call.enqueue(new Callback<usersPostResponse>() {
            @Override
            public void onResponse(retrofit2.Call<usersPostResponse> call, Response<usersPostResponse> response) {
                usersPostResponse usersResp = response.body();
                id = usersResp.getId();


                if(response.isSuccessful()) {
                    Log.d("good", "Gucci");

                }
                else
                    Log.d("bad", "not success");
            }

            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {
                Log.d("bad", "fafa");
            }
        });

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final EditText textBox1 = new EditText(this);

        dialogBuilder.setTitle("Give the type of the sensor");
        dialogBuilder.setMessage("What is the type");
        dialogBuilder.setView(textBox1);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                typeSensor = textBox1.getText().toString();
                retrofit2.Call call = apiService.sensorUpdate(new sensorRequest(typeSensor), id);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful())
                            Log.d("good", "Gucci");
                        else {
                            Log.d("bad", "not successful");
                            Log.d("bad", "status code=" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.d("bad", "fafa");
                        Log.d("bad", "status code=" + t.getMessage());
                    }
                });
            }
        });
        AlertDialog getType = dialogBuilder.create();
        getType.show();
        //Intent intent = new Intent(this, ListSensorsActivity.class);
        //startActivity(intent);
    }

    public void goToRobots(View v)
    {
        /*
        retrofit2.Call call = apiService.robotCreate();

        call.enqueue(new Callback<usersPostResponse>() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                if(response.isSuccessful())
                    Log.d("good", "Gucci");
                else
                    Log.d("bad", "not success");
            }

            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {
                Log.d("bad", "fafa");
            }
        });
        */
        Intent intent = new Intent(this, ListRobotsActivity.class);
        startActivity(intent);
    }

    // Helper method that removes the current fragment
    private void removeFragments()
    {
        if (progFrag != null)
        {
            fm.beginTransaction().remove(progFrag).commit();
        }
    }

    public void onFragmentInteraction(Uri uri)
    {

    }

    // Receives deleted data from Progress.java
    @Override
    public void sendDeletedData(int data)
    {
        Toast.makeText(getApplicationContext(), "[From Activity] Deleted Data Entry ID: "+data, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}