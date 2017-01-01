package org.usales.api.om

import groovy.sql.Sql

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

}
