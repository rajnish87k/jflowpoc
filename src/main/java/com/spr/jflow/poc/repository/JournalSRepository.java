package com.spr.jflow.poc.repository;

import com.spr.jflow.poc.domain.JournalS;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JournalS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JournalSRepository extends JpaRepository<JournalS, Long> {

}
