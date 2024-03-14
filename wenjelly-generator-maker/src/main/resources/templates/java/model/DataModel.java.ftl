package ${basePackage}.model;

/*
 * @time ${createTime}
 * @package ${basePackage}.model
 * @project ${name}
 * @author ${author}
 */

import lombok.Data;
@Data
public class DataModel {

    <#list modelConfig.models as modelInfo>

        <#if modelInfo.description??>
        /**
         *${modelInfo.description}
         */
        </#if>
        public ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c} </#if>;
    </#list>

}
