var myChart;

$(function(){
  $("#btnCount").click(function(){
	  var productiondate=$("#productiondate").val();
	  var Q_endcreatetime_DG=$("input[name='Q_endcreatetime_DG']").val();
	  if(productiondate==null||productiondate==''){
		  layer.alert("请选择起始时间!",{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});
		  return;
	  }
	  if(Q_endcreatetime_DG==null||Q_endcreatetime_DG==''){
		  layer.alert("请选择截止时间!",{ icon: 2,skin: 'layui-layer-molv'  ,closeBtn: 0});
		  return;
	  }
	  $(".pageContent").show();
	  tongji(productiondate,Q_endcreatetime_DG);
  });	
	
	
});
 function tongji(productiondate,Q_endcreatetime_DG){
     require.config({
         paths: {
             echarts: CTX+'/echarts-2.2.7/build/dist'
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
         		url : CTX+"/invitited/newLoadData.ht",
         		dataType: "json",
         		data:{
         			begincreatetime:productiondate,
         			endcreatetime:Q_endcreatetime_DG
         		},
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
 }
        function setOptionValues(data){
        	 var totalRegistered=data.totalRegistered;
        	 var certificationNumber=data.certificationNumber;
        	
         	option = {
        		    /*title: {
        		        text: '企业邀请注册统计结果'
        		    },*/
         			
         		  /*  tooltip : {
         		    	trigger: 'item',
          		        formatter: "{a} <br/>{b} : {c}"
         		    },
         		    legend: {
         		        orient: 'vertical',
         		        left: 'left',
         		        data: ['已邀请成功注册会员总数','已完成企业认证数']
         		    },
         		    series : [
         		        {
         		            name: '企业邀请注册统计结果',
         		            type: 'pie',
         		            radius : '55%',
         		            center: ['50%', '60%'],
         		            data:[
         		                 {value: totalRegistered, name: '已邀请成功注册会员总数'},
         		                {value: certificationNumber, name: '已完成企业认证数'}
         		            ],
         		            itemStyle: {
         		                emphasis: {
         		                    shadowBlur: 10,
         		                    shadowOffsetX: 0,
         		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
         		                }
         		            }
         		        }
         		    ]*/
         		    tooltip : {
         		        trigger: 'item',
         		        formatter: "{a} <br/>{b} : {c}"
         		    },
         		    legend: {
         		        orient : 'vertical',
         		        x : 'left',
         		       data: ['已邀请成功注册会员总数','已完成企业认证数']
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
         		            name:'访问来源',
         		            type:'pie',
         		            radius : '55%',
         		            center: ['50%', '60%'],
         		           data:[
         		                 {value: totalRegistered, name: '已邀请成功注册会员总数'},
         		                {value: certificationNumber, name: '已完成企业认证数'}
         		            ]
         		        }
         		    ]
                 }; 
        	
        		var ecConfig = require('echarts/config');
        		function eConsole(param) {
        		    console.info(param.name);
        		    fun(param.name);
        		}
            myChart.on(ecConfig.EVENT.CLICK, eConsole);
             myChart.setOption(option);
        	
        }
        
        var fun=function(name){
        	 var productiondate=$("#productiondate").val();
       	     var Q_endcreatetime_DG=$("input[name='Q_endcreatetime_DG']").val();
        	if(name=='已邀请成功注册会员总数'){
        		var params=new Array();
        		params.push({name:"begincreatetime",value:productiondate});
        		params.push({name:"endcreatetime",value:Q_endcreatetime_DG});
        		Post(CTX+"/invitited/myNewList.ht",params);
        	}else{
        		
        	}
        	
        }
        
        function Post(url,params){
        	//创建一个form
        	 var temp_form = document.createElement("form");
        	 //指定form的跳转action 
        	 temp_form.action=url;
        	 temp_form.method = "post";
        	 temp_form.style.display = "none";
        	 //添加参数
        	 for(var item in params){
        		 //创建若干文本域
        		 var opt = document.createElement("input");
        		     opt.setAttribute("type", "text");
        		     opt.name=params[item].name;
        		     opt.value=params[item].value;
        		     temp_form.appendChild(opt);
        	 }
        	 document.body.appendChild(temp_form);
        	//提交数据
             temp_form.submit();
        }