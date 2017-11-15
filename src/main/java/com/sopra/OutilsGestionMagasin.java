package com.sopra;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class OutilsGestionMagasin {

	@PersistenceContext(name = "Catalogue")
	EntityManager em;

	public Categorie ajouterCategorie() {//OK
		Categorie categorie = new Categorie();
		categorie.setNom("Categorie" + System.nanoTime());
		categorie.setExternalReference(UUID.randomUUID().toString());
		em.persist(categorie);
		return categorie;
	}
	
	public Fabricant ajouterFabricant() {//OK
		Fabricant fabricant = new Fabricant();
		fabricant.setNom("Fabricant" + System.nanoTime());
		fabricant.setAdresse("AdresseFabricant" + System.nanoTime());
		fabricant.setExternalReference(UUID.randomUUID().toString());
		em.persist(fabricant);
		return fabricant;
	}
	
	public Produit createProduit() {//OK
		Produit produit = new Produit();
		produit.setNom("Produit" + System.nanoTime());
		produit.setReference("Reference" + System.nanoTime());
		produit.setCategorie(findAllCategories().get(0));
		produit.setFabricant(findAllFabricants().get(0));
		em.persist(produit);
		return produit;
	}
	
	public List<Categorie> findAllCategories(){
		TypedQuery<Categorie> query = em.createQuery("from Categorie", Categorie.class);
		return query.getResultList();
	}
	
	public List<Fabricant> findAllFabricants(){
		TypedQuery<Fabricant> query = em.createQuery("from " + Fabricant.class.getSimpleName(), Fabricant.class);
		return query.getResultList();
	}
	
	public List<Produit> findAllProduits(){
		TypedQuery<Produit> query = em.createQuery("from Produit", Produit.class);
		return query.getResultList();
	}
	
	public Categorie findCategorieById(int id) {//OK
		Categorie c = em.find(Categorie.class, id);
		return c;
	}
	
	public Fabricant findFabricantById(int id) {//OK
		Fabricant f = em.find(Fabricant.class, id);
		return f;
	}
	
	public Produit findById(int id) {//OK
		Produit p = em.find(Produit.class, id);
		return p;
	}



	public Produit ajouterProduit(Produit produit) {
		return em.merge(produit);
	}
	
	
	

	public void afficherCategorie(int id) {
		Categorie categorie = em.find(Categorie.class, id);
		System.out.println(categorie.getNom());
		
		for(Produit current : categorie.getProduits()){
			System.out.println("Nom produit : "+current.getNom());
		}
	}

	

	public void updateCategorie(Categorie categorie) {
		em.merge(categorie);
	}
	
	public void updateProduit(Produit produit) {
		em.merge(produit);
	}
	
	public void updateFabricant(Fabricant fabricant) {
		em.merge(fabricant);
	}

	public void supprimerCategorie(Categorie categorie) {
		List<Produit> listeProduits = findAllProduitsFromCategorie(categorie);
		if (!listeProduits.isEmpty()){
			for (Produit produit:listeProduits){
				supprimerProduit(produit);
			}
		}
		em.remove(em.find(Categorie.class, categorie.getId()));
	}

	public void supprimerFabricant(Fabricant fabricant) {
		List<Produit> listeProduits = findAllProduitsFromFabricant(fabricant);
		if (!listeProduits.isEmpty()){
			for (Produit produit:listeProduits){
				supprimerProduit(produit);
			}
		}
		em.remove(em.find(Fabricant.class, fabricant.getId()));		
	}
	
	public void supprimerProduit(Produit produit) {
		em.remove(em.find(Produit.class, produit.getId()));		
	}

	public List<Produit> findAllProduitsFromFabricant (Fabricant fabricant){
		Query query = em.createQuery("from Produit p where p.fabricant=:fabricant").setParameter("fabricant", fabricant);
		List<Produit> listeProduitsFabricant = query.getResultList();
		
		return listeProduitsFabricant;
	}
	
	public List<Produit> findAllProduitsFromCategorie (Categorie categorie){
		Query query = em.createQuery("from Produit p where p.categorie=:categorie").setParameter("categorie", categorie);
		List<Produit> listeProduitsCategorie = query.getResultList();
		
		return listeProduitsCategorie;
	}

	public long compterNbProduitsCategorie(Categorie categorie) {
		Query query = em.createQuery("select count(p) from Produit p where p.categorie.id= :id")
				.setParameter("id", categorie.getId());
		long nbResultats = (long) query.getSingleResult();
		return nbResultats;
	}
	
	public long compterNbProduitsFabricant(Fabricant fabricant) {
		Query query = em.createQuery("select count(p) from Produit p where p.fabricant.id= :id")
				.setParameter("id", fabricant.getId());
		long nbResultats = (long) query.getSingleResult();
		return nbResultats;
	}
	
	public Fabricant getFabricantByExternalReference(String externalReference){
		for(Fabricant current:findAllFabricants()){
			if (current.getExternalReference().equals(externalReference)){
				return current;
			}
		}
		return null;
	}
	
	public Categorie getCategorieByExternalReference(String externalReference){
		for(Categorie current:findAllCategories()){
			if (current.getExternalReference().equals(externalReference)){
				return current;
			}
		}
		return null;
	}

	public List<Produit> rechercherProduitParNom(String rechercheProduit) {
		Query query = em.createQuery("from Produit p where UPPER(p.nom) like :rechercheProduit")
				.setParameter("rechercheProduit", "%" + rechercheProduit.toUpperCase() +"%");
		List<Produit> listeProduitsRecherchess = query.getResultList();
		return listeProduitsRecherchess;
	}

}
