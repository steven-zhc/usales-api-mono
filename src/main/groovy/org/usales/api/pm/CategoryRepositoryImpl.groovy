package org.usales.api.pm

import groovy.sql.Sql
import ratpack.exec.Blocking
import ratpack.exec.Promise

import javax.inject.Inject

/**
 * Created by steven on 28/12/2016.
 */
class CategoryRepositoryImpl implements CategoryRepository {

    private final Sql sql

    @Inject
    CategoryRepositoryImpl(Sql sql) {
        this.sql = sql
    }

    @Override
    Promise<Category> create(Category category) {
        return Promise.value(new Category(cid: 1, name: "toy"))
    }

    @Override
    Promise<Category> save(Category category) {
        return Promise.value(new Category(cid: 1, name: "toy"))
    }

    @Override
    Promise<Category> find(String cid) {
        return Promise.value(new Category(cid: 1, name: "toy"))
    }

    @Override
    Promise<List<Category>> findByName(String name) {
        List list = [
                new Category(cid: 1, name: "toy"),
                new Category(cid: 2, name: "beauty")
        ]

        return Promise.value(list)
    }

    @Override
    Promise<List<Category>> all() {
        Blocking.get {
            sql.rows("select * from category").collect {
                def id = (long) it["id"]
                def name = it["name"]
                new Category(cid: id, name: name)
            }
        }
    }
}
