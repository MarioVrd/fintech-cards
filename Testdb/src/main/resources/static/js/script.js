$('#card-form-select').change(function() {
    opt = $(this).val();
    if (opt=="PHYSICAL") {
        $('.address').toggleClass('hidden');
    }
    else {
        $('.address').addClass('hidden');
    }
});