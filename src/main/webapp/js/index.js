$(document).ready(function() {

	var addModal = $('.add.modal');
	var modifyModal = $('.modify.modal');
	var confirmModal = $('.confirm.modal');
	
	
    $('#addProject').click(function(){
    	addModal.modal('show');
    });
   
    addModal.find('.no.button').click(function(){
    	addModal.modal('hide');
    	addModal.find('input').val('');
    	addModal.find('textarea').val('');
    });
    
    addModal.find('.yes.button').click(function(){
    	addModal.find('.ui.dimmer').dimmer('show');
        $.ajax({
            url: '/RMS/addProject',
            type: 'post',
            dataType:'json',
            data: "name="+addModal.find('input').val()+"&desc="+addModal.find('textarea').val(),
            success: function(msg) {
                if(msg.result!="成功"){
                	alert('出错');
                }
                addModal.find('.ui.dimmer').dimmer('hide');
                window.location.reload();
            }
        });
    });

    addModal.find('input').keypress(function (e){
    	if(e.keyCode==13) {
    		addModal.find("textarea")[0].focus();
    		return false;
    	}
    });

    addModal.find("input").bind("input propertychange", function() {
        if(addModal.find('input').val()==''){
        	addModal.find('.yes.button').addClass('disabled');
    	} else{
    		addModal.find('.yes.button').removeClass('disabled');
    	}
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
            data: "id="+modifyModal.data('id')+"&name="+modifyModal.find('input').val()+"&desc="+modifyModal.find('textarea').val(),
            success: function(msg) {
                if(msg.result!="成功"){
                	alert('出错');
                }
                addModal.find('.ui.dimmer').dimmer('hide');
                window.location.reload();
            }
        });
    });

    modifyModal.find('input').keypress(function (e){
    	if(e.keyCode==13) {
    		modifyModal.find("textarea")[0].focus();
    		return false;
    	}
    });

    modifyModal.find("input").bind("input propertychange", function() {
        if(modifyModal.find('input').val()==''){
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
    
    
    
    
//    if (localStorage) { 
//
//    	var trs = $('tbody tr');
//    	
//    	if (localStorage.getItem('sort')) {
//    		var old=JSON.parse(localStorage.getItem('sort'));
//    		var endI = $('tbody tr:last').data('id');
//    		if(trs.length!=old.length && endI!=old){
//    			
//    		}
//    	} else { // no stored
//    		trs.each(function(){
//    			i = $(this).data('id');
//    		});
//    	}
//    }
//    
//    JSON.stringify(obj);
    
    
    
    $('.project').click(function(){
    	location.href = "/RMS/risk/"+$(this).data('id');
    });
    
    $('tbody tr td.modify').click(function(e){
    	if(e&&e.stopPropagation){
    		e.stopPropagation();
    	} else {
    		e.cancelBubble=true;
    	}
    	self=$(this).parent();
    	modifyModal.data('id', self.data('id'));
    	modifyModal.find('input').val(self.find('td:eq(0)').text().trim());
    	t=self.find('td:eq(1)').text().trim();
    	modifyModal.find('textarea').val(t=="暂无描述"?"":t);
    	modifyModal.modal('show');
    });
    
    $('.ui.dimmer').dimmer({closable:false});
	
});

