
$(document).ready( function() {
var $validateForm = $("#validateForm");
	if ($validateForm.size() > 0) {
		$.metadata.setType("attr", "validate");
		$validateForm.validate({
			//errorContainer: '#validateErrorContainer',
			//errorLabelContainer: '#validateErrorContainer ul',
			//wrapper: "div",
			errorClass: "validateError",
			ignoreTitle: true,
			ignore: ".ignoreValidate",
			submitHandler: function(form) {
				$(form).find(":submit").attr("disabled", true);
				form.submit();
			}
		});
	}
	
});