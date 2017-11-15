package com.sopra;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;


@RunWith( Arquillian.class )
public class OutilsGestionMagasinTest {

	@Deployment
	 	public static Archive<?> createDeployment()
	 	{
	 		return ShrinkWrap.create( WebArchive.class, "test.war" )
	 				.addPackage( OutilsGestionMagasin.class.getPackage() )
	 				.addAsResource( "test-persistence.xml", "META-INF/persistence.xml" )
	 				.addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml" );
	 	}
	
	@EJB
	private OutilsGestionMagasin gestion;
	
	/*@BeforeClass
	public void aExecuterEnPremier(){
		Categorie categorieTest1 = gestion.ajouterCategorie();
		Categorie categorieTest2 = gestion.ajouterCategorie();
		Categorie categorieTest3 = gestion.ajouterCategorie();
		Fabricant fabricantTest1 = gestion.ajouterFabricant();
		Fabricant fabricantTest2 = gestion.ajouterFabricant();
		Fabricant fabricantTest3 = gestion.ajouterFabricant();
		Produit produitTest11 = gestion.createProduit();
		Produit produitTest12 = gestion.createProduit();
		Produit produitTest13 = gestion.createProduit();
		Produit produitTest21 = gestion.createProduit();
		Produit produitTest22 = gestion.createProduit();
		Produit produitTest23 = gestion.createProduit();
		Produit produitTest31 = gestion.createProduit();
		Produit produitTest32 = gestion.createProduit();
		Produit produitTest33 = gestion.createProduit();
	}*/
	
	@Test
	public void testAjouterCategorie(){
		Categorie categorieTest = gestion.ajouterCategorie();
		Assert.assertNotNull("id de categorieTest1 est different de null : ", categorieTest.getId());
	}
	
	@Test
	public void testAjouterFabricant(){
		Fabricant fabricantTest = gestion.ajouterFabricant();
		Assert.assertNotNull("id de fabricantTest est different de null : ", fabricantTest.getId());
	}
	
	@Test
	public void testCreateProduit(){
		Fabricant fabricantTest = gestion.ajouterFabricant();
		Categorie categorieTest = gestion.ajouterCategorie();
		Produit produitTest = gestion.createProduit();
		Assert.assertNotNull("id du produitTest est different de null : ", produitTest.getId());
	}
	
	@Test
	public void testFindCategorieById(){
		Categorie categorieTest1 = gestion.ajouterCategorie();
		Categorie categorieTest2 = gestion.ajouterCategorie();
		Categorie categorieTest3 = gestion.ajouterCategorie();
		Assert.assertEquals("bonne egalite des ids des categories : ", categorieTest2.getId(), gestion.findCategorieById(categorieTest2.getId()).getId());
		Assert.assertEquals("bonne egalite des noms des categories : ", categorieTest2.getNom(), gestion.findCategorieById(categorieTest2.getId()).getNom());
		Assert.assertEquals("bonne egalite des ref externes des categories : ", categorieTest2.getExternalReference(), gestion.findCategorieById(categorieTest2.getId()).getExternalReference());
		
	}
	
	@Test
	public void testFindFabricantById(){
		Fabricant fabricantTest1 = gestion.ajouterFabricant();
		Fabricant fabricantTest2 = gestion.ajouterFabricant();
		Fabricant fabricantTest3 = gestion.ajouterFabricant();
		Assert.assertEquals("bonne egalite des ids des fabricants : ", fabricantTest2.getId(), gestion.findFabricantById(fabricantTest2.getId()).getId());
		Assert.assertEquals("bonne egalite des noms des fabricants : ", fabricantTest2.getNom(), gestion.findFabricantById(fabricantTest2.getId()).getNom());
		Assert.assertEquals("bonne egalite des ref externes des fabricants : ", fabricantTest2.getExternalReference(), gestion.findFabricantById(fabricantTest2.getId()).getExternalReference());
		
	}
	
	@Test
	public void testFindById(){
		Fabricant fabricantTest = gestion.ajouterFabricant();
		Categorie categorieTest = gestion.ajouterCategorie();
		Produit produitTest1 = gestion.createProduit();
		Produit produitTest2 = gestion.createProduit();
		Produit produitTest3 = gestion.createProduit();
		
		Assert.assertEquals("bonne egalite des ids des produits : ", produitTest2.getId(), gestion.findById(produitTest2.getId()).getId());
		Assert.assertEquals("bonne egalite des refs des produits : ", produitTest2.getReference(), gestion.findById(produitTest2.getId()).getReference());
		Assert.assertEquals("bonne egalite des noms des produits : ", produitTest2.getNom(), gestion.findById(produitTest2.getId()).getNom());
		Assert.assertEquals("bonne egalite des fabricants des produits : ", produitTest2.getFabricant().getId(), gestion.findById(produitTest2.getId()).getFabricant().getId());
		Assert.assertEquals("bonne egalite des categories des produits : ", produitTest2.getCategorie().getId(), gestion.findById(produitTest2.getId()).getCategorie().getId());
		
	}
	
}
