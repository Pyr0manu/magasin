<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="skeleton.css">
<link rel="stylesheet" type="text/css" href="normalize.css">
<title>Login</title>
</head>
<body>

	<h1>Connexion</h1>
	
	<%=request.getAttribute("alerte") %>
	
	<form method="post">
		<label>login <input type="text" name="login" placeholder="Veuillez entrer votre login"></label>
		<label>password <input type="text" name="password" placeholder="Veuillez entrer votre mdp"></label>
		<button>Se connecter</button>	
	</form>
	<a href="inscription">S'inscrire</a>
	

</body>
</html>