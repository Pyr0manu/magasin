package com.sopra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index.html")
public class TestServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestionDesProduits;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Fabricant> listeFabricants = gestionDesProduits.findAllFabricants();
		if (listeFabricants.isEmpty()){
			for (int i = 0; i < 10; i++) {
				listeFabricants.add(gestionDesProduits.ajouterFabricant());
			}			
		}

		List<Categorie> listeCategories = gestionDesProduits.findAllCategories();
		if (listeCategories.isEmpty()){
			listeCategories = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				listeCategories.add(gestionDesProduits.ajouterCategorie());
			}			
		}

		for (int i = 0; i < 100; i++) {
			Produit produit = new Produit();
			Categorie categorie = listeCategories.get(new Random().nextInt(listeCategories.size()));
			produit.setCategorie(categorie);
			Fabricant fabricant = listeFabricants.get(new Random().nextInt(listeFabricants.size()));
			produit.setFabricant(fabricant);
			produit.setNom("Produit" + System.nanoTime());
			produit.setReference(UUID.randomUUID().toString());
			gestionDesProduits.ajouterProduit(produit);
		}

	}
}
