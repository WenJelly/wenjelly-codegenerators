package package ${basePackage}.generator;

/*
 * @time ${createTime}
 * @package ${basePackage}.generator
 * @project ${name}
 * @author ${author}
*/

import com.wenjelly.maker.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 生成动态和静态，相当于结合
 */
public class FileGenerator {

    /**
     * 完整生成（静态+动态）
     * @param args
     */
    public static void main(String[] args) throws TemplateException, IOException {

    }

    public void doGenerate(DataModel model) throws TemplateException, IOException {

        // 输入位置的根目录
        String inputRootPath = "${fileConfig.inputRootPath}";
        // 输出位置的根目录
        String outputRootPath = "${fileConfig.outputRootPath}";
        // 最终输入路径 ： 输入位置的根目录 + 相对路径
        String inputPath;
        // 最终输出路径 ： 输出位置的根目录 + 相对路径
        String outputPath;

        <#list fileConfig.files as fileInfo>
            inputPath = new File(inputRootPath,"${fileInfo.inputPath}").getAbsolutePath();
            outputPath = new File(outputRootPath,"${fileInfo.outputPath}").getAbsolutePath();

            <#if fileInfo.generateType == "static">
            // 生成静态文件
            StaticFileGenerator.copyFileByHuTool(inputPath,outputPath);
            <#else >
            // 生成动态文件
            DynamicGenerator.doGenerate(inputPath, outputPath, model);
            </#if>
        </#list>

    }


}
