package com.allo.simpletodo.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ALLO on 19/6/16.
 */
@Table(name = "Item")
public class Item extends Model implements Serializable {

    @Column(name = "description")
    public String description;

    @Column(name = "priority")
    public int priority;

    @Column(name = "dueDate")
    public Date dueDate;

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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
