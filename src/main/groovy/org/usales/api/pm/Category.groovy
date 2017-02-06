package org.usales.api.pm

/**
 * Created by steven on 28/12/2016.
 */
class Category {
    Long cid
    String name

    @Override
    String toString() {
        return """Category{cid=$cid, name='$name'}"""
    }
}
