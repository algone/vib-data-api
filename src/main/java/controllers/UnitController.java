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
import model.Unit;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.ReverseRouter;
import ninja.params.PathParam;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.DataService;
import services.VibandaAuthService;

/**
 *
 * @author A4372949
 */
@Singleton
@FileProvider(DiskFileItemProvider.class)
public class UnitController {

    static final Logger LOG = LoggerFactory.getLogger(UnitController.class);
    @Inject
    ReverseRouter reverseRouter;
    @Inject
    VibandaAuthService authService;
    @Inject
    ObjectMapper objectMapper;
    @Inject
    DataService dbService;

    public Result addUnit(Context context) {
        ParamsExtrator pe = new ParamsExtrator(context);
        Unit vUnit = pe.getUnit();
        dbService.addUnit(vUnit);
        return reverseRouter.with(ApplicationController::showImageUploadForm).redirect();
    }

    public Result deleteUnit(@PathParam("unitId") String unitId) {
        dbService.deleteUnit(unitId);
        return Results.noContent();
    }

    public Result getAllUnits() {
        List<Unit> all = dbService.getAllUnits();
        return Results.json().render(all);
    }

    public Result getUnit(@PathParam("parentId") String parentId) {
        List<Unit> units = dbService.findUnitsByParentId(parentId);
        return Results.json().render(units);
    }

    public Result getUnitsByHostId(@PathParam("hostId") String hostId) {
        List<Unit> units = dbService.findUnitsByHostId(hostId);
        return Results.json().render(units);
    }

    public Result getUnitsByParentId(@PathParam("unitId") String unitId) {
        //@TODO check the implementation, it was not finalized
        Unit parent = dbService.findUnitById(unitId);
        return Results.json().render(parent);
    }

    public Result searchUnits(String searchWord) {
        List<Unit> result = dbService.searchUnits(searchWord);
         return Results.json().render(result);
    }
}
