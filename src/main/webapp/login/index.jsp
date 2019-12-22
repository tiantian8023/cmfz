<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <script src="assets/js/jquery-2.2.1.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script>
        function slidedetail() {
            $("#onright").load("/cmfz/slideshow.jsp");
        }

        function importExcel() {
            $.ajax({
                url: "${pageContext.request.contextPath}/slide/importExcel"
            })
        }
    </script>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">

            <span class="navbar-brand">持明法洲后台管理系统</span>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="navbar-text ">欢迎:${admin.name}</li>
                <li class="navbar-link navbar-right"><a href="">安全退出</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="row">
    <div class="col-md-3">
        <div class="panel-group" id="acc2">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <a href="#slideshow" class="collapsed" data-toggle="collapse" data-parent="#acc2">轮播图管理</a>
                </div>

                <div class="panel-collapse collapse in" id="slideshow">
                    <div class="panel-body">
                        <div>
                            <button class="btn btn-warning" onclick="return slidedetail();">轮播图详情</button>
                            <br>
                        </div>
                        <br>
                        <div>
                            <button class="btn btn-warning" onclick="return importExcel();">导入轮播图</button>
                            <br>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <a href="#album" class="collapsed" data-toggle="collapse" data-parent="#acc2">专辑管理</a>
                </div>
                <div class="panel-collapse collapse" id="album">
                    <div class="panel-body">
                        <a href="javascript:$('#onright').load('/cmfz/albumshow.jsp')" data-toggle="modal"
                           class="btn btn-default">专辑详情</a>
                    </div>
                </div>
            </div>
            <div class="panel panel-danger">
                <div class="panel-heading">
                    <a href="#article" class="collapsed" data-toggle="collapse" data-parent="#acc2">文章管理</a>
                </div>
                <div class="panel-collapse collapse" id="article">
                    <div class="panel-body">
                        <a href="javascript:$('#onright').load('/cmfz/articshow.jsp')" data-toggle="modal"
                           class="btn btn-default">文章详情</a>
                    </div>
                </div>
            </div>
            <div class="panel panel-success">
                <div class="panel-heading">
                    <a href="#user" class="collapsed" data-toggle="collapse" data-parent="#acc2">用户管理</a>
                </div>
                <div class="panel-collapse collapse " id="user">
                    <div class="panel-body">
                        <div>
                            <a href="javascript:$('#onright').load('/cmfz/articshow.jsp')" data-toggle="modal"
                               class="btn btn-success">用户详情</a>
                        </div>
                        <br>
                        <div>
                            <a href="javascript:$('#onright').load('/cmfz/chat.jsp')" data-toggle="modal"
                               class="btn btn-warning">生成报表</a>
                        </div>
                        <br>
                        <div>
                            <a href="javascript:$('#onright').load('/cmfz/echarts/china.jsp')" data-toggle="modal"
                               class="btn btn-info">查看地图</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-8" id="onright">
        <%@include file="right.jsp" %>
    </div>
    <nav class="navbar navbar-default navbar-fixed-bottom">
        <div class="container">
            123
        </div>
    </nav>
</div>


</body>
</html>
