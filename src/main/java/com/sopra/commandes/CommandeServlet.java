package com.sopra.commandes;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sopra.Commande;
import com.sopra.OutilsGestionCommandes;
import com.sopra.OutilsGestionMagasin;
import com.sopra.OutilsGestionUtilisateurs;
import com.sopra.Produit;
import com.sopra.Utilisateur;

@WebServlet("/commandes")
public class CommandeServlet extends HttpServlet {
	
	@EJB
	OutilsGestionMagasin gestionMagasin;
	
	@EJB
	private OutilsGestionUtilisateurs gestionUtilisateurs;
	
	@EJB
	private OutilsGestionCommandes gestionCommandes;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}
		
		if(req.getParameterMap().containsKey("commandeASupprimer")){
			Commande commande = gestionCommandes.getCommandeById(Integer.parseInt(req.getParameter("commandeASupprimer")));
			gestionCommandes.supprimerCommande(commande);
			resp.sendRedirect("commandes");
			return;
		}
		
		Produit produit = gestionMagasin.findById(Integer.parseInt(req.getParameter("produit")));
		Integer quantity = Integer.parseInt(req.getParameter("quantity"));
		Utilisateur utilisateur = gestionUtilisateurs.getUtilisateurConnecte(req.getSession());
		gestionCommandes.addCommande(utilisateur, produit, quantity);
		resp.sendRedirect("produits");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}
		
		List<Commande> listeCommandes = gestionCommandes.getAllCommandesFromUtilisateur(gestionUtilisateurs.getUtilisateurConnecte(req.getSession()));
		if (listeCommandes == null || listeCommandes.isEmpty()){
			req.setAttribute("alert", "Vous n'avez rien commande !");
		}
		req.setAttribute("listeCommandes", listeCommandes);
		req.getRequestDispatcher("/WEB-INF/commandes.jsp").forward(req, resp);
		
	}
}