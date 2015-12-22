package com.meidusa.venus.registry.web.controller;

import com.meidusa.venus.registry.domain.Pagination;
import com.meidusa.venus.registry.domain.VenusServer;
import com.meidusa.venus.registry.domain.VenusService;
import com.meidusa.venus.registry.domain.VenusServiceMapping;
import com.meidusa.venus.registry.domain.datatables.BusListDataTableRecord;
import com.meidusa.venus.registry.domain.datatables.DataTableResult;
import com.meidusa.venus.registry.domain.datatables.RemoteServiceDataTableRecord;
import com.meidusa.venus.registry.exception.VenusRegistryException;
import com.meidusa.venus.registry.service.RemoteVenusService;
import com.meidusa.venus.registry.service.VenusServerService;
import com.meidusa.venus.registry.service.VenusServiceMappingService;
import com.meidusa.venus.registry.service.VenusServiceService;
import com.meidusa.venus.registry.utils.Constants;
import com.meidusa.venus.registry.utils.DataTableUtils;
import com.meidusa.venus.registry.utils.ResponseUtils;
import com.meidusa.venus.registry.utils.ServiceUtils;
import com.meidusa.venus.registry.web.form.*;
import com.saike.framework.cmdb.CmdbClient;
import com.saike.framework.cmdb.CmdbInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huawei on 12/18/15.
 */
