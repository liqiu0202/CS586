$(document).ready(function(){
	$("#BookSpecs").hide();
	$("#MovieSpecs").hide();
	$("#AuthorSpecs").hide();
	$("#ActorSpecs").hide();
	$("#DirectorSpecs").hide();
	$("#paginationBar").hide();
	$("#sqlInput").hide();

	var url = "http://localhost:8080/examples/servlets/servlet/QueryProcessor?target=";
	var param = "";
	var previousId = "";
	var perviousSelected = "";

	/* ---  Handle nav tabs --- */
	$("#sparqlSearch").on("click", function(e){
		$("#menu").hide();
		$("#sqlInput").show();
	})
	$("#facetedSearch").on("click", function(e){
		$("#sqlInput").hide();
		$("#menu").show();
	})

	/* --- Handle menu tabs --- */
	$("#Book, #Movie, #Author, #Actor, #Director").on("click", function (e) {
		// Prevent page from refreshing
		e.preventDefault();
		$("#"+perviousSelected).closest('dl').removeClass("selected");

		var elementId = $(this).attr("id");
		perviousSelected = elementId;
		$("#"+elementId).closest('dl').addClass("selected");

		param = "";
	    param = url + document.getElementById(elementId).innerHTML;
	})
	$("#Book, #Movie, #Author, #Actor, #Director").hover(
		function(){
			//hover on
			$("#"+previousId+"Specs").hide();
			var elementId = $(this).attr("id");
			previousId = elementId;
			
			$("#"+elementId).closest('dl').addClass("active");
			$("#"+elementId+"Specs").show();
		},
		function(){
			//hover out
			var elementId = $(this).attr("id");
			$("#"+elementId).closest('dl').removeClass("active");
		}
	)
	$("#menu").hover(function(){},function(){
		$("#"+previousId+"Specs").hide();
	})

	/* --- Handle submit button --- */
	$("#submitButton").on("click", function(e){
		sendRequest(param);
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

	if( req != null){
		req.open("GET", url, false);
		req.onreadystatechange = showResult;
		req.setRequestHeader("Connection", "Close");
		req.setRequestHeader("Method", "GET" + url + "HTTP/1.1" );
		req.send(null);
	}else{
		alert("Sorry, but I couldn't create an XMLHttpRequest");
	}
}

function showResult(){

	if( req.readyState == 4  && req.status == 200 ){
		var str = "<ul>";
		var jsonData = JSON.parse( req.responseText );
		results = jsonData;

		if(results.length == 0){
			str += "<h2>No Result Found</h2></ul>";
			document.getElementById('result_area').innerHTML = str;
			return;
		}
		for(var i=0; i<10; i++){
			var link = results[i].wikiLink;
			var name = results[i].name;
			str += "<li><a href='" + link + "' target='_blank' style='color:black'>" + name + "</a></li>";
		}
		str += "</ul>";
		document.getElementById('result_area').innerHTML = str;
	}

}