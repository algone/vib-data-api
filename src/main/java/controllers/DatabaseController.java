/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import services.DataService;
import services.VibandaImageService;

/**
 *
 * @author algone
 */
@Singleton
@FileProvider(DiskFileItemProvider.class)
public class DatabaseController {

    @Inject
    DataService dbService;
    @Inject
    VibandaImageService imgService;
    private List<VibandaImage> parentImages = new ArrayList<>();
    private List<Unit> units = new ArrayList<>();
    private List<VibandaImage> unitImages = new ArrayList<>();

    public Result deleteParent(@PathParam("parentId") long productId) {
        dbService.deleteParent(productId);
        return Results.noContent();
    }

    public Result findParent(@PathParam("parentId") String id) {
        ParentUnit parent = dbService.findParent(id);
        return Results.json().render(parent);

    }

    public Result addParent(Context context) {
        ParentUnit vpu = new ParamsExtrator(context).getParent();
        vpu.setParentImages(parentImages);
        dbService.addParent(vpu);
        units.clear();
        return Results.html().template("views/ApplicationController/index.ftl.html");

    }

    public Result uploadImage(Context context, @Param("unitImageFile") FileItem unitImageFile,
            @Param("imageName") String imageName,
            @Param("coverImage") boolean isCoverImage,
            @Param("useAsParentImage") boolean useAsParentImage,
            @Param("unitId") String unitId,
            @Param("imageDescription") String imageDescription) throws IOException {

        System.out.println("Uploading .....image: " + imageName);

        Map uploadParams = ObjectUtils.asMap(
                "tags", imageDescription,
                "imageDescription", imageDescription
        );

        Map result = imgService.uploadImage(unitImageFile.getFile(), uploadParams);

        VibandaImage img = new VibandaImage();
        String url = (String) result.get("url");
        img.setImageId((String) result.get("public_id"));
        img.setImageUrl(url);
        img.setUnitId(unitId);
        img.setImageDescription((String) uploadParams.get("tags"));

//        dbService.upload(unitImageFile.getFile().getAbsolutePath(), imageName);
        String iscover = context.getParameter("coverImage");
        String isParentImage = context.getParameter("useAsParentImage");

        if (isParentImage != null && iscover.equalsIgnoreCase("on")) {
            System.out.println("Adding parent Image");
            img.setIsCoverImage(true);
        }
        if (iscover != null && iscover.equalsIgnoreCase("on")) {
            System.out.println("Adding unit Image");
            img.setUseAsParentImage(true);
        }

        dbService.saveImage(img);

        List<String> unitIds = dbService.getUnitIds();
        Map<String, Object> data = new HashMap<>();
        data.put("units", unitIds);
        return Results.html().template("views/ApplicationController/imagesUpload.ftl.html").render("data", data);
    }

    private Map<String, Object> getCloudinaryResult() throws JsonSyntaxException {
        JsonElement root = new JsonParser().parse("{\n"
                + " \"public_id\":\"tquyfignx5bxcbsupr6a\",\n"
                + " \"version\":1375302801,\n"
                + " \"signature\":\"52ecf23eeb987b3b5a72fa4ade51b1c7a1426a97\",\n"
                + " \"width\":1920,\n"
                + " \"height\":1200,\n"
                + " \"format\":\"jpg\",\n"
                + " \"resource_type\":\"image\",\n"
                + " \"created_at\":\"2017-07-31T20:33:21Z\",\n"
                + " \"bytes\":737633,\n"
                + " \"type\":\"upload\",\n"
                + " \"url\":\n"
                + "   \"https://res.cloudinary.com/demo/image/upload/v1375302801/tquyfignx5bxcbsupr6a.jpg\",\n"
                + " \"secure_url\":\n"
                + "   \"https://res.cloudinary.com/demo/image/upload/v1375302801/tquyfignx5bxcbsupr6a.jpg\",\n"
                + " \"etag\":\"1adf8d2ad3954f6270d69860cb126b24\"\n"
                + "}");
        java.lang.reflect.Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Gson gson = new Gson();
        Map<String, Object> result = gson.fromJson(root, mapType);

        return result;
    }

    public Result addImage(Context context,
            @Param("unitImageFile") FileItem unitImageFile,
            @Param("imageName") String imageName,
            @Param("coverImage") boolean isCoverImage,
            @Param("imageDescription") String imageDescription) {

        VibandaImage image = new VibandaImage();
        image.setImageUrl("assets/img/images/" + imageName);
        image.setImageDescription(imageDescription);
        dbService.upload(unitImageFile.getFile().getAbsolutePath(), imageName);
        if (isCoverImage) {
            parentImages.add(image);
        } else {
            unitImages.add(image);
        }
        return Results.noContent();

    }

    public Result listAll(Context context) {
        List<ParentUnit> vpus = dbService.getAllParents();
        return Results.json().render(vpus);
    }

    public Result listAllUnits(Context context) {
        List<Unit> vus = dbService.getAllUnits();

        return Results.json().render(vus);
    }

    public Result findUnit(@PathParam("unitId") String id) {
        Unit vu = dbService.findUnit(id);

        return Results.json().render(vu);
    }

    public Result findParentUnit(@PathParam("parentId") String id) {
        ParentUnit vpu = dbService.findParent(id);
        return Results.json().render(vpu);
    }

    public Result getUnitsByParentId(@PathParam("parentId") String id) {
        List<Unit> vpu = dbService.findUnitsByParentId(id);
        return Results.json().render(vpu);
    }

    public Result addUnit(Context context) {
        ParamsExtrator pe = new ParamsExtrator(context);
        Unit vUnit = pe.getUnit();
        List<String> parents = dbService.getParentIds();
        Map<String, Object> data = new HashMap<>();
        data.put("parentUnits", parents);
        data.put("msg", vUnit.getId() + " Added!");
        dbService.addUnit(vUnit);
        return Results.html().template("views/ApplicationController/unitUpload.ftl.html").render("data", data);
    }
}
