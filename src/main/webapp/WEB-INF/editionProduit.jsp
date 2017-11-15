<%@page import="com.sopra.Categorie"%>
<%@page import="com.sopra.Fabricant"%>
<%@page import="java.util.List"%>
<%@page import="com.sopra.Produit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="skeleton.css">
<link rel="stylesheet" type="text/css" href="normalize.css">
<title>Editer Produit</title>
</head>
<body>

	<%Produit produit = (Produit) request.getAttribute("produit"); 
	List<Fabricant> listeFabricants = (List<Fabricant>) request.getAttribute("listeFabricants");
	List<Categorie> listeCategories = (List<Categorie>) request.getAttribute("listeCategories");%>
	
	<h1>Editer produit</h1>
	
	<form method="post" action="produits">
		<input type="hidden" name="idProduit" value="<%=produit.getId()%>">
		<label>Reference : <input type="text" placeholder="Ecrivez une reference de produit" name="nouvelleReferenceProduit" value="<%=produit.getReference()%>"></label>
		<label>Nom : <input type="text" placeholder="Ecrivez un nom de produit" name="nouveauNomProduit" value="<%=produit.getNom()%>"></label>
		Fabricant :<select name="nouveauFabricantProduit">
			<%for(int i=0;i<listeFabricants.size();i++){%>
				<option value="<%=listeFabricants.get(i).getId() %>" <%if(listeFabricants.get(i).getId().equals(produit.getFabricant().getId())){%>selected="selected"<%} %>>
					<%=listeFabricants.get(i).getNom() %>
				</option>
			<%} %>
		</select>
		Categorie :<select name="nouvelleCategorieProduit">
			<%for(int i=0;i<listeCategories.size();i++){%>
				<option value="<%=listeCategories.get(i).getId() %>" <%if(listeCategories.get(i).getId().equals(produit.getCategorie().getId())){%>selected="selected"<%} %>>
					<%=listeCategories.get(i).getNom() %>
				</option>
			<%} %>
		</select>
		<button>OK</button>
	</form>

</body>
</html>