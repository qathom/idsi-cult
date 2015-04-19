<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		
		<title>CultWeb | ${name}</title>

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
			     <ul id="nav-mobile" class="left hide-on-med-and-down">
			       <li><a href="../../">Retour</a></li>
			     </ul>
			   </div>
			 </nav>
		</div>
	
		<article class="container">
		
			<ul class="collection with-header">
				<li class="collection-header name-header"><h4>${name}</h4><i class="medium toggle-favorite"></i></li>
				<li class="collection-item">${contact}</li>
				<li class="collection-item"><a href="${officialsite}" title="Aller sur ${officialsite}" target="_blank">${officialsite}</a></li>
				<li class="collection-item">${address}</li>
				<li class="collection-item">${town}</li>
			</ul>
			
			<div class="row">
				
				<div class="col s12 m6">
					<div id="map"></div>
				</div>
				<div class="col s12 m6">
					
					<h4>Recommendations</h4>
					
					<c:choose>
						
						<c:when test="${not empty links}">
							<ul>
							<c:forEach var="link" items="${links}" varStatus="i" begin="0">
								
								<li>${link}</li>
							</c:forEach>
							</ul>
						</c:when>
						<c:otherwise>
							<p>Pas de recommendations.</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		
		</article>
		
		<script src="<c:url value="/assets/js/jquery.min.js" />"></script>
		<script src="<c:url value="/assets/js/materialize.min.js" />"></script>
		<script src="<c:url value="/assets/js/googlemaps.js" />"></script>
		<script src="<c:url value="/assets/js/gmaps.min.js" />"></script>
		
		<script src="<c:url value="/assets/js/favorites.js" />"></script>
		
		<script type="text/javascript">
				
			$(document).ready(function() {
			
			    var geocoder = new google.maps.Geocoder();
			    var address = "boulevard Carl-VOGT 65";
			    geocoder.geocode( { 'address': address}, function(results, status) {
					if (status == google.maps.GeocoderStatus.OK) {
			    	
						var latitude = results[0].geometry.location.lat();
						var longitude = results[0].geometry.location.lng();
			    	  
						var map = new GMaps({
						    el: '#map',
						    lat: latitude,
						    lng: longitude
						});
						
						map.addMarker({
							lat: latitude,
							lng: longitude,
							title: "${name}"
						});
			      } 
		
			      else {
			        alert("La map ne peut pas être affichée.");
			      }
			    });
			    
			    /*
			     * Favorites
			    */
			    
			    var id = "${id}";
			    var name = "${name}";
			    
			    var favState = $('.toggle-favorite');
			    
			    var favorites = new Favorites();
			    
			    function toggleFavorite() {
			    	
			    	var isFav = favorites.isFavorite(id);
			    	
			    	if(isFav) {
			    		
			    		favorites.deleteFavorite(id);
			    		
			    		favState.addClass('mdi-toggle-check-box-outline-blank');
			    		favState.removeClass('mdi-toggle-check-box');
			    		
			    	} else {
			    		
			    		favorites.addFavorite(id, name);
						
			    		favState.addClass('mdi-toggle-check-box');
			    		favState.removeClass('mdi-toggle-check-box-outline-blank');
			    		
			    		toast('<a href="../../favorites/" style="color: #fff;">Votre favori a été ajouté</a>', 5500)
			    	}
			    }
			    
			    favState.on('click', toggleFavorite);
			    
			    // Init the state
			    favState.addClass( favorites.isFavorite(id) ? 'mdi-toggle-check-box' : 'mdi-toggle-check-box-outline-blank' );
			});
			
		</script>

	</body>
</html>