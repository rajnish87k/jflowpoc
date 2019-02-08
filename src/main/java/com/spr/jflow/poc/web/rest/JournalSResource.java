package com.spr.jflow.poc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spr.jflow.poc.domain.JournalS;
import com.spr.jflow.poc.repository.JournalSRepository;
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
 * REST controller for managing JournalS.
 */
@RestController
@RequestMapping("/api")
public class JournalSResource {

    private final Logger log = LoggerFactory.getLogger(JournalSResource.class);

    private static final String ENTITY_NAME = "journalS";

    private final JournalSRepository journalSRepository;

    public JournalSResource(JournalSRepository journalSRepository) {
        this.journalSRepository = journalSRepository;
    }

    /**
     * POST  /journal-s : Create a new journalS.
     *
     * @param journalS the journalS to create
     * @return the ResponseEntity with status 201 (Created) and with body the new journalS, or with status 400 (Bad Request) if the journalS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/journal-s")
    @Timed
    public ResponseEntity<JournalS> createJournalS(@Valid @RequestBody JournalS journalS) throws URISyntaxException {
        log.debug("REST request to save JournalS : {}", journalS);
        if (journalS.getId() != null) {
            throw new BadRequestAlertException("A new journalS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JournalS result = journalSRepository.save(journalS);
        return ResponseEntity.created(new URI("/api/journal-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /journal-s : Updates an existing journalS.
     *
     * @param journalS the journalS to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated journalS,
     * or with status 400 (Bad Request) if the journalS is not valid,
     * or with status 500 (Internal Server Error) if the journalS couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/journal-s")
    @Timed
    public ResponseEntity<JournalS> updateJournalS(@Valid @RequestBody JournalS journalS) throws URISyntaxException {
        log.debug("REST request to update JournalS : {}", journalS);
        if (journalS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JournalS result = journalSRepository.save(journalS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, journalS.getId().toString()))
            .body(result);
    }

    /**
     * GET  /journal-s : get all the journalS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of journalS in body
     */
    @GetMapping("/journal-s")
    @Timed
    public List<JournalS> getAllJournalS() {
        log.debug("REST request to get all JournalS");
        return journalSRepository.findAll();
    }

    /**
     * GET  /journal-s/:id : get the "id" journalS.
     *
     * @param id the id of the journalS to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journalS, or with status 404 (Not Found)
     */
    @GetMapping("/journal-s/{id}")
    @Timed
    public ResponseEntity<JournalS> getJournalS(@PathVariable Long id) {
        log.debug("REST request to get JournalS : {}", id);
        Optional<JournalS> journalS = journalSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(journalS);
    }

    /**
     * DELETE  /journal-s/:id : delete the "id" journalS.
     *
     * @param id the id of the journalS to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/journal-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteJournalS(@PathVariable Long id) {
        log.debug("REST request to delete JournalS : {}", id);

        journalSRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
