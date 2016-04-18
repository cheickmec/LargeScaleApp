package com.example.alex.assignment5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.alex.assignment5.cloudService.cloudAPIClass;
import com.example.alex.assignment5.cloudService.cloudAPIClient;
import com.example.alex.assignment5.cloudService.usersPostResponse;

import okhttp3.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 3/27/2016.
 */
public class ListUsersActivity extends AppCompatActivity
{
    private AlertDialog.Builder dialogBuilder;
    private cloudAPIClient apiService;
    private cloudAPIClass apiClass;
    private  String id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);

        Intent intent = getIntent();
        apiService = apiClass.getApiClient();



        // Implements an OnClickListener for button_back
        Button button_back = (Button) findViewById(R.id.user_back);
        button_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        // Implements adding a new user. Given the API we've been given by cloud services
        // this will simply post a new user
        Button button_new = (Button) findViewById(R.id.new_user);
        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postUser();
            }
        });

        Button button_delete = (Button) findViewById(R.id.delete_user);
        button_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                deleteUser();
            }

        });
    }

    private void deleteUser(){
        dialogBuilder = new AlertDialog.Builder(this);
        final EditText txtBox = new EditText(this);


        dialogBuilder.setTitle("Give the Id you wish to delete");
        dialogBuilder.setMessage("What is the ID");
        dialogBuilder.setView(txtBox);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                id = txtBox.getText().toString();
                retrofit2.Call call = apiService.userDelete(id);

                call.enqueue(new Callback<usersPostResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call call, Response response) {
                        if(response.isSuccessful())
                            Log.d("good", "Gucci");
                        else {
                            Log.d("bad", "not successful");
                            Log.d("bad", "status code=" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call call, Throwable t) {
                        Log.d("bad", "fafa");
                        Log.d("bad", "status code=" + t.getMessage());
                    }
                });

            }
        });
        AlertDialog delteUser = dialogBuilder.create();
        delteUser.show();

    }
    private void postUser(){
        retrofit2.Call call = apiService.userCreate();

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