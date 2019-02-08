package com.spr.jflow.poc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spr.jflow.poc.domain.Titledata;
import com.spr.jflow.poc.repository.TitledataRepository;
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
 * REST controller for managing Titledata.
 */
@RestController
@RequestMapping("/api")
public class TitledataResource {

    private final Logger log = LoggerFactory.getLogger(TitledataResource.class);

    private static final String ENTITY_NAME = "titledata";

    private final TitledataRepository titledataRepository;

    public TitledataResource(TitledataRepository titledataRepository) {
        this.titledataRepository = titledataRepository;
    }

    /**
     * POST  /titledata : Create a new titledata.
     *
     * @param titledata the titledata to create
     * @return the ResponseEntity with status 201 (Created) and with body the new titledata, or with status 400 (Bad Request) if the titledata has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/titledata")
    @Timed
    public ResponseEntity<Titledata> createTitledata(@Valid @RequestBody Titledata titledata) throws URISyntaxException {
        log.debug("REST request to save Titledata : {}", titledata);
        if (titledata.getId() != null) {
            throw new BadRequestAlertException("A new titledata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Titledata result = titledataRepository.save(titledata);
        return ResponseEntity.created(new URI("/api/titledata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /titledata : Updates an existing titledata.
     *
     * @param titledata the titledata to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated titledata,
     * or with status 400 (Bad Request) if the titledata is not valid,
     * or with status 500 (Internal Server Error) if the titledata couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/titledata")
    @Timed
    public ResponseEntity<Titledata> updateTitledata(@Valid @RequestBody Titledata titledata) throws URISyntaxException {
        log.debug("REST request to update Titledata : {}", titledata);
        if (titledata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Titledata result = titledataRepository.save(titledata);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, titledata.getId().toString()))
            .body(result);
    }

    /**
     * GET  /titledata : get all the titledata.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of titledata in body
     */
    @GetMapping("/titledata")
    @Timed
    public List<Titledata> getAllTitledata() {
        log.debug("REST request to get all Titledata");
        return titledataRepository.findAll();
    }

    /**
     * GET  /titledata/:id : get the "id" titledata.
     *
     * @param id the id of the titledata to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the titledata, or with status 404 (Not Found)
     */
    @GetMapping("/titledata/{id}")
    @Timed
    public ResponseEntity<Titledata> getTitledata(@PathVariable Long id) {
        log.debug("REST request to get Titledata : {}", id);
        Optional<Titledata> titledata = titledataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(titledata);
    }

    /**
     * DELETE  /titledata/:id : delete the "id" titledata.
     *
     * @param id the id of the titledata to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/titledata/{id}")
    @Timed
    public ResponseEntity<Void> deleteTitledata(@PathVariable Long id) {
        log.debug("REST request to delete Titledata : {}", id);

        titledataRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
