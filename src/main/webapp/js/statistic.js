$(document).ready(function() {

	var myChart = echarts.init(document.getElementById('main'));
	// 初始 option
	option = {
		tooltip : {
			trigger : 'axis',
			axisPointer: {
				type:"shadow"
			}
		},
		grid : {
			top : '12%',
			left : '1%',
			right : '10%',
			containLabel : true
		},
		xAxis : [ {
			type : 'category',
			data : []
		} ],
		yAxis : [ {
			type : 'value',
			name : '次数'
		} ],
		dataZoom : [ {
			show : true,
			start : 0,
			end : 100
		}, {
			type : 'inside',
			start : 0,
			end : 100
		}, {
			show : true,
			yAxisIndex : 0,
			filterMode : 'empty',
			width : 30,
			height : '80%',
			showDataShadow : false,
			left : '93%'
		} ],
		series : [  {
			type : 'bar',
			data : []
		} ]
	};
	myChart.setOption(option)
	
	
	
    $('.project').click(function(){
    	location.href = "/RMS/risk/"+$(this).data('id');
    });
    
    function restoreDropdown(dom, val){
    	dom=$(dom);
    	if(val!=0){
    		dom.dropdown('set selected', val);
    	}else{
    		dom.dropdown('restore defaults');
    	}
    }

    $('.reset.button').click(function(){
    	$("input.filter").each(function(){
    		$(this).val($(this).data('default'));
    	});
    	$('input.filter').parent().removeClass('error');
    	filterType($('.inline.dropdown').data('value'));
    
    });
    $("input.filter").bind("input propertychange", function() {
    	regTime = /^(\d{4})-(0[1-9]|[1-9]|1[0-2])-([1-9]|0[1-9]|[12]\d{1}|3[01])$/;
    	if(!regTime.test($(this).val())){
    		$(this).parent().addClass('error');
    	} else {
    		$(this).parent().removeClass('error');
        	filterType($('.inline.dropdown').data('value'));
    	}
   	});
    $('.inline.dropdown').dropdown({
    	onChange:function(value){
    		filterType(value);
	   	}
    });
    
    function filterType(value){
    	from = formatDate($('input.filter')[0].value);
    	to = formatDate($('input.filter')[1].value);
		if(value==0){
			filterLink(from, to)
		} else if(value==1){
			filterError(from, to)
		}
    }
	
    function isIn(date,from,to){
    	if(date<from || date>to){
    		return false;
    	}
    	return true;
    }
	
    risks=JSON.parse($('textarea.hidden.risks').val());// id title time link
    trails=JSON.parse($('textarea.hidden.trails').val());// risk time
    risks.pop();
    trails.pop();
    
    riskst=[];
    trailst=[];
    d={'title':[],'num':[]}
    
	// using data after timed
	function filterLink(from,to){
    	riskst=[]
    	for (a in risks){
			if(isIn(risks[a].time, from, to)){
				riskst.push(risks[a]);
			}
		}
		resetD();
		tl={};
		for(a in riskst){
			id=riskst[a].link;
			if(id==0){
				id=riskst[a].id;
				tl[id]=[riskst[a].title,1];
			} else {
				if(tl[id]==null){
					tl[id]=[riskst[a].title,1];
				} else {
					tl[id][1]+=1;
				}
			}
		}

		var stl=Object.keys(tl).sort(function(a,b){return tl[b][1]-tl[a][1]});
		for( each in stl ){
			d.title.push(tl[stl[each]][0]);
			d.num.push(tl[stl[each]][1]);
		}
		
		update(d,1);
	}
	function filterError(from,to){
    	trailst=[];
		for (a in trails){
			if(isIn(trails[a].time, from, to)){
				trailst.push(trails[a].risk);
			}
		}
		resetD();
		console.log(trailst)
		tl={}
		for(a in trailst){
			riskId=trailst[a]
			r=risks.find(function (e) {
			    return e['id'] === riskId;
			})
			id=r.link;
			if(id==0){
				id=riskId;
				if(tl[id]==null){
					tl[id]=[r.title,1];
				} else {
					tl[id][1]+=1;
				}
			} else {
				if(tl[id]==null){
					tl[id]=[r.title,1];
				} else {
					tl[id][1]+=1;
				}
			}
		}
		var stl=Object.keys(tl).sort(function(a,b){return tl[b][1]-tl[a][1]});
		for( each in stl ){
			d.title.push(tl[stl[each]][0]);
			d.num.push(tl[stl[each]][1]);
		}
		
		update(d,2);
	}
    
    
    function formatDate(str){
    	t=str.split("-");
    	if(t[1].length==1){
    		t[1]="0"+t[1];
    	}
    	if(t[2].length==1){
    		t[2]="0"+t[2];
    	}
    	return t.join("-");
    }
    
    function resetD(){
    	d.title=[];
    	d.num=[];
    }
    function update(d,i){
    	$("#count").html(d.num.length);
		myChart.setOption({xAxis:{data: d.title},series:[{name : i==1?'识别次数':"演变次数",data: d.num}]});
    }
    
	filterType(0);

});

