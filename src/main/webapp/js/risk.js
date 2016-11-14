$(document).ready(function() {
    
    $('.project').click(function(){
    	location.href = "/RMS/risk/"+$(this).data('id');
    });
    
	var modifyModal = $('.modify.modal');
	var confirmModal = $('.confirm.modal');
	var addRiskModal = $('.addRisk.modal');
	var trailModal = $('.trailRisk.modal');
	
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

    $('.accordion.menu>.add.item').click(function(){
    	addRiskModal.modal('show');
    });
    
    addRiskModal.find('.labeled.button>.label').click(function(){
    	n=$(this).next();
    	if(n.hasClass('visible')){
    		n.dropdown('hide');
    	} else {
    		n.dropdown('show');
    	}
//    	$(this).next().dropdown('toggle');
    });
    
    colors1=['yellow','red','green'];
    colors2=['red','yellow','green'];
    addRiskModal.find('.header .ui.dropdown').dropdown({
    	onChange:function(value,text){
    		choose=colors1;
    		if(text.length==1){
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
        	pid: addRiskModal.data('id')
        };
    	modifyModal.find('.ui.dimmer').dimmer('show');
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

    
});

