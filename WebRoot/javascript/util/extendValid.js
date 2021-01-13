$.extend($.fn.validatebox.defaults.rules, {
	devicecode: {
	        validator: function (value) {
	        	var rules = $.fn.validatebox.defaults.rules;
	    		if(/[\u4e00-\u9fa5]/.test(value)){
	    			rules.devicecode.message = "不能包含中文字符，请输入英文和数字"; 
	    			return false;
	    		}else if(/ +/.test(value)){
	    			rules.devicecode.message = "不能包含空格，请输入英文和数字"; 
	    			return false;
	    		}else if(value.length<0||value.length>50){
	    			rules.devicecode.message = "输入的字符长度必须小于50"; 
	    			return false;
	    		}else if(/^\d+$/.test(value)){
	    			rules.devicecode.message = "不能是纯数字，请输入英文和数字";
	    		}else if(/([A-Z]([^A-Z])?)/.test(value)){
	    			rules.devicecode.message = "不能包含大写字母";
	    		}else if(/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/.test(value)){
	    			rules.devicecode.message = "不能包含非法字符，请输入英文和数字。";
	    		}
	    		else{
	    			return true;
	    		}
	        },
	        message: ""
	    },
	    //端口号验证
    devicevalid: {
        validator: function (value) {
        	 var num = parseInt(value);//把value转化成Int类型
        	 var rules = $.fn.validatebox.defaults.rules;
        	 if(num < 1000 || num > 65535){//进行数值 范围判断
        		 rules.devicevalid.message = "请输入端口号1000-65535";
        		 return false;
        	 }
        	 else
        		return true;        	
        },
        message: ""
    },
    coordinate: {
        validator: function (value) {
        	 return  /^[-]?\d+(\.\d+)?$/i.test(value);
        },
        message: '请输入坐标'
    },
    idcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
    minLength: {
        validator: function (value, param) {
            return value.length >= param[0];
        },
        message: '请输入至少（2）个字符.'
    },
    length: { validator: function (value, param) {
        var len = $.trim(value).length;
        return len >= param[0] && len <= param[1];
    },
        message: "输入内容长度必须介于{0}和{1}之间."
    },
    phone: {// 验证电话号码
        validator: function (value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '格式不正确,请使用下面格式:020-88888888'
    },
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    intOrFloat: {// 验证整数或小数
    	validator: function (value, param) {
    	    var rules = $.fn.validatebox.defaults.rules;  
    		if(!(/^[+-]?\d*\.?\d{0,6}$/.test(value))){
	    		 rules.intOrFloat.message = "请输入数字，并确保格式正确，最大精确度为小数点后6位";  
   			  	 return false;  
    		}else{
    			rules.intOrFloat.message = ""; 
    			if(value>param[1] || value<param[0]){
	    			 rules.intOrFloat.message = "所填数据应在{0}-{1}的范围内";  
	    			  return false;  
	    		}else{
	    			  return true;
	    		}
    		}
    	},
        message: ""
    },
    retainedDecimal:{//精确到小数点位数
    	validator: function (value,param) {
    		var weishu = param[0];
    		if(weishu==1){
    			 return  /^[+-]?\d*\.?\d{1}$/.test(value);
    		}else if(weishu==3){
    			return  /^[+-]?\d*\.?\d{3}$/.test(value);
    		}else if(weishu==4){
    			return  /^[+-]?\d*\.?\d{4}$/.test(value);
    		}else if(weishu==5){
    			return  /^[+-]?\d*\.?\d{5}$/.test(value);
    		}else if(weishu==6){
    			return  /^[+-]?\d*\.?\d{6}$/.test(value);
    		}else{
    			 return  /^[+-]?\d*\.?\d{2}$/.test(value);
    		}
    	},
        message: "请输入数字，并确保格式正确，精确度为小数点后{0}位"
    },
    currency: {// 验证货币
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '货币格式不正确'
    },
    qq: {// 验证QQ,从10000开始
        validator: function (value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message: 'QQ号码格式不正确'
    },
    integer: {// 验证整数 可正负数
        validator: function (value) {
            //return /^[+]?[1-9]+\d*$/i.test(value);

            return /^([+]?[0-9])|([-]?[0-9])+\d*$/i.test(value);
        },
        message: '请输入整数'
    },
    pointeger: {// 验证整数 可正负数
        validator: function (value) {
            //return /^[+]?[1-9]+\d*$/i.test(value);

            return /^([+]?[0-9])+\d*$/i.test(value);
        },
        message: '请输入正整数'
    },
    age: {// 验证年龄
        validator: function (value) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        },
        message: '年龄必须是0到120之间的整数'
    },

    chinese: {// 验证中文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value);
        },
        message: '请输入中文'
    },
    english: {// 验证英语
        validator: function (value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: '请输入英文'
    },
    unnormal: {// 验证是否包含空格和非法字符
        validator: function (value) {
            return /.+/i.test(value);
        },
        message: '输入值不能为空和包含其他非法字符'
    },
    user: {// 验证用户
        validator: function (value,param) {
        		return /^[A-Za-z0-9_]{5,17}$/i.test(value);
        },
        message: '输入不合法（字母开头，允许6-18字节，允许字母数字）'
    },
    faxno: {// 验证传真
        validator: function (value) {
            //            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '传真号码不正确'
    },
    zip: {// 验证邮政编码
        validator: function (value) {
            return /^[1-9]\d{5}$/i.test(value);
        },
        message: '邮政编码格式不正确'
    },
    ip: {// 验证IP地址
        validator: function (value) {
        	var rules = $.fn.validatebox.defaults.rules;
        	if(value=="0.0.0.0" || value=="255.255.255.255"){
        		rules.ip.message = "IP地址不能全为"+value;  
        		return false;  
        	}else{
        		var reg = /^((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))$/;
        	    if(!reg.test(value)){
        	    	rules.ip.message = "IP地址格式不正确,格式应为128.0.0.0";  
        	    	return false;
        	    }else{
        	    	return true;
        	    }
        	}
        	return "";
        },
        message: 'IP地址格式不正确'
    },
    name: {// 验证姓名，可以是中文或英文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
        },
        message: '请输入姓名'
    },
    date: {
        validator: function (value) {
            /*//格式yyyy-MM-dd或yyyy-M-d
            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);*/
        	//格式yyyy-MM-dd
            return /^((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)$/i.test(value);
        },
        message: '请输入正确日期，并确认格式为:"2015-05-08"'
    },
    time: {
        validator: function (value) {
            //格式HH-MM-SS
            return /([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$/i.test(value);
        },
        message: '请输入正确时间，并确认格式为:"09:05:07"'
    },
    datetime: {
        validator: function (value) {
            //格式yyyy-MM-dd HH:MM:SS
            return /^((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29) ([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$/i.test(value);
        },
        message: '请输入正确日期，并确认格式为:"2015-05-08 09:05:07"'
    },
    msn: {
        validator: function (value) {
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
        },
        message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
    },
    same: {
        validator: function (value, param) {
            if ($("#" + param[0]).val() != "" && value != "") {
                return $("#" + param[0]).val() == value;
            } else {
                return true;
            }
        },
        message: '两次输入的密码不一致！'
    },
    number: {
    	validator: function (value, param) {
    	    var rules = $.fn.validatebox.defaults.rules;  
    		if(!(/^(?:[1-9]\d*|0)$/.test(value))){
	    		 rules.number.message = "请输入数字型数值";  
   			  	 return false;  
    		}else{
    			rules.number.message = ""; 
    			if(value>param[1] || value<param[0]){
	    			 rules.number.message = "所填数据应在{0}-{1}的范围内";  
	    			  return false;  
	    		}else{
	    			  return true;
	    		}
    		}
    	},
        message: ""
    },
    positiveInteger: {//正数
    	validator: function (value) {
    		return /^(?:[0-9]\d*|0)$/.test(value);
    	},
    	message: '请输入正整数'
    }, 
    maxLength: {     
    	validator: function (value, param) {     
    		return param[0] >= value.length;     
    	},     
    	message: '输入的字符最大{0}位.'    
    },
    binary: {
	   	validator: function (value, param) {
	   	    var rules = $.fn.validatebox.defaults.rules;
	   		if(!(/^[0-1]+$/.test(value))){
	    		rules.binary.message = "请输入一个二进制数";  
			  	return false;  
	   		}else{
	   			rules.binary.message = "";
	   			//
   				if(value.length != param[0]){
   					rules.binary.message = "输入的字符长度必须是{0}位";  
   					return false;  
   				}else{
   					if(param.length > 1){
   						var intVal = parseInt(value,2);
   						if(intVal < param[1] || intVal > param[2]){
   							rules.binary.message = "二进制数值的范围应在{1}-{2}之间";  
   							return false;  
   						}else{
   							return true;
   						}
   					}
   				}
	   		}
	   	},
	    message: ""
	},
	loginId: {
    	validator: function (value) {
    		var rules = $.fn.validatebox.defaults.rules;
    		if(/[\u4e00-\u9fa5]/.test(value)){
    			rules.loginId.message = "不能输入中文字符"; 
    			return false;
    		}else if(/ +/.test(value)){
    			rules.loginId.message = "不能包含空格"; 
    			return false;
    		}else if(value.length<0||value.length>25){
    			rules.loginId.message = "输入的字符长度必须是1~25字节"; 
    			return false;
    		}else{
    			return true;
    		}
    	},
    	message: ""
    },
    loginPwd: {
    	validator: function (value) {
    		var rules = $.fn.validatebox.defaults.rules;
    		if(/[\u4e00-\u9fa5]/.test(value)){
    			rules.loginPwd.message = "不能输入中文字符"; 
    			return false;
    		}else if(/ +/.test(value)){
    			rules.loginPwd.message = "不能包含空格"; 
    			return false;
    		}else if(value.length<0||value.length>32){
    			rules.loginPwd.message = "输入的字符长度必须是1~32字节"; 
    			return false;
    		}else{
    			return true;
    		}
    	},
    	message: ""
    },
    teleLen:{
    	validator: function(value){
    		var rules = $.fn.validatebox.defaults.rules;
    		if(value.length<1||value.length>20){
    			rules.teleLen.message = "输入的长度必须是1~20字节"; 
    			return false;
    		}
    	},
    	message: ""
    }
});