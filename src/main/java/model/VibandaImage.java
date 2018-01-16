/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;



/**
 *
 * @author A4372949
 */

@Embedded
public class VibandaImage implements Serializable {

    private String imageId = new ObjectId().toHexString();
    private String unitId ;
    private String imageUrl;
    private String imageDescription;
    private boolean isCoverImage;
    private boolean useAsParentImage;

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isIsCoverImage() {
        return isCoverImage;
    }

    public void setIsCoverImage(boolean isCoverImage) {
        this.isCoverImage = isCoverImage;
    }

    public boolean isUseAsParentImage() {
        return useAsParentImage;
    }

    public void setUseAsParentImage(boolean useAsParentImage) {
        this.useAsParentImage = useAsParentImage;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    
}