@Controller
@RequestMapping("/registry")
public class RegistryController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RegistryController.class);

    @Resource
    private RemoteVenusService remoteVenusService;

    @Resource
    private VenusServerService venusServerService;

    @Resource
    private VenusServiceService venusServiceService;

    @Resource
    private VenusServiceMappingService venusServiceMappingService;

    @Resource
    private CmdbClient cmdbClient;

    @Resource(name = "cmdbEnviroment")
    private String cmdbEnviroment;

    @RequestMapping("/findRemote")
    public void lookupRemoteVenusServiceAction(HttpServletResponse response, LookupRemoteServiceForm form) throws ServletException, IOException {
        logger.info(form.toString());
        if (form.getHostname() == null || form.getPort() == null) {
            response(response, Constants.CONTENT_TYPE_JSON, DataTableUtils.getDataTableResult(form.getDraw(), 0, 0, null, null).toJson());
            return;
        }
        try {
            List<VenusServiceMapping> venusServiceMappings = this.remoteVenusService.findRemoteService(form.getHostname(), form.getPort());
            List<RemoteServiceDataTableRecord> records = this.remoteVenusService.getRemoteServiceForDataTable(venusServiceMappings);
            response(response, Constants.CONTENT_TYPE_JSON, DataTableUtils.getDataTableResult(form.getDraw(),records.size(), records.size(), records, null).toJson());
        } catch (VenusRegistryException e) {
            logger.error("查询服务异常", e);
            response(response, Constants.CONTENT_TYPE_JSON, DataTableUtils.getDataTableResult(form.getDraw(), 0, 0, null, e.getMessage()).toJson());
        }
    }

    @RequestMapping("/do-registry")
    public void registryServiceAction(HttpServletResponse response, RegistryServiceForm form) throws ServletException, IOException {
        logger.info(form.toString());
        try {
            List<VenusServiceMapping> venusServiceMappings = this.remoteVenusService.findRemoteService(form.getHostname(), form.getPort());

            if (venusServiceMappings == null) {
                responseJson(response, ResponseUtils.getNotFoundResponse("未查询到远端服务"));
                return;
            }
            VenusServer server = this.venusServerService.addVenusServer(form.getHostname(), form.getPort());
            ServiceUtils.registerService(server, venusServiceMappings, venusServiceService, venusServiceMappingService);
            responseJson(response, ResponseUtils.getOkResponse(null));
        } catch (VenusRegistryException e) {
            logger.error("", e);
            responseJson(response, ResponseUtils.getInternalErrorResponse("注册服务失败"));
        }

    }

    @RequestMapping("/list-venus-server")
    public void listVenusServerAction(HttpServletResponse response, ListVenusServerForm form,  @RequestParam(value = "search[value]", required = false) String searchValue) throws ServletException, IOException {
        Pagination<VenusServer> paginate = this.venusServerService.paginate(form.getHostname(), form.getPort(), searchValue, form.getStart(), form.getLength());
        response(response, Constants.CONTENT_TYPE_JSON, DataTableUtils.getDataTableResult(form.getDraw(), paginate.getCount(), paginate.getCount(), paginate.getResult(), null).toJson());
    }

    @RequestMapping("/list-venus-service")
    public void listVenusServiceAction(HttpServletResponse response, ListVenusServiceForm form) throws ServletException, IOException {
        Pagination<VenusService> paginate = this.venusServiceService.paginate(form.getServiceName(), form.getStart(), form.getLength());
        response(response, Constants.CONTENT_TYPE_JSON, DataTableUtils.getDataTableResult(form.getDraw(), paginate.getCount(), paginate.getCount(), paginate.getResult(), null).toJson());
    }

    @RequestMapping("/list-venus-by-id")
    public void listVenusServiceByServerIdAction(HttpServletResponse response, ListVenusMappingForm form, @RequestParam(value = "search[value]", required = false) String searchValue) throws ServletException, IOException{

        Pagination<VenusServiceMapping> paginate = null;
        try {
            paginate = this.venusServiceMappingService.paginate(form.getServerId(), form.getServiceId(), searchValue, form.getStart(), form.getLength());
        } catch (VenusRegistryException e) {
            logger.error("", e);
            response(response, Constants.CONTENT_TYPE_JSON, DataTableUtils.getDataTableResult(form.getDraw(), 0, 0,null, e.getMessage()).toJson());
            return;
        }
        response(response, Constants.CONTENT_TYPE_JSON, DataTableUtils.getDataTableResult(form.getDraw(), paginate.getCount(), paginate.getCount(), paginate.getResult(), null).toJson());
    }

    @RequestMapping("/list-bus")
    public void displayBusListAction(HttpServletResponse response, DatatableForm form) throws ServletException, IOException {

        HashMap<String, String> params = new HashMap<>();
        params.put("appname","saic-venus-bus");
        params.put("env", cmdbEnviroment);

        List<CmdbInformation> cmdbInformations = cmdbClient.request(params);

        List<BusListDataTableRecord> ipLists = new ArrayList<>();

        if (cmdbInformations != null) {
            for(CmdbInformation cmdbInformation: cmdbInformations) {
                BusListDataTableRecord record = new BusListDataTableRecord();
                record.setIp(cmdbInformation.getPrivateIp());
                ipLists.add(record);
            }
        }

        DataTableResult<BusListDataTableRecord> result = new DataTableResult<>();
        result.setRecordsFiltered(ipLists.size());
        result.setRecordsTotal(ipLists.size());
        result.setDraw(form.getDraw());
        result.setData(ipLists);

        response(response, Constants.CONTENT_TYPE_JSON, result.toJson());

    }

    @RequestMapping("/update-mapping")
    public void updateMappingAction(HttpServletResponse response, UpdateMappingForm form) throws ServletException, IOException{
        try{
            boolean active = (form.getActive() != null && form.getActive()  == 1)? true: false;
            this.venusServiceMappingService.updateMapping(form.getId(), form.getVersion(), active, false);
        }catch (VenusRegistryException e) {
            responseJson(response, ResponseUtils.getInternalErrorResponse("更新异常"));
        }
        responseJson(response, ResponseUtils.getOkResponse(null));

    }

    @RequestMapping("/sync-on")
    public  void updateMappingSyncOn(HttpServletResponse response, Integer id) throws ServletException, IOException {
        try{
            this.venusServiceMappingService.syncOn(id);
        }catch (Exception e){
            logger.error("", e);
            responseJson(response, ResponseUtils.getInternalErrorResponse(e.getMessage()));
        }

        responseJson(response, ResponseUtils.getOkResponse(null));
    }

}
