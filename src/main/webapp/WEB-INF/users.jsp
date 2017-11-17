<%@page import="com.sopra.Utilisateur"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="skeleton.css">
<link rel="stylesheet" type="text/css" href="normalize.css">
<title>Utilisateurs</title>
</head>
<body>
	<%List<Utilisateur> listeUtilisateurs = (List<Utilisateur>) request.getAttribute("listeUsers"); 
	Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateurConnecte");%>
	Connecte en tant que '<%=utilisateur.getLogin() %>' <a href="login">se deconnecter</a>
	<h1>Liste des utilisateurs</h1><br>
	<a href="magasin">Retourner au magasin</a>
	<table class="u-full-width">
	<tr>
		<th>Id</th>
		<th>Login</th>
		<th>Nom</th>
		<th>Prenom</th>
		<th>Admin</th>
	</tr>
		<%for (int i=0;i<listeUtilisateurs.size();i++){%>
	<tr>
		<td><%=listeUtilisateurs.get(i).getId() %></td>
		<td>
			<form method="get" action="gestionCommandes">
				<input type="hidden" name="voirCommandesUtilisateur" value="<%= listeUtilisateurs.get(i).getLogin() %>">
				<button><%=listeUtilisateurs.get(i).getLogin() %></button>
			</form>
			
		</td>		
		<td><%=listeUtilisateurs.get(i).getNom() %></td>
		<td><%=listeUtilisateurs.get(i).getPrenom() %></td>
		<td><%=listeUtilisateurs.get(i).isAdmin() %></td>
		<%if(!listeUtilisateurs.get(i).isAdmin() || utilisateur.isSuperAdmin() && !listeUtilisateurs.get(i).isSuperAdmin()){ %>
		<td>
			<form method='post'>
				<input type='hidden' name='supprimerUtilisateur' value='<%= listeUtilisateurs.get(i).getLogin() %>'/>
				<button>X</button>
			</form>
		</td>
		<td>
			<form method='post'>
				<input type='hidden' name='setAdminUtilisateur' value='<%= listeUtilisateurs.get(i).getLogin() %>'/>
				<button>SetAdmin</button>
			</form>
		</td>
			<%if(utilisateur.isSuperAdmin()){ %>
				<td>
					<form method='post'>
						<input type='hidden' name='unsetAdminUtilisateur' value='<%= listeUtilisateurs.get(i).getLogin() %>'/>
						<button>UnsetAdmin</button>
					</form>
				</td>
			<%} %>
		<%} %>
	</tr>
	<% }%>
</table>
</body>
</html>