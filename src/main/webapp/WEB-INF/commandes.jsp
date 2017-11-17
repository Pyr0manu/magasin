<%@page import="com.sopra.commandes.EtatCommande"%>
<%@page import="com.sopra.Commande"%>
<%@page import="java.util.List"%>
<%@page import="com.sopra.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="skeleton.css">
<link rel="stylesheet" type="text/css" href="normalize.css">
<title>Commandes</title>
</head>
<body>
<%Utilisateur utilisateur=(Utilisateur) request.getSession().getAttribute("utilisateurConnecte");
List<Commande> listeCommandes = (List<Commande>)request.getAttribute("listeCommandes");%>
<h1>Commandes de <%=utilisateur.getNom() %></h1>
<a href="magasin">Retourner au magasin</a><br>
<a href="produits">Continuer mes commandes</a><br>
Connecte en tant que '<%=utilisateur.getLogin() %>' <a href="login">se deconnecter</a>
<%if (request.getAttribute("alert")!=null){
	%><%=request.getAttribute("alert") %>
<%} else {
	%>
	<table class="u-full-width">
		<tr>
			<th>Produit</th>
			<th>Quantité</th>
			<th>Etat commande</th>
		</tr>
		<%for(int i=0;i<listeCommandes.size();i++){
			%>
		<tr>
			<td><%=listeCommandes.get(i).getProduit().getNom() %></td>
			<td><%=listeCommandes.get(i).getQuantity() %></td>
			<td><%=listeCommandes.get(i).getEtatCommande() %></td>
			<%if(listeCommandes.get(i).getEtatCommande().equals(EtatCommande.EnPreparation)){ %>
			<td>
				<form method="post" action="commandes">
					<input type="hidden" name="commandeASupprimer" value="<%=listeCommandes.get(i).getId()%>">
					<button>X</button>
				</form>
			</td>
			<%} %>
		</tr>
		<%} %>
		
		
	</table>
<%}%>

</body>
</html>