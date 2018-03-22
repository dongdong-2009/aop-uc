<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>图表展示</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script src="${ctx}/echarts-2.2.7/build/dist/echarts.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>

</head>
<body>
    <div id="main" style="height:400px;"></div>
    <script type="text/javascript">
       var myChart;
        require.config({
            paths: {
                echarts: '${ctx}/echarts-2.2.7/build/dist'
            }
        });
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/map',
                'echarts/chart/funnel'
            ],
            function (ec) {
            	$.ajax({
            		type : 'POST',
            		url : "${ctx}/invitited/loadData.ht",
            		dataType: "json",
            		success : function(data) {
            			if(data.result=='1'){
                            var result=eval('(' + data.message+ ')');
                            setOptionValues(result);
            			}
            			else{
            				layer.alert("数据加载失败");
            			}
            		},
            		error : function(data){
            			layer.alert("数据加载失败");
            		}
            	});
                myChart = ec.init(document.getElementById('main'));
               
            }
        );
        function setOptionValues(data){
        	 var totalRegistered=data.totalRegistered;
        	 var certificationNumber=data.certificationNumber;
        	
         	option = {
        		    title: {
        		        text: '企业邀请注册统计结果'
        		    },
        		    tooltip: {
        		        trigger: 'item',
        		        formatter: "{a} <br/>{b} : {c}"
        		    },
        		    toolbox: {
        		        feature: {
        		            dataView: {readOnly: false},
        		            restore: {},
        		            saveAsImage: {}
        		        }
        		    },
        		    legend: {
        		        data: ['已邀请成功注册会员总数','已完成企业认证数']
        		    },
        		    calculable: true,
        		    series: [
        		        {
        		            name:'企业邀请注册统计结果',
        		            type:'funnel',
        		            left: '10%',
        		            top: 60,
        		            bottom: 60,
        		            width: '100%',
        		            sort: 'descending',
        		            gap: 2,
        		            label: {
        		                normal: {
        		                    show: true,
        		                    position: 'inside'
        		                },
        		                emphasis: {
        		                    textStyle: {
        		                        fontSize: 20
        		                    }
        		                }
        		            },
        		            labelLine: {
        		                normal: {
        		                    length: 10,
        		                    lineStyle: {
        		                        width: 1,
        		                        type: 'solid'
        		                    }
        		                }
        		            },
        		            itemStyle: {
        		                normal: {
        		                    borderColor: '#fff',
        		                    borderWidth: 1
        		                }
        		            },
        		            data: [
        		                {value: totalRegistered, name: '已邀请成功注册会员总数'},
        		                {value: certificationNumber, name: '已完成企业认证数'}
        		              
        		            ]
        		        }
        		    ]
        		}; 
        	
        		var ecConfig = require('echarts/config');
        		　function eConsole(param) {
        		　　　console.info(param.name);　　 // 弹出当前柱子的数值
        		　}
        		　myChart.on(ecConfig.EVENT.CLICK, eConsole);
             myChart.setOption(option);
        	
        }
        
    </script>
    
</body>
</html>