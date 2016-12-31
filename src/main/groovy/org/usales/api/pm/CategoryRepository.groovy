package org.usales.api.pm

import ratpack.exec.Promise

/**
 * Created by steven on 28/12/2016.
 */
interface CategoryRepository {

    Promise<Category> create(Category category)

    Promise<Category> save(Category category)

    Promise<Category> find(Long cid)

    Promise<List<Category>> findByName(String name)

    Promise<List<Category>> all()

}
