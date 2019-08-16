/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-24 19:42 创建
 */
package com.acooly.module.test.param;

import com.acooly.core.common.dao.support.EnhanceDefaultConversionService;
import com.acooly.core.utils.Money;
import com.google.common.base.Charsets;
import junitparams.custom.ParametersProvider;
import junitparams.internal.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.runners.model.FrameworkMethod;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

import java.beans.PropertyEditorManager;
import java.io.*;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author qiubo@yiji.com
 */
@Slf4j
public class CsvProvider implements ParametersProvider<CsvParameter> {
    static {
        PropertyEditorManager.registerEditor(Money.class, MoneyEditor.class);
        PropertyEditorManager.registerEditor(Date.class, DateEditor.class);

    }

    private CsvParameter parameter;
    private DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
    private FrameworkMethod frameworkMethod;
    private boolean needConvert = false;
    private Class<?> parameterType = null;
    private ConversionService conversionService = EnhanceDefaultConversionService.INSTANCE;

    public CsvProvider(FrameworkMethod frameworkMethod) {
        this.frameworkMethod = frameworkMethod;
        Class<?>[] parameterTypes = frameworkMethod.getMethod().getParameterTypes();
        if (parameterTypes.length > 1) {
            needConvert = false;
        } else {
            needConvert = true;
            parameterType = parameterTypes[0];
            if (ClassUtils.isPrimitiveOrWrapper(parameterType)) {
                needConvert = false;
            }
            if (String.class.isAssignableFrom(parameterType)) {
                needConvert = false;
            }
        }
    }

    @Override
    public void initialize(CsvParameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public Object[] getParameters() {
        Reader reader = buildReader();
        if (reader == null) {
            return new Object[0];
        }
        String line;
        List<Object> result = new LinkedList<>();
        int lineNo = 0;
        String[] header = null;
        try (BufferedReader br = new BufferedReader(reader);) {
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (lineNo == 1) {
                    header = Utils.splitAtCommaOrPipe(line);
                } else {
                    if (line.contains("~")) {
                        line = line.replace("~", "");
                    }
                    result.add(parseLine(header, line, lineNo));
                }
            }
            return result.toArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object parseLine(String[] header, String line, int lineNo) {
        if (!needConvert) {
            return line;
        } else {
            BeanWrapper beanWrapper = new BeanWrapperImpl(parameterType);
            beanWrapper.setConversionService(conversionService);
            String[] params = Utils.splitAtCommaOrPipe(line);
            if (params.length != header.length) {
                throw new RuntimeException("数据文件:" + parameter.value() + " 第" + lineNo + "行格式错误");
            }
            for (int i = 0; i < header.length; i++) {
                try {
                    beanWrapper.setPropertyValue(header[i], params[i]);
                } catch (NotWritablePropertyException e) {
                    //ignore
                }
            }
            return beanWrapper.getWrappedInstance();
        }
    }

    private Reader buildReader() {
        String filepath = parameter.value();
        Resource resource = resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + filepath);
        if (resource.exists()) {
            try {
                return new InputStreamReader(resource.getInputStream(), "utf-8");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            String file =
                    this.frameworkMethod.getDeclaringClass().getClassLoader().getResource(".").getFile();
            file = file.substring(0, file.indexOf("target/test-classes/"));
            String baseDir = file + "src/test/resources/";
            String csvFile = baseDir + filepath;
            StringBuilder header = new StringBuilder();
            Parameter[] parameters = this.frameworkMethod.getMethod().getParameters();
            for (Parameter parameter : parameters) {
                String paramName = parameter.getName();
                header.append(paramName).append(",");
            }
            header.deleteCharAt(header.length() - 1);
            header.append("\n");
            log.warn("csv文件不存在，创建默认csv文件:[{}]", csvFile);
            try {
                FileUtils.write(new File(csvFile), header.toString(), Charsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }
}
