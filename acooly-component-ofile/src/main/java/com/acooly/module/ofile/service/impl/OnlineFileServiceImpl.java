package com.acooly.module.ofile.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.ofile.dao.OnlineFileDao;
import com.acooly.module.ofile.domain.OnlineFile;
import com.acooly.module.ofile.service.OnlineFileService;
import org.springframework.stereotype.Service;

@Service
public class OnlineFileServiceImpl extends EntityServiceImpl<OnlineFile, OnlineFileDao>
        implements OnlineFileService {
}
