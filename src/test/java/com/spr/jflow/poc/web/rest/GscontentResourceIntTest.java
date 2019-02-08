package com.spr.jflow.poc.web.rest;

import com.spr.jflow.poc.JflowApp;

import com.spr.jflow.poc.domain.Gscontent;
import com.spr.jflow.poc.repository.GscontentRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.spr.jflow.poc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GscontentResource REST controller.
 *
 * @see GscontentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JflowApp.class)
public class GscontentResourceIntTest {

    private static final String DEFAULT_OBJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REPO_CONTENT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_REPO_CONTENT_PATH = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PARENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_ID = "BBBBBBBBBB";

    @Autowired
    private GscontentRepository gscontentRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGscontentMockMvc;

    private Gscontent gscontent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GscontentResource gscontentResource = new GscontentResource(gscontentRepository);
        this.restGscontentMockMvc = MockMvcBuilders.standaloneSetup(gscontentResource)
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
    public static Gscontent createEntity(EntityManager em) {
        Gscontent gscontent = new Gscontent()
            .objectId(DEFAULT_OBJECT_ID)
            .repoContentPath(DEFAULT_REPO_CONTENT_PATH)
            .creationDate(DEFAULT_CREATION_DATE)
            .parentId(DEFAULT_PARENT_ID);
        return gscontent;
    }

    @Before
    public void initTest() {
        gscontent = createEntity(em);
    }

    @Test
    @Transactional
    public void createGscontent() throws Exception {
        int databaseSizeBeforeCreate = gscontentRepository.findAll().size();

        // Create the Gscontent
        restGscontentMockMvc.perform(post("/api/gscontents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gscontent)))
            .andExpect(status().isCreated());

        // Validate the Gscontent in the database
        List<Gscontent> gscontentList = gscontentRepository.findAll();
        assertThat(gscontentList).hasSize(databaseSizeBeforeCreate + 1);
        Gscontent testGscontent = gscontentList.get(gscontentList.size() - 1);
        assertThat(testGscontent.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testGscontent.getRepoContentPath()).isEqualTo(DEFAULT_REPO_CONTENT_PATH);
        assertThat(testGscontent.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testGscontent.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
    }

    @Test
    @Transactional
    public void createGscontentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gscontentRepository.findAll().size();

        // Create the Gscontent with an existing ID
        gscontent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGscontentMockMvc.perform(post("/api/gscontents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gscontent)))
            .andExpect(status().isBadRequest());

        // Validate the Gscontent in the database
        List<Gscontent> gscontentList = gscontentRepository.findAll();
        assertThat(gscontentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkObjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = gscontentRepository.findAll().size();
        // set the field null
        gscontent.setObjectId(null);

        // Create the Gscontent, which fails.

        restGscontentMockMvc.perform(post("/api/gscontents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gscontent)))
            .andExpect(status().isBadRequest());

        List<Gscontent> gscontentList = gscontentRepository.findAll();
        assertThat(gscontentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = gscontentRepository.findAll().size();
        // set the field null
        gscontent.setCreationDate(null);

        // Create the Gscontent, which fails.

        restGscontentMockMvc.perform(post("/api/gscontents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gscontent)))
            .andExpect(status().isBadRequest());

        List<Gscontent> gscontentList = gscontentRepository.findAll();
        assertThat(gscontentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGscontents() throws Exception {
        // Initialize the database
        gscontentRepository.saveAndFlush(gscontent);

        // Get all the gscontentList
        restGscontentMockMvc.perform(get("/api/gscontents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gscontent.getId().intValue())))
            .andExpect(jsonPath("$.[*].objectId").value(hasItem(DEFAULT_OBJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].repoContentPath").value(hasItem(DEFAULT_REPO_CONTENT_PATH.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.toString())));
    }
    

    @Test
    @Transactional
    public void getGscontent() throws Exception {
        // Initialize the database
        gscontentRepository.saveAndFlush(gscontent);

        // Get the gscontent
        restGscontentMockMvc.perform(get("/api/gscontents/{id}", gscontent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gscontent.getId().intValue()))
            .andExpect(jsonPath("$.objectId").value(DEFAULT_OBJECT_ID.toString()))
            .andExpect(jsonPath("$.repoContentPath").value(DEFAULT_REPO_CONTENT_PATH.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGscontent() throws Exception {
        // Get the gscontent
        restGscontentMockMvc.perform(get("/api/gscontents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGscontent() throws Exception {
        // Initialize the database
        gscontentRepository.saveAndFlush(gscontent);

        int databaseSizeBeforeUpdate = gscontentRepository.findAll().size();

        // Update the gscontent
        Gscontent updatedGscontent = gscontentRepository.findById(gscontent.getId()).get();
        // Disconnect from session so that the updates on updatedGscontent are not directly saved in db
        em.detach(updatedGscontent);
        updatedGscontent
            .objectId(UPDATED_OBJECT_ID)
            .repoContentPath(UPDATED_REPO_CONTENT_PATH)
            .creationDate(UPDATED_CREATION_DATE)
            .parentId(UPDATED_PARENT_ID);

        restGscontentMockMvc.perform(put("/api/gscontents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGscontent)))
            .andExpect(status().isOk());

        // Validate the Gscontent in the database
        List<Gscontent> gscontentList = gscontentRepository.findAll();
        assertThat(gscontentList).hasSize(databaseSizeBeforeUpdate);
        Gscontent testGscontent = gscontentList.get(gscontentList.size() - 1);
        assertThat(testGscontent.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testGscontent.getRepoContentPath()).isEqualTo(UPDATED_REPO_CONTENT_PATH);
        assertThat(testGscontent.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testGscontent.getParentId()).isEqualTo(UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingGscontent() throws Exception {
        int databaseSizeBeforeUpdate = gscontentRepository.findAll().size();

        // Create the Gscontent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGscontentMockMvc.perform(put("/api/gscontents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gscontent)))
            .andExpect(status().isBadRequest());

        // Validate the Gscontent in the database
        List<Gscontent> gscontentList = gscontentRepository.findAll();
        assertThat(gscontentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGscontent() throws Exception {
        // Initialize the database
        gscontentRepository.saveAndFlush(gscontent);

        int databaseSizeBeforeDelete = gscontentRepository.findAll().size();

        // Get the gscontent
        restGscontentMockMvc.perform(delete("/api/gscontents/{id}", gscontent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gscontent> gscontentList = gscontentRepository.findAll();
        assertThat(gscontentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gscontent.class);
        Gscontent gscontent1 = new Gscontent();
        gscontent1.setId(1L);
        Gscontent gscontent2 = new Gscontent();
        gscontent2.setId(gscontent1.getId());
        assertThat(gscontent1).isEqualTo(gscontent2);
        gscontent2.setId(2L);
        assertThat(gscontent1).isNotEqualTo(gscontent2);
        gscontent1.setId(null);
        assertThat(gscontent1).isNotEqualTo(gscontent2);
    }
}
