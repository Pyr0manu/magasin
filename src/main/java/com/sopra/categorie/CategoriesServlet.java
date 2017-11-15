package com.sopra.categorie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sopra.Categorie;
import com.sopra.OutilsGestionMagasin;

@WebServlet("/categories")
public class CategoriesServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Categorie> listeCategories =gestion.findAllCategories();
		req.setAttribute("listeCategories", listeCategories);
		List<Long> nbProduitsCategories = new ArrayList<>();
		for (int i=0;i<listeCategories.size();i++){
			nbProduitsCategories.add(gestion.compterNbProduitsCategorie(listeCategories.get(i)));
		}
		req.setAttribute("nbProduitsCategories", nbProduitsCategories);
		req.getRequestDispatcher("/WEB-INF/categories.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Categorie categorie = gestion.findCategorieById(Integer.parseInt(req.getParameter("idCategorie")));
		categorie.setNom(req.getParameter("nouveauNomCategorie"));
		
		gestion.updateCategorie(categorie);
		resp.sendRedirect("categories");
	}
	
}
