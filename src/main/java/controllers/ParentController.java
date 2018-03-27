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
import model.ParentUnit;
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
import services.VibandaImageService;

/**
 *
 * @author A4372949
 */
@Singleton
@FileProvider(DiskFileItemProvider.class)
public class ParentController {

    static final Logger LOG = LoggerFactory.getLogger(ParentController.class);
    @Inject
    ReverseRouter reverseRouter;
    @Inject
    VibandaAuthService authService;
    @Inject
    ObjectMapper objectMapper;
    @Inject
    DataService dbService;

    public Result addParent(Context context) {
        ParentUnit vpu = new ParamsExtrator(context).getParent();

        dbService.addParent(vpu);

        return reverseRouter.with(ApplicationController::showUnitForm)
                .redirect();
    }

    public Result deleteParent(@PathParam("parentId") long productId) {
        dbService.deleteParent(productId);
        return Results.noContent();
    }

    public Result getAllParents() {
        List<ParentUnit> all = dbService.getAllParents();
        return Results.json().render(all);
    }

    public Result getParent(@PathParam("parentId") String parentId) {
        ParentUnit parent = dbService.findParentUnitById(parentId);
        return Results.json().render(parent);
    }

    public Result getParentByHostId(@PathParam("hostId") String hostId) {
        List<ParentUnit> parents = dbService.getHostParentUnits(hostId);
        return Results.json().render(parents);
    }

    public Result getParentByUnitId(@PathParam("unitId") String unitId) {
        //@TODO check the implementation, it was not finalized
        ParentUnit parent = dbService.findParentByUnitId(unitId);
        return Results.json().render(parent);
    }

    public Result searchParent(String searchWord) {
//        dbService.fin(productId);
        return Results.noContent();
    }
}
