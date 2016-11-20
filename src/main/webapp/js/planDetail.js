$(document).ready(function() {
    
    $('.project').click(function(){
    	location.href = "/RMS/risk/"+$(this).data('id');
    });
    
	var modifyModal = $('.modify.modal');
	var confirmModal = $('.confirm.modal');
	var addRiskModal = $('.addRisk.modal');
	var modifyRiskModal = $('.modifyRisk.modal');
	var adjustPlanModal = $('.adjustPlan.modal');
	
	$('.deleteRisk.button').click(function(e){
    	if(e&&e.stopPropagation){
    		e.stopPropagation();
    	} else {
    		e.cancelBubble=true;
    	}
        $.ajax({
            url: '/RMS/deleteRisk',
            type: 'post',
            dataType:'json',
            data: "id="+$(this).parent().parent().data('id'),
            success: function(msg) {
                if(msg.result!="成功"){
                	alert('出错');
                }
                window.location.reload();
            }
        });
	});
	
	
	$('h1.click').click(function(){
    	modifyModal.modal('show');
    });
    
    modifyModal.modal({allowMultiple: true});
    confirmModal.modal({allowMultiple: true});
    modifyModal.find('.no.button').click(function(){
    	modifyModal.modal('hide');
    });
    
    modifyModal.find('.yes.button').click(function(){
    	modifyModal.find('.ui.dimmer').dimmer('show');
        $.ajax({
            url: '/RMS/modifyPlan',
            type: 'post',
            dataType:'json',
            data: "id="+modifyModal.data('id')+"&name="+modifyModal.find('input:eq(0)').val()+"&desc="+modifyModal.find('textarea').val(),
            success: function(msg) {
                if(msg.result!="成功"){
                	alert('出错');
                }
                window.location.reload();
            }
        });
    });

    modifyModal.find('input:first').keypress(function (e){
    	if(e.keyCode==13) {
    		modifyModal.find("textarea")[0].focus();
    		return false;
    	}
    });

    modifyModal.find("input").bind("input propertychange", function() {
        if(modifyModal.find('input:eq(0)').val()=='' || modifyModal.find('input:eq(1)').val()==''){
        	modifyModal.find('.yes.button').addClass('disabled');
    	} else{
    		modifyModal.find('.yes.button').removeClass('disabled');
    	}
   	});

    confirmModal.modal('attach events', '.modify.modal .delete.button');
    confirmModal.find('.negative.button').click(function(){
    	modifyModal.find('.ui.dimmer').dimmer('show');
        $.ajax({
            url: '/RMS/deletePlan',
            type: 'post',
            dataType:'json',
            data: "id="+modifyModal.data('id'),
            success: function(msg) {
                if(msg.result!="成功"){
                	alert('出错');
                }
                modifyModal.find('.ui.dimmer').dimmer('hide');
                window.location.reload();
            }
        });
    });
    
    $('.ui.accordion').accordion({exclusive:false});

    $('.removeRisk').click(function(e){
    	if(e&&e.stopPropagation){
    		e.stopPropagation();
    	} else {
    		e.cancelBubble=true;
    	}
        $.ajax({
            url: '/RMS/removeRisk',
            type: 'post',
            dataType:'json',
            data: "rid="+$(this).parent().parent().data('id')+"&pid="+$(this).data('id'),
            success: function(msg) {
                if(msg.result!="成功"){
                	alert('出错');
                }
                window.location.reload();
            }
        });
	});
    
    adjustPlanModal.find('.dropdown').dropdown({
    		 onChange: function(value, text, $selectedItem) {
    			 if(value.length>0){
    				 adjustPlanModal.find('.positive.button').removeClass('disabled');
    			 } else {
    				 adjustPlanModal.find('.positive.button').addClass('disabled');
    			 }
    		 }
    });
    adjustPlanModal.find('.no.button').click(function(e){
		adjustPlanModal.modal('hide');
    });
    adjustPlanModal.find('.checkbox').checkbox();
    
    adjustPlanModal.find('.positive.button').click(function(e){
    	adjustPlanModal.find('.ui.dimmer').dimmer('show');
    	var arr = [];
    	$('.menu .checkbox input:checked').parent().parent().parent().each(function(){ arr.push($(this).data('id')); })
    	to = adjustPlanModal.find('.multiple.dropdown').dropdown('get value');
        $.ajax({
            url: '/RMS/adjustRisk',
            type: 'post',
            dataType:'json',
            data: "rids="+arr+"&fromP="+adjustPlanModal.data('id')+"&toP="+to+"&type="+adjustPlanModal.find('input:checked').data('type'),
            success: function(msg) {
                if(msg.result!="成功"){
                	alert('出错');
                }
                window.location.reload();
            }
        });
    });

    
    $('.menu .checkbox').click(function(e) {
       	if(e&&e.stopPropagation){
    		e.stopPropagation();
    	} else {
    		e.cancelBubble=true;
    	}
    });
	$('.menu .checkbox').checkbox({onChange: function() {
		len = $('.menu .checkbox input:checked').length;
        $('.save.button span').html(len);
        if(len<=0){
        	$('.save.button').addClass('disabled');
        } else{
        	$('.save.button').removeClass('disabled');
        }
    }});
	$('.save.button').click(function(e) {
		adjustPlanModal.modal('show');
	});
   
    function calculateDistance(elem, mouseX, mouseY) {
        return Math.floor(Math.sqrt(Math.pow(mouseX - (elem.offset().left+(elem.width()/2)), 2) + Math.pow(mouseY - (elem.offset().top+(elem.height()/2)), 2)));
    }
    $('.accordion.menu .item').click(function(e){
        box=$(this).find('.ui.checkbox');
        distance=100;
        if(box.length>0)
        	distance = calculateDistance(box, e.pageX, e.pageY);
        if(distance<=30){
        	if(e&&e.stopPropagation){
        		e.stopPropagation();
        	} else {
        		e.cancelBubble=true;
        	}
        	box.checkbox('toggle');
        } else {
        	if($(e.target).hasClass('item') || $(e.target).hasClass('content')){
        		$('.ui.accordion').accordion('toggle',$(this).index());
        	}
        }
    })
    $(".check.button").click(function(){
    	cb=$('.menu .checkbox');
    	if(cb.hasClass('hidden')){
    		cb.removeClass("hidden");
    		$(this).html("取消");
    		$(this).next().removeClass("hidden");
    		$(this).next().next().addClass("hidden");;
    	} else {
    		cb.addClass("hidden");
    		cb.checkbox('uncheck');
    		$(this).html("导出");
    		$(this).next().addClass("hidden");
    		$(this).next().next().removeClass("hidden");;
    	}
	});
    
    $('.single.button').click(function(){
    	if($(this).html().startsWith('隐藏')){
    		$(this).html('显示引用');
    	} else {
    		$(this).html('隐藏引用');
    	}
    	$('.accordion.menu .item').not('.add').each(function(){
    		combineFilter($(this));
    	});
    });
    
    $('.accordion.menu .content .celled.table').hover(
       function(){ $(this).parent().addClass('raised'); },
       function(){ $(this).parent().removeClass('raised'); }
    )
    
    
	addRiskModal.modal({autofocus: false});
    modifyRiskModal.modal({autofocus: false});

    $('.accordion.menu>.add.item').click(function(){
    	resetAddRisk();
    	addRiskModal.modal('show');
    });
    
    function restoreDropdown(dom, val){
    	dom=$(dom);
    	if(val!=0){
    		dom.dropdown('set selected', val);
    	}else{
    		dom.dropdown('restore defaults');
    	}
    }

    $('.accordion.menu>.item>.content table.fixed').click(function(){
    	self=$(this)
    	titleDom=self.parent().parent().prev();
    	labels=self.find('.label');
    	t=modifyRiskModal.find('input,textarea,.dropdown');
    	modifyRiskModal.data('id', titleDom.parent().data('id'));
    	restoreDropdown(t[0], titleDom.find('.label').data('value'))
    	t[1].value=titleDom.find('.header').html().trim();
    	
    	restoreDropdown(t[2], labels.eq(0).data('value'));
    	restoreDropdown(t[3], labels.eq(1).data('value'))
    	t[4].value=(self.find('pre:first').html().trim()=='暂无描述')?"":self.find('pre:first').html().trim();
    	t[5].value=labels.eq(3).html();
    	t[6].value=labels.eq(4).html();
		t[7].value=(labels.eq(5).html().trim()=='暂无')?"":labels.eq(5).html().trim();
    	t[8].value=self.find('pre:eq(1)').html();
    	
    	modifyRiskModal.modal('show');
    });
    
    addRiskModal.find('.labeled.button>.label').click(function(){
    	n=$(this).next();
    	if(n.hasClass('visible')){
    		n.dropdown('hide');
    	} else {
    		n.dropdown('show');
    	}
    });
    
    colors1=['yellow','red','green'];
    colors2=['red','yellow','green'];
    addRiskModal.find('.header .ui.dropdown.button').dropdown({
    	onChange:function(value,text){
    		choose=colors1;
    		if(text!=null && text.length==1){
    			choose=colors2;
    			$(this).prev().removeClass(choose.join(' '));
    			$(this).prev().addClass(choose[value]);
    		}
	    	$(this).removeClass(choose.join(' '));
	    	$(this).addClass(choose[value]);
	   	}
    });
    modifyRiskModal.find('.header .ui.dropdown').dropdown({
    	onChange:function(value,text){
    		choose=colors1;
    		if(text!=null && text.length==1){
    			choose=colors2;
    			$(this).prev().removeClass(choose.join(' '));
    			$(this).prev().addClass(choose[value]);
    		}
    		$(this).removeClass(choose.join(' '));
    		$(this).addClass(choose[value]);
    	}
    });
    

    
    
    modifyRiskModal.find('.no.button').click(function(){
    	modifyRiskModal.modal('hide');
    })
    addRiskModal.find('.no.button').click(function(){
    	addRiskModal.modal('hide');
    })
    
    required=addRiskModal.find('.required input, .required textarea');
    required.bind("input propertychange", function() {
    	num=0;
    	required.each(function(){
    		if($(this).val()!=''){
    			num=num+1;
    		}
    	});
        if(num==required.length){
        	addRiskModal.find('.yes.button').removeClass('disabled');
    	} else{
    		addRiskModal.find('.yes.button').addClass('disabled');
    	}
   	});
    
    addRiskModal.find('.yes.button').click(function(){
    	addRiskModal.find('.ui.dimmer').dimmer('show');
    	t=addRiskModal.find('input,textarea,.dropdown');
    	var fields = {
        	state: $(t[0]).data('value'),
        	name: t[1].value,
        	possibility: $(t[2]).data('value'),
        	damage: $(t[3]).data('value'),
        	desc: t[4].value,
        	spy: t[5].value,
        	trigger: t[6].value,
        	trailer: t[7].value,
        	plan: t[8].value,
        	planId: addRiskModal.data('id')
        };
        $.ajax({
            url: '/RMS/addRiskInPlan',
            type: 'post',
            dataType:'json',
            data: fields,
            success: function(msg) {
                if(msg.result!="成功"){
                	alert('出错');
                }
                addRiskModal.find('.ui.dimmer').dimmer('hide');
                window.location.reload();
            }
        });
    });

    modifyRiskModal.find('.yes.button').click(function(){
    	modifyRiskModal.find('.ui.dimmer').dimmer('show');
    	t=modifyRiskModal.find('input,textarea,.dropdown');
    	var fields = {
    		rid: modifyRiskModal.data('id'),
        	state: $(t[0]).data('value'),
        	name: t[1].value,
        	possibility: $(t[2]).data('value'),
        	damage: $(t[3]).data('value'),
        	desc: t[4].value,
        	spy: t[5].value,
        	trigger: t[6].value,
        	trailer: t[7].value,
        	plan: t[8].value
        };
        $.ajax({
            url: '/RMS/modifyRisk',
            type: 'post',
            dataType:'json',
            data: fields,
            success: function(msg) {
                if(msg.result!="成功"){
                	alert('出错');
                }
                modifyRiskModal.find('.ui.dimmer').dimmer('hide');
                window.location.reload();
            }
        });
    });

    
    function resetAddRisk(){
    	t=addRiskModal.find('input,textarea,.dropdown');

    	restoreDropdown(t[0], 0);
    	t[1].value="";
    	t[1].disabled='';
    	restoreDropdown(t[2], 0);
    	restoreDropdown(t[3], 0);
    	t[4].value="";
    	t[5].value="";
    	t[6].value="";
    	t[8].value="";
    	
    	addRiskModal.find('.yes.button').addClass('disabled');
    }
    

    
    
    // filter
    
    $("input.search").bind("input propertychange", function() {
    	$('.accordion.menu .item').not('.add').each(function(){
    		combineFilter($(this));
    	});
    	$('#count').html($('.accordion.menu .item').not('.add,.hidden').length);
    });
    function combineFilter(t){
    	val=$("input.search").val();
    	isShowLink = $('.single.button').html().startsWith('隐藏');
    	from = formatDate($('input.filter')[0].value);
    	to = formatDate($('input.filter')[1].value);
		date=t.find('.time').html().trim().split(" ")[0];
		if((!isShowLink && t.data('lid')!=0 && t.data('id')!=t.data('lid')) || date<from || date>to || t.find('.title span').html().indexOf(val)<0){
			t.addClass('hidden');
		} else {
			t.removeClass('hidden');
		}
    }
    
    $('.reset.button').click(function(){
    	$("input.search").val("");
    	$("input.filter").each(function(){
    		$(this).val($(this).data('default'));
    	});
    	$('input.filter').parent().removeClass('error');

    	$(this).prev().prev().dropdown("restore defaults");
    
		filter();
    });
    $("input.filter").bind("input propertychange", function() {
    	regTime = /^(\d{4})-(0[1-9]|[1-9]|1[0-2])-([1-9]|0[1-9]|[12]\d{1}|3[01])$/;
    	
    	if(!regTime.test($(this).val())){
    		$(this).parent().addClass('error');
    	} else {
    		$(this).parent().removeClass('error');
    		filter();
    	}
   	});
    function filter(){
    	
    	$('.accordion.menu .item').not('.add').each(function(){
    		combineFilter($(this));
    	});
    	$('#count').html($('.accordion.menu .item').not('.add,.hidden').length);
    	filterType($('.inline.dropdown').data('value'));
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
    
    $('.inline.dropdown').dropdown({
    	onChange:function(value){
    		filterType(value);
	   	}
    });
    
    function filterType(value){
    	from = formatDate($('input.filter')[0].value);
    	to = formatDate($('input.filter')[1].value);
		if(value==0){
			sortId(from, to)
		} else if(value==1){
			sortLink(from, to)
		} else {
			sortTrail(from, to)
		}
    }
    
    function sortId(from, to){
    	$('.accordion.menu .item').not('.add,.hidden').each(function(){
    		$(this).find('h4.comment.header').html('');
    	});
    	
    	tt=$('.accordion.menu .item').not('.add,.hidden').sort(calcId);
    	x=$('.accordion.menu .add.item');
    	for(i=0;i<tt.length;i++){
    		x.before($($(tt[i]).remove()));
    	}
    };
    function calcId(a, b){
    	return $(a).data('id')-$(b).data('id');
    };
    
    function sortLink(from, to){
    	$('.accordion.menu .item').not('.add,.hidden').each(function(){
    		t=$(this);
    		n=0;
    		tt=t.find('textarea.hidden.risks').val().trim().split(" ");
    		for (a in tt){
    			if(tt[a]>=from && tt[a]<=to){
    				n++;
        		}
    		}
    		t.find('h4.comment.header').html('该时间段内被识别<span>'+n+'</span>次');
    	});
    	
    	tt=$('.accordion.menu .item').not('.add,.hidden').sort(calcLink);
    	x=$('.accordion.menu .add.item');
    	for(i=0;i<tt.length;i++){
			x.before($($(tt[i]).remove()));
    	}
    };
    
    function calcLink(a, b){
    	return $(b).find('h4.comment.header span').html()-$(a).find('h4.comment.header span').html();
    };
    
    function sortTrail(){
    	$('.accordion.menu .item').not('.add,.hidden').each(function(){
    		t=$(this);
    		n=0;
    		tt=t.find('textarea.hidden.trails').val().trim().split(" ");
    		for (a in tt){
    			if(tt[a]>=from && tt[a]<=to){
    				n++;
        		}
    		}
    		t.find('h4.comment.header').html('该时间段内演变为问题<span>'+n+'</span>次');
    	});
    	
    	tt=$('.accordion.menu .item').not('.add,.hidden').sort(calcLink);
    	x=$('.accordion.menu .add.item');
    	for(i=0;i<tt.length;i++){
			x.before($($(tt[i]).remove()));
    	}
    };
    
    

});

