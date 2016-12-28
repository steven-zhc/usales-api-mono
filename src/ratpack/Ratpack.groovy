import org.usales.api.om.OrderEndpoint
import org.usales.api.pm.CategoryEndpoint
import org.usales.api.pm.ProductEndpoint

import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {

        prefix("category", new CategoryEndpoint())

        prefix("product", new ProductEndpoint())

        prefix("order", new OrderEndpoint())
    }
}