
package com.acooly.module.test;
import com.acooly.coder.Generator;
import com.acooly.coder.config.GenerateConfig;
import com.acooly.coder.generate.CodeGenerateService;


/**
 * 代码生成工具
 *
 * @author zhangpu
 * @date 2019-12-2
 */
public class AcoolyCoder {
    // 生成代码的目标模块
    static String MODULE_NAME = "acooly-component-smsend/acooly-component-smsend-core";
    // 生成代码的根包
    static String ROOT_PACKAGE = "com.acooly.module.smsend";
    // 生成代码的管理视图相对路径
    static String MANAGE_VIEW_PATH = "/manage/smsend/";
    // 配置表名转换为实体名时，需要忽略的表前缀。例如配置：p_ 则表示p_customer(表名) -> Customer(实体类名)
    static String TABLE_IGNOR_PREFIX = "";
    // 生成代码的表
    static String[] TABLES = {"sms_template","sms_app","sms_template_provider"};

    /**
     * 代码方式配置关键参数
     * <p>
     * 代码方式参数优先级高于配置文件
     *
     * @param args
     */
    public static void main(String[] args) {
        CodeGenerateService service = Generator.getGenerator();
        GenerateConfig config = GenerateConfig.INSTANCE();
        config.setWorkspace(getProjectPath() + MODULE_NAME);
        config.setManagePath(MANAGE_VIEW_PATH);
        config.setTableToEntityIgnorPrefix(TABLE_IGNOR_PREFIX);
        config.setRootPackage(ROOT_PACKAGE);
        service.generateTable(TABLES);
    }

    public static String getProjectPath() {
        String file = AcoolyCoder.class.getClassLoader().getResource(".").getFile();
        String testModulePath = file.substring(0, file.indexOf("/target/"));
        String projectPath = testModulePath.substring(0, testModulePath.lastIndexOf("/"));
        return projectPath + "/";
    }
}
