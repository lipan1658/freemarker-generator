package utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  模板生成类
 * 〈功能详细描述〉
 *
 * @author [作者]（必须）
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FreemarkerGenerator {
    //模板路径
    public static String PATH = "D:\\IdeaProjects\\freemarker-generator\\src\\main\\resources";
    //生成文件路径
    public static String OUT_PATH = "E:\\study\\";

    public static void createBean(Connection connection,String packageName, String className, String tableName,String dbType){
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(PATH));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<ColumnBean> columnBeans = null;
            if("mysql".equals(dbType)){
                columnBeans = DataUtils.processDataForMysql(connection,tableName);
            }else{
                columnBeans = DataUtils.processDataForPG(connection,tableName);
            }
            dataMap.put("packageName", packageName);
            dataMap.put("className", className);
            dataMap.put("attrs",columnBeans);
            // step4 加载模版文件
            Template template = configuration.getTemplate("bean.ftl");
            // step5 生成数据
            File docFile = new File(OUT_PATH+className+".java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void createSqlMap(Connection connection,String tableName,String fileName,String namespace,String dbType){
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(PATH));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();

            List<ColumnBean> columnBeans = null;
            if("mysql".equals(dbType)){
                columnBeans = DataUtils.processDataForMysql(connection,tableName);
            }else{
                columnBeans = DataUtils.processDataForPG(connection,tableName);
            }
            dataMap.put("tableName", tableName);
            dataMap.put("namespace", namespace);
            dataMap.put("pageSize","${pageSize}");
            dataMap.put("startPage","${startPage}");
            dataMap.put("attrs",columnBeans);
            // step4 加载模版文件
            Template template = configuration.getTemplate("sqlMap.ftl");
            // step5 生成数据
            File docFile = new File(OUT_PATH+fileName+".xml");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
