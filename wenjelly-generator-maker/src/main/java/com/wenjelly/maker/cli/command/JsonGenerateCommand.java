package com.wenjelly.maker.cli.command;

/*
 * @time 2024/4/8 15:00
 * @package com.wenjelly.maker.cli.command
 * @project wenjelly-generator-maker
 * @author WenJelly
 */

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.wenjelly.maker.generator.file.FileGenerator;
import com.wenjelly.maker.model.DataModel;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

/**
 * 定义一个文件路径（filePath）属性来接受 JSON 文件路径，在执行时读取该文件并转换为 DataModel 数据模型类，之后调用 MainGenerator.doGenerate 生成代码即可。
 * 通过路径指向元信息文件进行生成代码
 */
@Command(name = "json-generator", description = "读取JSON文件生成代码", mixinStandardHelpOptions = true)
@Data
public class JsonGenerateCommand implements Callable<Integer> {

    @Option(names = {"-f", "--file"}, arity = "0..1", description = "json文件路径", interactive = true, echo = true)
    private String filePath;

    @Override
    public Integer call() throws Exception {
        String jsonStr = FileUtil.readUtf8String(filePath);
        DataModel dataModel = JSONUtil.toBean(jsonStr, DataModel.class);
        FileGenerator.doGenerate(dataModel);
        return 0;

    }
}
