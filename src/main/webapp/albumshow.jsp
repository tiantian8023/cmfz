<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>
    <script>
        $(function () {
            function alarmFormatter(cellvalue, options, rowdata) {
                console.info("====" + rowdata.cover);
                return ' <img src="/cmfz/img/' + rowdata.cover + '"  style="width:50px;height:50px;" />';
            }

            $("#list11").jqGrid(
                {
                    url: "/cmfz/album/findAllAlbum",
                    datatype: "json",
                    height: 200,
                    cellEdit: true,
                    colNames: ['标题', '封面', '集数', '简介', '创建时间'],
                    colModel: [
                        {name: 'title', editable: true},
                        {name: 'cover', edittype: 'file', editable: true, formatter: alarmFormatter},
                        {name: 'amount', editable: true},
                        {name: 'introduce', editable: true},
                        {name: 'createDate'},
                    ],
                    styleUI: 'Bootstrap',
                    height: 300,
                    autowidth: true,
                    rowNum: 10,
                    rowList: [10, 20, 30],
                    pager: '#pager11',
                    sortname: 'id',
                    viewrecords: true,
                    sortorder: "desc",
                    multiselect: true,
                    editurl: '/cmfz/album/addAlbum',
                    subGrid: true,
                    caption: "文章详情",

                    subGridRowExpanded: function (subgrid_id, albumId) {
                        var subgrid_table_id = subgrid_id + "_t";
                        var pager_id = "p_" + subgrid_table_id;

                        $("#" + subgrid_id).html(
                            "<table id='" + subgrid_table_id + "' class='scroll'>" +
                            "</table><div id='" + pager_id + "' class='scroll'></div>");
                        $("#" + subgrid_table_id).jqGrid(
                            {
                                url: '${pageContext.request.contextPath}/chapter/findAllChapter?albumId=' + albumId,
                                datatype: "json",
                                colNames: ['ID', '章节名称', '大小', '时长', '创建时间', '章节音频'],
                                colModel: [
                                    {name: "id", hidden: true},
                                    {name: "title", width: 30, editable: true},
                                    {name: "size", width: 30},
                                    {name: "duration", width: 30},
                                    {name: "createDate", width: 100},
                                    {
                                        name: "audioPath",
                                        editable: true,
                                        edittype: 'file',
                                        formatter: function (value, options, rows) {
                                            return "<audio controls loop>\n" +
                                                "  <source src='${pageContext.request.contextPath}/mp3/" + rows.audioPath + "' type=\"audio/mpeg\">\n" +
                                                "</audio>"
                                        }
                                    }
                                ],
                                styleUI: 'Bootstrap',
                                height: 200,
                                autowidth: true,
                                rowNum: 20,
                                multiselect: true,
                                editurl: '/cmfz/chapter/addChapter?albumId=' + albumId,
                                pager: pager_id,
                                sortname: 'num',
                                sortorder: "asc",
                            });
                        $("#" + subgrid_table_id).jqGrid('navGrid',
                            "#" + pager_id, {
                                edit: true,
                                add: true,
                                del: true
                            }, {closeAfterEdit: true,},
                            {
                                closeAfterAdd: true,
                                afterSubmit: function (result) {
                                    $.ajaxFileUpload({
                                        url: '${pageContext.request.contextPath}/chapter/upload',
                                        fileElementId: 'audioPath',
                                        data: {
                                            id: result.responseJSON.data
                                        },
                                        type: 'post',
                                        datatype: 'json',
                                        success: function () {
                                            $("#list11").trigger("reloadGrid");
                                        }
                                    })
                                    return "123";
                                }

                            },
                            {closeAfterDel: true});
                    },
                }).navGrid('#pager11', {
                    add: true,
                    edit: true,
                    del: true,
                    search: false,
                    refresh: true
                },
                {
                    closeAfterEdit: true,
                },
                {
                    closeAfterAdd: true,  // 在添加执行完毕后，自动关闭模态框
                    afterSubmit: function (result) {
                        $.ajaxFileUpload({
                            url: '${pageContext.request.contextPath}/album/upload',
                            fileElementId: 'cover',
                            data: {
                                id: result.responseJSON.id
                            },
                            type: 'post',
                            datatype: 'json',
                            success: function () {
                                $("#subgrid_table_id").trigger("reloadGrid");
                            }
                        })
                        return "123";
                    }
                },
                {}
            );
        });

        function exportExcel() {
            $.ajax({
                url: '${pageContext.request.contextPath}/album/exportExcel',
                success: function (result) {
                    alert(result)
                }
            })
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="javascript:$('#pager11').load('/cmfz/albumshow.jsp')">专辑详情</a></li>
    <%--<li role="presentation"><a href="javascript:$('#msg').load('/cmfz/addArticle.jsp')">增加文章</a></li>--%>
    <li role="presentation"><a onclick="exportExcel()">导出专辑</a></li>
</ul>
<table id="list11"></table>
<div id="pager11" style="height: 50px;"></div>
</body>
</html>
