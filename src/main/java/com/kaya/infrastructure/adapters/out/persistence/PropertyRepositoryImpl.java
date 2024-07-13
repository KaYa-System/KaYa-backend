package com.kaya.infrastructure.adapters.out.persistence;

import com.kaya.application.port.out.PropertyRepository;
import com.kaya.domain.model.Property;
import com.kaya.application.dto.PropertySearchCriteriaDTO;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PropertyRepositoryImpl implements PropertyRepository, PanacheRepositoryBase<Property, UUID> {

    @Override
    public Uni<Property> findById(UUID id) {
        return findById(id);
    }

    @Override
    public Uni<List<Property>> search(PropertySearchCriteriaDTO criteria) {
        return find(buildQuery(criteria), buildParameters(criteria))
                .page(Page.of(criteria.getPage(), criteria.getPageSize()))
                .list();
    }

    @Override
    public Uni<Property> save(Property property) {
        return persistAndFlush(property);
    }

    @Override
    public Uni<Void> delete(UUID id) {
        return deleteById(id).replaceWithVoid();
    }

    private String buildQuery(PropertySearchCriteriaDTO criteria) {
        StringBuilder query = new StringBuilder("1=1");
        if (criteria.getKeyword() != null) {
            query.append(" and (lower(title) like lower(:keyword) or lower(description) like lower(:keyword))");
        }
        if (criteria.getPropertyTypes() != null && !criteria.getPropertyTypes().isEmpty()) {
            query.append(" and type in (:types)");
        }
        if (criteria.getCity() != null) {
            query.append(" and lower(address.city) = lower(:city)");
        }
        if (criteria.getCountry() != null) {
            query.append(" and lower(address.country) = lower(:country)");
        }
        if (criteria.getMinPrice() != null) {
            query.append(" and price >= :minPrice");
        }
        if (criteria.getMaxPrice() != null) {
            query.append(" and price <= :maxPrice");
        }
        // Add more conditions as needed
        return query.toString();
    }

    private Object[] buildParameters(PropertySearchCriteriaDTO criteria) {
        return new Object[] {
                "keyword", "%" + criteria.getKeyword() + "%",
                "types", criteria.getPropertyTypes(),
                "city", criteria.getCity(),
                "country", criteria.getCountry(),
                "minPrice", criteria.getMinPrice(),
                "maxPrice", criteria.getMaxPrice()
                // Add more parameters as needed
        };
    }
}