package utils;

import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  数据处理
 * 〈功能详细描述〉
 *
 * @author [作者]（必须）
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DataUtils {

    public static String dealDataTypeForPG(String dataType){
        if(dataType==null || dataType.length()==0){
            throw new RuntimeException("字段类型为空");
        }
        if(dataType.contains("character varying")){
            return "String";
        }else if(dataType.contains("numeric")){
            return "BigDecimal";
        }else if(dataType.contains("timestamp")){
            return "Date";
        }else if(dataType.contains("int4")){
            return "Integer";
        }
        throw new RuntimeException("字段类型转换异常");
    }

    public static String dealDataTypeForMysql(String dataType){
        if(dataType==null || dataType.length()==0){
            throw new RuntimeException("字段类型为空");
        }
        if(dataType.contains("varchar")){
            return "String";
        }else if(dataType.contains("decimal")){
            return "BigDecimal";
        }else if(dataType.contains("datetime")){
            return "Date";
        }else if(dataType.contains("int")){
            return "Integer";
        }
        throw new RuntimeException("字段类型转换异常");
    }

    /**
     * 首字母大写
     * @param columnName
     * @return
     */
    public static String dealColumnNameFirstUpper(String columnName){
        return columnName.substring(0,1)+columnName.substring(1);
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static ColumnBean createColumnBeanName(String dataType,String columnName,String remarks,String dbType){
        String type = "";
        if("mysql".equals(dbType)){
            type = dealDataTypeForMysql(dataType);
        }else{
            type = dealDataTypeForPG(dataType);
        }
        String name = lineToHump(columnName);
        String firstUpperName = dealColumnNameFirstUpper(name);
        String newColumnName = addEmptyChar(columnName.toUpperCase(), 25);
        ColumnBean columnBean = new ColumnBean(type,name,remarks,firstUpperName,columnName.toUpperCase(),newColumnName);
        return columnBean;
    }

    /**
     * 字符串不足指定长度补空格
     * @param str
     * @param length
     * @return
     */
    public static String addEmptyChar(String str,int length){
        StringBuffer buffer = new StringBuffer();
        if(str.isEmpty()){
            for(int i=0;i<length;i++){
                buffer.append(" ");
            }
        }else if(str.length()<length){
            buffer.append(str);
            for(int i=0;i<length-str.length();i++){
                buffer.append(" ");
            }
        }else{
            buffer.append(str);
        }
        return buffer.toString();
    }

    public static List<ColumnBean> processDataForPG(Connection connection, String tableName) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT  ");
        sql.append("     b.attname AS column_name, ");
        sql.append("     c.description AS remarks, ");
        sql.append("     pg_catalog.format_type(b.atttypid,b.atttypmod) AS data_type ");
        sql.append(" FROM ");
        sql.append("     pg_catalog.pg_class a, ");
        sql.append("     pg_catalog.pg_attribute b, ");
        sql.append("     pg_catalog.pg_description c ");
        sql.append(" WHERE ");
        sql.append("     a.oid=b.attrelid ");
        sql.append(" AND b.attrelid=c.objoid ");
        sql.append(" AND a.relname= ? ");
        sql.append(" AND c.objsubid=b.attnum ");
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        preparedStatement.setString(1,tableName);
        System.out.println(preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ColumnBean> list = new ArrayList<ColumnBean>();
        while (resultSet.next()){
            String dataType = resultSet.getString("data_type");
            String columnName = resultSet.getString("column_name");
            String remarks = resultSet.getString("remarks");
            ColumnBean columnBean = createColumnBeanName(dataType, columnName, remarks,"PG");
            list.add(columnBean);
        }
        return list;
    }

    public static List<ColumnBean> processDataForMysql(Connection connection, String tableName) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from information_schema.COLUMNS where table_name= ? ");
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        preparedStatement.setString(1,tableName);
        System.out.println(preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        List<ColumnBean> list = new ArrayList<ColumnBean>();
        while (resultSet.next()){
            String dataType = resultSet.getString("data_type");
            String columnName = resultSet.getString("column_name");
            String remarks = resultSet.getString("column_comment");
            ColumnBean columnBean = createColumnBeanName(dataType, columnName, remarks,"mysql");
            list.add(columnBean);
        }
        return list;
    }
}
