package com.recruit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.recruit.config.Constants;
import com.recruit.service.ResumeService;
import com.recruit.service.dto.ResumeDTO;
import com.recruit.web.rest.errors.BadRequestAlertException;
import com.recruit.web.rest.util.HeaderUtil;
import com.recruit.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Resume.
 */
@RestController
@RequestMapping("/api")
public class ResumeResource {

    private final Logger log = LoggerFactory.getLogger(ResumeResource.class);

    private static final String ENTITY_NAME = "resume";

    private final ResumeService resumeService;

    public ResumeResource(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    /**
     * POST  /resumes : Create a new resume.
     *
     * @param resumeDTO the resumeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resumeDTO, or with status 400 (Bad Request) if the resume has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resumes")
    @Timed
    public ResponseEntity<ResumeDTO> createResume(@RequestBody ResumeDTO resumeDTO) throws URISyntaxException {
        log.debug("REST request to save Resume : {}", resumeDTO);
        if (resumeDTO.getId() != null) {
            throw new BadRequestAlertException("A new resume cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumeDTO result = resumeService.save(resumeDTO);
        return ResponseEntity.created(new URI("/api/resumes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resumes : Updates an existing resume.
     *
     * @param resumeDTO the resumeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resumeDTO,
     * or with status 400 (Bad Request) if the resumeDTO is not valid,
     * or with status 500 (Internal Server Error) if the resumeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resumes")
    @Timed
    public ResponseEntity<ResumeDTO> updateResume(@RequestBody ResumeDTO resumeDTO) throws URISyntaxException {
        log.debug("REST request to update Resume : {}", resumeDTO);
        if (resumeDTO.getId() == null) {
            return createResume(resumeDTO);
        }
        ResumeDTO result = resumeService.save(resumeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resumeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resumes : get all the resumes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resumes in body
     */
    @GetMapping("/resumes")
    @Timed
    public ResponseEntity<List<ResumeDTO>> getAllResumes(Pageable pageable) {
        log.debug("REST request to get a page of Resumes");
        Page<ResumeDTO> page = resumeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resumes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /resumes/:id : get the "id" resume.
     *
     * @param id the id of the resumeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resumeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/resumes/{id}")
    @Timed
    public ResponseEntity<ResumeDTO> getResume(@PathVariable Long id) {
        log.debug("REST request to get Resume : {}", id);
        ResumeDTO resumeDTO = resumeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resumeDTO));
    }

    /**
     * GET  /resumes/user/:id : get the "userId" resume.
     *
     * @param id the userId of the resumeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resumeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/resumes/user/{id}")
    @Timed
    public ResponseEntity<ResumeDTO> getResumeByUserId(@PathVariable Long id) {
        log.debug("REST request to get Resume : {}", id);
        ResumeDTO resumeDTO = resumeService.findByUserId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resumeDTO));
    }

    /**
     * DELETE  /resumes/:id : delete the "id" resume.
     *
     * @param id the id of the resumeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resumes/{id}")
    @Timed
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {
        log.debug("REST request to delete Resume : {}", id);
        resumeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/resumes?query=:query : search for the resume corresponding
     * to the query.
     *
     * @param query    the query of the resume search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/resumes")
    @Timed
    public ResponseEntity<List<ResumeDTO>> searchResumes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Resumes for query {}", query);
        Page<ResumeDTO> page = resumeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/resumes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/resumes/upload")
    @Timed
    public ResponseEntity<Void> singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "upload_empty", "file is empty")).build();
        }
        int index = file.getOriginalFilename().lastIndexOf(".");
        String name = UUID.randomUUID().toString() + file.getOriginalFilename().substring(index, file.getOriginalFilename().length());

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone1());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(Constants.QINIU_ACCESS_KEY, Constants.QINIU_SECRET_KEY);
        String upToken = auth.uploadToken(Constants.QINIU_BUCKET);
        try {
            Response response = uploadManager.put(file.getInputStream(), name, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.debug("qiniu response {}",putRet.key);
            log.debug("qiniu response {}",putRet.hash);
            return ResponseEntity.noContent()
                .headers(HeaderUtil.createAlert("error.upload_success", putRet.key)).build();
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "upload_error","")).build();
    }

}
