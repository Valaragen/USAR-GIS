package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.model.Availability;
import com.usargis.usargisapi.model.Mission;
import com.usargis.usargisapi.model.UserInfo;
import com.usargis.usargisapi.search.AvailabilitySearch;

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
        Root<Availability> availabilityRoot =  query.from(Availability.class);

        List<Predicate> andPredicates = new ArrayList<>();

        Path<UserInfo> userInfoPath = availabilityRoot.get("userInfo");
        Path<Mission> missionPath = availabilityRoot.get("mission");

        if (availabilitySearch.getUserUUID() != null) {
            andPredicates.add(cb.like(userInfoPath.get("UUID"), availabilitySearch.getUserUUID()));
        } else {
            andPredicates.add(cb.like(userInfoPath.get("UUID"), "%"));
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
