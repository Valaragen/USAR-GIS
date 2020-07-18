package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.core.model.embeddable.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {
}
