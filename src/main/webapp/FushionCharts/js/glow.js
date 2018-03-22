 var dataString='';
$(function(){
	  var url=$("#url").val();
	  loadData(url);
  })
 //加载xml数据
  function loadData(url){
	$.ajax({
		type : 'POST',
		url : CTX+"/urlMonitor/loadData.ht",
		dataType: "json",
		data : {"url" : url},
		success : function(data) {
			if(data.result=='1'){
                var result=eval('(' + data.message+ ')');
				parseData(result);
			}
			else{
				layer.alert("数据加载失败");
			}
		},
		error : function(data){
			layer.alert("数据加载失败");
		}
	});
	
 }
function parseData(data){
  var total=data.total;
  var successTotal=data.successTotal;
  var percent=0;
  if(total!=null&&total!=""){
	  total=parseInt(total);
	  successTotal=parseInt(successTotal);
	  if(total!=0){
	  percent= ((successTotal/total).toFixed(2))*100;
	  }
  }
    dataString='';
    dataString+='<chart showPercentagevalues="1" caption="成功与失败比例" chartrightmargin="45" bgcolor="FFFFFF" chartleftmargin="50" charttopmargin="35" chartbottommargin="20" showplotborder="0" showshadow="1" showborder="1" bordercolor="0080FF" borderalpha="50" bgalpha="50" >\n';
    dataString+='<set value="'+percent+'" color="C295F2"  label="Success"/>\n';
    dataString+='<set value="'+(100-percent)+'" color="00ACE8"  label="Fail"/>\n';
    dataString+='<styles>\n';
    dataString+='<definition>\n';
    dataString+='<style name="Font_0" type="font" font="Calibri" size="14" bold="1" bgcolor="FFFFFF" bordercolor="FFFFFF" isHTML="0"/>\n';
    dataString+='<style name="Font_1" type="font" size="15" color="000080" bgcolor="FFFFFF" bordercolor="FFFFFF" isHTML="0"/>\n';
    dataString+='<style name="Glow_0" type="Glow" color="0080FF" alpha="43" quality="3"/>\n';
    dataString+='</definition>\n';
    dataString+='<application>\n';
    dataString+='<apply toObject="DATALABELS" styles="Font_0"/>\n';
    dataString+='<apply toObject="CAPTION" styles="Font_1"/>\n';
    dataString+='<apply toObject="DATAPLOT" styles="Glow_0"/>\n';
    dataString+='</application>\n';
    dataString+='</styles>\n';
    dataString+='</chart>\n';
    
    
    if (GALLERY_RENDERER && GALLERY_RENDERER.search(/javascript|flash/i)==0)  FusionCharts.setCurrentRenderer(GALLERY_RENDERER); 
    var chart = new FusionCharts(CTX+"/FushionCharts/Doughnut2D.swf", "ChartId", "560", "400", "0", "0");
    chart.setXMLData( dataString );
    chart.render("chartdiv");
}

