package com.spr.jflow.poc.web.rest;

import com.spr.jflow.poc.JflowApp;

import com.spr.jflow.poc.domain.JournalS;
import com.spr.jflow.poc.repository.JournalSRepository;
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

import com.spr.jflow.poc.domain.enumeration.Language;
import com.spr.jflow.poc.domain.enumeration.CompanyGroup;
/**
 * Test class for the JournalSResource REST controller.
 *
 * @see JournalSResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JflowApp.class)
public class JournalSResourceIntTest {

    private static final String DEFAULT_OBJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_TITLE = "BBBBBBBBBB";

    private static final Language DEFAULT_PRIMARY_LANGUAGE = Language.ENGLISH;
    private static final Language UPDATED_PRIMARY_LANGUAGE = Language.GERMAN;

    private static final CompanyGroup DEFAULT_COMPANY_GROUP = CompanyGroup.AA;
    private static final CompanyGroup UPDATED_COMPANY_GROUP = CompanyGroup.BB;

    private static final String DEFAULT_PUBLISHER = "AAAAAAAAAA";
    private static final String UPDATED_PUBLISHER = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLISHING_SEGMENT = "AAAAAAAAAA";
    private static final String UPDATED_PUBLISHING_SEGMENT = "BBBBBBBBBB";

    private static final String DEFAULT_IMPRINT = "AAAAAAAAAA";
    private static final String UPDATED_IMPRINT = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIUM = "AAAAAAAAAA";
    private static final String UPDATED_MEDIUM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private JournalSRepository journalSRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJournalSMockMvc;

    private JournalS journalS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JournalSResource journalSResource = new JournalSResource(journalSRepository);
        this.restJournalSMockMvc = MockMvcBuilders.standaloneSetup(journalSResource)
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
    public static JournalS createEntity(EntityManager em) {
        JournalS journalS = new JournalS()
            .objectId(DEFAULT_OBJECT_ID)
            .objectName(DEFAULT_OBJECT_NAME)
            .productId(DEFAULT_PRODUCT_ID)
            .title(DEFAULT_TITLE)
            .shortTitle(DEFAULT_SHORT_TITLE)
            .primaryLanguage(DEFAULT_PRIMARY_LANGUAGE)
            .companyGroup(DEFAULT_COMPANY_GROUP)
            .publisher(DEFAULT_PUBLISHER)
            .publishingSegment(DEFAULT_PUBLISHING_SEGMENT)
            .imprint(DEFAULT_IMPRINT)
            .medium(DEFAULT_MEDIUM)
            .creationDate(DEFAULT_CREATION_DATE);
        return journalS;
    }

    @Before
    public void initTest() {
        journalS = createEntity(em);
    }

    @Test
    @Transactional
    public void createJournalS() throws Exception {
        int databaseSizeBeforeCreate = journalSRepository.findAll().size();

        // Create the JournalS
        restJournalSMockMvc.perform(post("/api/journal-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalS)))
            .andExpect(status().isCreated());

        // Validate the JournalS in the database
        List<JournalS> journalSList = journalSRepository.findAll();
        assertThat(journalSList).hasSize(databaseSizeBeforeCreate + 1);
        JournalS testJournalS = journalSList.get(journalSList.size() - 1);
        assertThat(testJournalS.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testJournalS.getObjectName()).isEqualTo(DEFAULT_OBJECT_NAME);
        assertThat(testJournalS.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testJournalS.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJournalS.getShortTitle()).isEqualTo(DEFAULT_SHORT_TITLE);
        assertThat(testJournalS.getPrimaryLanguage()).isEqualTo(DEFAULT_PRIMARY_LANGUAGE);
        assertThat(testJournalS.getCompanyGroup()).isEqualTo(DEFAULT_COMPANY_GROUP);
        assertThat(testJournalS.getPublisher()).isEqualTo(DEFAULT_PUBLISHER);
        assertThat(testJournalS.getPublishingSegment()).isEqualTo(DEFAULT_PUBLISHING_SEGMENT);
        assertThat(testJournalS.getImprint()).isEqualTo(DEFAULT_IMPRINT);
        assertThat(testJournalS.getMedium()).isEqualTo(DEFAULT_MEDIUM);
        assertThat(testJournalS.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createJournalSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journalSRepository.findAll().size();

        // Create the JournalS with an existing ID
        journalS.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJournalSMockMvc.perform(post("/api/journal-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalS)))
            .andExpect(status().isBadRequest());

        // Validate the JournalS in the database
        List<JournalS> journalSList = journalSRepository.findAll();
        assertThat(journalSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkObjectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalSRepository.findAll().size();
        // set the field null
        journalS.setObjectName(null);

        // Create the JournalS, which fails.

        restJournalSMockMvc.perform(post("/api/journal-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalS)))
            .andExpect(status().isBadRequest());

        List<JournalS> journalSList = journalSRepository.findAll();
        assertThat(journalSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalSRepository.findAll().size();
        // set the field null
        journalS.setProductId(null);

        // Create the JournalS, which fails.

        restJournalSMockMvc.perform(post("/api/journal-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalS)))
            .andExpect(status().isBadRequest());

        List<JournalS> journalSList = journalSRepository.findAll();
        assertThat(journalSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = journalSRepository.findAll().size();
        // set the field null
        journalS.setCreationDate(null);

        // Create the JournalS, which fails.

        restJournalSMockMvc.perform(post("/api/journal-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalS)))
            .andExpect(status().isBadRequest());

        List<JournalS> journalSList = journalSRepository.findAll();
        assertThat(journalSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJournalS() throws Exception {
        // Initialize the database
        journalSRepository.saveAndFlush(journalS);

        // Get all the journalSList
        restJournalSMockMvc.perform(get("/api/journal-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journalS.getId().intValue())))
            .andExpect(jsonPath("$.[*].objectId").value(hasItem(DEFAULT_OBJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].objectName").value(hasItem(DEFAULT_OBJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].shortTitle").value(hasItem(DEFAULT_SHORT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].primaryLanguage").value(hasItem(DEFAULT_PRIMARY_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].companyGroup").value(hasItem(DEFAULT_COMPANY_GROUP.toString())))
            .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
            .andExpect(jsonPath("$.[*].publishingSegment").value(hasItem(DEFAULT_PUBLISHING_SEGMENT.toString())))
            .andExpect(jsonPath("$.[*].imprint").value(hasItem(DEFAULT_IMPRINT.toString())))
            .andExpect(jsonPath("$.[*].medium").value(hasItem(DEFAULT_MEDIUM.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getJournalS() throws Exception {
        // Initialize the database
        journalSRepository.saveAndFlush(journalS);

        // Get the journalS
        restJournalSMockMvc.perform(get("/api/journal-s/{id}", journalS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journalS.getId().intValue()))
            .andExpect(jsonPath("$.objectId").value(DEFAULT_OBJECT_ID.toString()))
            .andExpect(jsonPath("$.objectName").value(DEFAULT_OBJECT_NAME.toString()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.shortTitle").value(DEFAULT_SHORT_TITLE.toString()))
            .andExpect(jsonPath("$.primaryLanguage").value(DEFAULT_PRIMARY_LANGUAGE.toString()))
            .andExpect(jsonPath("$.companyGroup").value(DEFAULT_COMPANY_GROUP.toString()))
            .andExpect(jsonPath("$.publisher").value(DEFAULT_PUBLISHER.toString()))
            .andExpect(jsonPath("$.publishingSegment").value(DEFAULT_PUBLISHING_SEGMENT.toString()))
            .andExpect(jsonPath("$.imprint").value(DEFAULT_IMPRINT.toString()))
            .andExpect(jsonPath("$.medium").value(DEFAULT_MEDIUM.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingJournalS() throws Exception {
        // Get the journalS
        restJournalSMockMvc.perform(get("/api/journal-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJournalS() throws Exception {
        // Initialize the database
        journalSRepository.saveAndFlush(journalS);

        int databaseSizeBeforeUpdate = journalSRepository.findAll().size();

        // Update the journalS
        JournalS updatedJournalS = journalSRepository.findById(journalS.getId()).get();
        // Disconnect from session so that the updates on updatedJournalS are not directly saved in db
        em.detach(updatedJournalS);
        updatedJournalS
            .objectId(UPDATED_OBJECT_ID)
            .objectName(UPDATED_OBJECT_NAME)
            .productId(UPDATED_PRODUCT_ID)
            .title(UPDATED_TITLE)
            .shortTitle(UPDATED_SHORT_TITLE)
            .primaryLanguage(UPDATED_PRIMARY_LANGUAGE)
            .companyGroup(UPDATED_COMPANY_GROUP)
            .publisher(UPDATED_PUBLISHER)
            .publishingSegment(UPDATED_PUBLISHING_SEGMENT)
            .imprint(UPDATED_IMPRINT)
            .medium(UPDATED_MEDIUM)
            .creationDate(UPDATED_CREATION_DATE);

        restJournalSMockMvc.perform(put("/api/journal-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJournalS)))
            .andExpect(status().isOk());

        // Validate the JournalS in the database
        List<JournalS> journalSList = journalSRepository.findAll();
        assertThat(journalSList).hasSize(databaseSizeBeforeUpdate);
        JournalS testJournalS = journalSList.get(journalSList.size() - 1);
        assertThat(testJournalS.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testJournalS.getObjectName()).isEqualTo(UPDATED_OBJECT_NAME);
        assertThat(testJournalS.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testJournalS.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJournalS.getShortTitle()).isEqualTo(UPDATED_SHORT_TITLE);
        assertThat(testJournalS.getPrimaryLanguage()).isEqualTo(UPDATED_PRIMARY_LANGUAGE);
        assertThat(testJournalS.getCompanyGroup()).isEqualTo(UPDATED_COMPANY_GROUP);
        assertThat(testJournalS.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testJournalS.getPublishingSegment()).isEqualTo(UPDATED_PUBLISHING_SEGMENT);
        assertThat(testJournalS.getImprint()).isEqualTo(UPDATED_IMPRINT);
        assertThat(testJournalS.getMedium()).isEqualTo(UPDATED_MEDIUM);
        assertThat(testJournalS.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingJournalS() throws Exception {
        int databaseSizeBeforeUpdate = journalSRepository.findAll().size();

        // Create the JournalS

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJournalSMockMvc.perform(put("/api/journal-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalS)))
            .andExpect(status().isBadRequest());

        // Validate the JournalS in the database
        List<JournalS> journalSList = journalSRepository.findAll();
        assertThat(journalSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJournalS() throws Exception {
        // Initialize the database
        journalSRepository.saveAndFlush(journalS);

        int databaseSizeBeforeDelete = journalSRepository.findAll().size();

        // Get the journalS
        restJournalSMockMvc.perform(delete("/api/journal-s/{id}", journalS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JournalS> journalSList = journalSRepository.findAll();
        assertThat(journalSList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JournalS.class);
        JournalS journalS1 = new JournalS();
        journalS1.setId(1L);
        JournalS journalS2 = new JournalS();
        journalS2.setId(journalS1.getId());
        assertThat(journalS1).isEqualTo(journalS2);
        journalS2.setId(2L);
        assertThat(journalS1).isNotEqualTo(journalS2);
        journalS1.setId(null);
        assertThat(journalS1).isNotEqualTo(journalS2);
    }
}
