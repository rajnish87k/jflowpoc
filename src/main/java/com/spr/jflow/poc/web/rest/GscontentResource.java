package com.spr.jflow.poc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spr.jflow.poc.domain.Gscontent;
import com.spr.jflow.poc.repository.GscontentRepository;
import com.spr.jflow.poc.web.rest.errors.BadRequestAlertException;
import com.spr.jflow.poc.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Gscontent.
 */
@RestController
@RequestMapping("/api")
public class GscontentResource {

    private final Logger log = LoggerFactory.getLogger(GscontentResource.class);

    private static final String ENTITY_NAME = "gscontent";

    private final GscontentRepository gscontentRepository;

    public GscontentResource(GscontentRepository gscontentRepository) {
        this.gscontentRepository = gscontentRepository;
    }

    /**
     * POST  /gscontents : Create a new gscontent.
     *
     * @param gscontent the gscontent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gscontent, or with status 400 (Bad Request) if the gscontent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gscontents")
    @Timed
    public ResponseEntity<Gscontent> createGscontent(@Valid @RequestBody Gscontent gscontent) throws URISyntaxException {
        log.debug("REST request to save Gscontent : {}", gscontent);
        if (gscontent.getId() != null) {
            throw new BadRequestAlertException("A new gscontent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gscontent result = gscontentRepository.save(gscontent);
        return ResponseEntity.created(new URI("/api/gscontents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gscontents : Updates an existing gscontent.
     *
     * @param gscontent the gscontent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gscontent,
     * or with status 400 (Bad Request) if the gscontent is not valid,
     * or with status 500 (Internal Server Error) if the gscontent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gscontents")
    @Timed
    public ResponseEntity<Gscontent> updateGscontent(@Valid @RequestBody Gscontent gscontent) throws URISyntaxException {
        log.debug("REST request to update Gscontent : {}", gscontent);
        if (gscontent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Gscontent result = gscontentRepository.save(gscontent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gscontent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gscontents : get all the gscontents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gscontents in body
     */
    @GetMapping("/gscontents")
    @Timed
    public List<Gscontent> getAllGscontents() {
        log.debug("REST request to get all Gscontents");
        return gscontentRepository.findAll();
    }

    /**
     * GET  /gscontents/:id : get the "id" gscontent.
     *
     * @param id the id of the gscontent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gscontent, or with status 404 (Not Found)
     */
    @GetMapping("/gscontents/{id}")
    @Timed
    public ResponseEntity<Gscontent> getGscontent(@PathVariable Long id) {
        log.debug("REST request to get Gscontent : {}", id);
        Optional<Gscontent> gscontent = gscontentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gscontent);
    }

    /**
     * DELETE  /gscontents/:id : delete the "id" gscontent.
     *
     * @param id the id of the gscontent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gscontents/{id}")
    @Timed
    public ResponseEntity<Void> deleteGscontent(@PathVariable Long id) {
        log.debug("REST request to delete Gscontent : {}", id);

        gscontentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
