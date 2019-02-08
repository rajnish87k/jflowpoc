package com.spr.jflow.poc.web.rest;

import com.spr.jflow.poc.JflowApp;

import com.spr.jflow.poc.domain.Titledata;
import com.spr.jflow.poc.repository.TitledataRepository;
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
 * Test class for the TitledataResource REST controller.
 *
 * @see TitledataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JflowApp.class)
public class TitledataResourceIntTest {

    private static final String DEFAULT_OBJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TitledataRepository titledataRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTitledataMockMvc;

    private Titledata titledata;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TitledataResource titledataResource = new TitledataResource(titledataRepository);
        this.restTitledataMockMvc = MockMvcBuilders.standaloneSetup(titledataResource)
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
    public static Titledata createEntity(EntityManager em) {
        Titledata titledata = new Titledata()
            .objectId(DEFAULT_OBJECT_ID)
            .objectName(DEFAULT_OBJECT_NAME)
            .productId(DEFAULT_PRODUCT_ID)
            .productTitle(DEFAULT_PRODUCT_TITLE)
            .shortTitle(DEFAULT_SHORT_TITLE)
            .creationDate(DEFAULT_CREATION_DATE);
        return titledata;
    }

    @Before
    public void initTest() {
        titledata = createEntity(em);
    }

    @Test
    @Transactional
    public void createTitledata() throws Exception {
        int databaseSizeBeforeCreate = titledataRepository.findAll().size();

        // Create the Titledata
        restTitledataMockMvc.perform(post("/api/titledata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titledata)))
            .andExpect(status().isCreated());

        // Validate the Titledata in the database
        List<Titledata> titledataList = titledataRepository.findAll();
        assertThat(titledataList).hasSize(databaseSizeBeforeCreate + 1);
        Titledata testTitledata = titledataList.get(titledataList.size() - 1);
        assertThat(testTitledata.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testTitledata.getObjectName()).isEqualTo(DEFAULT_OBJECT_NAME);
        assertThat(testTitledata.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testTitledata.getProductTitle()).isEqualTo(DEFAULT_PRODUCT_TITLE);
        assertThat(testTitledata.getShortTitle()).isEqualTo(DEFAULT_SHORT_TITLE);
        assertThat(testTitledata.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createTitledataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = titledataRepository.findAll().size();

        // Create the Titledata with an existing ID
        titledata.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTitledataMockMvc.perform(post("/api/titledata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titledata)))
            .andExpect(status().isBadRequest());

        // Validate the Titledata in the database
        List<Titledata> titledataList = titledataRepository.findAll();
        assertThat(titledataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkObjectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = titledataRepository.findAll().size();
        // set the field null
        titledata.setObjectName(null);

        // Create the Titledata, which fails.

        restTitledataMockMvc.perform(post("/api/titledata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titledata)))
            .andExpect(status().isBadRequest());

        List<Titledata> titledataList = titledataRepository.findAll();
        assertThat(titledataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = titledataRepository.findAll().size();
        // set the field null
        titledata.setProductId(null);

        // Create the Titledata, which fails.

        restTitledataMockMvc.perform(post("/api/titledata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titledata)))
            .andExpect(status().isBadRequest());

        List<Titledata> titledataList = titledataRepository.findAll();
        assertThat(titledataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = titledataRepository.findAll().size();
        // set the field null
        titledata.setCreationDate(null);

        // Create the Titledata, which fails.

        restTitledataMockMvc.perform(post("/api/titledata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titledata)))
            .andExpect(status().isBadRequest());

        List<Titledata> titledataList = titledataRepository.findAll();
        assertThat(titledataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTitledata() throws Exception {
        // Initialize the database
        titledataRepository.saveAndFlush(titledata);

        // Get all the titledataList
        restTitledataMockMvc.perform(get("/api/titledata?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(titledata.getId().intValue())))
            .andExpect(jsonPath("$.[*].objectId").value(hasItem(DEFAULT_OBJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].objectName").value(hasItem(DEFAULT_OBJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].productTitle").value(hasItem(DEFAULT_PRODUCT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].shortTitle").value(hasItem(DEFAULT_SHORT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getTitledata() throws Exception {
        // Initialize the database
        titledataRepository.saveAndFlush(titledata);

        // Get the titledata
        restTitledataMockMvc.perform(get("/api/titledata/{id}", titledata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(titledata.getId().intValue()))
            .andExpect(jsonPath("$.objectId").value(DEFAULT_OBJECT_ID.toString()))
            .andExpect(jsonPath("$.objectName").value(DEFAULT_OBJECT_NAME.toString()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.productTitle").value(DEFAULT_PRODUCT_TITLE.toString()))
            .andExpect(jsonPath("$.shortTitle").value(DEFAULT_SHORT_TITLE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTitledata() throws Exception {
        // Get the titledata
        restTitledataMockMvc.perform(get("/api/titledata/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTitledata() throws Exception {
        // Initialize the database
        titledataRepository.saveAndFlush(titledata);

        int databaseSizeBeforeUpdate = titledataRepository.findAll().size();

        // Update the titledata
        Titledata updatedTitledata = titledataRepository.findById(titledata.getId()).get();
        // Disconnect from session so that the updates on updatedTitledata are not directly saved in db
        em.detach(updatedTitledata);
        updatedTitledata
            .objectId(UPDATED_OBJECT_ID)
            .objectName(UPDATED_OBJECT_NAME)
            .productId(UPDATED_PRODUCT_ID)
            .productTitle(UPDATED_PRODUCT_TITLE)
            .shortTitle(UPDATED_SHORT_TITLE)
            .creationDate(UPDATED_CREATION_DATE);

        restTitledataMockMvc.perform(put("/api/titledata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTitledata)))
            .andExpect(status().isOk());

        // Validate the Titledata in the database
        List<Titledata> titledataList = titledataRepository.findAll();
        assertThat(titledataList).hasSize(databaseSizeBeforeUpdate);
        Titledata testTitledata = titledataList.get(titledataList.size() - 1);
        assertThat(testTitledata.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testTitledata.getObjectName()).isEqualTo(UPDATED_OBJECT_NAME);
        assertThat(testTitledata.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testTitledata.getProductTitle()).isEqualTo(UPDATED_PRODUCT_TITLE);
        assertThat(testTitledata.getShortTitle()).isEqualTo(UPDATED_SHORT_TITLE);
        assertThat(testTitledata.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTitledata() throws Exception {
        int databaseSizeBeforeUpdate = titledataRepository.findAll().size();

        // Create the Titledata

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTitledataMockMvc.perform(put("/api/titledata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titledata)))
            .andExpect(status().isBadRequest());

        // Validate the Titledata in the database
        List<Titledata> titledataList = titledataRepository.findAll();
        assertThat(titledataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTitledata() throws Exception {
        // Initialize the database
        titledataRepository.saveAndFlush(titledata);

        int databaseSizeBeforeDelete = titledataRepository.findAll().size();

        // Get the titledata
        restTitledataMockMvc.perform(delete("/api/titledata/{id}", titledata.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Titledata> titledataList = titledataRepository.findAll();
        assertThat(titledataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Titledata.class);
        Titledata titledata1 = new Titledata();
        titledata1.setId(1L);
        Titledata titledata2 = new Titledata();
        titledata2.setId(titledata1.getId());
        assertThat(titledata1).isEqualTo(titledata2);
        titledata2.setId(2L);
        assertThat(titledata1).isNotEqualTo(titledata2);
        titledata1.setId(null);
        assertThat(titledata1).isNotEqualTo(titledata2);
    }
}
