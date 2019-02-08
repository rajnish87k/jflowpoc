package com.spr.jflow.poc.repository;

import com.spr.jflow.poc.domain.Gscontent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gscontent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GscontentRepository extends JpaRepository<Gscontent, Long> {

}
