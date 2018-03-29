/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.google.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import ninja.utils.NinjaProperties;

/**
 *
 * @author A4372949
 */
@Singleton
public class VibandaImageService implements ImageService {

    @Inject
    org.slf4j.Logger LOG;
    @Inject
    NinjaProperties ninjaProperties;

    @Override
    public Map uploadImage(File toUpload, Map uploadParams) {
        final Map config = ObjectUtils.asMap(
                "cloud_name", ninjaProperties.get("cloudinary.cloud_name"),
                "api_key", ninjaProperties.get("cloudinary.api_key"),
                "api_secret", ninjaProperties.get("cloudinary.api_secret"));
        Cloudinary cloudinary = new Cloudinary(config);
//        uploadParams.put("proxy", "http://10.151.249.76:8080");
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(toUpload, uploadParams);
            return uploadResult;
        } catch (IOException ex) {
            LOG.warn("Cannot retrieve image from cloudinary, gonna use default");
            Map map = new HashMap();
//            map.put("url", "");
            map.put("public_id", "xxxxxxxxxxxxxxxxxxxxxxxxx");
//           
            return map;
        }

    }
}
