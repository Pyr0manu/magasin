<%@page import="com.sopra.Utilisateur"%>
<%@page import="com.sopra.Fabricant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="skeleton.css">
<link rel="stylesheet" type="text/css" href="normalize.css">
<title>Editer Fabricant</title>
</head>
<body>

<%Fabricant fabricant = (Fabricant) request.getAttribute("fabricant"); %>
<%Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte"); %>
Connecte en tant que '<%=utilisateur.getLogin() %>' <a href="login">se deconnecter</a>
<h1>Editer fabricant</h1>

<form method="post" action="fabricants">
	<input type="hidden" name="idFabricant" value="<%=fabricant.getId()%>">
	<label>Nom : <input type="text" placeholder="Ecrivez un nom de fabricant" name="nouveauNomFabricant" value="<%=fabricant.getNom()%>"></label>
	<label>Adresse : <input type="text" placeholder="Ecrivez une adresse de fabricant" name="nouvelleAdresseFabricant" value="<%=fabricant.getAdresse()%>"></label>
	<button>OK</button>
</form>

</body>
</html>