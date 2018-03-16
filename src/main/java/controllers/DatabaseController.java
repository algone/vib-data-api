/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.ParentUnit;
import model.Unit;
import model.VibandaImage;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.ReverseRouter;
import ninja.params.Param;
import ninja.params.PathParam;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileItem;
import ninja.uploads.FileProvider;
import org.bson.Document;
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
    ReverseRouter reverseRouter;

    @Inject
    ObjectMapper objectMapper;
    @Inject
    DataService dbService;
    @Inject
    VibandaImageService imgService;

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

        dbService.addParent(vpu);

//        return Results.html().template("views/ApplicationController/index.ftl.html");
        return reverseRouter.with(ApplicationController::index)
                .redirect();
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

        List<Unit> unitIds = dbService.getAllUnits();
//        Map<String, Object> data = new HashMap<>();
//        data.put("units", unitIds);
        return Results.html().template("views/ApplicationController/imagesUpload.ftl.html").render("units", unitIds);
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
        Map<String, Object> parents = dbService.getParentIds();
        Map<String, Object> data = new HashMap<>();
        data.put("parentUnits", parents);
        data.put("msg", vUnit.getId() + " Added!");
        dbService.addUnit(vUnit);
        return Results.html().template("views/ApplicationController/unitUpload.ftl.html").render("data", data);
    }

    public Result findUnitImages(@PathParam("unitId") String id) {
        List<VibandaImage> vui = dbService.findUnitImages(id);
        return Results.json().render(vui);
    }

    public Result findCounties(Context context) {
        List<Document> counties = dbService.getCounties();

        for (Document county : counties) {
            if (county.get("ke_counties") != null) {
                List<Document> docs = (List<Document>) county.get("ke_counties");
                return Results.json().render(docs);
            }

        }
        return Results.json().render(counties);
    }

    public Result findDestinations(Context context) {
        List<Document> destinations = dbService.getDestinations();
        return Results.json().render(destinations);
    }
        public Result findTopDestinations(Context context) {
        List<Document> topDestinations = dbService.getTopDestinations();
        return Results.json().render(topDestinations);
    }

    public Result search(Context context) {
        InputStream is = null;
        JsonNode actualObj = null;
        try {
            is = context.getInputStream();
            String jsonString = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));

            actualObj = objectMapper.readTree(jsonString);

            List<Unit> res = dbService.searchUnits(actualObj);
            return Results.json().render(res);
        } catch (IOException ex) {
            return Results.internalServerError();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                return Results.internalServerError();
            }
        }

    }

}
