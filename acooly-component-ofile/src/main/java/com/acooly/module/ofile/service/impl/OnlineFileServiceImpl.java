package com.acooly.module.ofile.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import com.acooly.module.obs.ObsService;
import com.acooly.module.ofile.OFileProperties;
import com.acooly.module.ofile.dao.OnlineFileDao;
import com.acooly.module.ofile.domain.OnlineFile;
import com.acooly.module.ofile.enums.AccessTypeEnum;
import com.acooly.module.ofile.service.OnlineFileService;
import com.acooly.module.ofile.support.OfileSupportService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OnlineFileServiceImpl extends EntityServiceImpl<OnlineFile, OnlineFileDao>
        implements OnlineFileService {
    @Autowired
    private OFileProperties oFileProperties;

    @Autowired
    private ObsService obsService;

    @Autowired(required = false)
    private OfileSupportService ofileSupportService;

    @Override
    public OnlineFile findById(Long id) {
        OnlineFile onlineFile = this.get(id);
        return fillAccessUrl(onlineFile);
    }

    /**
     * 通过objectId查询，返回结果会按存储方式填充对应的可访问路径
     *
     * @param objectId
     * @return
     */
    @Override
    public OnlineFile findByObjectId(String objectId) {
        return getEntityDao().findByObjectId(objectId);
    }

    /**
     * 通过objectId查询，返回结果会按存储方式填充对应的可访问路径
     *
     * @param objectIds
     * @return
     */
    @Override
    public List<OnlineFile> findByObjectIds(List<String> objectIds) {
        List<OnlineFile>  onlineFile = getEntityDao().findByObjectIdIn(objectIds);
        return fillAccessUrl(onlineFile);
    }

    @Override
    public List<OnlineFile> findByobjectId(List<String> objectId) {
        return findByObjectIds(objectId);
    }


    /**
     * 通过BucketName查询，返回结果会按存储方式填充对应的可访问路径
     *
     * @param ids
     * @return
     */
    @Override
    public List<OnlineFile> findById(List<Long> ids) {
        List onlineFiles = getEntityDao().findAllById(ids);
        return fillAccessUrl(onlineFiles);
    }

    @Override
    public OnlineFile findByFilePathAndBucket(String filePath, String bucketName) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_filePath", filePath);
        if (Strings.isNotBlank(bucketName)) {
            map.put("EQ_bucketName", bucketName);
        }
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("createTime", false);
        List<OnlineFile> list = this.getEntityDao().list(map, sortMap);
        if (Collections3.isNotEmpty(list)) {
            return fillAccessUrl(list.get(0));
        }
        return null;
    }


    private List<OnlineFile> fillAccessUrl(List<OnlineFile> onlineFile) {
        for (OnlineFile file : onlineFile) {
            fillAccessUrl(file);
        }
        return onlineFile;
    };
    private OnlineFile fillAccessUrl(OnlineFile onlineFile) {
        if (onlineFile == null) {
            return null;
        }
        if (AccessTypeEnum.LOCAL_STORAGE.equals(onlineFile.getAccessType()) || onlineFile.getAccessType() == null) {
            //本地存储时，不需要做访问地址转换
            return onlineFile;
        }
        String accessUrl = obsService.getUrlByKey(onlineFile.getFilePath());
        //公网可访问的缩略图地址，按ofile配置进行缩放
        String accessThumbnailUrl = getAccessThumbnailUrl(accessUrl);
        if (AccessTypeEnum.OBS_STS.equals(onlineFile.getAccessType())) {
            accessUrl = obsService.getAccessUrlBySts(onlineFile.getBucketName(), onlineFile.getFilePath(), null, ofileSupportService.getExpireDate());
            accessThumbnailUrl = obsService.getAccessUrlBySts(onlineFile.getBucketName(), onlineFile.getFilePath(), ofileSupportService.getProcess(onlineFile.getFileType()), ofileSupportService.getExpireDate());
        }
        onlineFile.setAccessUrl(accessUrl);
        onlineFile.setAccessThumbnailUrl(accessThumbnailUrl);
        return onlineFile;
    }

    protected String getAccessThumbnailUrl(String accessUrl) {
        return accessUrl.concat("?x-oss-process=image/resize,w_").concat(String.valueOf(oFileProperties.getThumbnailSize()))
                .concat(",").concat("h_").concat(String.valueOf(oFileProperties.getThumbnailSize()));
    }
}
