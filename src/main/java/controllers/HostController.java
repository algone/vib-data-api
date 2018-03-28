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
import model.Host;
import model.ParentUnit;
import model.Unit;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.ReverseRouter;
import ninja.params.PathParam;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileProvider;
import services.DataService;
import services.VibandaAuthService;
import services.VibandaImageService;

/**
 *
 * @author A4372949
 */
@Singleton
@FileProvider(DiskFileItemProvider.class)
public class HostController {

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
    @Inject
    VibandaImageService imgService;

    public Result listAll(Context context) {
        List<ParentUnit> vpus = dbService.getAllParents();
        return Results.json().render(vpus);
    }

    public Result listAllUnits(Context context) {
        List<Unit> vus = dbService.getAllUnits();

        return Results.json().render(vus);
    }

    public Result listAllHosts(Context context) {
        List<Host> hosts = dbService.getAllHosts();
        return Results.json().render(hosts);
    }

    public Result findHostByUsername(@PathParam("hostId") String id) {
        Host host = dbService.getHostByHostId(id);
        return Results.json().render(host);
    }

    public Result getHost(@PathParam("id") String id) {
        LOG.info("HOST ID: "+id);
        Host host = dbService.getHost(id);
        return Results.json().render(host);
    }

    public Result findHostUnits(Context context, @PathParam("hostId") String id) {
        LOG.info("Retrieving Units for host: " + id);
        List<Unit> units = dbService.findUnitsByHostId(id);
//        units.add(new ParentUnit());
        return Results.json().render(units);
    }
}
