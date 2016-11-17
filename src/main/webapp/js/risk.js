$(document).ready(function() {
    
    $('.project').click(function(){
    	location.href = "/RMS/risk/"+$(this).data('id');
    });
    
	var modifyModal = $('.modify.modal');
	var confirmModal = $('.confirm.modal');
	var addRiskModal = $('.addRisk.modal');
	var trailModal = $('.trailRisk.modal');
	var modifyRiskModal = $('.modifyRisk.modal');
	
	$('.trail.right.button').click(function(){
		trailModal.data('id', $(this).parent().parent().data('id'));
		trailModal.find('.content p').html($(this).parent().find('.header').html());
    	trailModal.modal('show');
	});
	trailModal.find('.no.button').click(function(){
    	trailModal.modal('hide');
	});
	trailModal.find('.yes.button').click(function(){
		$.ajax({
			url: '/RMS/addTrail',
			type: 'post',
			dataType:'json',
			data: "id="+trailModal.data('id')+"&desc="+trailModal.find('input').val()+"&state="+trailModal.find('.dropdown').data('value'),
			success: function(msg) {
				if(msg.result!="成功"){
					alert('出错');
				}
				window.location.reload();
			}
		});
	});
	
	$('.deleteRisk.button').click(function(){
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
	
	
	$('h1').click(function(){
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
            url: '/RMS/modifyProject',
            type: 'post',
            dataType:'json',
            data: "id="+modifyModal.data('id')+"&name="+modifyModal.find('input:eq(0)').val()+"&desc="+modifyModal.find('textarea').val()+"&role="+modifyModal.find('input:eq(1)').val()+"&rid="+modifyModal.data('rid'),
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
            url: '/RMS/deleteProject',
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
    
    $('.accordion.menu .item').click(function(e){
    	if($(e.target).hasClass('item')){
    		$('.ui.accordion').accordion('toggle',$(this).index());
    	}
    })
    
    $('.accordion.menu .content .celled.table').hover(
       function(){ $(this).parent().addClass('raised'); },
       function(){ $(this).parent().removeClass('raised'); }
    )
    
    
	addRiskModal.modal({autofocus: false});
    modifyRiskModal.modal({autofocus: false});
    trailModal.modal({autofocus: false});

    $('.accordion.menu>.add.item').click(function(){
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
    	modifyRiskModal.data('lid', titleDom.parent().data('lid'));
    	restoreDropdown(t[0], titleDom.find('.label').data('value'))
    	t[1].value=titleDom.find('.header').html().trim();
    	
    	restoreDropdown(t[2], labels.eq(0).data('value'));
    	restoreDropdown(t[3], labels.eq(1).data('value'))
    	t[4].value=self.find('pre:first').html();
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
    
    trailModal.find('.ui.dropdown').dropdown({
    	onChange:function(value,text){
	    	$(this).removeClass(colors1.join(' '));
	    	$(this).addClass(colors1[value]);
	   	}
    });

    
    
    addRiskModal.find('.no.button').click(function(){
    	addRiskModal.modal('hide');
    	resetAddRisk();
    })
    
    t=addRiskModal.find('.required input, .required textarea');
    t.bind("input propertychange", function() {
    	num=0;
    	t.each(function(){
    		if($(this).val()!=''){
    			num=num+1;
    		}
    	});
        if(num==t.length){
        	addRiskModal.find('.yes.button').removeClass('disabled');
    	} else{
    		addRiskModal.find('.yes.button').addClass('disabled');
    	}
   	});

    addRiskModal.find('.yes.button').click(function(){
    	addRiskModal.find('.ui.dimmer').dimmer('show');
    	t=addRiskModal.find('input,textarea,.dropdown');
    	var fields = {
    		lid: addRiskModal.data('lid'),
        	state: $(t[0]).data('value'),
        	name: t[1].value,
        	possibility: $(t[2]).data('value'),
        	damage: $(t[3]).data('value'),
        	desc: t[4].value,
        	spy: t[5].value,
        	trigger: t[6].value,
        	trailer: t[7].value,
        	plan: t[8].value,
        	pid: addRiskModal.data('id')
        };
        $.ajax({
            url: '/RMS/addRisk',
            type: 'post',
            dataType:'json',
            data: fields,
            success: function(msg) {
                if(msg.result!="成功"){
                	alert('出错');
                }
                modifyModal.find('.ui.dimmer').dimmer('hide');
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

    $('.message .close').on('click', function() {
      $(this).closest('.message').transition('fade');
    });
    
    
    function resetAddRisk(){
    	t=addRiskModal.find('input,textarea,.dropdown');
    	addRiskModal.data('lid',0);

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
    
    $('li').click(function(){
    	s=$(this);
    	if(s.hasClass('select')){
    		s.removeClass('select')
    		resetAddRisk();
    	} else{
    		$('li').removeClass('select')
        	s.addClass('select');
        	t=addRiskModal.find('input,textarea,.dropdown');
        	addRiskModal.data('lid',s.data('id'));
        	
        	t[1].value=s.html();
        	t[1].disabled='disabled';
        	restoreDropdown(t[2], s.data('probability'));
        	restoreDropdown(t[3], s.data('effect'));
        	t[4].value=s.data('content');
        	t[5].value=s.data('spy');
        	t[6].value=s.data('switcher');
        	t[8].value=s.data('handle');
        	
        	addRiskModal.find('.yes.button').removeClass('disabled');
    	}
    });
    
    $('.reset.button').click(function(){
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
    	from = formatDate($('input.filter')[0].value);
    	to = formatDate($('input.filter')[1].value);
    	
    	$('.accordion.menu .item').not('.add').each(function(){
    		t=$(this);
    		date=t.find('.time').html().trim().split(" ")[0];
    		if(date<from || date>to){
    			t.addClass('hidden');
    		} else {
    			t.removeClass('hidden');
    		}
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

