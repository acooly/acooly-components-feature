
package com.acooly.module.test;
import com.acooly.coder.Generator;
import com.acooly.coder.generate.impl.DefaultCodeGenerateService;
import org.apache.commons.lang3.StringUtils;

/**
 * 代码生成工具
 */
public class AcoolyCoder {
    public static void main(String[] args) {
        DefaultCodeGenerateService service = (DefaultCodeGenerateService) Generator.getGenerator();
        //set workspace if possible
        if (StringUtils.isBlank(service.getGenerateConfiguration().getWorkspace())) {
            String workspace = getProjectPath() + "acooly-component-eav";
            service.getGenerateConfiguration().setWorkspace(workspace);
        }
        //set root pacakge if possible
        if (StringUtils.isBlank(service.getGenerateConfiguration().getRootPackage())) {
            service.getGenerateConfiguration().setRootPackage(getRootPackage());
        }
        service.generateTable("eav_option","eav_scheme_tag");
    }

    public static String getProjectPath() {
        String file = AcoolyCoder.class.getClassLoader().getResource(".").getFile();
        String testModulePath = file.substring(0, file.indexOf("/target/"));
        String projectPath = testModulePath.substring(0, testModulePath.lastIndexOf("/"));
        return projectPath + "/";
    }

    private static String getRootPackage() {
        return "com.acooly.module.eav";
    }
}
