package com.sopra.utilisateurs;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sopra.OutilsGestionUtilisateurs;
import com.sopra.Utilisateur;

@WebServlet("/users")
public class UtilisateursServlet extends HttpServlet {

	@EJB
	private OutilsGestionUtilisateurs gestionUtilisateurs;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}
		if(!gestionUtilisateurs.getUtilisateurConnecte(req.getSession()).isAdmin()){gestionUtilisateurs.isNotAllowed(req, resp);return;}
		
		List<Utilisateur> listeUtilisateurs = gestionUtilisateurs.findAllUsers();
		req.setAttribute("listeUsers", listeUtilisateurs);
		req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}
		if(!gestionUtilisateurs.getUtilisateurConnecte(req.getSession()).isAdmin()){gestionUtilisateurs.isNotAllowed(req, resp);return;}
		
		Utilisateur utilisateur;
		if(req.getParameterMap().containsKey("setAdminUtilisateur")){
			utilisateur = gestionUtilisateurs.findByLogin(req.getParameter("setAdminUtilisateur"));
			gestionUtilisateurs.setUtilisateurAdmin(utilisateur);
		}
		if(req.getParameterMap().containsKey("supprimerUtilisateur")){
			utilisateur = gestionUtilisateurs.findByLogin(req.getParameter("supprimerUtilisateur"));
			gestionUtilisateurs.supprimerUtilisateur(utilisateur);
		}
		if(req.getParameterMap().containsKey("unsetAdminUtilisateur")){
			utilisateur = gestionUtilisateurs.findByLogin(req.getParameter("unsetAdminUtilisateur"));
			gestionUtilisateurs.unsetUtilisateurAdmin(utilisateur);
		}
		resp.sendRedirect("users");
	}
	
}
