package com.sopra;

import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Stateless
public class OutilsGestionUtilisateurs {
	
	@PersistenceContext(name = "Catalogue")
	EntityManager em;
	
	public Utilisateur ajouterUtilisateur(String login, String password, String nom, String prenom){
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setLogin(login);
		utilisateur.setPassword(password);
		utilisateur.setNom(nom);
		utilisateur.setPrenom(prenom);
		utilisateur.setAdmin(false);
		utilisateur.setSuperAdmin(false);
		em.persist(utilisateur);
		return utilisateur;
	}
	
	public Utilisateur findByLogin(String login){
		Query query = em.createQuery("from Utilisateur u where u.login=:login")
				.setParameter("login", login);
		
		try {
			Utilisateur utilisateur = (Utilisateur) query.getSingleResult();
			return utilisateur;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public Utilisateur getUtilisateurConnecte(HttpSession httpSession){
		Utilisateur utilisateur = (Utilisateur) httpSession.getAttribute("utilisateurConnecte");
		return utilisateur;
	}
	
	public void setUtilisateurConnecte(HttpSession httpSession, Utilisateur utilisateur) {
		httpSession.setAttribute("utilisateurConnecte", utilisateur);
	}
	
	public boolean correctUserConnected(HttpSession httpSession, HttpServletResponse resp) throws IOException{
		if (getUtilisateurConnecte(httpSession) == null || findByLogin(getUtilisateurConnecte(httpSession).getLogin())==null){
			resp.sendRedirect("login");
			return false;
		}
		return true;
	}
	
	public void isNotAllowed(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
			req.setAttribute("isNotAllowed", "Vous n'etes pas autorise a acceder a cette page !");
			req.getRequestDispatcher("/WEB-INF/magasin.jsp").forward(req, resp);
	}
	
	public List<Utilisateur> findAllUsers(){
		TypedQuery<Utilisateur> query = em.createQuery("from Utilisateur", Utilisateur.class);
		return query.getResultList();
	}

	public void supprimerUtilisateur(Utilisateur utilisateur) {
		em.remove(em.find(Utilisateur.class, utilisateur.getId()));
	}

	public void setUtilisateurAdmin(Utilisateur utilisateur) {
		utilisateur.setAdmin(true);
		em.merge(utilisateur);
	}

	public void unsetUtilisateurAdmin(Utilisateur utilisateur) {
		utilisateur.setAdmin(false);
		em.merge(utilisateur);		
	}
	
}
