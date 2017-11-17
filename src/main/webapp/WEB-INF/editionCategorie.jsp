<%@page import="com.sopra.Utilisateur"%>
<%@page import="com.sopra.Categorie"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="skeleton.css">
<link rel="stylesheet" type="text/css" href="normalize.css">
<title>Editer Categorie</title>
</head>
<body>

<%Categorie categorie = (Categorie) request.getAttribute("categorie"); %>
<%Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte"); %>
Connecte en tant que '<%=utilisateur.getLogin() %>' <a href="login">se deconnecter</a>
<h1>Editer categorie</h1>

<form method="post" action="categories">
	<input type="hidden" name="idCategorie" value="<%=categorie.getId()%>">
	<label>Nom : <input type="text" placeholder="Ecrivez un nom de categorie" name="nouveauNomCategorie" value="<%=categorie.getNom()%>"></label>
	<button>OK</button>
</form>

</body>
</html>