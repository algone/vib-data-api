/**
 * Copyright (C) 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
import model.Location;
import model.ParentUnit;
import model.ParentUnitAccessibility;
import model.ParentUnitFacilities;
import model.Unit;
import model.VibandaImage;

import ninja.Context;
import ninja.ReverseRouter;
import ninja.jpa.UnitOfWork;
import ninja.params.Param;
import ninja.uploads.DiskFileItemProvider;
import ninja.uploads.FileItem;
import ninja.uploads.FileProvider;
import org.apache.commons.io.FileUtils;
import services.DataService;
import services.VibandaImageService;

@FileProvider(DiskFileItemProvider.class)
@Singleton
public class ApplicationController {

    @Inject
    DataService dbService;
    @Inject
    VibandaImageService imgService;
    @Inject
    ReverseRouter reverseRouter;
//    @Inject
//    Provider<EntityManager> entitiyManagerProvider;

    List<Unit> units = new ArrayList<>();
    List<VibandaImage> unitImages = new ArrayList<>();

    public Result index() {
//        List<ParentUnit> vpus = dbService.getAllParents();
        return Results.html().template("views/ApplicationController/index.ftl.html");

    }

    public Result showUpload() {

        return Results.html().template("views/ApplicationController/index_wiz.ftl.html");

    }

    public Result showParentUnitForm() {

        return Results.html().template("views/ApplicationController/parentUnitUpload.ftl.html");

    }

    public Result showUnitForm() {
        List<String> parents = dbService.getParentIds();

        Map<String, Object> data = new HashMap<>();
        data.put("parentUnits", parents);
        data.put("msg", "");

        return Results.html().template("views/ApplicationController/unitUpload.ftl.html").render("data", data);

    }

    public Result showImageUploadForm() {
        List<String> unitIds = dbService.getUnitIds();
        Map<String, Object> data = new HashMap<>();
        data.put("units", unitIds);
        return Results.html().template("views/ApplicationController/imagesUpload.ftl.html").render("data", data);

    }

    public Result addUnit(Context context) {
        ParamsExtrator pe = new ParamsExtrator(context);
        Unit vUnit = pe.getUnit();

        vUnit.getUnitImages().addAll(unitImages);
        unitImages.clear();
        units.add(vUnit);
//        return Results.noContent();
        return Results.json().render(units);
//return Results.html().template("views/ApplicationController/unitUpload.ftl.html")
    }

    @Transactional
    public Result addParent(Context context,
            @Param("parentUnitImage") FileItem parentUnitImage,
            @Param("puImageDescription") String parentUnitImageDesc) throws Exception {
        VibandaImage image = saveParentImage(parentUnitImage, parentUnitImageDesc);

        ParentUnit vpu = new ParamsExtrator(context).getParent();
        
        vpu.getParentImages().add(image);

        /*
        Add Pricing info
         */
//        vpu.getRentalUnits().addAll(units);
//        EntityManager entityManager = entitiyManagerProvider.get();
//        entityManager.persist(vpu);
        units.clear();

//        return Results.html().template("views/ApplicationController/index.ftl.html");
        dbService.addParent(vpu);
        return Results.json().render(vpu);

    }

    @UnitOfWork
    public Result listAll(Context context) {

//        EntityManager entityManager = entitiyManagerProvider.get();
//        Query q = entityManager.createQuery("SELECT pu FROM ParentUnit pu");
        List<ParentUnit> vpus = dbService.getAllParents();

        List<String> ids = dbService.getParentIds();
        System.out.println("JSON:" + Results.json().render(vpus));
        return Results.json().render(vpus);

    }

    public Result uploadUnitImage(Context context,
            @Param("unitImageFile") FileItem unitImageFile,
            @Param("imageName") String imageName,
            @Param("imageDescription") String imageDescription) throws Exception {

        VibandaImage img = saveUnitImage(unitImageFile, imageDescription);
        unitImages.add(img);
//        return Results.json().render(img);
        return Results.noContent();
    }
//
//    @Transactional
//    public Result add(Context context, ParentUnit parent) {
//        EntityManager entityManager = entitiyManagerProvider.get();
////        entityManager.persist(parentUnit);
////        return Results.html().template("views/ApplicationController/index.ftl.html").render("vpus", allPUs);
//        return Results.json().render(parent);
//    }

//    @UnitOfWork
//    public ParentUnit findParentUnit(String unitName) {
//
//        EntityManager entityManager = entitiyManagerProvider.get();
//        Query q = entityManager.createQuery("SELECT pu.unitName FROM ParentUnit pu WHERE pu.unitName LIKE '%" + unitName + "%'");
//        //TypedQuery<Item> q = em.createQuery("SELECT i FROM Item i JOIN FETCH i.order", Item.class);
//
//        ParentUnit vpu = (ParentUnit) q.getSingleResult();
//
//        return vpu;
//
//    }
    @UnitOfWork
    public Result findAllUnits() {

        List<Unit> vunits = dbService.getAllUnits();
        System.out.println("JSON:" + vunits);
        return Results.json().render(vunits);

    }

    private VibandaImage saveParentImage(FileItem imageFile, String imageDescription) throws IOException {
        File defDir = new File(System.getProperty("user.dir") + "/src/main/java/assets/img/images");
        File destFile = new File(defDir, imageFile.getFileName());
        FileUtils.copyFile(imageFile.getFile(), destFile);
        VibandaImage img = new VibandaImage();
        img.setImageUrl("assets/img/images/" + imageFile.getFileName());
        img.setImageDescription(imageDescription);
        return img;
    }

    private VibandaImage saveUnitImage(FileItem imageFile, String imageDescription) throws IOException {
        System.out.println("Working Directory = "
                + System.getProperty("user.dir"));
        File defDir = new File(System.getProperty("user.dir") + "/src/main/java/assets/img/images");
        File destFile = new File(defDir, imageFile.getFileName());
        FileUtils.copyFile(imageFile.getFile(), destFile);
        VibandaImage img = new VibandaImage();
        img.setImageUrl("assets/img/images/" + imageFile.getFileName());
        img.setImageDescription(imageDescription);
        return img;
    }

    public Result createParentUnit(Context context) {
        ParentUnit parentUnit = new ParentUnit();
        parentUnit.setLocation(new Location());
        parentUnit.setParentUnitAccessibility(new ParentUnitAccessibility());
        parentUnit.setParentUnitFacilities(new ParentUnitFacilities());
        parentUnit.setRentalUnits(new ArrayList<>());

        VibandaImage img1 = new VibandaImage();

        img1.setImageDescription("Image1 descr");
        img1.setImageUrl("some/image/url/img.jpg");
        img1.setImageId("img1");
        parentUnit.getParentImages().add(img1);
        dbService.addParent(parentUnit);
        return Results.json().render(img1);
//        return Results.html().template("views/ApplicationController/index.ftl.html").render("pu", parentUnit);
    }
//

}
