package com.acooly.module.test.meta;

import com.acooly.core.common.BootApp;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;

import java.lang.annotation.*;

/**
 * @author qiubo@yiji.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@OverrideAutoConfiguration(enabled = false)
@AutoConfigureDubbo
@ImportAutoConfiguration
@SpringBootApplication
@BootApp(sysName = "test")
public @interface DubboTest {
}
