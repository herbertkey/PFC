$(document).ready(function(){
  changeoptCategoria2();
  changeoptSubcategoria2();
});

function changeoptCategoria2(){
  $('.select-categoria').change(function(){
    var categoria_selected = $( ".select-categoria option:selected" ).text();
    alterSelectSubcategoria(categoria_selected);
    
  });
}

function alterSelectSubcategoria(categoria_selected){
  $('.select-subcategoria options').hide();
  $('.select-subcategoria option').each(function(index, data){
    console.log(this);
    console.log('Option:'+$(this).attr('data-categoria')+' selected:'+ categoria_selected);
    
    if($(this).attr('data-categoria') == categoria_selected){
      $(this).show();
    }else{
       $(this).hide();
    }
    
  });  
  $(".select-subcategoria option:first").prop('selectedIndex',0);
}


