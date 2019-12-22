<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.create('#editor_id', {
            width: '1000px',
            // fullscreenMode:true,
            fileManagerJson: "",//指定浏览远程图片的服务器端程序
            allowFileManager: true, //true时显示浏览远程服务器按钮。
            fontSizeTable: [],  //指定文字大小。数据类型: Array
            fileManagerJson: '${pageContext.request.contextPath}/article/brower', //指定浏览远程图片的服务器端程序
            uploadJson: "${pageContext.request.contextPath}/article/upload",

        });
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/article/addAriticle" method="post">
    <label for="title">标题</label>
    <input type="text" id="title" name="title">
    <label for="author">作者</label>
    <input type="text" id="author" name="author">
    <textarea id="editor_id" name="content" style="width:700px;height:300px;">
        </textarea>
    <input type="submit" value="保存">
    <input type="reset" value="取消">
</form>
</body>
</html>
