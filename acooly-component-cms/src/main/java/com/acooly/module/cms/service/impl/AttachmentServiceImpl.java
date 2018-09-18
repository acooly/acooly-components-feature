package com.acooly.module.cms.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.cms.dao.AttachmentDao;
import com.acooly.module.cms.domain.Attachment;
import com.acooly.module.cms.service.AttachmentService;
import org.springframework.stereotype.Service;

@Service("attachmentService")
public class AttachmentServiceImpl extends EntityServiceImpl<Attachment, AttachmentDao>
        implements AttachmentService {
}
