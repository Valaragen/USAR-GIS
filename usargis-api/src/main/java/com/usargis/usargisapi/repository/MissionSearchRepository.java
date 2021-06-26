package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.core.search.MissionSearchCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionSearchRepository {
    List<Mission> searchAll(MissionSearchCriteria missionSearchCriteria);
}
