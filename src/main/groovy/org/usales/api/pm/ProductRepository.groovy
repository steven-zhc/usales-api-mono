package org.usales.api.pm

import groovy.sql.Sql
import ratpack.exec.Blocking
import ratpack.exec.Promise
import ratpack.func.Block

import javax.inject.Inject

/**
 * Created by steven on 31/12/2016.
 */
class ProductRepository {

    private final Sql sql

    @Inject
    ProductRepository(Sql sql) {
        this.sql = sql
    }

    Promise<List<Product>> get(Long pid) {
        def s = "select p.*, c.name cname from product p, category c where p.category_id = c.id and p.id = $pid"

        Blocking.get {
            sql.rows(s).collect {
                def id = (long) it["id"]
                def c_id = (long) it["category_id"]
                def c_name = it["cname"]
                def desc = it["description"]
                def price = it["list_price"]
                def n = it["name"]
                def url = it["url"]

                new Product(pid: id, name: n, cid: c_id,
                        cname: c_name, desc: desc, price: price, url: url)
            }
        }
    }

    Promise<List<Product>> find(String name, Long cid) {
        def s = "select p.*, c.name cname from product p, category c where p.category_id = c.id and "
        if (name) {
            def n = "%$name%"
            s = s + " name like $n and "
        }
        if (cid) {
            s + s + " cid = $cid and "
        }

        Blocking.get {
            sql.rows(s[0..-5]).collect {
                def id = (long) it["id"]
                def c_id = (long) it["category_id"]
                def c_name = it["cname"]
                def desc = it["description"]
                def price = it["list_price"]
                def n = it["name"]
                def url = it["url"]

                new Product(pid: id, name: n, cid: c_id,
                        cname: c_name, desc: desc, price: price, url: url)
            }
        }
    }

    Promise<Product> create(Product product) {
        Blocking.get {
            sql.executeInsert "insert int product(version, category_id, description, list_price, url) " +
                    "values (0, $product.cid, $product.desc, $product.price, $product.url)", ['id']
        }.map {
            product.pid = it[0][0] as long
            product
        }
    }

    Promise<Product> save(Product product) {
        Blocking.get {
            sql.executeUpdate "update product set name=$product.name, category_id=$product.cid, " +
                    "description=$product.desc, list_price=$product.price, url=$product.url " +
                    "where id=$product.pid"
        }.then {
            product
        }
    }



}
