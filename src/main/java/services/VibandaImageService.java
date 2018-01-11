/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.inject.Singleton;
import model.VibandaImage;
import net.binggl.ninja.mongodb.MongoDB;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author A4372949
 */
@Singleton
public class VibandaImageService implements ImageService {

    @Inject
    private MongoDB mongoDB;

    @Override
    public Map uploadImage(File toUpload, Map uploadParams) throws IOException {
        final Map config = ObjectUtils.asMap(
                "cloud_name", "vibanda",
                "api_key", "683298726672889",
                "api_secret", "sjNGXv_v-TwTpetybaJe5Ld-yCY");
        Cloudinary cloudinary = new Cloudinary(config);

        Map uploadResult = cloudinary.uploader().upload(toUpload, uploadParams);
    
        Morphia morphia = this.mongoDB.getMorphia();

//        morphia.mapPackage("model");
        Datastore ds = this.mongoDB.getDatastore();
       // convert map to JSON String
//       Gson gsonObj = new Gson();
//        String jsonStr = gsonObj.toJson(uploadResult);
//        System.out.println(jsonStr);
VibandaImage img = new VibandaImage();
String url = (String) uploadResult.get("url");
img.setImageUrl(url);
img.setImageDescription((String) uploadParams.get("dffffsaf"));
        ds.save(img);
        return uploadResult;
    }
}
