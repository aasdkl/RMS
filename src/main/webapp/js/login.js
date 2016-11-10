$(document).ready(function() {

    var form = $('#logForm').form({
        fields: {
        	account: {
                identifier  : 'account',
                rules: [{
                    type   : 'empty',
                    prompt : '请输入您的帐户'
                }]
            },
            password: {
                identifier  : 'password',
                rules: [{
                    type   : 'empty',
                    prompt : '请输入您的密码'
                }]
            }
        },
        onSuccess : function(event, fields){
            event.preventDefault();            
            form.addClass('loading');
            $.ajax({
                url: '/RMS/login',
                type: 'post',
                dataType:'json',
                data: fields,
                success: function(msg) {
                    if(msg.result!="成功"){
                    	$('#logForm .field:eq(1)').addClass("error");
                    	form.removeClass('loading');
                    	$('#logForm').form('add errors', [msg.result]);
                    	$('#logForm .field.error input').val("");
                    	$('#logForm .field.error input').focus();
                    } else {
                    	window.location="/RMS/";
                    }
                }
            });
        }
    });
    
    var form2 = $('#regForm').form({
        fields: {
        	account: {
                identifier  : 'account',
                rules: [{
                    type   : 'empty',
                    prompt : '请输入您的帐户'
                }]
            },
            password1: {
                identifier  : 'password1',
                rules: [{
                    type   : 'empty',
                    prompt : '请输入您的密码'
                }]
            },
            password2: {
            	identifier  : 'password2',
            	rules: [{
                    type   : 'empty',
                    prompt : '请再次输入密码'
                }, {
            	    type   : 'match[password1]',
                    prompt : '两次密码不一致'
                }]
            }
        },
        onSuccess : function(event, fields){
            event.preventDefault();            
            form2.addClass('loading');
            $.ajax({
                url: '/RMS/register',
                type: 'post',
                dataType:'json',
                data: fields,
                success: function(msg) {
                	console.log(msg);
                    if(msg.result!="成功"){
                    	$('#regForm .field:first').addClass("error");
                    	form2.removeClass('loading');
                    	$('#regForm').form('add errors', [msg.result]);
                    } else {
                    	window.location="/RMS/";
                    }
                }
            });
        }
        
    });

    
    $('.shape').shape({duration:500});
    
    $('a').click(function(){
    	$('.shape').shape('flip over');
    });

});

