/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.inject.Inject;
import java.util.List;
import javax.inject.Singleton;
import model.ParentUnit;
import net.binggl.ninja.mongodb.MongoDB;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.lifecycle.Dispose;
import ninja.lifecycle.Start;
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
        System.out.println("Finding parent..... "+parentId);
        
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

        return parents;
    }

}
