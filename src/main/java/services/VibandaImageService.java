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
    public Map uploadImage() throws IOException {
        final Map config = ObjectUtils.asMap(
            "cloud_name", "vibanda",
            "api_key", "683298726672889",
            "api_secret", "sjNGXv_v-TwTpetybaJe5Ld-yCY");
        Cloudinary cloudinary = new Cloudinary(config);
        File toUpload = new File("N:\\Work\\Netbeans\\vibanda-html-markup\\sourceimages\\img10.jpg");
        Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
        return uploadResult;
    }
}
