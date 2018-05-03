package com.recruit.web.rest;

import com.recruit.RecruitApp;

import com.recruit.domain.Resume;
import com.recruit.repository.ResumeRepository;
import com.recruit.service.ResumeService;
import com.recruit.repository.search.ResumeSearchRepository;
import com.recruit.service.dto.ResumeDTO;
import com.recruit.service.mapper.ResumeMapper;
import com.recruit.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.recruit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResumeResource REST controller.
 *
 * @see ResumeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecruitApp.class)
public class ResumeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ADVANTAGE = "AAAAAAAAAA";
    private static final String UPDATED_ADVANTAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_PLACE = "BBBBBBBBBB";

    private static final Instant DEFAULT_WORK_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WORK_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_UNDERGO = "AAAAAAAAAA";
    private static final String UPDATED_UNDERGO = "BBBBBBBBBB";

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_SALARY = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_SALARY = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_POSITION = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_ENCLOSURE = "AAAAAAAAAA";
    private static final String UPDATED_ENCLOSURE = "BBBBBBBBBB";

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ResumeMapper resumeMapper;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeSearchRepository resumeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResumeMockMvc;

    private Resume resume;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResumeResource resumeResource = new ResumeResource(resumeService);
        this.restResumeMockMvc = MockMvcBuilders.standaloneSetup(resumeResource)
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
    public static Resume createEntity(EntityManager em) {
        Resume resume = new Resume()
            .name(DEFAULT_NAME)
            .sex(DEFAULT_SEX)
            .birth(DEFAULT_BIRTH)
            .email(DEFAULT_EMAIL)
            .wechat(DEFAULT_WECHAT)
            .state(DEFAULT_STATE)
            .advantage(DEFAULT_ADVANTAGE)
            .targetPlace(DEFAULT_TARGET_PLACE)
            .workTime(DEFAULT_WORK_TIME)
            .experience(DEFAULT_EXPERIENCE)
            .undergo(DEFAULT_UNDERGO)
            .education(DEFAULT_EDUCATION)
            .targetSalary(DEFAULT_TARGET_SALARY)
            .targetPosition(DEFAULT_TARGET_POSITION)
            .userId(DEFAULT_USER_ID)
            .enclosure(DEFAULT_ENCLOSURE);
        return resume;
    }

    @Before
    public void initTest() {
        resumeSearchRepository.deleteAll();
        resume = createEntity(em);
    }

    @Test
    @Transactional
    public void createResume() throws Exception {
        int databaseSizeBeforeCreate = resumeRepository.findAll().size();

        // Create the Resume
        ResumeDTO resumeDTO = resumeMapper.toDto(resume);
        restResumeMockMvc.perform(post("/api/resumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeDTO)))
            .andExpect(status().isCreated());

        // Validate the Resume in the database
        List<Resume> resumeList = resumeRepository.findAll();
        assertThat(resumeList).hasSize(databaseSizeBeforeCreate + 1);
        Resume testResume = resumeList.get(resumeList.size() - 1);
        assertThat(testResume.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResume.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testResume.getBirth()).isEqualTo(DEFAULT_BIRTH);
        assertThat(testResume.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testResume.getWechat()).isEqualTo(DEFAULT_WECHAT);
        assertThat(testResume.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testResume.getAdvantage()).isEqualTo(DEFAULT_ADVANTAGE);
        assertThat(testResume.getTargetPlace()).isEqualTo(DEFAULT_TARGET_PLACE);
        assertThat(testResume.getWorkTime()).isEqualTo(DEFAULT_WORK_TIME);
        assertThat(testResume.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testResume.getUndergo()).isEqualTo(DEFAULT_UNDERGO);
        assertThat(testResume.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testResume.getTargetSalary()).isEqualTo(DEFAULT_TARGET_SALARY);
        assertThat(testResume.getTargetPosition()).isEqualTo(DEFAULT_TARGET_POSITION);
        assertThat(testResume.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testResume.getEnclosure()).isEqualTo(DEFAULT_ENCLOSURE);

        // Validate the Resume in Elasticsearch
        Resume resumeEs = resumeSearchRepository.findOne(testResume.getId());
        assertThat(resumeEs).isEqualToIgnoringGivenFields(testResume);
    }

    @Test
    @Transactional
    public void createResumeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resumeRepository.findAll().size();

        // Create the Resume with an existing ID
        resume.setId(1L);
        ResumeDTO resumeDTO = resumeMapper.toDto(resume);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResumeMockMvc.perform(post("/api/resumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resume in the database
        List<Resume> resumeList = resumeRepository.findAll();
        assertThat(resumeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllResumes() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);

        // Get all the resumeList
        restResumeMockMvc.perform(get("/api/resumes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resume.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].birth").value(hasItem(DEFAULT_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].wechat").value(hasItem(DEFAULT_WECHAT.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].advantage").value(hasItem(DEFAULT_ADVANTAGE.toString())))
            .andExpect(jsonPath("$.[*].targetPlace").value(hasItem(DEFAULT_TARGET_PLACE.toString())))
            .andExpect(jsonPath("$.[*].workTime").value(hasItem(DEFAULT_WORK_TIME.toString())))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE.toString())))
            .andExpect(jsonPath("$.[*].undergo").value(hasItem(DEFAULT_UNDERGO.toString())))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION.toString())))
            .andExpect(jsonPath("$.[*].targetSalary").value(hasItem(DEFAULT_TARGET_SALARY.toString())))
            .andExpect(jsonPath("$.[*].targetPosition").value(hasItem(DEFAULT_TARGET_POSITION.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].enclosure").value(hasItem(DEFAULT_ENCLOSURE.toString())));
    }

    @Test
    @Transactional
    public void getResume() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);

        // Get the resume
        restResumeMockMvc.perform(get("/api/resumes/{id}", resume.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resume.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.birth").value(DEFAULT_BIRTH.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.wechat").value(DEFAULT_WECHAT.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.advantage").value(DEFAULT_ADVANTAGE.toString()))
            .andExpect(jsonPath("$.targetPlace").value(DEFAULT_TARGET_PLACE.toString()))
            .andExpect(jsonPath("$.workTime").value(DEFAULT_WORK_TIME.toString()))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE.toString()))
            .andExpect(jsonPath("$.undergo").value(DEFAULT_UNDERGO.toString()))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION.toString()))
            .andExpect(jsonPath("$.targetSalary").value(DEFAULT_TARGET_SALARY.toString()))
            .andExpect(jsonPath("$.targetPosition").value(DEFAULT_TARGET_POSITION.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.enclosure").value(DEFAULT_ENCLOSURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResume() throws Exception {
        // Get the resume
        restResumeMockMvc.perform(get("/api/resumes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResume() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);
        resumeSearchRepository.save(resume);
        int databaseSizeBeforeUpdate = resumeRepository.findAll().size();

        // Update the resume
        Resume updatedResume = resumeRepository.findOne(resume.getId());
        // Disconnect from session so that the updates on updatedResume are not directly saved in db
        em.detach(updatedResume);
        updatedResume
            .name(UPDATED_NAME)
            .sex(UPDATED_SEX)
            .birth(UPDATED_BIRTH)
            .email(UPDATED_EMAIL)
            .wechat(UPDATED_WECHAT)
            .state(UPDATED_STATE)
            .advantage(UPDATED_ADVANTAGE)
            .targetPlace(UPDATED_TARGET_PLACE)
            .workTime(UPDATED_WORK_TIME)
            .experience(UPDATED_EXPERIENCE)
            .undergo(UPDATED_UNDERGO)
            .education(UPDATED_EDUCATION)
            .targetSalary(UPDATED_TARGET_SALARY)
            .targetPosition(UPDATED_TARGET_POSITION)
            .userId(UPDATED_USER_ID)
            .enclosure(UPDATED_ENCLOSURE);
        ResumeDTO resumeDTO = resumeMapper.toDto(updatedResume);

        restResumeMockMvc.perform(put("/api/resumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeDTO)))
            .andExpect(status().isOk());

        // Validate the Resume in the database
        List<Resume> resumeList = resumeRepository.findAll();
        assertThat(resumeList).hasSize(databaseSizeBeforeUpdate);
        Resume testResume = resumeList.get(resumeList.size() - 1);
        assertThat(testResume.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResume.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testResume.getBirth()).isEqualTo(UPDATED_BIRTH);
        assertThat(testResume.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testResume.getWechat()).isEqualTo(UPDATED_WECHAT);
        assertThat(testResume.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testResume.getAdvantage()).isEqualTo(UPDATED_ADVANTAGE);
        assertThat(testResume.getTargetPlace()).isEqualTo(UPDATED_TARGET_PLACE);
        assertThat(testResume.getWorkTime()).isEqualTo(UPDATED_WORK_TIME);
        assertThat(testResume.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testResume.getUndergo()).isEqualTo(UPDATED_UNDERGO);
        assertThat(testResume.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testResume.getTargetSalary()).isEqualTo(UPDATED_TARGET_SALARY);
        assertThat(testResume.getTargetPosition()).isEqualTo(UPDATED_TARGET_POSITION);
        assertThat(testResume.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testResume.getEnclosure()).isEqualTo(UPDATED_ENCLOSURE);

        // Validate the Resume in Elasticsearch
        Resume resumeEs = resumeSearchRepository.findOne(testResume.getId());
        assertThat(resumeEs).isEqualToIgnoringGivenFields(testResume);
    }

    @Test
    @Transactional
    public void updateNonExistingResume() throws Exception {
        int databaseSizeBeforeUpdate = resumeRepository.findAll().size();

        // Create the Resume
        ResumeDTO resumeDTO = resumeMapper.toDto(resume);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResumeMockMvc.perform(put("/api/resumes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resumeDTO)))
            .andExpect(status().isCreated());

        // Validate the Resume in the database
        List<Resume> resumeList = resumeRepository.findAll();
        assertThat(resumeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResume() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);
        resumeSearchRepository.save(resume);
        int databaseSizeBeforeDelete = resumeRepository.findAll().size();

        // Get the resume
        restResumeMockMvc.perform(delete("/api/resumes/{id}", resume.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean resumeExistsInEs = resumeSearchRepository.exists(resume.getId());
        assertThat(resumeExistsInEs).isFalse();

        // Validate the database is empty
        List<Resume> resumeList = resumeRepository.findAll();
        assertThat(resumeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchResume() throws Exception {
        // Initialize the database
        resumeRepository.saveAndFlush(resume);
        resumeSearchRepository.save(resume);

        // Search the resume
        restResumeMockMvc.perform(get("/api/_search/resumes?query=id:" + resume.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resume.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].birth").value(hasItem(DEFAULT_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].wechat").value(hasItem(DEFAULT_WECHAT.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].advantage").value(hasItem(DEFAULT_ADVANTAGE.toString())))
            .andExpect(jsonPath("$.[*].targetPlace").value(hasItem(DEFAULT_TARGET_PLACE.toString())))
            .andExpect(jsonPath("$.[*].workTime").value(hasItem(DEFAULT_WORK_TIME.toString())))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE.toString())))
            .andExpect(jsonPath("$.[*].undergo").value(hasItem(DEFAULT_UNDERGO.toString())))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION.toString())))
            .andExpect(jsonPath("$.[*].targetSalary").value(hasItem(DEFAULT_TARGET_SALARY.toString())))
            .andExpect(jsonPath("$.[*].targetPosition").value(hasItem(DEFAULT_TARGET_POSITION.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].enclosure").value(hasItem(DEFAULT_ENCLOSURE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resume.class);
        Resume resume1 = new Resume();
        resume1.setId(1L);
        Resume resume2 = new Resume();
        resume2.setId(resume1.getId());
        assertThat(resume1).isEqualTo(resume2);
        resume2.setId(2L);
        assertThat(resume1).isNotEqualTo(resume2);
        resume1.setId(null);
        assertThat(resume1).isNotEqualTo(resume2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeDTO.class);
        ResumeDTO resumeDTO1 = new ResumeDTO();
        resumeDTO1.setId(1L);
        ResumeDTO resumeDTO2 = new ResumeDTO();
        assertThat(resumeDTO1).isNotEqualTo(resumeDTO2);
        resumeDTO2.setId(resumeDTO1.getId());
        assertThat(resumeDTO1).isEqualTo(resumeDTO2);
        resumeDTO2.setId(2L);
        assertThat(resumeDTO1).isNotEqualTo(resumeDTO2);
        resumeDTO1.setId(null);
        assertThat(resumeDTO1).isNotEqualTo(resumeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(resumeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(resumeMapper.fromId(null)).isNull();
    }
}
