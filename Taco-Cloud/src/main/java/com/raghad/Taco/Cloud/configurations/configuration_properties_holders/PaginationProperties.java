package com.raghad.Taco.Cloud.configurations.configuration_properties_holders;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Component
@ConfigurationProperties(prefix = "page-request")
@Data
public class PaginationProperties {
    private int pageNumber = 0;

    @Max(value = 25, message = "page size must be smaller than 25")
    @Min(value = 5, message = "page size must be greater than 5")
    private int pageSize = 15;
}
