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

@WebServlet("/supprimerFabricant")
public class SupprimerFabricantServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;
	
	@EJB
	private OutilsGestionUtilisateurs gestionUtilisateurs;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}
		if(!gestionUtilisateurs.getUtilisateurConnecte(req.getSession()).isAdmin()){gestionUtilisateurs.isNotAllowed(req, resp);return;}
		Fabricant fabricant = gestion.findFabricantById(Integer.parseInt(req.getParameter("fabricantASupprimer")));
		gestion.supprimerFabricant(fabricant);
		resp.sendRedirect("fabricants");
		
	}
}
