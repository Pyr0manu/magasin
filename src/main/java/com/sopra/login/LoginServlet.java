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

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	@EJB
	OutilsGestionUtilisateurs gestion;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("utilisateurConnecte");
		req.setAttribute("alerte", "");
		req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		Utilisateur utilisateur = gestion.findByLogin(login);
		if (utilisateur == null){
			req.setAttribute("alerte", "Erreur: login incorrect");
			req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
		}
		String password = req.getParameter("password");
		if (!utilisateur.getPassword().equals(password)){
			req.setAttribute("alerte", "Erreur: password incorrect");
			req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
		}
		gestion.setUtilisateurConnecte(req.getSession(), utilisateur);
		resp.sendRedirect("magasin");
	}

}
