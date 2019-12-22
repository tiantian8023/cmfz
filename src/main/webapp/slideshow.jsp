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

            $('#tt').jqGrid({
                url: '/cmfz/slide/findAllSlide',
                datatype: 'json',
                colNames: ['名称', '封面', '描述', '创建日期', '状态'],
                cellEdit: true, // 开启表格编辑模式
                cellurl: '/bootstrap_day4/emp/editEmp.do', // 指定编辑后提交的路径
                colModel: [
                    {name: 'title', editable: true},
                    {
                        name: 'cover',

                        editable: true,// 开启列编辑模式
                        edittype: 'file', // 指定编辑当前列的类型
                        formatter: alarmFormatter
                    },
                    {
                        name: 'title',
                        editable: true,
                        editrules: {number: true, required: true} // 规定单元格编辑内容的类型，类似于表单验证

                    },
                    {name: 'create_date'},
                    {
                        name: 'status', editable: true
                    }

                ],
                styleUI: "Bootstrap",
                height: 330,
                autowidth: true,
                multiselect: true, // 在首列前加入一列checkbox
                pager: '#pg', // 指定分页的工具栏
                rowList: [2, 10, 20, 30, 40, 50], // 指定每页显示信息条数的列表选项，默认每页展示20条
                rowNum: 2, // 指定默认每页显示的条数，值必须是rowList中的一个
                viewrecords: true, // 指定是否显示信息的总条数
                page: 1, // 指定默认显示的是第几页
                subGrid: true, // 开启二级表格
                editurl: '/cmfz/slide/addSlide', // 指定模态框中表单编辑后提交的路径
                /**
                 * 第一个参数： 指定初始化工具栏的div选择器
                 * 第二个参数： 控制显示哪些工具栏，默认都显示
                 *
                 *
                 */
            }).navGrid('#pg', {add: true, edit: true, del: true, search: false, refresh: true},
                {
                    closeAfterEdit: true, // 在编辑执行完毕后，自动关闭模态框

                },
                {
                    closeAfterAdd: true,  // 在添加执行完毕后，自动关闭模态框
                    afterSubmit: function (result) {
                        $.ajaxFileUpload({
                            url: '${pageContext.request.contextPath}/slide/upload',
                            fileElementId: 'cover',
                            data: {
                                id: result.responseJSON.data
                            },
                            type: 'post',
                            datatype: 'json',
                            success: function () {
                                $("#tt").trigger("reloadGrid");
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
                url: '${pageContext.request.contextPath}/slide/exportExcel',
                success: function (result) {
                    alert(result)
                }
            })
        }

        $(function () {
            $("#mm").click(function () {
                $.ajax({
                    url: "${pageContext.request.contextPath}/desktop/news/newslectAll2.do",
                    type: "post",
                    datatype: "json",
                    success: function (data) {
                        $("#label_name").val(data.id);
                        $("#label_content").val(data.content);
                        $("#label_parent.name").val(data.parent.name);
                    },
                    error: function (data) {

                    }
                });
            })
        })


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="">轮播图展示</a></li>
    <li role="presentation"><a onclick="exportExcel()">导出轮播图</a></li>
</ul>
<table id="tt"></table>

<div id="pg" style="height: 50px"></div>
</body>
</html>
