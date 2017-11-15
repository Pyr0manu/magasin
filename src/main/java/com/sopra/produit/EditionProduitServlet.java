package com.sopra.produit;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sopra.OutilsGestionMagasin;
import com.sopra.Produit;

@WebServlet("/editionProduit")
public class EditionProduitServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Produit produit = new Produit();
		if (req.getParameterMap().containsKey("produit")){
			produit = gestion.findById(Integer.parseInt(req.getParameter("produit")));
		} else {
			produit = gestion.createProduit();
			produit.setNom("Nom nouveau produit");
			produit.setReference("Reference nouveau produit");
		}
		req.setAttribute("produit", produit);
		req.setAttribute("listeCategories", gestion.findAllCategories());
		req.setAttribute("listeFabricants", gestion.findAllFabricants());
		req.getRequestDispatcher("/WEB-INF/editionProduit.jsp").forward(req, resp);
	}

}
