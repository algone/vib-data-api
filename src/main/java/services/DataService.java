/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.inject.Singleton;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.lifecycle.Dispose;
import ninja.lifecycle.Start;

/**
 *
 * @author algone
 */
@Singleton
public class DataService {
        @Start(order = 90)
    public void startService() {
        //do something     
            System.out.println("Data service started....");
    }

    @Dispose(order = 90)
    public void stopService() {
       //do something
        System.out.println("Stoping data service...");
    }

    public Result getCount(Context ctx) {
        return Results.json();
    }
}
