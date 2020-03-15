$(document).ready(function(){
  changeoptCategoria2();
  changeoptSubcategoria2();
  
});

function changeoptCategoria2(){
   
  $('.select-categoria').change(function(){
    var categoria_selected = $( ".select-categoria option:selected" ).text();
    alterSelectSubcategoria(categoria_selected);
  });
  
  window.onload = function (){
    var categoria_selected = $( ".select-categoria option:selected" ).text();
    startSelectSubcategoria(categoria_selected);
  };
  
}

function alterSelectSubcategoria(categoria_selected){    
  $('.select-subcategoria options').hide();
  $('.select-subcategoria option').each(function(index, data){  

    console.log(this);
    console.log('Option:'+$(this).attr('data-categoria')+' selected:'+ categoria_selected);
    
    if($(this).attr('data-categoria') === categoria_selected){
      $(this).show().attr('selected',true); 
    }else{
       $(this).hide().attr('selected',false);
    }
    
  });
  $(".select-subcategoria option: last").prop('selectedIndex',0);
}

function startSelectSubcategoria(categoria_selected){    
  
  $('.select-subcategoria option').each(function(index, data){  
    console.log(this);
    console.log('Option:'+$(this).attr('data-categoria')+' selected:'+ categoria_selected);
    
    if($(this).attr('data-categoria') === categoria_selected){
      $(this).show();
    }else{
       $(this).hide();
    }
    
  });
}


