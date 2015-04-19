<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		
		<title>CultWeb</title>

		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
		<meta name="description" content="">
		<meta name="keywords" content="">
		<meta name="author" content="">
		
		<link rel="stylesheet" href="<c:url value="/assets/css/materialize.min.css" />">
		<link rel="stylesheet" href="<c:url value="/assets/css/app.css" />">
	</head>
	<body>
	
		<div class="navbar-fixed">
			<nav>
			   <div class="nav-wrapper">
			     <a href="#" class="brand-logo center">CultWeb</a>
			     <ul id="nav-mobile" class="right hide-on-med-and-down">
			       <li><a href="#museums">Musées</a></li>
			       <li><a href="#cinemas">Cinémas</a></li>
			       <li><a href="favorites">Mes Favoris</a></li>
			     </ul>
			   </div>
			 </nav>
		</div>
	
		<div class="slider">
		  <ul class="slides">
		    <li>
		      <img src="<c:url value="/assets/img/meg.jpg" />"> <!-- random image -->
		      <div class="caption center-align">
		        <h3 class="cultweb-title">CultWeb</h3>
		        <h5 class="light grey-text text-lighten-3 discover-title">Découvrez des musées et des cinémas à Genève !</h5>
		      </div>
		    </li>
		    <li>
		      <img src="<c:url value="/assets/img/musee-ariana.jpg" />"> <!-- random image -->
		      <div class="caption left-align">
		        <h3>Musées</h3>
		        <h5 class="light grey-text text-lighten-3">Plus de 20 musées sont disponibles.</h5>
		      </div>
		    </li>
		    <li>
		      <img src="<c:url value="/assets/img/device.jpg" />"> <!-- random image -->
		      <div class="caption right-align">
		        <h3>CultWeb sur votre mobile</h3>
		        <h5 class="light grey-text text-lighten-3">Téléchargez l'application mobile CultWeb.</h5>
		      </div>
		     </li>
		  </ul>
		</div>
  
		<article class="container">
			
			<c:if test="${not empty museums}">
					<div class="collection with-header">
						
						<div class="collection-header"><h4 id="museums">Musées</h4></div>
					<c:forEach var="museum" items="${museums}" varStatus="i" begin="0">
					
						<a href="info/museum/${museum.id}" title="${museum.name}" class="collection-item">${museum.name}</a>
					</c:forEach>
					</div>
			</c:if>
			
			<c:if test="${not empty cinemas}">
					<div class="collection with-header">
						
						<div class="collection-header"><h4 id="cinemas">Cinémas</h4></div>
					<c:forEach var="cine" items="${cinemas}" varStatus="i" begin="0">
					
						<a href="info/cinema/${cine.id}" title="${cine.name}" class="collection-item">${cine.name}</a>
					</c:forEach>
					</div>
			</c:if>
		
		</article>
		
        <footer class="page-footer">
          <div class="container">
            <div class="row">
              <div class="col l6 s12">
                <h5 class="white-text">CultWeb</h5>
                <p class="grey-text text-lighten-4">CultWeb est un projet en Intégration et Déploiement de Services Informatisés.</p>
              </div>
            </div>
          </div>
          <div class="footer-copyright">
            <div class="container">
            © 2015 CultWeb
            </div>
          </div>
        </footer>
		
		<script src="<c:url value="/assets/js/jquery.min.js" />"></script>
		<script src="<c:url value="/assets/js/materialize.min.js" />"></script>
		
		<script type="text/javascript">
		
			$(document).ready(function() {
				
				$('.slider').slider({full_width: true});
			});
		</script>
	</body>
</html>