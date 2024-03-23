/*
 * @time 2024/3/7 12:17
 * @package PACKAGE_NAME
 * @project wenjelly-generator-makerplus
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.wenjelly.makerplus.meta.Meta;
import com.wenjelly.makerplus.meta.enums.FileGenerateTypeEnum;
import com.wenjelly.makerplus.meta.enums.FileTypeEnum;
import com.wenjelly.makerplus.template.enums.FileFilterRangeEnum;
import com.wenjelly.makerplus.template.enums.FileFilterRuleEnum;
import com.wenjelly.makerplus.template.model.FileFilterConfig;
import com.wenjelly.makerplus.template.model.TemplateMakerConfig;
import com.wenjelly.makerplus.template.model.TemplateMakerFileConfig;
import com.wenjelly.makerplus.template.model.TemplateMakerModelConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        String path = "D:\\Development\\IDEAJavaProjects\\wenjelly-generators\\wenjelly-generator-maker-plus\\src\\main\\resources\\templates\\java";
        if (FileUtil.isDirectory(path)) {
            List<File> fileList = FileUtil.loopFiles(path);
            for (File file : fileList) {
                if (StrUtil.contains(file.getName(), "Command")) {
                    String absolutePath = file.getAbsolutePath();
                    // 分割路径
                    List<String> split = StrUtil.split(absolutePath, "\\java");
                    String path1 = split.get(split.size() - 1).replace("\\", "/");;

                    String result = StrUtil.sub(path1, 0, path1.length() - 4);
                    System.out.println(result);

                }
            }

        }
    }

}
