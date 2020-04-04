package utils;

import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  测试类
 * 〈功能详细描述〉
 *
 * @author [作者]（必须）
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FreemarkerGeneratorTest {

    @Test
    public void testKeyWord() {
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(FreemarkerGenerator.PATH));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("aa", "${pageSize}");
            // step4 加载模版文件
            Template template = configuration.getTemplate("test.ftl");
            // step5 生成数据
            File docFile = new File(FreemarkerGenerator.OUT_PATH + "aa.txt");
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


    @Test
    public void processDataForMysqlTest() throws Exception {
        Connection connection = MysqlDBUtil.getConnection();
        List<ColumnBean> list = DataUtils.processDataForMysql(connection, "d_menu");
        JSONObject.toJSONString(list);
        System.out.println(JSONObject.toJSONString(list));
        MysqlDBUtil.close(connection);
    }

    @Test
    public void createBeanWithMysql() throws Exception {
        String packageName = "com.test";
        String className = "Menu";
        String tableName = "d_menu";
        Connection connection = MysqlDBUtil.getConnection();
        FreemarkerGenerator.createBean(connection,packageName,className,tableName,"mysql");
        PGDBUtil.close(connection);
    }

    @Test
    public void createSqlMapWithMysql() throws Exception {
        String tableName = "d_menu";
        String fileName = "sqlMap_menu";
        String namespace = "menu";
        Connection connection = MysqlDBUtil.getConnection();
        FreemarkerGenerator.createSqlMap(connection,tableName,fileName,namespace,"mysql");
        PGDBUtil.close(connection);
    }
}
