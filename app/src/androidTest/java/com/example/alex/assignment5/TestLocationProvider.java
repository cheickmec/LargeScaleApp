package com.example.alex.assignment5;

/**
 * Created by Rachael on 4/9/2016.
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.test.ApplicationTestCase;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.io.Writer;
import java.net.UnknownHostException;

import junit.framework.Assert;
import dalvik.annotation.TestTarget;
public final class TestLocationProvider extends ApplicationTestCase<Application> {


    public TestLocationProvider() {
        super(Application.class);

        for (int i = 0; i < 20; i++) {
            sendLocation(1.1, 1.3, 0); //send example location data
        }
    }

    //Sends a new location to the location service
    void sendLocation(double log, double lat, double alt) {
        try {
            Socket socket = new Socket("localhost", 5554); // usually 5554
            //send a new locations to the location services
            String str = "geo fix " + log + " " + lat + " " + alt ;
            Writer w = new OutputStreamWriter(socket.getOutputStream());
            w.write(str + "\r\n");
            w.flush();
            Context context = getContext();
                    //get from preferences
            SharedPreferences prefs = context.getSharedPreferences("Preferences", 0);
            //Convert the strings back into double precision values
            Double longitude = Double.parseDouble(
                    prefs.getString("longitude", null));
            Double latitude  = Double.parseDouble(
                    prefs.getString("longitude", null));
            Double altitude  = Double.parseDouble(
                    prefs.getString("longitude", null));

            //assert that the changes hold true
            //these assertions also check that change in location has been called
            Assert.assertEquals(log, longitude);
            Assert.assertEquals(lat, latitude);
            Assert.assertEquals(alt, altitude);
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

