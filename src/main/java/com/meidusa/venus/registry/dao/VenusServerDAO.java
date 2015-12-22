package com.meidusa.venus.registry.dao;

import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.exception.VenusRegistryException;

import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
public interface VenusServerDAO {

    void addServer(String hostname, int port) throws VenusRegistryException;

    VenusServer getServer(String hostname, int port) throws VenusRegistryException;

    List<VenusServer> paginate(String hostname, String port, String searchValue, int start, int length);

    int count(String hostname, String port, String searchValue);

    List<VenusServer> getAllServer();

}
