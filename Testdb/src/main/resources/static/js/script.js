$('#card-form-select').change(function() {
    opt = $(this).val();
    if (opt=="PHYSICAL") {
        $('.address').toggleClass('hidden');
        $('#address-input').val("");
    }
    else {
        $('.address').addClass('hidden');
    }
});