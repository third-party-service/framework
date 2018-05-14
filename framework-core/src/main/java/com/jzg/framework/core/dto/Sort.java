package com.jzg.framework.core.dto;

import java.io.Serializable;
import java.util.*;

/**
 * 排序列表信息
 */
public class Sort implements Serializable {
    private static final long serialVersionUID = 1611399015878913184L;
    private List<Order> orders;

    /**
     * 升降序
     */
    public static enum Direction {
        ASC, DESC;

        private Direction() {
        }

        public static Direction fromString(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", new Object[]{value}), e);
            }
        }
    }

    /**
     * 排序字段信息
     */
    public static class Order {
        private Sort.Direction direction;
        private String propertyName;

        public Order(String propertyName, Sort.Direction direction) {
            this.direction = direction;
            this.propertyName = propertyName;
        }

        public Sort.Direction getDirection() {
            return this.direction;
        }

        public void setDirection(Sort.Direction direction) {
            this.direction = direction;
        }

        public String getPropertyName() {
            return this.propertyName;
        }

        public boolean isAscending() {
            return this.direction.equals(Sort.Direction.ASC);
        }

        public String toString() {
            return this.propertyName + ' ' + (this.direction == Sort.Direction.ASC ? "asc" : "desc");
        }
    }

    public Sort(Order... orders) {
        this.orders = Arrays.asList(orders);
    }

    public Sort(String... props) {
        this(Direction.ASC, props);
    }

    public Sort(Direction direction, String... props) {
        this.orders = new ArrayList();
        for (String p : props) {
            this.orders.add(new Order(p, direction));
        }
    }

    public Order getOrderFor(String prop) {
        for (Order o : this.orders) {
            if (o.getPropertyName().equalsIgnoreCase(prop)) {
                return o;
            }
        }
        return null;
    }

    public Iterator<Order> iterator() {
        return this.orders.iterator();
    }
}