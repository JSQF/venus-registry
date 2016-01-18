<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>

    var serviceManagerDataTable = null;
    var serverTabTable = null;

    $(document).ready(function () {
        serviceManagerDataTable = $("#manager_service").dataTable({
            "processing": false,
            "serverSide": true,
            "sort": false,
            "paging": true,
            "searching": false,
            "autoWidth": true,
            "ajax": {
                url: "<%=request.getContextPath()%>/registry/list-venus-service.json",
                data: function (d) {
                    d.serviceName = $("#searchManagerServiceName").val();
                },
                type: "POST"

            },
            "columns": [{
                "mData": "id",
                "title": "ID",
                "width": '10%',
                "render": function (data, type, row) {
                    return "<span class='row-details row-details-close' data_id='" + data + "'></span>&nbsp;" + data;
                }
            }, {
                "mData": "name",
                "title": "服务名称",
                "width": '50%'
            }, {
                "mData": "description",
                "title": "服务描述",
                "width": '30%'
            }]
        });

        $('#manager_service').on('click', ' tbody td .row-details', function () {
            var trs = $("#manager_service").children('tbody').children('tr');
            var nTr = $(this).parents('tr')[0];
            if (serviceManagerDataTable.fnIsOpen(nTr)) {
                $(this).addClass("row-details-close").removeClass("row-details-open");
                serviceManagerDataTable.fnClose(nTr);
            } else {
                $(".row-details").addClass("row-details-close").removeClass("row-details-open");
                trs.each(function () {
                    var tr = $(this);
                    if (serviceManagerDataTable.fnIsOpen(tr)) {

                        serviceManagerDataTable.fnClose(tr);
                    }

                })
                $(this).addClass("row-details-open").removeClass("row-details-close");
                var html = "<table class='table table-striped table-bordered table-hover' id='server_tab_table' width='100%'> <tr role='row' class='heading'> <th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr> </table>";

                serviceManagerDataTable.fnOpen(nTr, html, "info_row");

                var serviceId = $(this).attr("data_id");
                var table1 = $("#server_tab_table");

                serverTabTable = table1.dataTable({
                    "processing": false,
                    "serverSide": true,
                    "sort": false,
                    "paging": true,
                    "searching": true,
                    "autoWidth": true,
                    "ajax": {
                        url: "<%=request.getContextPath()%>/registry/list-venus-by-id.json",
                        data: function (data) {
                            data.serviceId = serviceId;
                        },
                        type: "POST"
                    },
                    "columns": [{
                        "mData": "server.hostname",
                        "title": "主机",
                        "width": '10%',
                    }, {
                        "mData": "server.port",
                        "title": "端口",
                        "width": '30%',
                    }, {
                        "mData": "version",
                        "title": "版本",
                        "width": '20%',
                        "render": function (data) {
                            if (!data) {
                                return "全版本";
                            }
                            return data;
                        }
                    }, {
                        "mData": "active",
                        "title": "是否启用",
                        "width": '10%',
                        "render": function (data) {
                            if (data) {
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    }, {
                        "mData": "sync",
                        "title": "是否与服务端同步",
                        "width": '10%',
                        "render": function (data) {
                            if (data) {
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    }, {
                        "title": "编辑",
                        "width": '10%',
                        "render": function (data, type, row) {
                            var editButton = "<a class='edit' href='javascript:;' data_id='" + row.id + "'>修改</a>";

                            return editButton;
                        }
                    }, {
                        "title": "操作",
                        "width": '10%',
                        "render": function (data, type, row) {
                            var syncButton = null;
                            if (row.sync) {
                                syncButton = "";
                            } else {
                                syncButton = "<a class='sync' sync_id='" + row.id + "'>启用同步</a>";
                            }
                            var delButton = "<a class='edit' onclick='" + row.id + "'>删除</a>";
                            return syncButton;
                        }
                    }]
                });

            }

            var nServiceEditing = null;
            $("#server_tab_table").on('draw.dt', function () {
                nServiceEditing = null;
            });

            serverTabTable.on('click', '.edit', function (e) {
                e.preventDefault();
                var nRow = $(this).parents('tr')[0];
                if (nServiceEditing !== null && nServiceEditing != nRow) {
                    restoreRow(serverTabTable, nServiceEditing);
                    editRow(serverTabTable, nRow, $(this).attr("data_id"));
                    nServiceEditing = nRow;
                } else if (nServiceEditing == nRow && this.innerHTML == "保存") {
                    if (confirm("修改后该服务将不与服务端同步状态, 确定修改吗?")) {
                        if (updateMapping(serverTabTable, nServiceEditing, $(this).attr("data_id"))) {
                            nServiceEditing = null;
                        }

                    }
                } else {
                    editRow(serverTabTable, nRow, $(this).attr("data_id"));
                    nServiceEditing = nRow;
                }
            });

            serverTabTable.on('click', '.cancel', function (e) {
                restoreRow(serverTabTable, nServiceEditing);
                nServiceEditing = null;
            });

            serverTabTable.on('click', '.sync', function () {
                if (confirm("确定恢复与服务端同步吗?")) {
                    var mappingId = $(this).attr("sync_id");
                    if (mappingId != null && !isNaN(mappingId)) {
                        $.ajax({
                            "url": "<%=request.getContextPath()%>/registry/sync-on.json",
                            "type":"POST",
                            "dataType":"json",
                            "data": {
                                "id":mappingId
                            },
                            success: function(result){
                                if (result.code ==200) {
                                    alert("启用同步成功");
                                    serverTabTable.fnDraw();
                                }else{
                                    alert(result.message);
                                }
                            },
                            error: function(){
                                alert("网络异常")
                            }
                        });
                    }
                }
            })


        });
    });

    function queryVenusService() {
        serviceManagerDataTable.fnDraw();
    }
</script>