<%@page import="com.sopra.Utilisateur"%>
<%@page import="com.sopra.OutilsGestionMagasin"%>
<%@page import="com.sopra.Fabricant"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="skeleton.css">
<link rel="stylesheet" type="text/css" href="normalize.css">
<title>Liste des fabricants</title>
</head>
<body>

<% List<Fabricant> listeFabricants = (List<Fabricant>) request.getAttribute("listeFabricants"); 
List<Long> nbProduitsFabricants = (List<Long>) request.getAttribute("nbProduitsFabricants");%>
<%Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte"); %>
Connecte en tant que '<%=utilisateur.getLogin() %>' <a href="login">se deconnecter</a>
<h1>Liste des fabricants</h1><br>

<a href="magasin">Retourner au magasin</a>

<table class="u-full-width">
	<tr>
		<th>Id</th>
		<th>Nom</th>
		<th>Adresse</th>
		<th>Nombre de produits</th>
	</tr>
	<%for (int i=0;i<listeFabricants.size();i++){%>
	<tr>
		<td><%=listeFabricants.get(i).getId() %></td>
		<td><%=listeFabricants.get(i).getNom() %></td>
		<td><%=listeFabricants.get(i).getAdresse() %></td>
		<td><%=nbProduitsFabricants.get(i) %></td>
		<%if(utilisateur.isAdmin()){ %>
			<td class="edit-button">
				<form method='get' action="editionFabricant">
					<input type='hidden' name='fabricant' value='<%= listeFabricants.get(i).getId() %>'/>
					<button>Editer</button>
				</form>
			</td>
			<td class="delete-button">
				<form method='post' action="supprimerFabricant">
					<input type='hidden' name='fabricantASupprimer' value='<%= listeFabricants.get(i).getId() %>'/>
					<button>X</button>
				</form>
			</td>
		<%} %>
	</tr>
	<% }%>
</table>

<%if(utilisateur.isAdmin()){ %>
	<form method='get' action="editionFabricant">
		<button>Nouveau fabricant</button>
	</form>
<%} %>
</body>
</html>