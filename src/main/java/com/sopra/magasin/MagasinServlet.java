package com.sopra.magasin;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sopra.OutilsGestionMagasin;
import com.sopra.Produit;

@WebServlet("/magasin")
public class MagasinServlet extends HttpServlet {
	
	@EJB
	private OutilsGestionMagasin gestion;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/magasin.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String rechercheProduit = req.getParameter("rechercheProduitNom");
		List<Produit> listeProduitsRecherches = gestion.rechercherProduitParNom(rechercheProduit);
		req.setAttribute("listeProduitsTrouves", listeProduitsRecherches);
		req.setAttribute("recherche", rechercheProduit);
		req.getRequestDispatcher("/WEB-INF/magasin.jsp").forward(req, resp);
	}

}
