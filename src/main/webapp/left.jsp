<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js"></script>
    <script>
        function change() {
            var id = $("#select").val();
            alert(id);
        }

        function selectNewsLabelByID() {

            var id = $("#select").val();
            alert("要查找的id:")
            alert(id);
            $.ajax({
                url: '${pageContext.request.contextPath}/desktop/queryNewsLabel.do',
                type: 'post',
                data: {id: id},
                datatype: 'json',
                success: function (data) {
                    alert("查询成功")
                },
                error: function (data) {
                }
            });
        }

    </script>
</head>

<body>
<%--<select id="select" onchange="change()">--%>
<%--<option value="1">篮球</option>--%>
<%--<option value="2">足球</option>--%>
<%--<option value="3">台球</option>--%>
<%--</select>--%>


<select id="select" onchange="selectNewsLabelByID()" name="select" class="input" style="width:99% ">
    <%--<select name="select" class="input" style="width:99% ">--%>
    <option value="1" selected>--请选择--</option>
    <option value="1">ll</option>
</select>

</body>
</html>

