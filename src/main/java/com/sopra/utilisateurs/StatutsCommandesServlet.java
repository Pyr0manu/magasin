package com.sopra.utilisateurs;

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
import com.sopra.OutilsGestionUtilisateurs;
import com.sopra.Utilisateur;
import com.sopra.commandes.EtatCommande;

@WebServlet("/gestionCommandes")
public class StatutsCommandesServlet extends HttpServlet{
	
	@EJB
	private OutilsGestionUtilisateurs gestionUtilisateurs;
	
	@EJB
	private OutilsGestionCommandes gestionCommandes;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}
		if(!gestionUtilisateurs.getUtilisateurConnecte(req.getSession()).isAdmin()){gestionUtilisateurs.isNotAllowed(req, resp);return;}
		Utilisateur utilisateur = gestionUtilisateurs.findByLogin(req.getParameter("voirCommandesUtilisateur"));
		req.setAttribute("utilisateurARegarder", utilisateur);
		List<Commande> listeCommandes = gestionCommandes.getAllCommandesFromUtilisateur(utilisateur);
		if (listeCommandes.isEmpty()){
			req.setAttribute("alert", "Cet utilisateur n'a rien commande !");
		}
		req.setAttribute("listeCommandes", listeCommandes);
		req.getRequestDispatcher("/WEB-INF/listeCommandes.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}
		if(!gestionUtilisateurs.getUtilisateurConnecte(req.getSession()).isAdmin()){gestionUtilisateurs.isNotAllowed(req, resp);return;}
		
		Commande commande = gestionCommandes.getCommandeById(Integer.parseInt(req.getParameter("commande")));
		if(req.getParameterMap().containsKey("etatCommande")){
			commande.setEtatCommande(EtatCommande.valueOf(req.getParameter("etatCommande")));
			gestionCommandes.updateCommande(commande);
		} else {
			EtatCommande etatCommande = commande.getEtatCommande().next();
			commande.setEtatCommande(etatCommande);
			gestionCommandes.updateCommande(commande);
		}
		
		Utilisateur utilisateur = gestionUtilisateurs.findByLogin(commande.getUtilisateur().getLogin());
		req.setAttribute("utilisateurARegarder", utilisateur);
		List<Commande> listeCommandes = gestionCommandes.getAllCommandesFromUtilisateur(utilisateur);
		if (listeCommandes.isEmpty()){
			req.setAttribute("alert", "Cet utilisateur n'a rien commande !");
		}
		req.setAttribute("listeCommandes", listeCommandes);
		req.getRequestDispatcher("/WEB-INF/listeCommandes.jsp").forward(req, resp);
		
	}
}
