<%@page import="com.sopra.Utilisateur"%>
<%@page import="com.sopra.Categorie"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="skeleton.css">
<link rel="stylesheet" type="text/css" href="normalize.css">
<title>Liste des categories</title>
</head>
<body>

<% List<Categorie> listeCategories = (List<Categorie>) request.getAttribute("listeCategories");
List<Long> nbProduitsCategories = (List<Long>) request.getAttribute("nbProduitsCategories");%>
<%Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte"); %>
Connecte en tant que '<%=utilisateur.getLogin() %>' <a href="login">se deconnecter</a>
<h1>Liste des categories</h1><br>

<a href="magasin">Retourner au magasin</a>

<table class="u-full-width">
	<tr>
		<th>Id</th>
		<th>Nom</th>
		<th>Nombre de produits</th>
	</tr>
	<%for (int i=0;i<listeCategories.size();i++){%>
	<tr>
		<td><%=listeCategories.get(i).getId() %></td>
		<td><%=listeCategories.get(i).getNom() %></td>
		<td><%=nbProduitsCategories.get(i) %></td>
		<%if(utilisateur.isAdmin()){%>
			<td class="edit-button">
				<form method='get' action="editionCategorie">
					<input type='hidden' name='categorie' value='<%= listeCategories.get(i).getId() %>'/>
					<button>Editer</button>
				</form>
			</td>
			<td class="delete-button">
				<form method='post' action="supprimerCategorie">
					<input type='hidden' name='categorieASupprimer' value='<%= listeCategories.get(i).getId() %>'/>
					<button>X</button>
				</form>
			</td>
		<%} %>
	</tr>
	<% }%>
</table>

<%if(utilisateur.isAdmin()){ %>
	<form method='get' action="editionCategorie">
		<button>Nouvelle categorie</button>
	</form>
<%} %>
</body>
</html>