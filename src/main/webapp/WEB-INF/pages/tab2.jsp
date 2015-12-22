<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="tab-pane fade" id="tab_1_2">
    <div class="portlet">
        <div class="portlet light bordered">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-equalizer font-red-sunglo"></i>
                    <span class="caption-subject font-red-sunglo bold uppercase">查询条件</span>
                </div>
            </div>
            <div class="portlet-body form">
                <!-- BEGIN FORM-->
                <form action="#" class="form-horizontal">
                    <div class="form-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="control-label col-md-3">IP</label>

                                    <div class="col-md-9">
                                        <input type="text" class="form-control" value=""
                                               placeholder="服务IP" id="searchManagerServerIp">
                                    </div>
                                </div>
                            </div>
                            <!--/span-->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="control-label col-md-3">端口</label>

                                    <div class="col-md-9">
                                        <input type="text" class="form-control"
                                               value="16800"
                                               placeholder="服务端口" id="searchManagerServerPort">
                                    </div>
                                </div>
                            </div>
                            <!--/span-->
                        </div>
                        <!--/row-->
                    </div>
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="row">
                                    <div class="col-md-offset-3 col-md-9 ">
                                        <button type="button" class="btn green"
                                                onclick="queryVenusServer()">查询
                                        </button>
                                        <button type="reset" class="btn default">重置
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                            </div>
                        </div>
                    </div>
                </form>
                <!-- END FORM-->
            </div>
        </div>
        <div class="portlet-body">
            <div class="table-container">
                <table class="table table-striped table-bordered table-hover"
                       id="manager_server" width="100%">
                    <tr role="row" class="heading">
                        <th width="10%"></th>
                        <th width="30%">

                        </th>
                        <th width="50%">
                        </th>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
