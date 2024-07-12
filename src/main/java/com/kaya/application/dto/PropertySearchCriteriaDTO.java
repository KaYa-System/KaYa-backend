package com.kaya.application.dto;

import com.kaya.domain.model.enums.PropertyType;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.ws.rs.QueryParam;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Input("PropertySearchCriteria")
@Description("Criteria for searching properties")
public class PropertySearchCriteriaDTO {

    @Description("Keyword to search in property title and description")
    @QueryParam("keyword")
    private String keyword;

    @Description("Types of properties to include in the search")
    @QueryParam("propertyTypes")
    private List<PropertyType> propertyTypes;

    @Description("City where the property is located")
    @QueryParam("city")
    private String city;

    @Description("Country where the property is located")
    @QueryParam("country")
    private String country;

    @Description("Minimum price of the property")
    @QueryParam("minPrice")
    private BigDecimal minPrice;

    @Description("Maximum price of the property")
    @QueryParam("maxPrice")
    private BigDecimal maxPrice;

    @Description("Minimum number of bedrooms")
    @QueryParam("minBedrooms")
    private Integer minBedrooms;

    @Description("Minimum number of bathrooms")
    @QueryParam("minBathrooms")
    private Integer minBathrooms;

    @Description("Minimum area of the property")
    @QueryParam("minArea")
    private Double minArea;

    @Description("Maximum area of the property")
    @QueryParam("maxArea")
    private Double maxArea;

    @Description("List of amenities the property should have")
    @QueryParam("amenities")
    private List<String> amenities;

    @Description("Whether the property should have a virtual tour")
    @QueryParam("hasVirtualTour")
    private Boolean hasVirtualTour;

    @Description("Page number for pagination")
    @QueryParam("page")
    private Integer page;

    @Description("Number of items per page")
    @QueryParam("pageSize")
    private Integer pageSize;

    @Description("Field to sort the results by")
    @QueryParam("sortBy")
    private String sortBy;

    @Description("Direction of sorting (ASC or DESC)")
    @QueryParam("sortDirection")
    private String sortDirection;
}
