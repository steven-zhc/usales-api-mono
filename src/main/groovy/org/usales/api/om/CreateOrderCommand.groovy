package org.usales.api.om

/**
 * Created by steven on 01/01/2017.
 */
class CreateOrderCommand {
    String note

    List<CreateOrderLineCommand> lines
}

class CreateOrderLineCommand {
    Long pid
    Integer q
    String note
    String model

    CreateLineBobyCommand purchase
    CreateLineBobyCommand sell
}

class CreateLineBobyCommand {
    Float p
    Float t
    Float s
    Float d
}