/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.ParentUnit;
import model.Unit;
import model.VibandaImage;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileItem;
import ninja.uploads.FileProvider;
import org.apache.commons.io.FileUtils;
import services.DataService;

/**
 *
 * @author algone
 */
@Singleton
@FileProvider(DiskFileItemProvider.class)
public class DatabaseController {

    @Inject
    DataService dbService;
    private List<VibandaImage> parentImages = new ArrayList<>();
    private List<Unit> units = new ArrayList<>();
    private List<VibandaImage> unitImages = new ArrayList<>();

    public Result deleteParent(@PathParam("parentId") long productId) {
        dbService.deleteParent(productId);
        return Results.noContent();
    }

    public Result findParent(Context context, @PathParam("parentId") long id) {
        System.out.println("ID:" + id);
        ParentUnit parent = dbService.findParent(id);

        return Results.json().render(parent);

    }

    public Result addParent(Context context) {
        ParentUnit vpu = new ParamsExtrator(context).getParent();
        vpu.setParentImages(parentImages);
        vpu.getRentalUnits().addAll(units);
        units.clear();
        dbService.addParent(vpu);
//        return Results.json().render(vpu);
      return Results.html().template("views/ApplicationController/index.ftl.html");

    }

    public Result addImage(Context context,
            @Param("unitImageFile") FileItem unitImageFile,
            @Param("imageName") String imageName,
            @Param("imageDescription") String imageDescription) {
        File defDir = new File(System.getProperty("user.dir") + "/src/main/java/assets/img/images");
        System.out.println("DEST DIR: " + defDir.getAbsolutePath());
        File destFile = new File(defDir, unitImageFile.getFileName());

        System.out.println("DEST FILE: " + destFile.getAbsolutePath());

        try {
            FileUtils.copyFile(unitImageFile.getFile(), destFile);
        } catch (IOException ex) {
            return Results.json().render("Error...." + ex.getLocalizedMessage());
        }
        VibandaImage image = new VibandaImage();
        image.setImageUrl("assets/img/images/" + imageName);
        image.setImageDescription(imageDescription);
        parentImages.add(image);
        return Results.noContent();

    }

    public Result listAll(Context context) {
        List<ParentUnit> vpus = dbService.getAllParents();
        return Results.json().render(vpus);
    }

    public Result addUnit(Context context) {
        ParamsExtrator pe = new ParamsExtrator(context);
        Unit vUnit = pe.getUnit();

        vUnit.getUnitImages().addAll(unitImages);
        unitImages.clear();
        units.add(vUnit);
        return Results.noContent();

    }
}
