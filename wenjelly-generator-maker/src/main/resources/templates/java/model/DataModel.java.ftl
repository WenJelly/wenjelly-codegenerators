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

        <#if modelInfo.groupKey??>
        /**
        *${modelInfo.groupName}
        */
        public ${modelInfo.type} ${modelInfo.groupKey} = new ${modelInfo.type}();

        /**
        * ${modelInfo.description}
        */
        @Data
        public static class ${modelInfo.type} {
            <#list modelInfo.models as modelInfo>
            <#if modelInfo.description??>
            /**
            *${modelInfo.description}
            */
            </#if>
            public ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c} </#if>;
            </#list>
        }
        <#else >
        <#if modelInfo.description??>
        /**
         *${modelInfo.description}
         */
        </#if>
        public ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c} </#if>;
        </#if>
    </#list>

}
