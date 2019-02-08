package com.spr.jflow.poc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spr.jflow.poc.domain.JournalR;
import com.spr.jflow.poc.repository.JournalRRepository;
import com.spr.jflow.poc.web.rest.errors.BadRequestAlertException;
import com.spr.jflow.poc.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JournalR.
 */
@RestController
@RequestMapping("/api")
public class JournalRResource {

    private final Logger log = LoggerFactory.getLogger(JournalRResource.class);

    private static final String ENTITY_NAME = "journalR";

    private final JournalRRepository journalRRepository;

    public JournalRResource(JournalRRepository journalRRepository) {
        this.journalRRepository = journalRRepository;
    }

    /**
     * POST  /journal-rs : Create a new journalR.
     *
     * @param journalR the journalR to create
     * @return the ResponseEntity with status 201 (Created) and with body the new journalR, or with status 400 (Bad Request) if the journalR has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/journal-rs")
    @Timed
    public ResponseEntity<JournalR> createJournalR(@RequestBody JournalR journalR) throws URISyntaxException {
        log.debug("REST request to save JournalR : {}", journalR);
        if (journalR.getId() != null) {
            throw new BadRequestAlertException("A new journalR cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JournalR result = journalRRepository.save(journalR);
        return ResponseEntity.created(new URI("/api/journal-rs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /journal-rs : Updates an existing journalR.
     *
     * @param journalR the journalR to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated journalR,
     * or with status 400 (Bad Request) if the journalR is not valid,
     * or with status 500 (Internal Server Error) if the journalR couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/journal-rs")
    @Timed
    public ResponseEntity<JournalR> updateJournalR(@RequestBody JournalR journalR) throws URISyntaxException {
        log.debug("REST request to update JournalR : {}", journalR);
        if (journalR.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JournalR result = journalRRepository.save(journalR);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, journalR.getId().toString()))
            .body(result);
    }

    /**
     * GET  /journal-rs : get all the journalRS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of journalRS in body
     */
    @GetMapping("/journal-rs")
    @Timed
    public List<JournalR> getAllJournalRS() {
        log.debug("REST request to get all JournalRS");
        return journalRRepository.findAll();
    }

    /**
     * GET  /journal-rs/:id : get the "id" journalR.
     *
     * @param id the id of the journalR to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journalR, or with status 404 (Not Found)
     */
    @GetMapping("/journal-rs/{id}")
    @Timed
    public ResponseEntity<JournalR> getJournalR(@PathVariable Long id) {
        log.debug("REST request to get JournalR : {}", id);
        Optional<JournalR> journalR = journalRRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(journalR);
    }

    /**
     * DELETE  /journal-rs/:id : delete the "id" journalR.
     *
     * @param id the id of the journalR to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/journal-rs/{id}")
    @Timed
    public ResponseEntity<Void> deleteJournalR(@PathVariable Long id) {
        log.debug("REST request to delete JournalR : {}", id);

        journalRRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
