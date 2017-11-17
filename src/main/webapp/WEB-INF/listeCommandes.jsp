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
<title>Liste des commandes</title>
</head>
<body>
<%Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte");
Utilisateur utilisateurARegarder = (Utilisateur) request.getAttribute("utilisateurARegarder");
List<Commande> listeCommandes = (List<Commande>) request.getAttribute("listeCommandes");%>
Connecte en tant que '<%=utilisateur.getLogin() %>' <a href="login">se deconnecter</a>
<h1>Liste des commandes de <%=utilisateurARegarder.getLogin() %></h1>
<a href="users">Retourner a la liste des utilisateurs</a><br>
<a href="magasin">Retourner au magasin</a><br>
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
			<td>
				<form method="post" action="gestionCommandes">
					<input type="hidden" name="commande" value="<%=listeCommandes.get(i).getId() %>">
					<select name="etatCommande" onchange="this.form.submit()">
						<%for(EtatCommande etatCommande:EtatCommande.values()){%>
							<option <%if(etatCommande.equals(listeCommandes.get(i).getEtatCommande())){ %>
								selected="selected"
							<%} %>>
								<%=etatCommande.name() %>
							</option>
						<%} %>
					</select>
				</form>
			</td>
			<td>
				<form method="post" action="gestionCommandes">
					<input type="hidden" name="commande" value="<%=listeCommandes.get(i).getId() %>">
					<button>Next</button>
				</form>
			</td>
		</tr>
		<%} %>
		
		
	</table>
<%}%>
</body>
</html>