package com.sopra.produit;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sopra.OutilsGestionMagasin;
import com.sopra.OutilsGestionUtilisateurs;
import com.sopra.Produit;

@WebServlet("/supprimerProduit")
public class SupprimerProduitServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;
	
	@EJB
	private OutilsGestionUtilisateurs gestionUtilisateurs;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}
		if(!gestionUtilisateurs.getUtilisateurConnecte(req.getSession()).isAdmin()){gestionUtilisateurs.isNotAllowed(req, resp);return;}
		Produit produit = gestion.findById(Integer.parseInt(req.getParameter("produitASupprimer")));
		gestion.supprimerProduit(produit);
		resp.sendRedirect("produits");
		
	}
	
}
