package com.acooly.module.security.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.security.domain.Portallet;

import java.util.List;

/**
 * 桌面管理 Service
 *
 * <p>Date: 2013-05-02 15:40:57
 *
 * @author Acooly Code Generator
 */
public interface PortalletService extends EntityService<Portallet> {

    List<Portallet> queryByUserName(String userName);
}
