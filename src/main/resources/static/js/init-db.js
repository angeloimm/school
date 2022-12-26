$(function() {
  $("#saveOk").hide();
  $("#saveKo").hide();
  $("#dataNascita").datepicker({
    uiLibrary: 'bootstrap5',
    locale: 'it-it',
    format: 'dd/mm/yyyy',
    showOtherMonths: true,
    calendarWeeks: false,
    maxDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()),
    modal: false,
    footer: true
  });
  $("#salva").click(function(event){

    let form = document.getElementById("inserimento-amministratore-form");
    if(form.checkValidity()){
        event.preventDefault();
        let datiAmministratore = convertFormToJSON(form);
        let dataNascitaString = $("#dataNascita").datepicker().value();
        datiAmministratore.dataNascita = moment(dataNascitaString, 'dd/mm/yyyy').toDate().getTime();
        salvaAmministratore( datiAmministratore );
    }
  });
});

function salvaAmministratore( datiAmministratore ){
    $.ajax({
			url:  $("#inserimento-amministratore-form").data("salva-admin-url"),
			type:'POST',
			dataType: "json",
			data:JSON.stringify(datiAmministratore),
			contentType: "application/json; charset=utf-8",
			beforeSend: function(xhr) {
			    $.blockUI({ message: $('#operazioneInCorsoMessage') });
			},
			complete:function(xhr, status){
            	$.unblockUI();
            },
			success : function(result) {
			    $("#saveOk").show();
				setTimeout(salvataggioOk, 5000);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				$("#saveKo").show();
			}
		});
}
function salvataggioOk(){
    $("#salva").prop("disabled",true);
    window.location.href = $("#inserimento-amministratore-form").data("login-redirect-url");
}