$(document).ready(function(){
	$("#BookSpecs").hide();
	$("#MovieSpecs").hide();
	$("#AuthorSpecs").hide();
	$("#ActorSpecs").hide();
	$("#DirectorSpecs").hide();
	$("#paginationBar").hide();

	$("#Book, #Movie, #Author, #Actor, #Director").on("click", function (e) {
		// Prevent page from refreshing
		e.preventDefault();
		var url = "http://localhost:8080/examples/servlets/servlet/QueryProcessor?target=";
	    var elementId = $(this).attr("id");

	    url += document.getElementById(elementId).innerHTML;
	    sendRequest(url);
	})

	var previousId = "";
	$("#Book, #Movie, #Author, #Actor, #Director").hover(
		function(){
		$("#"+previousId+"Specs").hide();
		var elementId = $(this).attr("id");
		previousId = elementId;
		
		$("#"+elementId).closest('dl').addClass("active");
		$("#"+elementId+"Specs").show();
		},function(){
			var elementId = $(this).attr("id");
			$("#"+elementId).closest('dl').removeClass("active");
		})

	$("#submitButton").on("click", function(e){
		$("#paginationBar").show();
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
	alert(typeof req);
	if( req != null){
		req.open("GET", url, false);
		req.onreadystatechange = showResult;
		req.setRequestHeader("Connection", "Close");
		req.setRequestHeader("Method", "GET" + url + "HTTP/1.1" );
		req.send();
	}else{
		alert("Sorry, but I couldn't create an XMLHttpRequest");
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
		var result = document.getElementById('result_area');
		result.innerHTML = str;
	}

}