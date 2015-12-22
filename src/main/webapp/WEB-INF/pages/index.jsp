<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<%@include file="head.jsp" %>
<!-- BEGIN BODY -->
<body class="page-header-fixed page-quick-sidebar-over-content">
<!-- BEGIN HEADER -->
<div class="page-header navbar navbar-fixed-top">
    <!-- BEGIN HEADER INNER -->
    <div class="page-header-inner">
        <!-- BEGIN LOGO -->
        <div class="page-logo">
            <a href="<%=request.getContextPath()%>/index.htm">
                <img src="" alt="logo" class="logo-default"/>
            </a>

            <div class="menu-toggler sidebar-toggler hide">
                <!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
            </div>
        </div>
        <!-- END LOGO -->
        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse"
           data-target=".navbar-collapse">
        </a>
        <!-- END RESPONSIVE MENU TOGGLER -->
        <!-- BEGIN TOP NAVIGATION MENU -->
        <div class="top-menu">

        </div>
        <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END HEADER INNER -->
</div>
<!-- END HEADER -->
<div class="clearfix">
</div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <%@include file="sidebar.jsp" %>
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <div class="page-content">
            <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
            <!-- /.modal -->
            <div class="modal fade" id="registryServiceWatingModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">注册中...</h4>
                        </div>
                        <div class="modal-body">
                            <img src="<%=request.getContextPath()%>/assets/wait.gif"/>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>

            <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
            <!-- BEGIN PAGE HEADER-->
            <h3 class="page-title">
                服务管理
                <small>发现 & 管理服务</small>
            </h3>
            <div class="page-bar">
                <ul class="page-breadcrumb">
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="<%=request.getContextPath()%>/index.htm">服务管理</a>
                        <i class="fa fa-angle-right"></i>
                    </li>

                </ul>
            </div>
            <!-- END PAGE HEADER-->
            <!-- BEGIN PAGE CONTENT-->
            <div class="row">
                <div class="col-md-12">
                    <div class="portlet box green">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa fa-gift"></i>服务管理
                            </div>
                        </div>
                        <div class="portlet-body">
                            <ul class="nav nav-tabs">
                                <li class="active" onclick="doSometing(1)">
                                    <a href="#tab_1_1" data-toggle="tab">
                                        服务发现 </a>
                                </li>
                                <li onclick="doSometing(2)">
                                    <a href="#tab_1_2" data-toggle="tab">
                                        服务器管理 </a>
                                </li>

                                <li onclick="doSometing(3)">
                                    <a href="#tab_1_3" data-toggle="tab">
                                        服务管理 </a>
                                </li>


                                <li onclick="doSometing(4)">
                                    <a href="#tab_1_4" data-toggle="tab">
                                        bus列表 </a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <%@include file="tab1.jsp" %>
                                <%@include file="tab2.jsp" %>
                                <%@include file="tab3.jsp" %>
                                <%@include file="tab4.jsp" %>
                            </div>
                            <div class="clearfix margin-bottom-20">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- END PAGE CONTENT-->
        </div>
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<%@include file="footer.jsp" %>
<%@include file="javascripts.jsp" %>
<%@include file="tab1-javascripts.jsp" %>
<%@include file="tab2-javascripts.jsp" %>
<%@include file="tab3-javascripts.jsp" %>
<%@include file="tab4-javascripts.jsp" %>
<script type="text/javascript">
    function doSometing(tabIdx) {
        if (tabIdx == 1) {
            $("#hiddenServerIp").val("");
            $("#hiddenServerPort").val("");
            $("#searchServerIp").val("");
            $("#searchServerPort").val("16800");
            findRemoteServerDataTable.clear().draw();
        } else if (tabIdx == 2) {
            $("#searchManagerServerIp").val("");
            $("#searchManagerServerPort").val("16800");
            serverManagerDataTable.fnDraw();
        } else if (tabIdx == 3) {
            $("#searchManagerServiceName").val("");
            serviceManagerDataTable.fnDraw();
        } else if (tabIdx == 4) {
            busListDataTable.clear().draw();
        } else {
            alert("illegal num:" + tabIdx);
        }
    }

</script>
<input type="hidden" id="hiddenServerIp">
<input type="hidden" id="hiddenServerPort">
</body>
<!-- END BODY -->
</html>