package com.meidusa.venus.registry.service.impl;

import com.meidusa.toolkit.common.util.StringUtil;
import com.meidusa.venus.registry.dao.VenusServiceMappingDAO;
import com.meidusa.venus.registry.domain.Pagination;
import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.domain.VenusService;
import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.registry.exception.VenusRegistryException;
import com.meidusa.venus.registry.service.VenusServiceMappingService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huawei on 12/21/15.
 */
@Component
public class DefaultVenusServiceMappingService implements VenusServiceMappingService {

    @Resource
    private VenusServiceMappingDAO venusServiceMappingDAO;

    @Override
    public VenusServiceMapping getMapping(int serverId, int serviceId) throws VenusRegistryException {
        return this.venusServiceMappingDAO.getServiceMapping(serverId, serviceId);
    }

    @Override
    public void addMapping(VenusServer server, VenusService service, VenusServiceMapping mapping) throws VenusRegistryException {

        Assert.notNull(server, "服务器信息不能为空");
        Assert.notNull(service, "服务信息不能为空");
        Assert.notNull(mapping, "映射信息不能为空");

        Assert.notNull(server.getId(), "服务器ID不能为空");
        Assert.notNull(service.getId(), "服务ID不能为空");

        this.venusServiceMappingDAO.addMapping(server.getId(), service.getId(), mapping.getVersion(), mapping.isActive(), true);

    }

    @Override
    public void updateMapping(VenusServer server, VenusService service, VenusServiceMapping mapping) throws VenusRegistryException {

        Assert.notNull(server, "服务器信息不能为空");
        Assert.notNull(service, "服务信息不能为空");
        Assert.notNull(mapping, "映射信息不能为空");

        Assert.notNull(server.getId(), "服务器ID不能为空");
        Assert.notNull(service.getId(), "服务ID不能为空");

        this.venusServiceMappingDAO.updateMapping(server.getId(), service.getId(), mapping.getVersion(), mapping.isActive());

    }

    @Override
    public void updateMapping(Integer mappingId, String version, Boolean active, Boolean sync) throws VenusRegistryException {
        this.venusServiceMappingDAO.updateMapping(mappingId, version, active, sync);
    }

    @Override
    public Pagination<VenusServiceMapping> paginate(Integer serverId, Integer serviceId, String searchValue, int start, int length) throws VenusRegistryException {
        Pagination<VenusServiceMapping> pagination = new Pagination<>();

        try {
            pagination.setResult(this.venusServiceMappingDAO.paginate(serverId, serviceId, searchValue, start, length));
            pagination.setCount(this.venusServiceMappingDAO.count(serverId, serviceId, searchValue));
        } catch (VenusRegistryException e) {
            throw e;
        }
        return pagination;
    }

    @Override
    public List<VenusServiceMapping> getAllActivityServiceMapping() {
        VenusServiceMapping mapping = new VenusServiceMapping();
        mapping.setActive(true);
        return this.venusServiceMappingDAO.getServiceMapping(mapping);
    }

    @Override
    public VenusServiceMapping getMapping(String name, int version) {
        VenusServiceMapping mapping = new VenusServiceMapping();
        VenusService service = new VenusService();
        service.setName(name);
        mapping.setService(service);

        List<VenusServiceMapping> mappingList = this.venusServiceMappingDAO.getServiceMapping(mapping);

        if (mappingList == null || mappingList.size() == 0) {
            return null;
        }

        for (VenusServiceMapping mapping1 : mappingList) {
            if (!StringUtils.hasLength(mapping1.getVersion())) {
                return mapping1;
            } else if (mapping1.getVersion().startsWith("{") && mapping1.getVersion().endsWith("}")) {
                String versionRange = mapping1.getVersion().substring(1, mapping1.getVersion().length() - 2);
                String[] versionRangeArray = versionRange.split(",");
                if (versionRangeArray == null || versionRangeArray.length <= 0) {
                    continue;
                }
                for (String version1 : versionRangeArray) {
                    if (Integer.parseInt(version1) == version) {
                        return mapping1;
                    }
                }
            } else if (mapping1.getVersion().startsWith("[") && mapping1.getVersion().endsWith("]")) {
                String versionRange = mapping1.getVersion().substring(1, mapping1.getVersion().length() - 2);
                String[] versionRangeArray = versionRange.split(",");
                if (versionRangeArray == null || versionRangeArray.length != 2) {
                    continue;
                }

                if (version >= Integer.parseInt(versionRangeArray[0].trim()) && version <= Integer.parseInt(versionRangeArray[1].trim())) {
                    return mapping1;
                }

            }

        }

        return null;
    }

    @Override
    public List<VenusServiceMapping> getMappings(Integer serverId, Integer serviceId) {

        VenusServiceMapping mapping = new VenusServiceMapping();
        VenusServer server = new VenusServer();
        VenusService service = new VenusService();
        server.setId(serverId);
        service.setId(serviceId);

        mapping.setServer(server);
        mapping.setService(service);

        return this.venusServiceMappingDAO.getServiceMapping(mapping);
    }

    @Override
    public void deleteMapping(Integer id) {
        this.venusServiceMappingDAO.deleteById(id);
    }

    @Override
    public void syncOn(Integer id) {
        this.venusServiceMappingDAO.updateMappingSyncStatus(id, true);
    }
}
