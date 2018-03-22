<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/global.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>图表展示</title>
<script type="text/javascript">
var CTX="${pageContext.request.contextPath}";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
</head>
<body>
	 <input type="hidden" id="url" value="${url}"/>
    <div id="main" style="height:400px;"></div>
    <script src="${ctx}/echarts-2.2.7/build/dist/echarts.js"></script>
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
                'echarts/chart/pie',
                'echarts/chart/funnel'
            ],
            function (ec) {
            	$.ajax({
            		type : 'POST',
            		url : CTX+"/urlMonitor/loadData.ht",
            		dataType: "json",
            		data : {"url" : $("#url").val()},
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
        	 var total=data.total;
        	 var successTotal=data.successTotal;
        	var suc=successTotal,fail=total-successTotal;
        	 option = {
             	    title : {
             	        text: 'url请求情况',
             	        x:'center'
             	    },
             	    tooltip : {
             	        trigger: 'item',
             	        formatter: "{a} <br/>{b} : {c} ({d}%)"
             	    },
             	    legend: {
             	        orient : 'vertical',
             	        x : 'left',
             	        data:['成功','失败']
             	    },
             	    toolbox: {
             	        show : true,
             	        feature : {
             	            mark : {show: true},
             	            dataView : {show: true, readOnly: false},
             	            magicType : {
             	                show: true, 
             	                type: ['pie', 'funnel'],
             	                option: {
             	                    funnel: {
             	                        x: '25%',
             	                        width: '50%',
             	                        funnelAlign: 'left',
             	                        max: 1548
             	                    }
             	                }
             	            },
             	            restore : {show: true},
             	            saveAsImage : {show: true}
             	        }
             	    },
             	    calculable : true,
             	    series : [
             	        {
             	            name:'访问结果',
             	            type:'pie',
             	            radius : '55%',
             	            center: ['50%', '60%'],
             	            data:[
             	                {value:suc, name:'成功'},
             	                {value:fail, name:'失败'}
             	               
             	            ]
             	        }
             	    ]
             	}
             myChart.setOption(option);
        	
        }
        
    </script>
    
</body>
</html>