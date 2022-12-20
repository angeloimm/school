var rigaAnagraficaAmministratoreEnte = Handlebars.compile($("#rigaAnagraficaAmministratoreEnte").html());
var selezioneTipoEnte = Handlebars.compile($("#selezioneTipoEnte").html());
var templateAzioniEnte = Handlebars.compile($("#templateAzioniEnte").html());
var confermaCambioStato = Handlebars.compile($("#confermaCambioStatoTemplate").html());
var templateTipoEnte = Handlebars.compile($("#templateTipoEnte").html());
Handlebars.registerHelper("isTrue", function(valore) {
	return valore === true;
});
Handlebars.registerHelper('toLowerCase', function(str) {
  return str.toLowerCase();
});
var elencoEntiCensitiDt = null;
$(function(){
	 
/*	$("#aggiungiEnteButton").click(function(evt){
		evt.preventDefault();
			BootstrapDialog.show({
            title: $(this).data("titolo-dialog"),
			nl2br:false,
            message: function(dialog) {
               let $body = selezioneTipoEnte();
				
                return $body;
            }
        });
	})*/
	
	elencoEntiCensitiDt = $("#elencoEntiCensiti").DataTable({
		"responsive": true,
		"searching": true,
		"ordering" : true,
		"lengthChange": true,
		"serverSide": true,
		"processing" : true,
		"pagingType": "full_numbers",
		"mark"      : true,
		"language" : {
			"url" : $("#elencoEntiCensiti").data("table-lang-url")
		},
		"deferRender" : true,
		"drawCallback": function( settings ) {
			//initTooltip();
			$('[data-toggle="tooltip"]').tooltip()
		},
		"columnDefs": [
			{"data" : "denominazioneEnte", "targets":0, "sortable" : true},
			{"data" : "codiceEnte", "targets":1, "sortable" : true,"render":function(data,type, row){ 
				return data.toLowerCase();
			}},
			{"data" : "anagraficaResponsabile", "targets":2, "sortable" : false,"render":function(data,type, row){ 
				return rigaAnagraficaAmministratoreEnte(row);
			}},
			{"data" : "entePrivato", "targets":3, "sortable" : false,"render":function(data,type, row){
				 
				let obj = new Object()
				obj.privato = data;
				return templateTipoEnte(obj);
			}},
			{"data" : "azioni"   , "targets":4, "sortable" : false,"render":function(data,type, row){ 
				return templateAzioniEnte(row);
			}}			
		],
		"ajax": {
			"dataSrc" : "payload",
			"url": $("#elencoEntiCensiti").data("elenco-enti-url"),
			"contentType" : "application/json; charset=utf-8"
		}
	});
	$('#elencoEntiCensiti tbody').on( 'click', 'tr td button.cambio-stato-ente', function () {
        let tr = $(this).closest('tr');
        let row = elencoEntiCensitiDt.row( tr );
		let data = row.data();
        let attivo = data.attivo;
		let idEnte = data.id;
		let codiceEnte = data.codiceEnte;
		let denominazioneEnte = data.denominazioneEnte;
		BootstrapDialog.show({
            title: $(this).data("modifica-stato-dialog-title"),
			nl2br:false,
            message: function(dialog) {
				let content = new Object();
				content.enteAttivo = attivo;
				content.idEnte = idEnte; 
				content.codiceEnte = codiceEnte;
				content.denominazioneEnte = denominazioneEnte;
               	let $body = confermaCambioStato(content);
				
                return $body;
            },
			onshown: function(dialog){
				$("#conferma-cambio-stato-ente").click(function(evt){
					evt.preventDefault();
					//Passo !attivo per inviare il nuovo valore di stato. Se dal DB arriva attivo = true -> invio al server attivo = false
					modificaStatoEnte(idEnte, !attivo, 'cardCambioStatoEnte')
				});
			}
		});
    } );
});
function modificaStatoEnte(idEnte, nuovoStato, idElementoBlockUi){
	let baseUrl = $("#"+idElementoBlockUi).data("url-cambio-stato");
	let url = baseUrl.endsWith("/") ? baseUrl+idEnte+"/"+nuovoStato : baseUrl+"/"+idEnte+"/"+nuovoStato;
	$.ajax({
		url:  url,
		type:'PUT',
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		beforeSend: function(xhr) {
			$("#"+idElementoBlockUi).block({ message: $('#operazioneInCorsoMessage') });
		},
		complete:function(xhr, status){
			$("#"+idElementoBlockUi).unblock();
		},
		success : function(result) {
			$("#esitoOperazioneResult").html($("#"+idElementoBlockUi).data("esito-salvataggio-ok"));
			$("#esitoOperazioneResult").show()
			$("#conferma-cambio-stato-ente").prop("disabled", true);
			elencoEntiCensitiDt.ajax.reload();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			$("#esitoOperazioneResult").html($("#"+idElementoBlockUi).data("esito-salvataggio-ko"));			
		}
	});
}