package com.kaya.infrastructure.adapters.out.persistence;

import com.kaya.application.port.out.PropertyRepository;
import com.kaya.domain.model.Property;
import com.kaya.application.dto.PropertySearchCriteriaDTO;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PropertyRepositoryImpl implements PropertyRepository,  PanacheRepositoryBase<Property, UUID> {

    @Override
    public Uni<Property> findById(UUID id) {
        return PanacheRepositoryBase.super.findById(id);
    }

    @Override
    public Uni<List<Property>> search(PropertySearchCriteriaDTO criteria) {
        String query = buildSearchQuery(criteria);
        Parameters parameters = buildSearchParameters(criteria);
        return find(query, parameters).list();
    }

    @Override
    public Uni<Property> save(Property property) {
        return persistAndFlush(property);
    }

    @Override
    public Uni<Void> delete(UUID id) {
        return deleteById(id).replaceWithVoid();
    }

    private String buildSearchQuery(PropertySearchCriteriaDTO criteria) {
        // Implement query building logic based on criteria
        // This is a simplified example
        StringBuilder queryBuilder = new StringBuilder("from Property where 1=1");
        if (criteria.getPropertyTypes() != null && !criteria.getPropertyTypes().isEmpty()) {
            queryBuilder.append(" and type in :types");
        }
        if (criteria.getMinPrice() != null) {
            queryBuilder.append(" and price >= :minPrice");
        }
        if (criteria.getMaxPrice() != null) {
            queryBuilder.append(" and price <= :maxPrice");
        }
        // Add more conditions as needed
        return queryBuilder.toString();
    }

    private Parameters buildSearchParameters(PropertySearchCriteriaDTO criteria) {
        Parameters parameters = new Parameters();
        if (criteria.getPropertyTypes() != null && !criteria.getPropertyTypes().isEmpty()) {
            parameters.and("types", criteria.getPropertyTypes());
        }
        if (criteria.getMinPrice() != null) {
            parameters.and("minPrice", criteria.getMinPrice());
        }
        if (criteria.getMaxPrice() != null) {
            parameters.and("maxPrice", criteria.getMaxPrice());
        }
        // Add more parameters as needed
        return parameters;
    }
}