package com.allo.simpletodo.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by ALLO on 19/6/16.
 */
@Table(name = "Item")
public class Item extends Model implements Serializable {

    @Column(name = "description")
    public String description;

    @Column(name = "priority")
    public int priority;

    public Item() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
