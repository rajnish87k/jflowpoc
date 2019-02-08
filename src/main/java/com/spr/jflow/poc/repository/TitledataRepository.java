package com.spr.jflow.poc.repository;

import com.spr.jflow.poc.domain.Titledata;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Titledata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TitledataRepository extends JpaRepository<Titledata, Long> {

}
