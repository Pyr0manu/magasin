<%@page import="com.sopra.Produit"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="skeleton.css">
<link rel="stylesheet" type="text/css" href="normalize.css">
<title>Liste des produits</title>
</head>
<body>

<% List<Produit> listeProduits = (List<Produit>) request.getAttribute("listeProduits"); %>

<h1>Liste des produits</h1><br>

<a href="magasin">Retourner au magasin</a>

<table class="u-full-width">
	<tr>
		<th>Id</th>
		<th>Reference</th>
		<th>Nom</th>
		<th>Categorie</th>
		<th>Fabricant</th>
	</tr>
		<%for (int i=0;i<listeProduits.size();i++){%>
	<tr>
		<td><%=listeProduits.get(i).getId() %></td>
		<td><%=listeProduits.get(i).getReference() %></td>		
		<td><%=listeProduits.get(i).getNom() %></td>
		<td><%=listeProduits.get(i).getCategorie().getNom() %></td>
		<td><%=listeProduits.get(i).getFabricant().getNom() %></td>
		
		<td class="edit-button">
			<form method='get' action="editionProduit">
				<input type='hidden' name='produit' value='<%= listeProduits.get(i).getId() %>'/>
				<button>Editer</button>
			</form>
		</td>
		<td class="delete-button">
			<form method='post' action="supprimerProduit">
				<input type='hidden' name='produitASupprimer' value='<%= listeProduits.get(i).getId() %>'/>
				<button>X</button>
			</form>
		</td>
	</tr>
	<% }%>
</table>

<form method='get' action="editionProduit">
	<button>Nouveau produit</button>
</form>

</body>
</html>