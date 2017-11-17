package com.sopra.fabricant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sopra.Fabricant;
import com.sopra.OutilsGestionMagasin;
import com.sopra.OutilsGestionUtilisateurs;

@WebServlet("/fabricants")
public class FabricantsServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;
	
	@EJB
	private OutilsGestionUtilisateurs gestionUtilisateurs;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}

		List<Fabricant> listeFabricants =gestion.findAllFabricants();
		req.setAttribute("listeFabricants", listeFabricants);
		List<Long> nbProduitsFabricants = new ArrayList<>();
		for (int i=0;i<listeFabricants.size();i++){
			nbProduitsFabricants.add(gestion.compterNbProduitsFabricant(listeFabricants.get(i)));
		}
		req.setAttribute("nbProduitsFabricants", nbProduitsFabricants);
		req.getRequestDispatcher("/WEB-INF/fabricants.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(!gestionUtilisateurs.correctUserConnected(req.getSession(), resp)){return;}

		Fabricant fabricant = gestion.findFabricantById(Integer.parseInt(req.getParameter("idFabricant")));
		fabricant.setNom(req.getParameter("nouveauNomFabricant"));
		fabricant.setAdresse(req.getParameter("nouvelleAdresseFabricant"));
		
		gestion.updateFabricant(fabricant);
		resp.sendRedirect("fabricants");
	}
}
