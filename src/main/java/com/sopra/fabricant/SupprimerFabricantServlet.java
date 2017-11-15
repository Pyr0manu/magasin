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

@WebServlet("/supprimerFabricant")
public class SupprimerFabricantServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Fabricant fabricant = gestion.findFabricantById(Integer.parseInt(req.getParameter("fabricantASupprimer")));
		gestion.supprimerFabricant(fabricant);
		resp.sendRedirect("fabricants");
		
	}
}
