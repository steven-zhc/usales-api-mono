package org.usales.api.om

/**
 * Created by steven on 01/01/2017.
 */
class Order {
    Long oid
    Integer status

    Float deliverFee
    Date deliverDate
    String trackingNo
    Float payment
    String note

    List<OrderLine> lines
}

class OrderLine {
    Long lid

    Long pid
    Integer q
    String note
    Float total
    Float profit

    LineBody purchase
    LineBody sell
}

class LineBody {
    Float p
    Float t
    Float s
    Float d
    Float total
}