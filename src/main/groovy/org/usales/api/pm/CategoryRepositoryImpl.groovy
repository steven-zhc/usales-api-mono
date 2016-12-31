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
        Blocking.get {
            sql.executeInsert("insert into category(name, version) values ($category.name, 0)", ['id'])
        }.map {
            category.cid = it[0][0] as long
            category
        }
    }

    @Override
    Promise<Category> save(Category category) {
        Blocking.get {
            sql.executeUpdate("update category set name=$category.name where id=$category.id")
        }.then {
            category
        }
    }

    @Override
    Promise<Category> find(Long cid) {
        Blocking.get {
            sql.firstRow("select * from category where id = $cid")
        }.then {
            def id = (long) it["id"]
            def n = it["name"]
            new Category(cid: id, name: n)
        }
    }

    @Override
    Promise<List<Category>> findByName(String name) {
        Blocking.get {
            String str = "%$name%"
            sql.rows("select * from category where name like $str").collect {
                def id = (long) it["id"]
                def n = it["name"]
                new Category(cid: id, name: n)
            }
        }
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
