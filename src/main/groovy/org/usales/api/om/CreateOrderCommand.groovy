package org.usales.api.om

/**
 * Created by steven on 01/01/2017.
 */
class CreateOrderCommand {
    Float deliverFee
    Date deliverDate
    String trackingNo
    Float payment
    String note

    List<CreateOrderLineCommand> lines
}

class CreateOrderLineCommand {
    Long pid
    Integer q
    String note
    Float total
    Float profit

    CreateLineBobyCommand purchase
    CreateLineBobyCommand sell
}

class CreateLineBobyCommand {

    Float p
    Float t
    Float s
    Float d
    Float total
}