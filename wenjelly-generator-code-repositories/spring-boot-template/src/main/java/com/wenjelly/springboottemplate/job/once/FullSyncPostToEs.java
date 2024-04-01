package com.wenjelly.springboottemplate.job.once;

/*
 * @time 2024/3/24 14:47
 * @package com.wenjelly.springboottemplate.job.once
 * @project spring-boot-template
 * @author WenJelly
 * 全量同步帖子到 es
 */

import com.wenjelly.springboottemplate.esdao.PostEsDao;
import com.wenjelly.springboottemplate.model.dto.post.PostEsDTO;
import com.wenjelly.springboottemplate.model.entity.Post;
import com.wenjelly.springboottemplate.service.PostService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

// todo 取消注释开启任务
//@Component
@Slf4j
public class FullSyncPostToEs implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Resource
    private PostEsDao postEsDao;

    @Override
    public void run(String... args) {
        List<Post> postList = postService.list();
        if (CollectionUtils.isEmpty(postList)) {
            return;
        }
        List<PostEsDTO> postEsDTOList = postList.stream().map(PostEsDTO::objToDto).collect(Collectors.toList());
        final int pageSize = 500;
        int total = postEsDTOList.size();
        log.info("FullSyncPostToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            postEsDao.saveAll(postEsDTOList.subList(i, end));
        }
        log.info("FullSyncPostToEs end, total {}", total);
    }
}
