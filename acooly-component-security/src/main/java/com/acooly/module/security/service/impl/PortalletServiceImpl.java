package com.acooly.module.security.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.dao.PortalletDao;
import com.acooly.module.security.domain.Portallet;
import com.acooly.module.security.service.PortalletService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("portalletService")
@Transactional
@ConditionalOnProperty(
        value = SecurityProperties.PREFIX + ".shiro.auth.enable",
        matchIfMissing = true
)
public class PortalletServiceImpl extends EntityServiceImpl<Portallet, PortalletDao>
        implements PortalletService {

    @Override
    public List<Portallet> queryByUserName(String userName) {
        return getEntityDao().queryByUserName(userName);
    }
}
