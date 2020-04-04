package ${packageName};

import com.alibaba.fastjson.JSONObject;
import java.util.Date;

/**
* 〈一句话功能简述〉
* 〈功能详细描述〉
*
* @author 19043197
* @see [相关类/方法]（可选）
* @since [产品/模块版本] （可选）
*/
public class ${className} implements Serializable {

    private static final long serialVersionUID = 1L;

<#-- 循环类型及属性 -->
<#list attrs as attr>
    //${attr.remarks}
    private ${attr.type} ${attr.name};

</#list>

<#-- 循环生成set get方法 -->
<#list attrs as attr>
    public void set${attr.firstUpperName}(${attr.type} ${attr.name}) {
        this.${attr.name} = ${attr.name};
    }

    public ${attr.type} get${attr.firstUpperName}() {
        return ${attr.name};
    }

</#list>

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}