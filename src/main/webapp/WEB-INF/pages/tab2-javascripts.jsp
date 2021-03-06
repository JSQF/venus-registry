<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var serverManagerDataTable = null;
    $(document).ready(function () {
        serverManagerDataTable = $("#manager_server").dataTable({
            "processing": false,
            "serverSide": true,
            "sort": false,
            "paging": true,
            "searching": true,
            "autoWidth": true,
            "ajax": {
                url: "<%=request.getContextPath()%>/registry/list-venus-server.json",
                data: function (data) {
                    data.hostname = $("#searchManagerServerIp").val();
                    data.port = $("#searchManagerServerPort").val();
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
                "mData": "hostname",
                "title": "服务IP",
                "width": '50%'
            }, {
                "mData": "port",
                "title": "端口",
                "width": '30%'
            }]
        });

        $('#manager_server').on('click', ' tbody td .row-details', function () {
            var trs = $("#manager_server").children('tbody').children('tr');
            var nTr = $(this).parents('tr')[0];
            if (serverManagerDataTable.fnIsOpen(nTr)) {
                $(this).addClass("row-details-close").removeClass("row-details-open");
                serverManagerDataTable.fnClose(nTr);
            } else {
                $(".row-details").addClass("row-details-close").removeClass("row-details-open");
                trs.each(function () {
                    var tr = $(this);
                    if (serverManagerDataTable.fnIsOpen(tr)) {

                        serverManagerDataTable.fnClose(tr);
                    }

                })
                $(this).addClass("row-details-open").removeClass("row-details-close");
                var html = "<table class='table table-striped table-bordered table-hover' id='service_tab_table' width='100%'> <tr role='row' class='heading'> <th width='10%'></th><th width='10%'></th><th width='10%'></th><th width='10%'></th> <th width='30%'></th> <th width='50%'></th><th width='50%'></th> </tr> </table>";

                serverManagerDataTable.fnOpen(nTr, html, "info_row");

                var serverId = $(this).attr("data_id");
                var table = $("#service_tab_table");
                var serviceTabTable = table.dataTable({
                    "processing": false,
                    "serverSide": true,
                    "sort": false,
                    "paging": true,
                    "searching": true,
                    "autoWidth": true,
                    "ajax": {
                        url: "<%=request.getContextPath()%>/registry/list-venus-by-id.json",
                        data: function (data) {
                            data.serverId = serverId;
                        },
                        type: "POST"
                    },
                    "columns": [{
                        "mData": "service.name",
                        "title": "服务名",
                        "width": '10%',
                    }, {
                        "mData": "service.description",
                        "title": "服务描述",
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
            table.on('draw.dt', function () {
                nServiceEditing = null;
            });

            serviceTabTable.on('click', '.edit', function (e) {
                e.preventDefault();
                var nRow = $(this).parents('tr')[0];
                if (nServiceEditing !== null && nServiceEditing != nRow) {
                    restoreRow(serviceTabTable, nServiceEditing);
                    editRow(serviceTabTable, nRow, $(this).attr("data_id"));
                    nServiceEditing = nRow;
                } else if (nServiceEditing == nRow && this.innerHTML == "保存") {
                    if (confirm("修改后该服务将不与服务端同步状态, 确定修改吗?")) {
                        if (updateMapping(serviceTabTable, nServiceEditing, $(this).attr("data_id"))) {
                            nServiceEditing = null;
                        }

                    }
                } else {
                    editRow(serviceTabTable, nRow, $(this).attr("data_id"));
                    nServiceEditing = nRow;
                }
            });

            serviceTabTable.on('click', '.cancel', function (e) {
                restoreRow(serviceTabTable, nServiceEditing);
                nServiceEditing = null;
            });

            serviceTabTable.on('click', '.sync', function () {
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
                                    serviceTabTable.fnDraw();
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

    var record = new Array();

    function restoreRow(oTable, nRow) {
        oTable.fnUpdate(record[0], nRow, 2, false);
        oTable.fnUpdate(record[1], nRow, 3, false);
        oTable.fnUpdate(record[2], nRow, 5, false);
    }

    function editRow(oTable, nRow, dataId) {
        var aData = oTable.fnGetData(nRow);
        var jqTds = $('>td', nRow);

        var version = jqTds[2].innerHTML;

        jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="' + (version == '全版本' ? "" : version) + '"><span class="help-block">空-全版本, {}-指定版本逗号分隔,例:{1,2,3},[]-版本范围,例如[1,3]</span>';
        var isActiveDesc = jqTds[3].innerHTML;

        if (isActiveDesc == '是') {
            jqTds[3].innerHTML = '<select class="form-control input-small">' +
                    '<option value="0" >否</option>' +
                    '<option value="1" selected="selected">是</option>' +
                    '</select>';
        } else {
            jqTds[3].innerHTML = '<select class="form-control input-small">' +
                    '<option value="0" selected="selected">否</option>' +
                    '<option value="1" >是</option>' +
                    '</select>';
        }
        record[0] = version;
        record[1] = isActiveDesc;
        record[2] = jqTds[5].innerHTML;

        jqTds[5].innerHTML = '<a class="edit" data_id="' + dataId + '" href="javascript:;">保存</a>&nbsp;<a class="cancel" href="javascript:;">取消</a>';
    }

    function updateMapping(oTable, nRow, id) {

        var saveResult = false;

        var jqInputs = $('input', nRow);
        var selection = $('select', nRow);

        var version = jqInputs[0].value;
        var active = selection[0].value;

        if (version != '') {
            if (version.startsWith("[") && version.endsWith("]")) {
                var range = version.substring(1, version.length - 1);
                var rangeArray = range.split(',');
                if (rangeArray == null || rangeArray.length != 2) {
                    alert("版本信息格式不正确");
                    return saveResult;
                }
                if (isNaN(rangeArray[0])) {
                    alert("版本信息格式不正确");
                    return saveResult;
                }
                if (isNaN(rangeArray[1])) {
                    alert("版本信息格式不正确");
                    return saveResult;
                }

                if (parseInt(rangeArray[0]) > parseInt(rangeArray[1])) {
                    alert("起始值必须小于结束值");
                    return saveResult;
                }
            } else if (version.startsWith("{") && version.endsWith("}")) {
                var range = version.substring(1, version.length - 1);
                var rangeArray = range.split(',');
                for (var i = 0; i < rangeArray.length; i++) {
                    if (isNaN(rangeArray[i])) {
                        alert("版本信息格式不正确");
                        return saveResult;
                    }
                }
            } else {
                alert("版本信息格式不正确");
                return saveResult;
            }
        }
        $.ajax({
            "url": "<%=request.getContextPath()%>/registry/update-mapping.json",
            "type": "post",
            "dataType": "json",
            "data": {
                "id": id,
                "version": version,
                "active": active
            },
            success: function (result) {
                if (result.code == 200) {
                    alert("更新成功!");
                    oTable.fnDraw();
                    saveResult = true;
                } else {
                    alert("更新失败");
                }
            },
            error: function () {
                alert("网络异常");
            }
        });

        return saveResult;
    }

    function queryVenusServer() {
        serverManagerDataTable.fnDraw();
    }
</script>