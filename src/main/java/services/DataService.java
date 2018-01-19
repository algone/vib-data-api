/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.inject.Inject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Singleton;
import model.Location;
import model.ParentUnit;
import model.Unit;
import model.VibandaImage;
import net.binggl.ninja.mongodb.MongoDB;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.lifecycle.Dispose;
import ninja.lifecycle.Start;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author algone
 */
@Singleton
public class DataService implements Service {
    
    @Inject
    private MongoDB mongoDB;
    
    @Start(order = 90)
    public void startService() {
        //do something     
        System.out.println("Data service started....");
    }
    
    @Dispose(order = 90)
    public void stopService() {
        //do something
        System.out.println("Stoping data service...");
    }
    
    public Result getCount(Context ctx) {
        return Results.json();
    }
    
    @Override
    public void addParent(ParentUnit vpu) {
        Morphia morphia = this.mongoDB.getMorphia();
        Datastore ds = morphia.createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        ds.save(vpu);
    }
    
    @Override
    public void updateParent(ParentUnit parent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void deleteParent(long parentId) {
        Morphia morphia = this.mongoDB.getMorphia();
        morphia.mapPackage("model");
        Datastore ds = morphia.createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        final Query<ParentUnit> parentToDel = ds.createQuery(ParentUnit.class).filter("parentId =", parentId);
        ds.delete(parentToDel);
        
    }

//    @Override
//    public ParentUnit findParent(long parentId) {
//        Morphia morphia = this.mongoDB.getMorphia();
//        morphia.mapPackage("model");
//        Datastore ds = morphia.createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
//        final Query<ParentUnit> parent = ds.createQuery(ParentUnit.class)
//                .field("parentId").equal(parentId);
//
////        Document document = myCollection.find(eq("_id", new ObjectId("4f693d40e4b04cde19f17205"))).first();
////        if (document == null) {
////            //Document does not exist
////        } else {
////            //We found the document
////        }
//        System.out.println("Finding parent..... " + parentId);
//
//        return parent.get();
//    }
    public List<String> getParentIds() {
        Morphia morphia = this.mongoDB.getMorphia();
        morphia.mapPackage("model");
        Datastore ds = morphia.createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        final Query<ParentUnit> query = ds.createQuery(ParentUnit.class);
        List<ParentUnit> parents = query.asList();
        List<String> parentIds = new ArrayList<>();
        parents.forEach((parent) -> {
            parentIds.add(parent.getId());
        });
        return parentIds;
    }
    
    public List<String> getUnitIds() {
        List<Unit> units = getAllUnits();
        List<String> unitIds = new ArrayList<>();
        units.forEach((parent) -> {
            unitIds.add(parent.getId());
        });
        return unitIds;
    }
    
    @Override
    public void saveImage(VibandaImage img) {
        System.out.println("Persisting image to mongolab....");
        Morphia morphia = this.mongoDB.getMorphia();
        Datastore ds = morphia.createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        ds.save(img);
    }
    
    @Override
    public List<ParentUnit> getAllParents() {
        Morphia morphia = this.mongoDB.getMorphia();
        morphia.mapPackage("model");
        Datastore ds = morphia.createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        final Query<ParentUnit> query = ds.createQuery(ParentUnit.class);
        getParentIds();
        List<ParentUnit> parents = query.asList();
        return parents;
    }
    
    public ObjectId upload(String filePath, String fileName) {
        System.out.println("Calling upload...");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        ObjectId fileId = null;
        try {
            MongoDatabase db = this.mongoDB.getMongoClient().getDatabase("mongolab-amazon-vibanda");
            GridFSBucket gridBucket = GridFSBuckets.create(db, "vibanda_imgdb");
            InputStream inputStream = new FileInputStream(new File(filePath));
            // Create some custom options
            GridFSUploadOptions uploadOptions = new GridFSUploadOptions()
                    .chunkSizeBytes(1024)
                    .metadata(new Document("type", "image")
                            .append("upload_date", format.parse("2016-09-01T00:00:00Z"))
                            .append("content_type", "image/jpg"));
            
            fileId = gridBucket.uploadFromStream(fileName, inputStream, uploadOptions);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            mongoClient.close();
        }
        return fileId;
    }

    // Download File
    public void download(String fileName) {
        System.out.println("Calling download...");
        
        try {
            MongoDatabase db = this.mongoDB.getMongoClient().getDatabase("mongolab-amazon-vibanda");
//            db.createCollection("vibanda_imgdb");
            GridFSBucket gridBucket = GridFSBuckets.create(db, "vibanda_imgdb");
            
            FileOutputStream fileOutputStream = new FileOutputStream("download-baby-image.jpg");
            gridBucket.downloadToStream(fileName, fileOutputStream);
            fileOutputStream.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//   mongoClient.close();
        }
    }
    
    @Override
    public List<Unit> getAllUnits() {
        Morphia morphia = this.mongoDB.getMorphia();
        Datastore ds = morphia.mapPackage("model").createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<Unit> query = ds.createQuery(Unit.class);
        return query.asList();
    }
    
    @Override
    public void addUnit(Unit unit) {
        Morphia morphia = this.mongoDB.getMorphia();
        Datastore ds = morphia.createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        String parentId = unit.getUnitParentId();
        ParentUnit parent = findParent(parentId);
        unit.setLocation(parent.getLocation());
        unit.setEcorated(parent.isEcorated());
        ds.save(unit);
    }
    
    @Override
    public void updateUnit(Unit unit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void deleteUnit(String unitId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ParentUnit findParent(String parentId) {
        Morphia morphia = this.mongoDB.getMorphia();
        Datastore ds = morphia.mapPackage("model").createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<ParentUnit> query = ds.createQuery(ParentUnit.class);
        Query<ParentUnit> result = query.field("id").equal(parentId);
        return result.get();
    }
    
    @Override
    public Unit findUnit(String unitId) {
        Morphia morphia = this.mongoDB.getMorphia();
        Datastore ds = morphia.mapPackage("model").createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<Unit> query = ds.createQuery(Unit.class);
        Query<Unit> result = query.field("id").equal(unitId);
        return result.get();
    }
    
    @Override
    public List<VibandaImage> findUnitImages(String unitId) {
        Morphia morphia = this.mongoDB.getMorphia();
        Datastore ds = morphia.mapPackage("model").createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<VibandaImage> query = ds.createQuery(VibandaImage.class);
        Query<VibandaImage> result = query.field("unitId").equal(unitId);
        return result.asList();
    }
    
    @Override
    public List<Unit> findUnitsByParentId(String parentId) {
        Morphia morphia = this.mongoDB.getMorphia();
        
        Datastore ds = morphia.mapPackage("model").createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        Query<Unit> query = ds.createQuery(Unit.class);
        Query<Unit> result = query.field("unitParentId").equal(parentId);
        return result.asList();
    }
}
