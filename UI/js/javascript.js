
var index = 1;

$(document).ready(function(){
	$("#BookSpecs").hide();
	$("#MovieSpecs").hide();
	$("#AuthorSpecs").hide();
	$("#ActorSpecs").hide();
	$("#DirectorSpecs").hide();
	$("#paginationBar").hide();
	$("#sqlInput").hide();
	$(".custom-checkbox").attr("checked", false);
	$(".form-control").val('');

	init();

	var url = "http://localhost:8080/examples/servlets/servlet/QueryProcessor?target=";
	var param = "";
	var target = "";
	var previousId = "";
	var perviousSelected = "";
	var lastIndex = 1;
	var handler = false;
	var tagIndex = 0;

	/* ---  Handle nav tabs --- */

	$("#sparqlSearch").on("click", function(e){
		init();
		$("#menu").hide();
		$("#sqlInput").show();
		handler = true;
		param = "http://localhost:8080/examples/servlets/servlet/QueryProcessor?";
	})
	$("#facetedSearch").on("click", function(e){
		init();
		$(".custom-checkbox").attr("checked", false);
		$(".form-control").val('');
		$("#sqlInput").hide();
		$("#menu").show();
		handler = false;
		param = "";
	})

	/* --- Handle menu tabs --- */

	$("#Book, #Movie, #Author, #Actor, #Director").on("click", function (e) {
		// Prevent page from refreshing
		e.preventDefault();
		init();
		$(".custom-checkbox").attr("checked", false);
		$(".form-control").val('');
		$("#"+previousId+"Specs").hide();
		$("#"+perviousSelected).closest('dl').removeClass("selected");

		var elementId = $(this).attr("id");

		tagIndex = 1;
		$("#tag"+tagIndex).html($("#"+elementId).html()).show();
		tagIndex++;

		perviousSelected = elementId;
		previousId = elementId;
		$("#"+elementId).closest('dl').addClass("selected");
		$("#"+elementId+"Specs").show();

		target = elementId;

		param = "";
		
	    param = url + document.getElementById(elementId).innerHTML;
	})

	$("#BookName").on('click', function(e){
		if($("#BookName").is(':checked')){
			if($("#bookNameStr").val() != ""){
				$("#tag"+tagIndex).html($("#bookNameStr").val()).show();
				tagIndex++;
			}		
		}
	})
	$("#BookAuthor").on('click', function(e){
		if($("#BookAuthor").is(':checked')){
			if($("#bookAuthorStr").val() != ""){
				$("#tag"+tagIndex).html($("#bookAuthorStr").val()).show();
				tagIndex++;
			}			
		}
	})
	$("#BookLanguage").on('click', function(e){
		if($("#BookLanguage").is(':checked')){
			if($("#bookLanguageStr").val() != ""){
				$("#tag"+tagIndex).html($("#bookLanguageStr").val()).show();
				tagIndex++;
			}	
		}
	})
	$("#MovieName").on('click', function(e){
		if($("#MovieName").is(':checked')){
			$("#tag"+tagIndex).html($("#movieNameStr").val()).show();
			tagIndex++;
		}
	})
	$("#MovieWriter").on('click', function(e){
		if($("#MovieWriter").is(':checked')){
			if($("#movieWriterStr").val() != ""){
				$("#tag"+tagIndex).html($("#movieWriterStr").val()).show();
				tagIndex++;
			}
		}
	})
	$("#MovieDirector").on('click', function(e){
		if($("#MovieDirector").is(':checked')){
			if($("#movieDirectorStr").val() != ""){
				$("#tag"+tagIndex).html($("#movieDirectorStr").val()).show();
				tagIndex++;
			}
		}
	})
	$("#MovieStarring").on('click', function(e){
		if($("#MovieStarring").is(':checked')){
			if($("#movieStarringStr").val() != ""){
				$("#tag"+tagIndex).html($("#movieStarringStr").val()).show();
				tagIndex++;
			}	
		}
	})
	$("#MovieLanguage").on('click', function(e){
		if($("#MovieLanguage").is(':checked')){
			if($("#movieLanguageStr").val() != ""){
				$("#tag"+tagIndex).html($("#movieLanguageStr").val()).show();
				tagIndex++;
			}
		}
	})
	$("#AuthorMovieName").on('click', function(e){
		if($("#AuthorMovieName").is(':checked')){
			if($("#authorMovieNameStr").val() != ""){
				$("#tag"+tagIndex).html($("#authorMovieNameStr").val()).show();
				tagIndex++;
			}
		}
	})
	$("#AuthorBookName").on('click', function(e){
		if($("#AuthorBookName").is(':checked')){
			if($("#authorBookNameStr").val() != ""){
				$("#tag"+tagIndex).html($("#authorBookNameStr").val()).show();
				tagIndex++;
			}
		}
	})
	$("#ActorMovieName").on('click', function(e){
		if($("#ActorMovieName").is(':checked')){
			if($("#actorMovieNameStr").val() != ""){
				$("#tag"+tagIndex).html($("#actorMovieNameStr").val()).show();
				tagIndex++;
			}		
		}
	})
	$("#DirectorMovieName").on('click', function(e){
		if($("#DirectorMovieName").is(':checked')){
			if($("#directorMovieNameStr").val() != ""){
				$("#tag"+tagIndex).html($("#directorMovieNameStr").val()).show();
				tagIndex++;
			}			
		}
	})
	
	// $("#Book, #Movie, #Author, #Actor, #Director").hover(
	// 	function(){
	// 		//hover on
	// 		$("#"+previousId+"Specs").hide();
	// 		var elementId = $(this).attr("id");
	// 		previousId = elementId;
			
	// 		$("#"+elementId).closest('dl').addClass("active");
	// 		$("#"+elementId+"Specs").show();
	// 	},
	// 	function(){
	// 		//hover out
	// 		var elementId = $(this).attr("id");
	// 		$("#"+elementId).closest('dl').removeClass("active");
	// 	}
	// )
	// $("#menu").hover(function(){},function(){
	// 	$("#"+previousId+"Specs").hide();
	// })
	

	/* --- Pagination Handler --- */
	$("#1, #2, #3, #4, #5, #last, #next").on('click', function(e){
		var currentId = $(this).attr("id");

		if(currentId == "last"){
			if(lastIndex == 1) return;
			if(lastIndex % 5 == 1){
				for(var i = 5; i>=1; i--){
					$("#"+(6-i)).html(lastIndex-i);
				}
				currentId = 5;
			}else if(lastIndex % 5 == 0){
				currentId = 4;
			}else{
				currentId = parseInt(lastIndex % 5 - 1);
			}		
		}
		if(currentId == "next"){
			if(lastIndex % 5 == 0){
				for(var i = 1; i<=5; i++){
					$("#"+i).html(lastIndex+i);
				}
			}
			currentId = parseInt(lastIndex % 5 + 1);
		}

		index = parseInt($("#"+currentId).text());
		lastIndex = index;
		showResult();
	})

	/* --- Handle submit button --- */
	$("#submitButton").on("click", function(e){
		init();
		index = 1;
		var detail = "";
		var urlToGo = "";

		if(handler == true){
			detail += "queryString=" + $("textarea#text_area").val();			
		}else{		
			switch(target){
				case "Book":
					detail += ($("#BookName").is(':checked')) ? "&name=" + $("#bookNameStr").val() : "";
					detail += ($("#BookAuthor").is(':checked')) ? "&author=" + $("#bookAuthorStr").val() : "";
					detail += ($("#BookLanguage").is(':checked')) ? "&language=" + $("#bookLanguageStr").val() : "";
					break;
				case "Movie":
					detail += ($("#MovieName").is(':checked')) ? "&name=" + $("#movieNameStr").val() : "";
					detail += ($("#MovieWriter").is(':checked')) ? "&writer=" + $("#movieWriterStr").val() : "";
					detail += ($("#MovieDirector").is(':checked')) ? "&director=" + $("#movieDirectorStr").val() : "";
					detail += ($("#MovieStarring").is(':checked')) ? "&starring=" + $("#movieStarringStr").val() : "";
					detail += ($("#MovieLanguage").is(':checked')) ? "&language=" + $("#movieLanguageStr").val() : "";
					break;
				case "Author":
					detail += ($("#AuthorMovieName").is(':checked')) ? "&movieName=" + $("#authorMovieNameStr").val() : "";
					detail += ($("#AuthorBookName").is(':checked')) ? "&bookName=" + $("#authorBookNameStr").val() : "";
					break;
				case "Actor":
					detail += ($("#ActorMovieName").is(':checked')) ? "&movieName=" + $("#actorMovieNameStr").val() : "";
					break;
				case "Director":
					detail += ($("#DirectorMovieName").is(':checked')) ? "&movieName=" + $("#directorMovieNameStr").val() : "";
					break;
			}
		}
		
		
		urlToGo = param + detail;

		sendRequest(urlToGo);
		var flag = showResult();
		(flag) ? $("#paginationBar").show() : $("#paginationBar").hide();
		
	})

});



function sendRequest(url) {
	// alert(url);
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
		// req.setRequestHeader("Connection", "Close");
		// req.setRequestHeader("Method", "GET" + url + "HTTP/1.1" );
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
			str += "<h3>No Result Found</h3></ul>";
			document.getElementById('result_area').innerHTML = str;
			
			return false;
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
			str += "<p><span class='resultTitle'><b><a href='" + link + "' target='_blank'>" + name + "</a></b></span><br>";
			str += "<span class='resultLink'>" + link + "</span><br>";
			str += "<span class='resultContent'>"+ tmp +"</span><br></p>";
		}
		str += "</div>";
		document.getElementById('result_area').innerHTML = str;
		return true;
	}

}

function init(){

	for(var i = 1; i<=5; i++){
		$("#"+i).html(i);
	}
	for(var i = 1; i<=6; i++){
		$("#tag"+i).hide();
	}

}
