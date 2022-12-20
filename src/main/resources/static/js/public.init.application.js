var templateDownload = Handlebars.compile($('#templateDownload').html());
var templateUploadFile = Handlebars.compile($('#templateUpload').html());
var templateErroreUploadFile = Handlebars.compile($('#templateErroreUploadFile').html()); 
$(function () {
	$('#file-elenco-operatori').fileupload({ 
					autoUpload:true,
					url: $('#file-elenco-operatori').data("upload-url"),
					dropZone: $('#dropzone'),
					filesContainer: $('#elencoFile'),
					uploadTemplateId: null,
					downloadTemplateId: null,
					maxNumberOfFiles  : 1,
					maxFileSize: 1000000,
					messages: {
       						maxNumberOfFiles: $('#file-elenco-operatori').data("too-many-files"),
        					acceptFileTypes: $('#file-elenco-operatori').data("file-not-allowed"),
        					maxFileSize: $('#file-elenco-operatori').data("file-too-big"),
        					minFileSize: $('#file-elenco-operatori').data("file-too-small")
      				},
      				acceptFileTypes: /(\.|\/)(csv)$/i,
					uploadTemplate: function(o) {
						//Gestisco il template di visualizzazione dei vari file allegati prima dell'upload
						//Svuoto il contenuto precedente
						$('#elencoFile').empty();
						var rows = $();						        	
						$.each(o.files, function(index, file) {				                        
							var data = {o: o, file: file, error:file.error};
							data.dimensione = formatBytes(file.size);
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
							
							var data = {o: o, file: file, error:file.error};
							var template = templateDownload(data);
							var row = $(template);
							rows = rows.add(row);
							$('#elencoErroriUploadFile').html("");
						});
						return rows;
					}
				});
				$('#file-elenco-operatori').on('fileuploaddone', function (e, data) {
					let redirectUrl = $("#file-elenco-operatori").data("success-upload-redirect-url");
					// Your delay in milliseconds
					let delay = 5000; 
					setTimeout(function(){ window.location = redirectUrl; }, delay);
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

		   		
});
function formatBytes(bytes,decimals) {
	if(bytes == 0) return '0 Bytes';
	var k = 1000,
	   dm = decimals + 1 || 3,
	   sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
	   i = Math.floor(Math.log(bytes) / Math.log(k));
	return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
}