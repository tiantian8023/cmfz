<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <link href="${pageContext.request.contextPath}/statics/boot/css/bootstrap.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/boot/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>
    <script>
        $(function () {



            /*function alarmFormatter(cellvalue, options, rowdata) {
                console.info("===="+rowdata.cover);
                return ' <img src="/cmfz/img/'+rowdata.cover+'"  style="width:50px;height:50px;" />';
            }*/
            $("#list11").jqGrid(
                {
                    url: "/cmfz/article/findAllArticle",
                    datatype: "json",
                    height: 200,
                    cellEdit: true,
                    colNames: ['Id', '标题', '作者', '发布日期', '内容', '操作'],
                    colModel: [
                        {name: 'id', hidden: true, editable: true, key: true},
                        {name: 'title'},
                        {name: 'author'},
                        {name: 'publishDate'},
                        {name: 'content'},
                        {
                            name: 'caozuo', edittype: 'button', formatter: function (cellvalue, options, rowdata) {
                                var detail = "<a class='btn btn-primary' onclick=\"openModal('edit','" + rowdata.id + "')\">修改</a> ";
                                return detail;
                            }
                        }
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
                    // multiselect : true,
                    editurl: '/cmfz/article/addAriticle',
                }).navGrid('#pager11', {
                    add: false,
                    edit: false,
                    del: true,
                    search: false,
                    refresh: true
                },
                {
                    closeAfterEdit: true,
                },
                {
                    closeAfterAdd: true,  // 在添加执行完毕后，自动关闭模态框
                },
                {}
            );
        });

        function openModal(oper, rowId) {
            // 获取单行数据
            var rowData = $("#list11").jqGrid('getRowData', rowId);
            $("#list11").jqGrid('getRowData', rowId);
            $("#article-oper").val(oper);
            // 数据回显
            $("#md").modal("show");
            KindEditor.html($("#editor-id"), "");
            $("#article-title").val(rowData.title);
            $("#article-author").val(rowData.author);
            $("#article-id").val(rowId);
            KindEditor.html($("#editor-id"), rowData.content);
            // 打开模态框
            KindEditor.create('#editor-id', {
                width: '100%',
                // fullscreenMode:true,
                allowFileManager: true, //true时显示浏览远程服务器按钮。
                fontSizeTable: [],  //指定文字大小。数据类型: Array
                fileManagerJson: '${pageContext.request.contextPath}/article/brower', //指定浏览远程图片的服务器端程序
                uploadJson: "${pageContext.request.contextPath}/article/upload",
                fillDescAfterUploadImage: true,
                resizeType: 1,
                afterBlur: function () {
                    this.sync();
                },
                afterChange: function () {
                    this.sync();
                }
            });
        }

        function save() {
            $.ajax({
                url: '${pageContext.request.contextPath}/article/addAriticle',
                type: 'post',
                data: $("#article-form").serialize(),
                datatype: 'json',
                success: function (data) {
                    $("#md").modal("hide");
                    $("#list11").trigger("reloadGrid")
                },
                error: function (data) {

                }
            })
        }
    </script>
</head>
<body>
<%--模态框开始--%>
<div class="modal fade" id="md">
    <!-- dialog用于控制模态框的大小 -->
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <!--关闭按钮-->
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3>修改文章</h3>
            </div>
            <div class="modal-body">
                <form id="article-form">
                    <input type="hidden" name="oper" id="article-oper">
                    <input type="hidden" name="id" id="article-id">
                    标题:<input type="text" name="title" id="article-title">
                    作者:<input type="text" name="author" id="article-author">
                    <textarea id="editor-id" name="content" style="width:700px;height:300px;">

                         </textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" onclick="save()" class="btn btn-success">保存</button>
                <button type="button" class="btn btn-danger">关闭</button>
            </div>
        </div>
    </div>
</div>
<%--模态框结束--%>
<div id="div">
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="javascript:$('#div').load('/cmfz/articshow.jsp')">文章详情</a></li>
        <%--<li role="presentation"><a href="javascript:$('#msg').load('/cmfz/addArticle.jsp')">增加文章</a></li>--%>
        <li role="presentation"><a onclick="openModal('add')">增加文章</a></li>
    </ul>
    <div id="msg">
        <table id="list11"></table>
        <div id="pager11" style="height: 50px;"></div>
    </div>
</div>

</body>
</html>
