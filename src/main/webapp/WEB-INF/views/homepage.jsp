<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html >
<html>
<head>
<title>Home</title>

<!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, sh">
<link rel="stylesheet" href="css/styling.css">

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<style type="text/css">

.custom-toggler.navbar-toggler {
    border-color: #0FB9F4;
}
.custom-toggler .navbar-toggler-icon {
  background-image: url("data:image/svg+xml;charset=utf8,%3Csvg viewBox='0 0 32 32' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath stroke='rgba(15,185,244, 0.7)' stroke-width='2' stroke-linecap='round' stroke-miterlimit='10' d='M4 8h24M4 16h24M4 24h24'/%3E%3C/svg%3E");
}
.navbar{
color: rgba(15,185,244,0.7);
}
</style>
</head>
<body>

<nav class="navbar  navbar-default navbar-expand-md sticky-top">
	<div class="container-fluid">
	<h2 class="navbar-text d-flex w-50 mr-auto">Nirvachan</h2>

	<button class="navbar-toggler ml-auto custom-toggler" type="button" data-toggle="collapse" data-target="#collapse_target" >
		<span class="navbar-toggler-icon"></span>
	</button>


	<div class="collapse navbar-collapse w-100" id="collapse_target">
		<ul class="navbar-nav">
			<li class="nav-item">
				<a href="#" class="nav-link navbar-text">India 2019</a>
			</li>&nbsp;&nbsp;
			<li class="nav-item">
				<a href="#" class="nav-link">My State</a>
			</li>&nbsp;&nbsp;
			<li class="nav-item dropdown">
				<a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" data-target="dropdown-target">My Constituencies
				<span class="caret"></span>
				</a>
			<div class="dropdown-menu" aria-labelledby="dropdown-target">
				<a href="#" class="dropdown-item">Loksabha</a>
				<div class="dropdown-divider"></div>
				<a href="#" class="dropdown-item">Assembly</a>
			</div>	
			</li>&nbsp;&nbsp;
			<button class="btn btn-outline-primary my-2 my-sm-0">Sign Out</button>
		</ul>
	</div>
	</div>
</nav>

<div class="container-fluid">
	<div class="row">
			<%-- <c:forEach var="temp" items="${Party }">
					<div class="col-xs-6 col-md-3 col-xl-2">
			
			<div class="w3-card-4 w3-center">
				<header class="w3-container w3-blue">
					<h3>${temp.name }</h3>
				</header>
				<div class="w3-container">
				 <h1><b>${temp.seats }</b></h1>
				</div>
			</div>
			</div>
			</c:forEach> --%>
		   
			<c:forEach var="temp" items="${Party}">
				<div class="col-xs-6 col-md-3 col-xl-2">
			
				<div class="card" style="padding-top: 15px;padding-bottom: 15px;">
				<img class="card-img-top" src="" alt="Image of ${temp.name}">
				<div class="card-body" align="center">
					<h5 class="card-title">${temp.name}</h5>
					<h1 class="card-text">${temp.seats}</h1>
				</div>
				</div>
				</div>
			</c:forEach>
		   
		
	</div>
</div>

<a href="/vote">VOTE AGAIN HERE</a>
</body>
</html>