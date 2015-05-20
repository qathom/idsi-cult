<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="fr">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		
		<title>CultWeb | Mes Favoris</title>

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
			     <a href="#" class="brand-logo center">Mes Favoris</a>
			     <ul id="nav-mobile" class="left">
			       <li><a href="<c:url value="/" />">Retour</a></li>
			     </ul>
			   </div>
			 </nav>
		</div>
  
		<article class="container">
			
			<ul class="collection with-header favorites"></ul>
		
		</article>
		
		<script src="<c:url value="/assets/js/jquery.min.js" />"></script>
		<script src="<c:url value="/assets/js/materialize.min.js" />"></script>
		<script src="<c:url value="/assets/js/favorites.js" />"></script>
		
		<script type="text/javascript">
			
			$(document).ready(function() {
				
				var favorites = new Favorites();
				
				var userFavorites = favorites.getFavorites();
				var i = 0;
				var length = userFavorites.length;
				
				var listFavorites = $('.favorites');
				
				for(i;i<length;i++) {
					
					var fav = userFavorites[i];
					
					var li = $('<li class="collection-item"><div><a href="./info/'+fav.type.toLowerCase()+'/'+fav.id+'">'+fav.name+'</a><a href="../" class="secondary-content" data-id="'+fav.id+'"><i class="mdi-action-delete small"></i></a></div></li>');
					listFavorites.append(li);
				}
				
				
				$('.mdi-action-delete').on('click', function(evt) {
					evt.preventDefault();
					
					var li = $(evt.target.parentNode.parentNode.parentNode);
					var id = $(evt.target.parentNode).attr('data-id');
					
					li.remove();
					
					console.log(li);
					console.log(id);
					
					
					favorites.deleteFavorite(id);
				});
			});
			
		
		</script>
	</body>
</html>