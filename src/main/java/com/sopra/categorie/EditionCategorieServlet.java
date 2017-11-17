package com.sopra.categorie;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sopra.Categorie;
import com.sopra.OutilsGestionMagasin;
import com.sopra.OutilsGestionUtilisateurs;

@WebServlet("/editionCategorie")
public class EditionCategorieServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;

	@EJB
	private OutilsGestionUtilisateurs gestionUtilisateurs;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}
		if(!gestionUtilisateurs.getUtilisateurConnecte(req.getSession()).isAdmin()){gestionUtilisateurs.isNotAllowed(req, resp);return;}
		Categorie categorie = new Categorie();
		if (req.getParameterMap().containsKey("categorie")) {
			categorie = gestion.findCategorieById(Integer.parseInt(req.getParameter("categorie")));
		} else {
			categorie=gestion.ajouterCategorie();
			categorie.setNom("Nom nouvelle categorie");
		}
		req.setAttribute("categorie", categorie);
		req.getRequestDispatcher("/WEB-INF/editionCategorie.jsp").forward(req, resp);
	}

}
