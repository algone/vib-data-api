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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.ParentUnit;
import model.Rating;
import model.Review;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.DataService;
import services.VibandaAuthService;
import services.VibandaImageService;

/**
 *
 * @author algone
 */
@Singleton
@FileProvider(DiskFileItemProvider.class)
public class DatabaseController {

    Logger LOG = LoggerFactory.getLogger(DatabaseController.class);
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
        return Results.html().template("views/ApplicationController/imagesUpload.ftl.html").render("units", unitIds).render("host",context.getSession().get("userName") );
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
        dbService.uploadToGridFS(unitImageFile.getFile().getAbsolutePath(), imageName);
        return Results.noContent();

    }

    public Result findUnitImages(@PathParam("unitId") String id) {
        List<VibandaImage> vui = dbService.findUnitImagesById(id);
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

    public Result addReview(Context context,
            @Param("avatar") FileItem avatarImage,
            @Param("reviewText") String text,
            @Param("reviewTitle") String title,
            @Param("reviewerName") String reviewer,
            @Param("dateOfReviev") String reviewDate,
            @Param("identifier") String id,
            @Param("rating") int rating, @PathParam("revtype") String revType) {

        Review rev = new Review();
        rev.setReviewTitle(title);
        rev.setReviewText(text);
        rev.setReviewerName(reviewer);
        Map uploadParams = ObjectUtils.asMap(
                "tags", id
        );
        try {
            Map result = imgService.uploadImage(avatarImage.getFile(), uploadParams);
            String url = (String) result.get("url");
            rev.setReviewerAvatar(url);
        } catch (IOException ex) {
            rev.setReviewerAvatar("http://res.cloudinary.com/vibanda/image/upload/v1521676951/cr42yjg5wwiedzoxs8jy.png");

        }
        Rating rate = new Rating();
        rate.setDate(reviewDate);
        rate.setRating(rating);
        rate.setIpAddress(context.getRemoteAddr());
        rev.setRating(rate);
        dbService.storeReview(rev, id, revType);
        return reverseRouter.with(ApplicationController::showReviewForm)
                .redirect();

    }
}
