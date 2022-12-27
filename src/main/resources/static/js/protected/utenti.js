
$(function() {
    inizializzaTabellaUtenti();
});
function inizializzaTabellaUtenti(){
    $("#elencoUtenti").DataTable({
        "responsive": true,
        "searching": true,
        "ordering" : true,
        "lengthChange": true,
        "serverSide": false,
        "processing" : true,
        "pagingType": "full_numbers",
        "mark"      : true,
        "language" : {
        	"url" : $("#elencoUtenti").data("table-lang-url")
        },
        "deferRender" : true,
        "drawCallback": function( settings ) {
        	//initTooltip();
            $('[data-toggle="tooltip"]').tooltip()
         },
         "columnDefs": [
         			{"data" : "nome", "targets":0, "sortable" : true},
         			{"data" : "cognome", "targets":1, "sortable" : true,"render":function(data,type, row){
         				return data;
         			}},
         			{"data" : "sesso", "targets":2, "sortable" : false,"render":function(data,type, row){
         				if(data === 'M'){
         				    return "uomo";
         				}else if(data === 'D'){
                            return "donna";
                        }else if(data === 'O'){
                            return "altro";
                        };
                        return data;
         			}},
         			{"data" : "dataNascita", "targets":3, "sortable" : false,"render":function(data,type, row){

         				return data;
         			}},
         			{"data" : "azioni"   , "targets":4, "sortable" : false,"render":function(data,type, row){
         				return row;
         			}}
         		]
    });
	$('#elencoUtenti tbody').on( 'click', 'tr td button.cambio-stato-ente', function () {
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
}