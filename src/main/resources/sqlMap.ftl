<?xml version="1.0" encoding="UTF-8" ?>
<sqlMap namespace="${namespace}">

    <sql id="queryCount">
        <![CDATA[
            SELECT
                COUNT(ID)	AS	TOTALCOUNT
            FROM
                ${tableName?upper_case}
            WHERE 1=1
        ]]>
    </sql>

    <sql id="queryList">
        <![CDATA[
            SELECT
            <#list attrs as attr>
                ${attr.columnNameWithLen} AS "${attr.name}"<#if attr_has_next>,</#if>
            </#list>
            FROM
                ${tableName?upper_case}
            WHERE 1=1
                LIMIT ${pageSize} OFFSET ${startPage}
        ]]>
    </sql>

    <sql id="insert">
        <![CDATA[
            INSERT INTO ${tableName?upper_case}(
            <#list attrs as attr>
                ${attr.columnName}<#if attr_has_next>,</#if>
            </#list>
            )
            VALUES
            (
            <#list attrs as attr>
                :${attr.name}<#if attr_has_next>,</#if>
            </#list>
            )
        ]]>
    </sql>

    <sql id="update">
        <![CDATA[
            UPDATE ${tableName?upper_case}
            SET
            <#list attrs as attr>
                ${attr.columnName} = :${attr.name}<#if attr_has_next>,</#if>
            </#list>
            WHERE
            <#list attrs as attr>
            ${r"<#if "}${attr.name}${r"?exists && "}${attr.name}${r"!=''>"}
            <#if attr_index==0>
                    ${attr.columnName} = :${attr.name}
            <#else>
                AND ${attr.columnName} = :${attr.name}
            </#if>
            ${r"</#if>"}
            </#list>
        ]]>
    </sql>


</sqlMap>