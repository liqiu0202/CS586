
var index = 1;

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
	var lastId = 1;
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

	/* --- Pagination Handler --- */
	$("#1, #2, #3, #4, #5, #last, #next").on('click', function(e){
		var currentId = $(this).attr("id");

		if(currentId == "last"){
			if(lastId == 1) return;
			$("#last").closest('.pageBar').removeClass('active');
			currentId = lastId - 1;
			$("#"+currentId).closest('.pageBar').addClass('active');
		}
		if(currentId == "next"){
			if(lastId % 5 == 0){
				for(var i = 1; i<=5; i++){
					$("#"+i).html(lastId+i);
				}
			}
			currentId = parseInt(lastId % 5 + 1);
		}

		index = parseInt($("#"+currentId).text());
		lastId = index;
		showResult();
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
	var currentIndex = index;
	var start = (currentIndex - 1) * 5;
	var end = start + 2;

	if( req.readyState == 4  && req.status == 200 ){
		var str = "<div>";
		var jsonData = JSON.parse( req.responseText );
		results = jsonData;

		if(results.length == 0){
			str += "<h2>No Result Found</h2></ul>";
			document.getElementById('result_area').innerHTML = str;
			return;
		}
		for(var i=start; i<=end; i++){
			var link = results[i].wikiLink;
			var name = results[i].name;
			var description = results[i].description;
			descriptionArray = description.split(" ");
			var tmp = "";
			for(var j = 0; j < 30 && j < descriptionArray.length; ++j){
				if( tmp != "" )  tmp += " ";
				tmp += descriptionArray[j]; 
			}
			tmp += " ... ";
			str += "<a href='" + link + "' target='_blank'>" + name + "</a><p style='font-size:15px'><span style='color:#3498DB'>" + link + "</span><br>"+tmp+"</p>";
		}
		str += "</div>";
		document.getElementById('result_area').innerHTML = str;
	}

}

function paginationHandler(currentId){
	if(currentId == "last"){
		var lastElmt = document.getElementById(lastId);
		lastElmt.className = "";

		currentId = lastId - 1;

		var currentElmt = document.getElementById(currentId);
		currentElmt.className += "active";
	}
	if(currentId == "next"){
		currentId == lastId + 1;
	}
	index = parseInt(currentId);
	lastId = index;
	showResult();
}


