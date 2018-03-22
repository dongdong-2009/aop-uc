function FieldSetVisual( pTableID, pFieldSetID, pImageID ) 
{ 
var objTable = document.getElementById( pTableID ) ; 
var objFieldSet = document.getElementById( pFieldSetID) ; 
var objImage = document.getElementById( pImageID) ; 
if( objTable.style.visibility == 'visible' ) 
{ 
objTable.style.visibility = 'hidden' ; 
objFieldSet.style.height = "20px" ; 
objImage.src="../commons/images/expand.png" ; 
} 
else 
{ 
objTable.style.visibility = 'visible'; 
var heightFieldSet = parseInt( objFieldSet.style.height.substr(0,objFieldSet.style.height.length-2)) ; 
var heightTable = parseInt( objTable.offsetHeight ) ; 
objFieldSet.style.height = heightFieldSet + heightTable + "px" ; 
objImage.src="../commons/images/constringency.png" ; 
} 
}