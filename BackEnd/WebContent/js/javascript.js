
var index = 1;
var currentPageId = 1;
var recordsPerPage = 3;
var pageNum = 0;

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
	$('.label-info').hide();
	

	init();

	var url = "QueryProcessor?target=";
	var param = "";
	var target = "";
	var previousId = "";
	var perviousSelected = "";
//	var lastIndex = 1;
	var handler = false;
	var tagIndex = 0;

	/* ---  Handle nav tabs --- */

	$("#sparqlSearch").on("click", function(e){
		init();
		$("#menu").hide();
		$("#sqlInput").show();
		handler = true;
		param = "QueryProcessor?";
	});
	
	$("#facetedSearch").on("click", function(e){
		init();
		$(".custom-checkbox").attr("checked", false);
		$(".form-control").val('');
		$("#sqlInput").hide();
		$("#menu").show();
		handler = false;
		param = "";
	});

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

//		tagIndex = 1;
//		$("#tag"+tagIndex).html($("#"+elementId).html()).show();
//		tagIndex++;

		perviousSelected = elementId;
		previousId = elementId;
		$("#"+elementId).closest('dl').addClass("selected");
		$("#"+elementId+"Specs").show();

		target = elementId;

		param = "";
		
	    param = url + document.getElementById(elementId).innerHTML;
	    clickTarget(elementId);
	});

	

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
					detail += ($("#MovieBasedOn").is(':checked')) ? "&baseOn=" + $("#MovieBasedOnStr").val() : "";
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
		
	});

});


function clickTarget(targetId){
	$('.label-info').hide();
	$("#targetName").html(targetId);
	$("#targetName").show();
	
	//clickTag(targetId);
}
function clickCheckBox(checkBoxId){
	console.log("in click function");
	var tagId = $("#"+ checkBoxId).attr("tagId");
	var contentInput = $("#"+checkBoxId).attr("contentInput");
	//console.log( $("#"+ contentInput) );
	if( $("#"+ checkBoxId ).is(':checked') ){
		var content = $("#"+ contentInput).val();
		console.log(content);
		if( content != "" ){
			$("#"+tagId).html(content);
			$("#"+tagId).show();
		}
	}else{
		$("#"+tagId).hide();
	}
	
}


function showPaginationBar(results){
	pageNum = Math.ceil(results.length / recordsPerPage );
	var str = "<ul><li class='previous' id='movePrevious' onclick=\"moveToPrevious()\"><a class='fui-arrow-left'></a></li>";
	for(var i = 0; i < pageNum; ++i){
		if( i == 0 ) str += "<li class='pageBar active' onclick=\"changePage(this.id)\" id='" + (i+1) + "'><a id='" + (i+1) + "'>" + (i+1) + "</a></li>";
		else str += "<li class='pageBar' onclick=\"changePage(this.id)\" id='" + (i+1) + "'><a id='" + (i+1) + "'>" + (i+1) + "</a></li>";
	}
	str += "<li class='next' id='moveNext' onclick=\"moveToNext()\"><a class='fui-arrow-right'></a></li></ul>";
    document.getElementById('paginationBar').innerHTML = str;
}

function showRecords(pageId){
	var offset = (pageId - 1) * recordsPerPage;
	var str = "";
	for(var i = 0; i < recordsPerPage && offset + i < results.length; ++i ){
		var link = results[offset + i].wikiLink;
			var name = results[offset + i].name;
			var description = results[offset + i].description;
			descriptionArray = description.split(" ");
			var tmp = "";
			for(var j = 0; j < 30 && j < descriptionArray.length; ++j){
				if( tmp != "" )  tmp += " ";
				tmp += descriptionArray[j]; 
			}
			tmp = tmp.substring( 0, Math.min(tmp.length, 150) );
			tmp += " ... ";
			str += "<p><span class='resultTitle'><b><a href='" + link + "' target='_blank'>" + name + "</a></b></span><br>";
			str += "<span class='resultLink'>" + link + "</span><br>";
			str += "<span class='resultContent'>"+ tmp +"</span><br></p>";
	}
	document.getElementById('result_area').innerHTML = str;
}

/* ---- click page handler ------*/
function changePage(pageId){
	//inactive previous page
	document.getElementById(currentPageId).setAttribute("class", "pageBar");
	document.getElementById(pageId).setAttribute("class", "pageBar active");
	currentPageId = pageId;
	showRecords(pageId);
}

function moveToPrevious(){
	if( currentPageId > 1 ){
		changePage( currentPageId - 1);
	}
}
function moveToNext(){
	if( currentPageId < pageNum ){
		changePage(currentPageId + 1);
	}
}



function sendRequest(url) {
	console.log(url);
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

	if( req.readyState == 4  && req.status == 200 ){
		var str = "";
		var jsonData = JSON.parse( req.responseText );
		results = jsonData;

		if(results.length == 0){
			str += "<h3>No Result Found</h3></ul>";
			document.getElementById('result_area').innerHTML = str;
			
			return false;
		}
		currentPageId = 1;
		showPaginationBar(results);
		showRecords(1);
		return true;
	}

}

function init(){

	
	document.getElementById('result_area').innerHTML = "";
	
	$("#paginationBar").hide();
}
