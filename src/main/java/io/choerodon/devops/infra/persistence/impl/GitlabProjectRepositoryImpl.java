package io.choerodon.devops.infra.persistence.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.devops.domain.application.entity.gitlab.BranchE;
import io.choerodon.devops.domain.application.entity.gitlab.GitlabCommitE;
import io.choerodon.devops.domain.application.entity.gitlab.GitlabJobE;
import io.choerodon.devops.domain.application.entity.gitlab.GitlabPipelineE;
import io.choerodon.devops.domain.application.repository.GitlabProjectRepository;
import io.choerodon.devops.infra.dataobject.gitlab.*;
import io.choerodon.devops.infra.feign.GitlabServiceClient;

/**
 * Created by Zenger on 2018/4/9.
 */
@Component
public class GitlabProjectRepositoryImpl implements GitlabProjectRepository {

    private GitlabServiceClient gitlabServiceClient;

    public GitlabProjectRepositoryImpl(GitlabServiceClient gitlabServiceClient) {
        this.gitlabServiceClient = gitlabServiceClient;
    }

    @Override
    public List<GitlabPipelineE> listPipeline(Integer projectId, Integer userId) {
        ResponseEntity<List<PipelineDO>> responseEntity = gitlabServiceClient.listPipeline(projectId, userId);
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return new ArrayList<>();
        }
        return ConvertHelper.convertList(responseEntity.getBody(), GitlabPipelineE.class);
    }

    @Override
    public List<GitlabPipelineE> listPipelines(Integer projectId, Integer page, Integer size, Integer userId) {
        ResponseEntity<List<PipelineDO>> responseEntity =
                gitlabServiceClient.listPipelines(projectId, page, size, userId);
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return new ArrayList<>();
        }
        return ConvertHelper.convertList(responseEntity.getBody(), GitlabPipelineE.class);
    }

    @Override
    public GitlabPipelineE getPipeline(Integer projectId, Integer pipelineId, Integer userId) {
        ResponseEntity<PipelineDO> responseEntity = gitlabServiceClient.getPipeline(projectId, pipelineId, userId);
        return ConvertHelper.convert(responseEntity.getBody(), GitlabPipelineE.class);
    }

    @Override
    public GitlabCommitE getCommit(Integer projectId, String sha, Integer userId) {
        ResponseEntity<CommitDO> responseEntity = gitlabServiceClient.getCommit(projectId, sha, userId);
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return null;
        }
        return ConvertHelper.convert(responseEntity.getBody(), GitlabCommitE.class);
    }

    @Override
    public List<GitlabJobE> listJobs(Integer projectId, Integer pipelineId, Integer userId) {
        ResponseEntity<List<JobDO>> responseEntity = gitlabServiceClient.listJobs(projectId, pipelineId, userId);
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return new ArrayList<>();
        }
        return ConvertHelper.convertList(responseEntity.getBody(), GitlabJobE.class);
    }

    @Override
    public Boolean retry(Integer projectId, Integer pipelineId, Integer userId) {
        ResponseEntity<PipelineDO> responseEntity = gitlabServiceClient.retry(projectId, pipelineId, userId);
        return HttpStatus.OK.equals(responseEntity.getStatusCode());
    }

    @Override
    public Boolean cancel(Integer projectId, Integer pipelineId, Integer userId) {
        ResponseEntity<PipelineDO> responseEntity = gitlabServiceClient.cancel(projectId, pipelineId, userId);
        return HttpStatus.OK.equals(responseEntity.getStatusCode());
    }

    @Override
    public List<BranchE> listBranches(Integer projectId, Integer userId) {
        ResponseEntity<List<BranchDO>> responseEntity;
        try {
            responseEntity = gitlabServiceClient.listBranches(projectId, userId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return Collections.emptyList();
        }
        return ConvertHelper.convertList(responseEntity.getBody(), BranchE.class);
    }

    @Override
    public List<CommitStatuseDO> getCommitStatuse(Integer projectId, String sha, Integer useId) {
        ResponseEntity<List<CommitStatuseDO>> commitStatuse;
        commitStatuse = gitlabServiceClient.getCommitStatuse(projectId, sha, useId);
        if (!HttpStatus.OK.equals(commitStatuse.getStatusCode())) {
            return Collections.emptyList();
        }
        return commitStatuse.getBody();
    }
}
