package com.meidusa.venus.registry.service;

import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.registry.domain.datatables.RemoteServiceDataTableRecord;
import com.meidusa.venus.registry.exception.VenusRegistryException;

import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
public interface RemoteVenusService {

    List<VenusServiceMapping> findRemoteService(String hostname, int port) throws VenusRegistryException;

    List<RemoteServiceDataTableRecord> getRemoteServiceForDataTable(List<VenusServiceMapping> mappingList);

}
