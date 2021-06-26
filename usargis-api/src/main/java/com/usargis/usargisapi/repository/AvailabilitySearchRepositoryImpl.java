package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.core.search.AvailabilitySearchCriteria;
import com.usargis.usargisapi.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class AvailabilitySearchRepositoryImpl implements AvailabilitySearchRepository {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    @Autowired
    public AvailabilitySearchRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Availability> searchAll(AvailabilitySearchCriteria availabilitySearchCriteria) {
        CriteriaQuery<Availability> criteriaQuery = criteriaBuilder.createQuery(Availability.class);
        Root<Availability> availabilityRoot = criteriaQuery.from(Availability.class);

        Path<UserInfo> userInfoPath = availabilityRoot.get("userInfo");
        Path<Mission> missionPath = availabilityRoot.get("mission");

        Predicate predicate = getPredicate(availabilitySearchCriteria, userInfoPath, missionPath);

        criteriaQuery.select(availabilityRoot)
                .where(predicate);

        //Order
        setOrder(availabilitySearchCriteria, criteriaQuery, availabilityRoot, userInfoPath, missionPath);

        //Pagination
        TypedQuery<Availability> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(availabilitySearchCriteria.getPageNo() * availabilitySearchCriteria.getPageSize());
        typedQuery.setMaxResults(availabilitySearchCriteria.getPageSize());

        Pageable pageable = getPageable(availabilitySearchCriteria);

        long availabilityCount = getAvailabilityCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, availabilityCount).toList();
    }

    private Predicate getPredicate(AvailabilitySearchCriteria availabilitySearchCriteria, Path<UserInfo> userInfoPath,
                                   Path<Mission> missionPath) {
        List<Predicate> andPredicates = new ArrayList<>();

        if (Objects.nonNull(availabilitySearchCriteria.getUserUsername())) {
            andPredicates.add(criteriaBuilder.like(userInfoPath.get("username"), availabilitySearchCriteria.getUserUsername()));
        } else {
            andPredicates.add(criteriaBuilder.like(userInfoPath.get("username"), "%"));
        }

        if (Objects.nonNull(availabilitySearchCriteria.getMissionId())) {
            andPredicates.add(criteriaBuilder.equal(missionPath.get("id"), availabilitySearchCriteria.getMissionId()));
        }
        return criteriaBuilder.and(andPredicates.toArray(new Predicate[0]));
    }

    private void setOrder(AvailabilitySearchCriteria availabilitySearchCriteria, CriteriaQuery<Availability> criteriaQuery,
                          Root<Availability> availabilityRoot, Path<UserInfo> userInfoPath, Path<Mission> missionPath) {
        Path targetPath;
        switch (availabilitySearchCriteria.getSortBy().getTableName()) {
            case Constant.CR_ROOT_AVAILABILITY:
                targetPath = availabilityRoot;
                break;
            case Constant.CR_ROOT_USER:
                targetPath = userInfoPath;
                break;
            case Constant.CR_ROOT_MISSION:
                targetPath = missionPath;
                break;
            default:
                log.error("The criteria Sort by value doesn't match any existing table names, availability has been selected by default");
                targetPath = availabilityRoot;
        }
        if (Objects.nonNull(availabilitySearchCriteria.getOrder()) && availabilitySearchCriteria.getOrder().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(targetPath.get(availabilitySearchCriteria.getSortBy().getName())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(targetPath.get(availabilitySearchCriteria.getSortBy().getName())));
        }
    }

    private Pageable getPageable(AvailabilitySearchCriteria availabilitySearchCriteria) {
        Sort sort = Sort.by(availabilitySearchCriteria.getOrder(), availabilitySearchCriteria.getSortBy().getName());
        return PageRequest.of(availabilitySearchCriteria.getPageNo(), availabilitySearchCriteria.getPageSize(), sort);
    }

    private long getAvailabilityCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Availability> countRoot = countQuery.from(Availability.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
