// Js中类似JAVA 的Map用法

function BizMap() {    
    var struct = function(key, value) {    
         this.key = key;    
         this.value = value; 
    } 
    var put = function(key, value){    
         for (var i = 0; i < this.arr.length; i++) {    
             if ( this.arr[i].key === key ) {    
                 this.arr[i].value = value;    
                 return;
              }
         }    
         this.arr[this.arr.length] = new struct(key, value);    
     }
    var get = function(key) {    
         for (var i = 0; i < this.arr.length; i++) {    
             if ( this.arr[i].key === key ) {    
                 return this.arr[i].value;  }    
         }    
         return null;    
     }   
     var remove = function(key) {    
         var v;    
         for (var i = 0; i < this.arr.length; i++) {    
             v = this.arr.pop();    
             if ( v.key === key ) {    
                 continue;    
             }    
             this.arr.unshift(v);    
         }    
     }    
     var size = function() {    
         return this.arr.length;    
     }    
     var isEmpty = function() {    
         return this.arr.length <= 0;    
     }
     var containsKey = function(key) {    
         for (var i = 0; i < this.arr.length; i++) {    
             if ( this.arr[i].key === key ) {    
                 return true;  
             }    
         }    
         return false;    
     } 
     
     var containsValue = function(value) { 
         for (var i = 0; i < this.arr.length; i++) {    
        	 if ( this.arr[i].value === value ) {    
                 return true;  
             }
         }    
         return false;    
     }
     
     var getKeys = function() { 
    	 var keyArr = [];
         for (var i = 0; i < this.arr.length; i++) {    
        	 var key = this.arr[i].key;
        	 keyArr.push(key);
         }    
         return keyArr;    
     } 
     var getValues = function() { 
    	 var valueArr = [];
         for (var i = 0; i < this.arr.length; i++) {    
        	 var value = this.arr[i].value;
        	 valueArr.push(value);
         }    
         return valueArr;    
     }

     this.arr = new Array();    
     this.get = get;    
     this.put = put;    
     this.remove = remove;    
     this.size = size;    
     this.isEmpty = isEmpty; 
     this.containsKey = containsKey;
     this.containsValue = containsValue;
     this.getKeys = getKeys;
     this.getValues = getValues;
 } 