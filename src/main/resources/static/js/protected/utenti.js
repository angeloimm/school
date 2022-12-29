var templateAzioniUtente = Handlebars.compile($("#templateAzioniUtente").html());
var templateDatiUtente = Handlebars.compile($("#templateDatiUtente").html());
var templateDownload = Handlebars.compile($('#templateDownload').html());
var templateUploadFile = Handlebars.compile($('#templateUpload').html());
var templateErroreUploadFile = Handlebars.compile($('#templateErroreUploadFile').html());
var tabellaUtenti = null;
var idAllegati = [];
var datiUtenteModal = null;
$(function() {
    inizializzaTabellaUtenti();
    addModalEventsListener();
    $("#aggiungiUtente").click(function(evt){
        evt.preventDefault();
        let modalTitle = $("#aggiungiUtente").data("add-utente-dialog-title");
        //ripulisco quanto inserito prima e ri-appendo
        $("#modalTitle").empty();
        $("#modalTitle").append(modalTitle);
        let datiUtente = {};
        //ripulisco quanto inserito prima e ri-appendo
        $("#modalBody").empty();
        $("#modalBody").append(templateDatiUtente(datiUtente));
        let modalOptions = {};
        datiUtenteModal = new bootstrap.Modal(document.getElementById('dati-utente-modal'), modalOptions);
        datiUtenteModal.show();
    });
});
function addModalEventsListener(){
    let myModalEl = document.getElementById('dati-utente-modal');
    myModalEl.addEventListener('hidden.bs.modal', event => {
        // do something...
        console.log("Modale hidden");
    });
    myModalEl.addEventListener('hide.bs.modal', event => {
      // do something...
      console.log("Modale hide");
    });
    myModalEl.addEventListener('show.bs.modal', event => {
      // do something...
      console.log("Modale show");
    });
    myModalEl.addEventListener('shown.bs.modal', event => {
      // do something...
      //Aggiungo validazione form
      const datiUtenteForm = $("#inserimento-modifica-utente-form")[0];
      datiUtenteForm.addEventListener('submit', event => {
            if (!datiUtenteForm.checkValidity()) {
              event.preventDefault();
              event.stopPropagation();
            }
            datiUtenteForm.classList.add('was-validated');
            }, false);
            //Inizializzo caldendar
            $("#dataNascita").datepicker({
                uiLibrary: 'bootstrap5',
                locale: 'it-it',
                format: 'dd/mm/yyyy',
                showOtherMonths: true,
                calendarWeeks: false,
                maxDate: new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()),
                modal: false,
                footer: false
              });
            //Inizializzo uploader
            initUploadZone();
            //Avvio il listener per controllo username
            $("#username").blur(function(evt){
                verifyUsername($(this));
            });
    });
}
function inizializzaTabellaUtenti(){
    let tipoUtente = params.tipoUtente;
    let url = $("#elencoUtenti").data("elenco-utenti-url")+"?tipoUtente="+tipoUtente;
    tabellaUtenti = $("#elencoUtenti").DataTable({
        "responsive": true,
        "searching": true,
        "ordering" : true,
        "lengthChange": true,
        "serverSide": true,
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
         				    return "Uomo";
         				}else if(data === 'F'){
                            return "Donna";
                        }else if(data === 'O'){
                            return "Altro";
                        };
                        return data;
         			}},
         			{"data" : "codiceFiscale", "targets":3, "sortable" : false,"render":function(data,type, row){

         				return data;
         			}},
         			{"data" : "dataNascita", "targets":4, "sortable" : false,"render":function(data,type, row){

         				return data;
         			}},
         			{"data" : "azioni"   , "targets":5, "sortable" : false,"render":function(data,type, row){
         				return templateAzioniUtente(row);
         			}}
         		],

         "ajax": {
         	"dataSrc" : "payload",
            "url": url,
            "contentType" : "application/json"
         }
    });
	$('#elencoUtenti tbody').on( 'click', 'tr td button.modifica-utente', function () {
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
function formDataElementiForm(){
    var nome = $("#nome").val();
    var cognome = $("#cognome").val();
    var dataNascita = moment($("#dataNascita").datepicker().value(), 'dd/mm/yyyy').toDate().getTime();
    var codiceFiscale = $("#codiceFiscale").val();
    var sesso = $("#sesso").val();
    var indirizzo = $("#indirizzo").val();
    var username = $("#username").val();
    var password = $("#password").val();
    var tipoUtenteEnum = $("#tipoUtenteEnum").val();
    return {
        "nome":nome,
        "cognome":cognome,
        "dataNascita":dataNascita,
        "codiceFiscale":codiceFiscale,
        "sesso":sesso,
        "indirizzo":indirizzo,
        "tipoUtente":tipoUtenteEnum,
        "username":username,
        "password":password
    };
}
function initUploadZone(){

	$('#inserimento-modifica-utente-form').fileupload({
					autoUpload:true,
					url: $('#allegati-utente').data("upload-url"),
					dropZone: $('#dropzone'),
					filesContainer: $('#elencoFile'),
					uploadTemplateId: null,
					downloadTemplateId: null,
					messages: {
       						maxNumberOfFiles: $('#allegati-utente').data("too-many-files"),
        					acceptFileTypes: $('#allegati-utente').data("file-not-allowed"),
        					maxFileSize: $('#allegati-utente').data("file-too-big"),
        					minFileSize: $('#allegati-utente').data("file-too-small")
      				},
					maxNumberOfFiles  : 100,
					maxFileSize: 5000000,
      				acceptFileTypes: /(\.|\/)(pdf)$/i,
					uploadTemplate: function(o) {
						//Gestisco il template di visualizzazione dei vari file allegati prima dell'upload
						//Svuoto il contenuto precedente
						//$('#elencoFile').empty();
						var rows = $();						        	
						$.each(o.files, function(index, file) {
							let errore = file.error;
							//Controllo se ci sono errori nell'upload; se si, controllo se legati all'entity ID esistente
							var data = {o: o, file: file, error:errore};
							data.dimensione = formatBytes(file.size);
							data.size = file.size;
							var template = templateUploadFile(data,{allowProtoMethodsByDefault:true, allowProtoPropertiesByDefault: true });
							var row = $(template);
							rows = rows.add(row);
						});
						return rows;
					},			        		
					downloadTemplate: function(o) {
						//Gestisco il template di visualizzazione dei vari file allegati dopo l'upload
						var rows = $();			    			
						$.each(o.files, function(index, file) {
							let errore = file.error;
							//Controllo se ci sono errori nell'upload; se si, controllo se legati all'entity ID esistente
							if( errore && errore ==="SP_ENTITY_ID_ENTE_ESISTENTE" ){
								errore = $('#allegati-utente').data("sp-entity-id-esistente");
							} 
							var data = {o: o, file: file, error:errore};
							var template = templateDownload(data);
							var row = $(template);
							rows = rows.add(row);
							$('#elencoErroriUploadFile').html("");
						});
						return rows;
					}
				});
				$('#inserimento-modifica-utente-form').on('fileuploaddone', function (e, data) {
				    let uploadedFiles = data.result.files;
				    for( let indice = 0; indice < uploadedFiles.length; indice++ ){
				        idAllegati.push(uploadedFiles[indice].id);
				    }
				});
				//Mi metto in ascolto sul drag&drop dei file
				$(document).bind('dragover', function (e) {
					var dropZone = $('#dropzone'),timeout = window.dropZoneTimeout;
					if (timeout) {
						clearTimeout(timeout);
					} else {
						dropZone.addClass('border border-danger');
					}
					var hoveredDropZone = $(e.target).closest(dropZone);
					dropZone.toggleClass('border border-danger', hoveredDropZone.length);
					window.dropZoneTimeout = setTimeout(function () {
						window.dropZoneTimeout = null;
						dropZone.removeClass('border border-danger');
					}, 100);
				});
    $("#salva").click(function(evt){
        evt.preventDefault();
        var elementiForm = formDataElementiForm();
        elementiForm.allegati = idAllegati;
        var theUrl = $(this).data("salvataggio-utente-url");
        $.ajax({
        			url:  theUrl,
        			type:'POST',
        			dataType: "json",
        			data:JSON.stringify(elementiForm),
        			contentType: "application/json",
        			beforeSend: function(xhr) {
        			    $.blockUI({ message: $('#operazioneInCorsoMessage') });
        			},
        			complete:function(xhr, status){
                    	$.unblockUI();
                    },
        			success : function(result) {
        			    tabellaUtenti.ajax.reload();
        			    datiUtenteModal.hide();
        			},
        			error: function(XMLHttpRequest, textStatus, errorThrown) {
        				alert("Errore salvataggio dati utente");
        			}
        		});
    });
    $('#inserimento-modifica-utente-form').on('fileuploadsubmit', function (e, data) {

   		return true;
   	});
}
function formatBytes(bytes,decimals) {
	if(bytes == 0) return '0 Bytes';
	var k = 1000,
	   dm = decimals + 1 || 3,
	   sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
	   i = Math.floor(Math.log(bytes) / Math.log(k));
	return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
}
function verifyUsername(jqueryElement){
    let username = jqueryElement.val();
    let url = jqueryElement.data("username-validation-url");
    let finalUrl = url.endsWith("/")?url+username:url+"/"+username;
    $.ajax({
        url:  finalUrl,
        type:'GET',
        dataType: "json",
        //data:JSON.stringify(elementiForm),
        contentType: "application/json",
        beforeSend: function(xhr) {
            $.blockUI({ message: $('#operazioneInCorsoMessage') });
        },
        complete:function(xhr, status){
            $.unblockUI();
        },
        success : function(result) {
            let usernameValida = result.payload;
            if(usernameValida) {
                jqueryElement.addClass("is-valid");
                jqueryElement.removeClass("is-invalid");
                (jqueryElement[0]).setCustomValidity("");
                /*el.classList.add("is-valid");
                el.classList.remove("is-invalid");
                el.setCustomValidity("");*/
            } else {
                jqueryElement.removeClass("is-valid");
                jqueryElement.addClass("is-invalid");
                (jqueryElement[0]).setCustomValidity("invalid");
                /*el.classList.remove("is-valid");
                el.classList.add("is-invalid");
                el.setCustomValidity("invalid");*/
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("Errore nel processo di validazione della username");
        }
    });
}