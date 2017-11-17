package com.sopra;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sopra.commandes.EtatCommande;

@Stateless
public class OutilsGestionCommandes {
	
	@PersistenceContext(name = "Catalogue")
	EntityManager em;
	
	public List<Commande> getAllCommandesFromUtilisateur(Utilisateur utilisateur){
		Query query = em.createQuery("from Commande c where c.utilisateur.id=:utilisateurId")
				.setParameter("utilisateurId", utilisateur.getId());
		
		List<Commande> listeCommandes = query.getResultList();
		return listeCommandes;
	}
	
	public void addCommande(Utilisateur utilisateur, Produit produit, Integer quantity){
		Commande commande = new Commande();
		commande.setProduit(produit);
		commande.setQuantity(quantity);
		commande.setUtilisateur(utilisateur);
		commande.setEtatCommande(EtatCommande.EnPreparation);
		em.persist(commande);
	}
	
	public Commande getCommandeById(Integer id){
		Query query = em.createQuery("from Commande c where c.id=:commandeId")
				.setParameter("commandeId", id);
		return (Commande) query.getSingleResult();
	}
	
	public void updateCommande(Commande commande){
		em.merge(commande);
	}

	public void supprimerCommande(Commande commande) {
		em.remove(em.find(Commande.class, commande.getId()));
	}
	

}
