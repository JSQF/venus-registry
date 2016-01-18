<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>

    var findRemoteServerDataTable = null;
    $(document).ready(function(){
        findRemoteServerDataTable = $("#datatable_ajax").DataTable({
            "processing": false,
            "serverSide": true,
            "sort": false,
            "paging": false,
            "searching": false,
            "ajax": {
                url: "<%=request.getContextPath()%>/registry/findRemote.json",
                data: function (data) {
                    data.hostname = $("#hiddenServerIp").val();
                    data.port = $("#hiddenServerPort").val();
                },
                type: "POST"
            },
            "aoColumns": [{
                "mData": "name",
                "title": "服务名称"
            }, {
                "mData": "description",
                "title": "服务描述"
            }, {
                "mData": "version",
                "title": "支持版本",
                render: function (data, type, row) {
                    if (!data) {
                        return "无限制";
                    }
                    return data;
                }
            }, {
                "mData": "status",
                "title": "服务名是否存在",
                render: function (data, type, row) {
                    if (data == 0) {
                        return "<span class='label label-sm label-warning'>已存在</span>";
                    } else if (data == 1) {
                        return "<span class='label label-sm label-success'>不存在</span>";
                    } else {
                        return "<span class='label label-sm label-danger'>获取异常(未知)</span>";
                    }
                }
            }, {
                "mData": "active",
                "title": "服务是否激活",
                render: function (data, type, row) {
                    if (data) {
                        return "<span class='label label-sm label-success'>激活</span>";
                    } else {
                        return "<span class='label label-sm label-danger'>未激活</span>";
                    }
                }
            }]


        });
    });

    function findRemoteService() {
        var serverIp = $("#searchServerIp").val();
        var serverPort = $("#searchServerPort").val();

        if (!serverIp) {
            alert("请填写服务IP");
            return;
        }

        if (!serverPort) {
            alert("请填写服务端口");
            return;
        }

        if (isNaN(serverPort)) {
            alert("服务端口格式错误");
            return;
        }

        if (serverPort <= 0) {
            alert("服务端口不能小于等于0");
            return;
        }

        if (!checkIp(serverIp)) {
            alert("服务IP格式错误");
            return;
        }

        $("#hiddenServerIp").val(serverIp);
        $("#hiddenServerPort").val(serverPort)

        findRemoteServerDataTable.draw();


    }

    function doRegistryService() {
        var serverIp = $("#hiddenServerIp").val();
        var serverPort = $("#hiddenServerPort").val();

        if (serverIp == null || serverIp == '' || serverIp == null || serverIp == '') {
            alert("请查询远程服务后注册");
            return;
        }

        var isConfirm = confirm("注册IP:" + serverIp + ", PORT:" + serverPort + " ?");
        if (isConfirm) {
            $.ajax({
                "url": "<%=request.getContextPath()%>/registry/do-registry.json",
                "type": "POST",
                "dataType": "json",
                "beforeSend": function () {
                    $("#registryServiceWatingModal").modal();
                },
                "complete": function () {
                    $("#registryServiceWatingModal").modal('toggle');
                },
                "data": {
                    "hostname": serverIp,
                    "port": serverPort
                },
                success: function (result) {
                    if (result.code == 200) {
                        alert("注册完成")
                    } else {
                        alert(result.message);
                    }
                },
                error: function () {
                    alert("注册异常");
                }
            })
        }

    }

    function checkIp(ip) {
        var exp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
        return ip.match(exp);
    }

</script>