package ${basePackage}.generator;

/*
 * @time ${createTime}
 * @package ${basePackage}.generator
 * @project ${name}
 * @author ${author}
*/

import com.wenjelly.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 生成动态和静态，相当于结合
 */

<#macro generateFile indent fileInfo>
${indent}inputPath = new File(inputRootPath,"${fileInfo.inputPath}").getAbsolutePath();
${indent}outputPath = new File(outputRootPath,"${fileInfo.outputPath}").getAbsolutePath();
<#if fileInfo.generateType == "static">
${indent}// 生成静态文件
${indent}StaticFileGenerator.copyFileByHuTool(inputPath,outputPath);
<#else >
${indent}// 生成动态文件
${indent}DynamicFileGenerator.doGenerate(inputPath, outputPath, model);
</#if>
</#macro>
public class MainFileGenerator {

    /**
     * 完整生成（静态+动态）
     * @param args
     */
    public static void main(String[] args) throws TemplateException, IOException {

    }

    public static void doGenerate(DataModel model) throws TemplateException, IOException {

        // 输入位置的根目录
        String inputRootPath = "${fileConfig.inputRootPath}";
        // 输出位置的根目录
        String outputRootPath = "${fileConfig.outputRootPath}";
        // 最终输入路径 ： 输入位置的根目录 + 相对路径
        String inputPath;
        // 最终输出路径 ： 输出位置的根目录 + 相对路径
        String outputPath;

        <#list modelConfig.models as modelInfo>
        <#if modelInfo.groupName??>
        <#list modelInfo.models as subModelInfo>
        ${subModelInfo.type} ${subModelInfo.fieldName} = model.${modelInfo.groupKey}.${subModelInfo.fieldName};
        </#list>
        <#else>
        ${modelInfo.type} ${modelInfo.fieldName} = model.${modelInfo.fieldName};
        </#if>
        </#list>

        <#list fileConfig.files as fileInfo>
        <#if fileInfo.groupKey??>
        // groupKey = ${fileInfo.groupKey}
        if (${fileInfo.condition}) {
        <#list fileInfo.files as groupInfo>
        <@generateFile indent="            " fileInfo=groupInfo></@generateFile>
        </#list>
        }
        <#else >
        <@generateFile indent="        " fileInfo=fileInfo></@generateFile>
        </#if>
        </#list>

    }

}
