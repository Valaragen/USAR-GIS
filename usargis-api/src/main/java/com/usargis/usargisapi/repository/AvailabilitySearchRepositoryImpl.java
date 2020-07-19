package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.core.search.AvailabilitySearch;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class AvailabilitySearchRepositoryImpl implements AvailabilitySearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Availability> searchAll(AvailabilitySearch availabilitySearch) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Availability> query = cb.createQuery(Availability.class);
        Root<Availability> availabilityRoot = query.from(Availability.class);

        List<Predicate> andPredicates = new ArrayList<>();

        Path<UserInfo> userInfoPath = availabilityRoot.get("userInfo");
        Path<Mission> missionPath = availabilityRoot.get("mission");

        if (availabilitySearch.getUserId() != null) {
            andPredicates.add(cb.like(userInfoPath.get("id"), availabilitySearch.getUserId()));
        } else {
            andPredicates.add(cb.like(userInfoPath.get("id"), "%"));
        }

        if (availabilitySearch.getMissionId() != null) {
            andPredicates.add(cb.equal(missionPath.get("id"), availabilitySearch.getMissionId()));
        }

        query.select(availabilityRoot)
                .where(cb.and(andPredicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
