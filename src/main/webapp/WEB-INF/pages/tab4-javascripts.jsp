<%--
  Created by IntelliJ IDEA.
  User: huawei
  Date: 12/22/15
  Time: 8:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>

    var busListDataTable = null;

    $("#document").ready(function(){
        busListDataTable = $("#bus_list").DataTable({
            "processing": false,
            "serverSide": true,
            "sort": false,
            "paging": false,
            "searching": false,
            "deferLoading": 30,
            "ajax": {
                url: "<%=request.getContextPath()%>/registry/list-bus.json",
            },
            "aoColumns": [{
                "mData": "ip",
                "title": "IP"
            }]
        });

    });


</script>