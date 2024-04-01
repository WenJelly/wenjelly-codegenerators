package com.wenjelly.springboottemplate.esdao;

/*
 * @time 2024/3/24 14:26
 * @package com.wenjelly.springboottemplate.esdao
 * @project spring-boot-template
 * @author WenJelly
 * 帖子 ES 操作
 */

import com.wenjelly.springboottemplate.model.dto.post.PostEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {

    List<PostEsDTO> findByUserId(Long userId);
}
