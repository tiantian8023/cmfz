<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/echarts.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>

</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例


    var myChart = echarts.init(document.getElementById('main'));
    $(function () {
        // 指定图表的配置项和数据
        // 使用刚指定的配置项和数据显示图表。

        $.ajax({
            url: '${pageContext.request.contextPath}/user/findUserByRegisterTime',
            type: 'post',
            datatype: 'json',
            success: function (data) {
                var option = {
                    title: {
                        text: '驰名法州近三周用户注册量'
                    },
                    tooltip: {},
                    legend: {
                        data: ['用户']
                    },
                    xAxis: {
                        data: ["近一周", "近两周", "近三周"]
                    },
                    yAxis: {},
                    series: [{
                        name: '用户',
                        type: 'bar',
                        data: [data.nan1, data.nan2, data.nan3]
                    }]
                };
                myChart.setOption(option);
            },
            error: function (data) {

            }

        })
    })
    var goEasy = new GoEasy({
        host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-0f2ac1f445814d008f250eb9eb54cf3c", //替换为您的应用appkey
    });
    goEasy.subscribe({
        channel: "176_mp", //替换为您自己的channel
        onMessage: function (message) {
            $.ajax({
                url: '${pageContext.request.contextPath}/user/findUserByRegisterTime',
                type: 'post',
                datatype: 'json',
                success: function (result) {
                    console.info(result);
                    var option = {
                        series: [{
                            name: '用户',
                            type: 'bar',
                            data: [result.nan1, result.nan2, result.nan3]
                        }]
                    };
                    myChart.setOption(option);
                },
                error: function (data) {

                }

            })
        }
    });

</script>

</body>
</html>