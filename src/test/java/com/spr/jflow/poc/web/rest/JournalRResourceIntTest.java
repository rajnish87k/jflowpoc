package com.spr.jflow.poc.web.rest;

import com.spr.jflow.poc.JflowApp;

import com.spr.jflow.poc.domain.JournalR;
import com.spr.jflow.poc.repository.JournalRRepository;
import com.spr.jflow.poc.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.spr.jflow.poc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JournalRResource REST controller.
 *
 * @see JournalRResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JflowApp.class)
public class JournalRResourceIntTest {

    private static final String DEFAULT_OBJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_OF_SOCIETY = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_SOCIETY = "BBBBBBBBBB";

    private static final String DEFAULT_ONLINE_SERVICES = "AAAAAAAAAA";
    private static final String UPDATED_ONLINE_SERVICES = "BBBBBBBBBB";

    @Autowired
    private JournalRRepository journalRRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJournalRMockMvc;

    private JournalR journalR;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JournalRResource journalRResource = new JournalRResource(journalRRepository);
        this.restJournalRMockMvc = MockMvcBuilders.standaloneSetup(journalRResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JournalR createEntity(EntityManager em) {
        JournalR journalR = new JournalR()
            .objectId(DEFAULT_OBJECT_ID)
            .nameOfSociety(DEFAULT_NAME_OF_SOCIETY)
            .onlineServices(DEFAULT_ONLINE_SERVICES);
        return journalR;
    }

    @Before
    public void initTest() {
        journalR = createEntity(em);
    }

    @Test
    @Transactional
    public void createJournalR() throws Exception {
        int databaseSizeBeforeCreate = journalRRepository.findAll().size();

        // Create the JournalR
        restJournalRMockMvc.perform(post("/api/journal-rs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalR)))
            .andExpect(status().isCreated());

        // Validate the JournalR in the database
        List<JournalR> journalRList = journalRRepository.findAll();
        assertThat(journalRList).hasSize(databaseSizeBeforeCreate + 1);
        JournalR testJournalR = journalRList.get(journalRList.size() - 1);
        assertThat(testJournalR.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testJournalR.getNameOfSociety()).isEqualTo(DEFAULT_NAME_OF_SOCIETY);
        assertThat(testJournalR.getOnlineServices()).isEqualTo(DEFAULT_ONLINE_SERVICES);
    }

    @Test
    @Transactional
    public void createJournalRWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journalRRepository.findAll().size();

        // Create the JournalR with an existing ID
        journalR.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJournalRMockMvc.perform(post("/api/journal-rs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalR)))
            .andExpect(status().isBadRequest());

        // Validate the JournalR in the database
        List<JournalR> journalRList = journalRRepository.findAll();
        assertThat(journalRList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJournalRS() throws Exception {
        // Initialize the database
        journalRRepository.saveAndFlush(journalR);

        // Get all the journalRList
        restJournalRMockMvc.perform(get("/api/journal-rs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journalR.getId().intValue())))
            .andExpect(jsonPath("$.[*].objectId").value(hasItem(DEFAULT_OBJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].nameOfSociety").value(hasItem(DEFAULT_NAME_OF_SOCIETY.toString())))
            .andExpect(jsonPath("$.[*].onlineServices").value(hasItem(DEFAULT_ONLINE_SERVICES.toString())));
    }
    

    @Test
    @Transactional
    public void getJournalR() throws Exception {
        // Initialize the database
        journalRRepository.saveAndFlush(journalR);

        // Get the journalR
        restJournalRMockMvc.perform(get("/api/journal-rs/{id}", journalR.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journalR.getId().intValue()))
            .andExpect(jsonPath("$.objectId").value(DEFAULT_OBJECT_ID.toString()))
            .andExpect(jsonPath("$.nameOfSociety").value(DEFAULT_NAME_OF_SOCIETY.toString()))
            .andExpect(jsonPath("$.onlineServices").value(DEFAULT_ONLINE_SERVICES.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingJournalR() throws Exception {
        // Get the journalR
        restJournalRMockMvc.perform(get("/api/journal-rs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJournalR() throws Exception {
        // Initialize the database
        journalRRepository.saveAndFlush(journalR);

        int databaseSizeBeforeUpdate = journalRRepository.findAll().size();

        // Update the journalR
        JournalR updatedJournalR = journalRRepository.findById(journalR.getId()).get();
        // Disconnect from session so that the updates on updatedJournalR are not directly saved in db
        em.detach(updatedJournalR);
        updatedJournalR
            .objectId(UPDATED_OBJECT_ID)
            .nameOfSociety(UPDATED_NAME_OF_SOCIETY)
            .onlineServices(UPDATED_ONLINE_SERVICES);

        restJournalRMockMvc.perform(put("/api/journal-rs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJournalR)))
            .andExpect(status().isOk());

        // Validate the JournalR in the database
        List<JournalR> journalRList = journalRRepository.findAll();
        assertThat(journalRList).hasSize(databaseSizeBeforeUpdate);
        JournalR testJournalR = journalRList.get(journalRList.size() - 1);
        assertThat(testJournalR.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testJournalR.getNameOfSociety()).isEqualTo(UPDATED_NAME_OF_SOCIETY);
        assertThat(testJournalR.getOnlineServices()).isEqualTo(UPDATED_ONLINE_SERVICES);
    }

    @Test
    @Transactional
    public void updateNonExistingJournalR() throws Exception {
        int databaseSizeBeforeUpdate = journalRRepository.findAll().size();

        // Create the JournalR

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJournalRMockMvc.perform(put("/api/journal-rs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalR)))
            .andExpect(status().isBadRequest());

        // Validate the JournalR in the database
        List<JournalR> journalRList = journalRRepository.findAll();
        assertThat(journalRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJournalR() throws Exception {
        // Initialize the database
        journalRRepository.saveAndFlush(journalR);

        int databaseSizeBeforeDelete = journalRRepository.findAll().size();

        // Get the journalR
        restJournalRMockMvc.perform(delete("/api/journal-rs/{id}", journalR.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JournalR> journalRList = journalRRepository.findAll();
        assertThat(journalRList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JournalR.class);
        JournalR journalR1 = new JournalR();
        journalR1.setId(1L);
        JournalR journalR2 = new JournalR();
        journalR2.setId(journalR1.getId());
        assertThat(journalR1).isEqualTo(journalR2);
        journalR2.setId(2L);
        assertThat(journalR1).isNotEqualTo(journalR2);
        journalR1.setId(null);
        assertThat(journalR1).isNotEqualTo(journalR2);
    }
}
