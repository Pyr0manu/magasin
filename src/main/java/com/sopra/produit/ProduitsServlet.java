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

@WebServlet("/produits")
public class ProduitsServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("listeProduits", gestion.findAllProduits());
		req.getRequestDispatcher("/WEB-INF/produits.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Produit produit = gestion.findById(Integer.parseInt(req.getParameter("idProduit")));
		produit.setNom(req.getParameter("nouveauNomProduit"));
		produit.setReference(req.getParameter("nouvelleReferenceProduit"));
		produit.setFabricant(gestion.findFabricantById(Integer.parseInt(req.getParameter("nouveauFabricantProduit"))));
		produit.setCategorie(gestion.findCategorieById(Integer.parseInt(req.getParameter("nouvelleCategorieProduit"))));
		
		gestion.updateProduit(produit);
		resp.sendRedirect("produits");
	}
}
