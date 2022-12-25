$(function() {
  $("#dataNascita").datepicker({
    uiLibrary: 'bootstrap5',
    locale: 'it-it',
    format: 'dd/mm/yyyy',
    showOtherMonths: true,
    calendarWeeks: false,
    modal: false,
    footer: true
  });
  $("#salva").click(function(event){

    let form = document.getElementById("inserimento-amministratore-form");
    if(form.checkValidity()){
        event.preventDefault();
        alert("OK "+convertFormToJSON(form));
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

				$("#operazioneInCorsoModal").modal({
				    show: true
				});
			},
			complete:function(xhr, status){
				$("#operazioneInCorsoModal").modal({
				    show: false
				});
			},
			success : function(result) {
				console.log("OK");
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				console.log("Errore "+textStatus);
			}
		});
}