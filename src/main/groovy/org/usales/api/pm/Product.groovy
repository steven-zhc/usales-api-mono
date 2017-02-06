package org.usales.api.pm

/**
 * Created by steven on 31/12/2016.
 */
class Product {

    Long pid
    String name
    String desc
    Float price = 0
    String url

    Long cid
    Long cname


    @Override
    String toString() {
        return """Product{pid=$pid, name='$name', desc='$desc', price=$price, url='$url', cid=$cid, cname=$cname}"""
    }
}
