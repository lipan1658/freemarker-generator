package utils;

/**
 *  实体类
 * 〈功能详细描述〉
 *
 * @author [作者]（必须）
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ColumnBean {

    //成员变量类型
    private String type;
    //成员变量名称
    private String name;
    //注释
    private String remarks;
    //首字母大写
    private String firstUpperName;
    //字段（大写）
    private String columnName;
    //指定长度的字段大写，不足右侧补空格(select sql使用，美观，个人习惯)
    private String columnNameWithLen;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFirstUpperName() {
        return firstUpperName;
    }

    public void setFirstUpperName(String firstUpperName) {
        this.firstUpperName = firstUpperName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnNameWithLen() {
        return columnNameWithLen;
    }

    public void setColumnNameWithLen(String columnNameWithLen) {
        this.columnNameWithLen = columnNameWithLen;
    }

    public ColumnBean(String type, String name, String remarks, String firstUpperName, String columnName, String columnNameWithLen) {
        this.type = type;
        this.name = name;
        this.remarks = remarks;
        this.firstUpperName = firstUpperName;
        this.columnName = columnName;
        this.columnNameWithLen = columnNameWithLen;
    }

    public ColumnBean() {
    }
}
