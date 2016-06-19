package com.allo.simpletodo.model;

import com.allo.simpletodo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ALLO on 19/6/16.
 */
public enum Priority {

    LOW(0),
    NORMAL(1),
    HIGH(2);

    private int priority;

    public String toString() {
        if (this == LOW) {
            return "Low";
        } else if (this == NORMAL) {
            return "Normal";
        } else {
            return "High";
        }
    }

    public int getBackgroundColorResource() {
        if (this == LOW) {
            return R.color.priority_low;
        } else if (this == NORMAL) {
            return R.color.priority_normal;
        } else {
            return R.color.high_priority;
        }
    }

    private static Map<Integer, Priority> map = new HashMap<Integer, Priority>();

    static {
        for (Priority priority : Priority.values()) {
            map.put(priority.priority, priority);
        }
    }

    private Priority(final int priority) {
        this.priority = priority;
    }

    public static Priority valueOf(int priority) {
        return map.get(priority);
    }
}
