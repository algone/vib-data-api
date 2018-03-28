/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.ReverseRouter;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileProvider;
import org.bson.Document;
import services.DataService;
import services.VibandaAuthService;

/**
 *
 * @author A4372949
 */
@Singleton
@FileProvider(DiskFileItemProvider.class)
public class DestinationsController {
        @Inject
    org.slf4j.Logger LOG;
    @Inject
    ReverseRouter reverseRouter;
    @Inject
    VibandaAuthService authService;
    @Inject
    ObjectMapper objectMapper;
    @Inject
    DataService dbService; 
    
    
    public Result findDestinations(Context context) {
        List<Document> destinations = dbService.getDestinations();
        return Results.json().render(destinations);
    }



    public Result findTopDestinations(Context context) {
        List<Document> topDestinations = dbService.getTopDestinations();
        return Results.json().render(topDestinations);
    }

}
