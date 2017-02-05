package org.usales.api.om

import com.fasterxml.jackson.annotation.JsonFormat

/**
 * Created by steven on 04/02/2017.
 */
class UpdateOrderCommand {
    Integer status

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date deliverDate
    Float deliverFee

    String trackingNo
    Float payment
    String note
}

class UpdateOrderLineCommand {

    Integer q
    String note
    String model

    UpdateLineBodyCommand purchase
    UpdateLineBodyCommand sell

}

class UpdateLineBodyCommand {
    Float p
    Float t
    Float s
    Float d
}