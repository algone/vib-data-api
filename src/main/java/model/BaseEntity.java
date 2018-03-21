/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author A4372949
 */
@Entity
abstract public class BaseEntity {
    @Id
    private String id = new ObjectId().toHexString();

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
}
