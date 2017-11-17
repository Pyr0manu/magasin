package com.sopra.login;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sopra.OutilsGestionUtilisateurs;
import com.sopra.Utilisateur;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet{
	
	@EJB
	OutilsGestionUtilisateurs gestion;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		req.setAttribute("alerte", "");
		req.getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		Utilisateur testLogin = gestion.findByLogin(login);
		if(testLogin != null){
			req.setAttribute("alerte", "Le login '"+login+"' est deja utilise");
			req.getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
		} else{			
			String password = req.getParameter("password");
			String nom = req.getParameter("nom");
			String prenom = req.getParameter("prenom");
			gestion.ajouterUtilisateur(login, password, nom, prenom);
			Utilisateur utilisateur = gestion.findByLogin(login);
			gestion.setUtilisateurConnecte(req.getSession(), utilisateur);
			resp.sendRedirect("magasin");
		}
		
	}

}
