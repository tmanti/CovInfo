$('.register_form').hide();

$('.sign_in_btn').click(function(e){
  e.preventDefault();
  $(this).addClass('active');
  $('.login_form').show();
  $('.sign_up_btn').removeClass('active');
  $('.register_form').hide()
});

$('.sign_up_btn').click(function(e) {
  e.preventDefault();
  $(this).addClass('active');
  $('.sign_in_btn').removeClass('active');
  $('.register_form').show();
  $('.login_form').hide();
});
