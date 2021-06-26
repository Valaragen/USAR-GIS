package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.model.UserInfo;
import com.usargis.usargisapi.core.search.MissionSearchCriteria;
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
public class MissionSearchRepositoryImpl implements MissionSearchRepository {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    @Autowired
    public MissionSearchRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Mission> searchAll(MissionSearchCriteria missionSearchCriteria) {
        CriteriaQuery<Mission> criteriaQuery = criteriaBuilder.createQuery(Mission.class);
        Root<Mission> missionRoot = criteriaQuery.from(Mission.class);

        Path<UserInfo> userInfoPath = missionRoot.get("author");

        //Filter
        Predicate predicate = getPredicate(missionSearchCriteria, missionRoot, userInfoPath);

        criteriaQuery.select(missionRoot)
                .where(predicate);

        //Order
        setOrder(missionSearchCriteria, criteriaQuery, missionRoot, userInfoPath);

        //Pagination
        TypedQuery<Mission> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(missionSearchCriteria.getPageNo() * missionSearchCriteria.getPageSize());
        typedQuery.setMaxResults(missionSearchCriteria.getPageSize());

        Pageable pageable = getPageable(missionSearchCriteria);

        long missionCount = getAvailabilityCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, missionCount).toList();
    }

    private Predicate getPredicate(MissionSearchCriteria missionSearchCriteria, Root<Mission> missionRoot,
                                   Path<UserInfo> userInfoPath) {
        List<Predicate> andPredicates = new ArrayList<>();

        //Name
        if (Objects.nonNull(missionSearchCriteria.getName())) {
            andPredicates.add(criteriaBuilder.like(missionRoot.get(Constant.NAME), missionSearchCriteria.getName()));
        } else {
            andPredicates.add(criteriaBuilder.like(missionRoot.get(Constant.NAME), "%"));
        }

        //Id
        if (Objects.nonNull(missionSearchCriteria.getMissionId())) {
            andPredicates.add(criteriaBuilder.equal(missionRoot.get(Constant.ID), missionSearchCriteria.getMissionId()));
        }

        //Status
        if (Objects.nonNull(missionSearchCriteria.getMissionStatus())) {
            andPredicates.add(criteriaBuilder.equal(missionRoot.get(Constant.STATUS), missionSearchCriteria.getMissionStatus()));
        }

        //Description
        if (Objects.nonNull(missionSearchCriteria.getDescription())) {
            andPredicates.add(criteriaBuilder.like(missionRoot.get(Constant.DESCRIPTION), missionSearchCriteria.getDescription()));
        }

        //After Date
        if (Objects.nonNull(missionSearchCriteria.getAfterDate())) {
            andPredicates.add(criteriaBuilder.greaterThanOrEqualTo(missionRoot.get(Constant.CAMEL_START_DATE), missionSearchCriteria.getAfterDate().minusSeconds(1L)));
        }

        //Before Date
        if (Objects.nonNull(missionSearchCriteria.getBeforeDate())) {
            andPredicates.add(criteriaBuilder.lessThanOrEqualTo(missionRoot.get(Constant.CAMEL_START_DATE), missionSearchCriteria.getBeforeDate()));
        }

        //Address
        if (Objects.nonNull(missionSearchCriteria.getAddress())) {
            andPredicates.add(criteriaBuilder.like(missionRoot.get(Constant.ADDRESS), missionSearchCriteria.getAddress()));
        }

        //author username
        if (Objects.nonNull(missionSearchCriteria.getAuthorUsername())) {
            andPredicates.add(criteriaBuilder.like(userInfoPath.get(Constant.USERNAME), missionSearchCriteria.getAuthorUsername()));
        }

        return criteriaBuilder.and(andPredicates.toArray(new Predicate[0]));
    }

    private void setOrder(MissionSearchCriteria missionSearchCriteria, CriteriaQuery<Mission> criteriaQuery,
                          Root<Mission> missionRoot, Path<UserInfo> userInfoPath) {
        Path targetPath;
        switch (missionSearchCriteria.getSortBy().getTableName()) {
            case Constant.CR_ROOT_MISSION:
                targetPath = missionRoot;
                break;
            case Constant.CR_ROOT_USER:
                targetPath = userInfoPath;
                break;
            default:
                log.error("The criteria Sort by value doesn't match any existing table names, mission has been selected by default");
                targetPath = missionRoot;
        }
        if (Objects.nonNull(missionSearchCriteria.getOrder()) && missionSearchCriteria.getOrder().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(targetPath.get(missionSearchCriteria.getSortBy().getName())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(targetPath.get(missionSearchCriteria.getSortBy().getName())));
        }
    }

    private Pageable getPageable(MissionSearchCriteria missionSearchCriteria) {
        Sort sort = Sort.by(missionSearchCriteria.getOrder(), missionSearchCriteria.getSortBy().getName());
        return PageRequest.of(missionSearchCriteria.getPageNo(), missionSearchCriteria.getPageSize(), sort);
    }

    private long getAvailabilityCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Mission> countRoot = countQuery.from(Mission.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
