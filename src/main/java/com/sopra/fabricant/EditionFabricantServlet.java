package com.sopra.fabricant;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sopra.Fabricant;
import com.sopra.OutilsGestionMagasin;
import com.sopra.OutilsGestionUtilisateurs;

@WebServlet("/editionFabricant")
public class EditionFabricantServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;

	@EJB
	private OutilsGestionUtilisateurs gestionUtilisateurs;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}
		if(!gestionUtilisateurs.getUtilisateurConnecte(req.getSession()).isAdmin()){gestionUtilisateurs.isNotAllowed(req, resp);return;}
		Fabricant fabricant = new Fabricant();
		if (req.getParameterMap().containsKey("fabricant")) {
			fabricant = gestion.findFabricantById(Integer.parseInt(req.getParameter("fabricant")));
		} else {
			fabricant=gestion.ajouterFabricant();
			fabricant.setNom("Nom nouveau fabricant");
			fabricant.setAdresse("Nouvelle adresse fabricant");
		}
		req.setAttribute("fabricant", fabricant);
		req.getRequestDispatcher("/WEB-INF/editionFabricant.jsp").forward(req, resp);
	}

}
