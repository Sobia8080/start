package com.wsm.compose.compose_util.core;


import com.wsm.compose.compose_domain.generator.ColumnDO;
import com.wsm.compose.compose_domain.generator.TableDO;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 */
public class GenUtils {
    //表前缀
    private static final String TABLEPREFIX = "";
    //是否去掉前缀
    private static final String AUTOREMOVEPRE = "false";
    //生成文件名称
    private static final String PACKAGENAME = "generator";
    //生成地址
    private static final String PACKAGE = "compose_";
    //生成文件
    private static final String PACKAGEPATH = "com.wsm.compose";
    //作者
    private static final String AUTHOR = "wangshimin";
    //邮箱
    private static final String EMILE = "wangshinhome@qq.com";

    private static final String DOMAIN = "domain";

    private static final String DAO = "dao";

    private static final String SERVICE = "service";

    private static final String BUSINESS = "business";

    private static final String API = "api";






    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("generator/domain.java.vm");
        templates.add("generator/Dao.java.vm");
        templates.add("generator/Mapper.xml.vm");
        templates.add("generator/Service.java.vm");
        templates.add("generator/ServiceImpl.java.vm");
        templates.add("generator/Business.java.vm");
        templates.add("generator/BusinessImpl.java.vm");
        templates.add("generator/Controller.java.vm");
        return templates;
    }

    /**
     * 生成代码
     */


    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip) {

        //表信息
        TableDO tableDO = new TableDO();
        tableDO.setTableName(table.get("tableName"));
        tableDO.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableDO.getTableName(), TABLEPREFIX, AUTOREMOVEPRE);
        tableDO.setClassName(className);
        tableDO.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnDO> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(column.get("columnName"));
            columnDO.setDataType(column.get("dataType"));
            columnDO.setComments(column.get("columnComment"));
            columnDO.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnDO.getColumnName());
            columnDO.setAttrName(attrName);
            columnDO.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = columnDO.getDataType();
            if (attrType.equals("bigint")) {
                attrType = "Long";
            } else if (attrType.equals("float")) {
                attrType = "Float";
            } else if (attrType.equals("double")) {
                attrType = "Double";
            } else if (attrType.equals("decimal")) {
                attrType = "BigDecimal";
            } else if (attrType.equals("char") || attrType.equals("varchar")
                    || attrType.equals("tinytext") || attrType.equals("text")
                    || attrType.equals("mediumtext") || attrType.equals("longtext")) {
                attrType = "String";
            } else if (attrType.equals("date") || attrType.equals("datetime")
                    || attrType.equals("timestamp")) {
                attrType = "Date";
            } else if (attrType.equals("tinyint") || attrType.equals("smallint")
                    || attrType.equals("mediumint") || attrType.equals("int")
                    || attrType.equals("integer")) {
                attrType = "Integer";
            } else {
                attrType = "UnKnow";
            }
            columnDO.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }

            columsList.add(columnDO);
        }
        tableDO.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableDO.getPk() == null) {
            tableDO.setPk(tableDO.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableDO.getTableName());
        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("className", tableDO.getClassName());
        map.put("package", PACKAGE);
        map.put("columns", tableDO.getColumns());
        map.put("packagePath", PACKAGEPATH);
        map.put("packageName", PACKAGENAME);

        map.put("author", AUTHOR);
        map.put("email", EMILE);
        map.put("domain", DOMAIN);
        map.put("dao", DAO);
        map.put("service", SERVICE);
        map.put("business", BUSINESS);
        map.put("api", API);
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableDO.getClassname(), tableDO.getClassName(), PACKAGENAME)));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new MyException(0, "渲染模板失败，表名：" + tableDO.getTableName());
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix, String autoRemovePre) {
        if ("true".equals(autoRemovePre)) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration("generator.properties");
            return propertiesConfiguration;
        } catch (ConfigurationException e) {
            throw new MyException(0, "获取配置文件失败");
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String classname, String className, String packageName) {
        String rootPath = "Comple" + File.separator;
        String packagePath = "src" + File.separator + "main" + File.separator + "java" + File.separator;
        String root2Path = "com" + File.separator + "wsm" + File.separator + "compose" + File.separator;

        if (template.contains("domain.java.vm")) {
            return rootPath + "compose_domain" + File.separator + packagePath + root2Path
                    + "compose_domain" + File.separator + packageName + File.separator + className + "DO.java";
        }

        if (template.contains("Dao.java.vm")) {
            return rootPath + "compose_dao" + File.separator + packagePath + root2Path
                    + "compose_dao" + File.separator + packageName + File.separator + className + "Dao.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return rootPath + "compose_dao" + File.separator + "src" + File.separator + "main" + File.separator
                    + "resources" + File.separator + "mapper" + File.separator + packageName + File.separator + className + "Mapper.xml";
        }

        if (template.contains("Service.java.vm")) {
            return rootPath + "compose_service" + File.separator + packagePath + root2Path
                    + "compose_service" + File.separator + packageName + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return rootPath + "compose_service" + File.separator + packagePath + root2Path
                    + "compose_service" + File.separator + packageName + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Business.java.vm")) {
            return rootPath + "compose_business" + File.separator + packagePath + root2Path
                    + "compose_business" + File.separator + packageName + File.separator + className + "Business.java";
        }

        if (template.contains("BusinessImpl.java.vm")) {
            return rootPath + "compose_business" + File.separator + packagePath + root2Path
                    + "compose_business" + File.separator + packageName + File.separator + "impl" + File.separator + className + "BusinessImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return rootPath + "compose_api" + File.separator + packagePath + root2Path
                    + "compose_api" + File.separator + packageName + File.separator + className + "Api.java";
        }

        return null;
    }
}
