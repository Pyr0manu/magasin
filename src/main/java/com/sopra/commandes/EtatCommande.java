package com.sopra.commandes;

public enum EtatCommande {
	
	EnPreparation{
		public EtatCommande next(){
			return EtatCommande.EnAttente;
		}
	},
	
	EnAttente{
		public EtatCommande next(){
			return EtatCommande.Expedie;
		}
	},
	
	Expedie{
		public EtatCommande next(){
			return EtatCommande.EnCoursDeLivraison;
		}
	},
	
	EnCoursDeLivraison{
		public EtatCommande next(){
			return EtatCommande.Livre;
		}
	},
	
	Livre{
		public EtatCommande next(){
			return EtatCommande.Livre;
		}
	};
	
	public abstract EtatCommande next();
}
