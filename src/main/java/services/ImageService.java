/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.File;
import java.util.Map;

/**
 *
 * @author A4372949
 */
public interface ImageService {

    Map uploadImage(File file, Map uploadParams);
    
}
