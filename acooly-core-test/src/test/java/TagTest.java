/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-03-02 11:23 创建
 */

import com.acooly.openapi.framework.common.enums.SecretType;
import com.acooly.openapi.framework.domain.ApiPartner;
import org.apache.commons.beanutils.BeanUtils;

/**
 * @author acooly
 */
public class TagTest {

    public static void main(String[] args) throws Exception {

        ApiPartner p = new ApiPartner();
        p.setSecretType(SecretType.cert);

        String value = BeanUtils.getProperty(p, "secretType");
        System.out.println(value);
    }
}
