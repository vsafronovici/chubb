<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">	
	<head>
		<script type="text/javascript" src="<%= request.getContextPath() %>/res/js/jquery.min-1.8.3.js"></script>
		<script>
		
		function xmlToString(xmlData) { // this functions waits jQuery XML 

		    var xmlString = undefined;

		    if (window.ActiveXObject){
		        xmlString = xmlData.xml;
		    }

		    if (xmlString === undefined){
		        var oSerializer = new XMLSerializer();
		        xmlString = oSerializer.serializeToString(xmlData);
		    }

		    return xmlString;
		}		

		$(function() {	
			$.ajax({
				  type: "get",
				  url: '<%= request.getContextPath() %>/rest/userres/users',
				  cache: true,
				  dataType: "json",
				  success: function(json) {
					  console.log(json);
					  $('#jsonContent').html(JSON.stringify(json));
				  },
				  error: function(msg) {
					  alert(msg);
				  }
			});						
			$.ajax({
				  type: "get",
				  url: '<%= request.getContextPath() %>/rest/userres/users',
				  cache: true,
				  dataType: "xml",
				  success: function(xml) {
					  $('#xmlContent').text(xmlToString(xml));
				  },
				  error: function(msg) {
					  alert(msg);
				  }
			});						
		});
		
		
		
		
		</script>
	
	</head>
	<body>
		hi2 <%= request.getContextPath() %>
		<h2>jsonContent</h2>
		<div id="jsonContent"></div>
		<h2>xmlContent</h2>
		<div id="xmlContent"></div>
	</body>
</html>
