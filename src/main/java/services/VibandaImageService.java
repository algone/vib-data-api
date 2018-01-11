/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.inject.Singleton;

/**
 *
 * @author A4372949
 */
@Singleton
public class VibandaImageService implements ImageService {



    @Override
    public Map uploadImage(File toUpload, Map uploadParams) throws IOException {
        final Map config = ObjectUtils.asMap(
                "cloud_name", "vibanda",
                "api_key", "683298726672889",
                "api_secret", "sjNGXv_v-TwTpetybaJe5Ld-yCY");
        Cloudinary cloudinary = new Cloudinary(config);
        uploadParams.put("proxy","http://10.151.249.76:8080");
        Map uploadResult = cloudinary.uploader().upload(toUpload, uploadParams);
        return uploadResult;
    }
}
