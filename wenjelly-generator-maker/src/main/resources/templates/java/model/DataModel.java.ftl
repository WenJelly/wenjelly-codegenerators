package ${basePackage}model;

/*
 * @time 2024/3/3 22:24
 * @package com.wenjelly.model
 * @project wenjelly-generators
 * @author WenJelly
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
        private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??> = ${modelInfo.defaultValue?c} </#if>
    </#list>

}
