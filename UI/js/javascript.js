$(document).ready(function(){
    $("#accordian h3").click(function () {
		//slide up all the link lists
		$("#accordian ul ul").slideUp();
		//slide down the link list below the h3 clicked - only if its closed
		if(!$(this).next().is(":visible"))
		{
			$(this).next().slideDown();
		}
    })
	var url = "http://localhost:8080/examples/servlets/servlet/QueryProcessor?target=";

	$("#accordian a").on("click", function (e) {
		e.preventDefault();

	    var elementId = $(this).attr("id");
	    url += document.getElementById(elementId).text;
	    sendRequest(url);
	})
});

function sendRequest(url) {
	alert(url);
	req = null;
	if( window.XMLHttpRequest ){
		req = new XMLHttpRequest();
	}else if( window.ActiveObject ){
		try{
			req = new ActiveObject("Microsoft.XMLHTTP");
		}catch( e ){
		}
	}
	if( req ){
		req.open("GET", url, false);
		req.onreadystatechange = showResult;
		req.setRequestHeader("Connection", "Close");
		req.setRequestHeader("Method", "GET" + url + "HTTP/1.1" );
		req.send();
	}else{
		alert("Sorry, but I couldn't create and XMLHttpRequest");
	}
}

function showResult(){
	if( req.readyState == 4  && req.status == 200 ){
		var str = "<ul>";
		var jsonData = JSON.parse( req.responseText );
		results = jsonData;
		for(var i in results ){
			var link = results[i].wikiLink;
			var name = results[i].name;
			str += "<li><a href='" + link + "' target='_blank' style='color:black'>" + name + "</a></li>";
		}
		str += "</ul>";
		var result = document.getElementById('result');
		result.innerHTML = str;
	}
	
}