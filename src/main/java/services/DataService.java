/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import static com.mongodb.client.model.Filters.eq;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.inject.Singleton;
import model.ParentUnit;
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
import org.mongodb.morphia.query.FindOptions;
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
        morphia.mapPackage("model");
        Datastore ds = this.mongoDB.getDatastore();

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

    @Override
    public ParentUnit findParent(long parentId) {
        Morphia morphia = this.mongoDB.getMorphia();
        morphia.mapPackage("model");
        Datastore ds = morphia.createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        final Query<ParentUnit> parent = ds.createQuery(ParentUnit.class)
                .field("parentId").equal(parentId);

//        Document document = myCollection.find(eq("_id", new ObjectId("4f693d40e4b04cde19f17205"))).first();
//        if (document == null) {
//            //Document does not exist
//        } else {
//            //We found the document
//        }
        System.out.println("Finding parent..... " + parentId);

        return parent.get();
    }

    @Override
    public List<ParentUnit> getAllParents() {
        Morphia morphia = this.mongoDB.getMorphia();
        morphia.mapPackage("model");
        Datastore ds = morphia.createDatastore(this.mongoDB.getMongoClient(), "mongolab-amazon-vibanda");
        final Query<ParentUnit> query = ds.createQuery(ParentUnit.class);
        FindOptions opts = new FindOptions();

        List<ParentUnit> parents = query.asList(opts);

        GridFSBucket gridBucket = GridFSBuckets.create(this.mongoDB.getMongoClient().getDatabase("mongolab-amazon-vibanda"));
        return parents;
    }

    public ObjectId upload(String filePath, String fileName) {
        System.out.println("Calling upload...");
        MongoClient mongoClient = this.mongoDB.getMongoClient();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        ObjectId fileId = null;
        try {
            MongoDatabase db = this.mongoDB.getMongoClient().getDatabase("mongolab-amazon-vibanda");
//            db.createCollection("vibanda_imgdb");
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
}
