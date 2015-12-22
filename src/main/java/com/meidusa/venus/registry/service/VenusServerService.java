package com.meidusa.venus.registry.service;

import com.meidusa.venus.registry.domain.Pagination;
import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.exception.VenusRegistryException;

import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
public interface VenusServerService {

    VenusServer addVenusServer(String hostname, int port) throws VenusRegistryException;

    VenusServer getVenusServer(String hostname, int port) throws VenusRegistryException;

    Pagination<VenusServer> paginate(String hostname, String port, String searchValue, int start, int length);

    List<VenusServer> getAllServer();

}
