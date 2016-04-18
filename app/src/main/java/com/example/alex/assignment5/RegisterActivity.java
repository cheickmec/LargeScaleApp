// Alex Mun
// Team 2
// ECE 4574
/*
RegisterActivity.java

Users can register new accounts in this activity.
Required information: username and password.
 */
package com.example.alex.assignment5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alex.assignment5.cloudService.cloudAPIClass;
import com.example.alex.assignment5.cloudService.cloudAPIClient;
import com.example.alex.assignment5.cloudService.usersPostResponse;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 3/27/2016.
 */
public class RegisterActivity extends AppCompatActivity
{
    // The string value that is transmitted by the Intent upon user verification
    public final static String NEW_USER_INFO = "com.example.alex.MESSAGE";
    private cloudAPIClient apiService;
    private cloudAPIClass apiClass;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiService = apiClass.getApiClient();

        // The sign-up button implements an OnClickListener to verify user credentials
        ImageButton signupButton = (ImageButton)findViewById(R.id.registerButton);
        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Retrieves the username string
                EditText nameField = (EditText) findViewById(R.id.newNameField);
                String userName = nameField.getText().toString();
                userName = userName.trim();

                // Registration requires a valid username and matching password fields
                if (!userName.isEmpty())
                {
                    register(v);
                } else
                {

                        Toast.makeText(getApplicationContext(),
                                "Error: You must enter a username",
                                Toast.LENGTH_LONG).show();

                }
            }
        });

        // The exit button implements an OnClickListener to exit the screen
        ImageButton exitButton = (ImageButton)findViewById(R.id.button_create);
        exitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    // This function is called when the user inputs a valid username and password combination
    public void register(View view)
    {
        retrofit2.Call call = apiService.userCreate();

        call.enqueue(new Callback<usersPostResponse>() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                if(response.isSuccessful())
                    Log.d("good", "Gucci");
                else {
                    Log.d("bad", "non successful");
                    Log.d("bad", "status code=" + response.code());
                }
            }

            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {
                Log.d("bad", "failure");
            }
        });

        // Uses an Intent to transmit user credentials between activities
        Intent intent = new Intent(this, MainMenuActivity.class);

        // Retrieves the username string
        EditText nameField = (EditText) findViewById(R.id.newNameField);
        String userName = nameField.getText().toString();
        userName = userName.trim();

        // Concatenates the username and password into a single string for the Intent
        String userInfo = (userName);
        intent.putExtra(NEW_USER_INFO, userInfo);
        startActivity(intent);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
