<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<script src="${pageContext.request.contextPath}/statics/boot/js/jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/echarts/echarts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/echarts/china.js"></script>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="china" style="width: 600px;height: 600px;margin-top: 30px;margin-left: 30px">

</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('china'));

    // function randomData() {
    //     return Math.round(Math.random() * 10000);
    // }
    $(function () {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/findUserCountByMap",
            type: 'post',
            datatype: 'json',
            success: function (result) {
                console.info(result);
                var option = {
                    title: {
                        text: '持明法洲用户注册统计男女分布图',
                        subtext: '2019年12月11日 最新数据',
                        left: 'center'
                    },
                    tooltip: {},
                    // 说明
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['男', '女']
                    },
                    visualMap: {
                        min: 0,
                        max: 30000,
                        left: 'left',
                        top: 'bottom',
                        text: ['高', '低'],           // 文本，默认为数值文本
                        calculable: true
                    },
                    // 工具箱
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            dataView: {readOnly: false},
                            restore: {},
                            saveAsImage: {}
                        }
                    },
                    series: [
                        {
                            name: '男',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: true
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: result.nan
                            // data: $.each(result,function (i,nanval) {
                            //     alert(i);
                            // })
                        },
                        {
                            name: '女',
                            type: 'map',
                            mapType: 'china',
                            label: {
                                normal: {
                                    show: true
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data:
                            result.nv,

                        }
                    ]
                };
                myChart.setOption(option);
            },
            error: function () {

            }
        });
    })
    var goEasy = new GoEasy({
        host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-0f2ac1f445814d008f250eb9eb54cf3c", //替换为您的应用appkey
    });
    goEasy.subscribe({
        channel: "176_mp", //替换为您自己的channel
        onMessage: function (message) {
            $.ajax({
                url: '${pageContext.request.contextPath}/user/findUserCountByMap',
                type: 'post',
                datatype: 'json',
                success: function (result) {
                    var option = {
                        series: [{
                            name: '男',
                            type: 'bar',
                            data: [
                                {name: result.nv[0].provinceName, value: result.nv[0].userCount},
                                {name: result.nv[1].provinceName, value: result.nv[1].userCount},
                            ]
                        },
                            {
                                name: '女',
                                type: 'bar',
                                data: [
                                    {name: result.nv[0].provinceName, value: result.nv[0].userCount},
                                    {name: result.nv[1].provinceName, value: result.nv[1].userCount},
                                ]
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












