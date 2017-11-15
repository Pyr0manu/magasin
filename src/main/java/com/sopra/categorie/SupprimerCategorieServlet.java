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

@WebServlet("/supprimerCategorie")
public class SupprimerCategorieServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Categorie categorie = gestion.findCategorieById(Integer.parseInt(req.getParameter("categorieASupprimer")));
		gestion.supprimerCategorie(categorie);
		resp.sendRedirect("categories");
		
	}
}
