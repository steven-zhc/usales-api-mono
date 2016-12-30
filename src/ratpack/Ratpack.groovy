import org.usales.api.common.CommonModule
import org.usales.api.om.OrderEndpoint
import org.usales.api.pm.CategoryEndpoint
import org.usales.api.pm.ProductEndpoint
import org.usales.api.pm.ProductModule

import static ratpack.groovy.Groovy.chain
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module(CommonModule)
        module(ProductModule)
    }

    handlers { CategoryEndpoint categoryEndpoint,
               ProductEndpoint productEndpoint ->

        prefix("category", categoryEndpoint)

        prefix("product", productEndpoint)

        prefix("order", new OrderEndpoint())
    }
}