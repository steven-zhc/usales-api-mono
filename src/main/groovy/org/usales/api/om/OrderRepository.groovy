package org.usales.api.om

import groovy.sql.Sql
import ratpack.exec.Promise

import javax.inject.Inject

/**
 * Created by steven on 01/01/2017.
 */
class OrderRepository {
    private final Sql sql

    @Inject
    OrderRepository(Sql sql) {
        this.sql = sql
    }

    Promise<List<Order>> get(Long id) {

    }

    Promise<List<Order>> find(String name, Integer status) {

    }

    Promise<Order> create(Order order) {

    }

    Promise<Order> save(Order order) {

    }

    Promise<OrderLine> getLine(Long id) {

    }

    Promise<OrderLine> add(Order order, OrderLine) {

    }

}
