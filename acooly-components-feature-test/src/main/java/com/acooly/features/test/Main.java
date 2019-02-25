package com.acooly.features.test;

import com.acooly.core.common.BootApp;
import com.acooly.core.common.boot.Apps;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangpu
 */
@BootApp(sysName = "acooly-featrues-test", httpPort = 8090)
public class Main {
	public static void main(String[] args) {
		Apps.setProfileIfNotExists("sdev");
		new SpringApplication(Main.class).run(args);
	}
}