package com.wenjelly.generatorbackend.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;


/*
 * @time 2024/4/16 10:12
 * @package com.wenjelly.generatorbackend.manager
 * @project wenjelly-generator-web-backend
 * @author WenJelly
 */

@SpringBootTest
class CosManagerTest {

    @Resource
    private CosManager cosManager;

    @Test
    void deleteObject() {
        cosManager.deleteObject("/memreduct/test.docx");
    }

    @Test
    void deleteObjects() {
        cosManager.deleteObjects(Arrays.asList("memreduct/MCB2301032_2.pdf",
                "memreduct/党章.docx"
        ));
    }

    @Test
    void deleteDir() {
        cosManager.deleteDir("/memreduct/");
    }
}