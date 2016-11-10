$(document).ready(function() {
    
    $('.project').click(function(){
    	location.href = "/RMS/risk/"+$(this).data('id');
    });
    
	var modifyModal = $('.modify.modal');
	var confirmModal = $('.confirm.modal');

	$('h1').click(function(){
		modifyModal.find('textarea').val(modifyModal.data('des'));
		modifyModal.find('input:eq(0)').val(modifyModal.data('name'));
		modifyModal.find('input:eq(1)').val(modifyModal.data('role'));
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
    
    $('.accordion.menu>.ui.segments').hover(
       function(){ $(this).addClass('raised'); },
       function(){ $(this).removeClass('raised'); }
    )
    
});

