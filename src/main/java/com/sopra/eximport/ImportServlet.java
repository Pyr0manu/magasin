package com.sopra.eximport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.sopra.Categorie;
import com.sopra.Fabricant;
import com.sopra.OutilsGestionMagasin;
import com.sopra.Produit;

@WebServlet("/import.xls")
@MultipartConfig
public class ImportServlet extends HttpServlet{

	@EJB
	private OutilsGestionMagasin gestion;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Part filePart = req.getPart("file");
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream fileContent = filePart.getInputStream();
	    
	    HSSFWorkbook workbook = new HSSFWorkbook(fileContent);
	    Sheet sheet = workbook.getSheetAt(0);
	    int rowNum = 0;
	    
	    //Recup categorie
	    Row row = sheet.getRow(rowNum);
	    int nbCategories = (int) row.getCell(1).getNumericCellValue(); //recuperation du nombre de categories
	    rowNum = rowNum + 2; //positionnement du rownum sur la ligne des premieres categories
	    for (int i =0; i<nbCategories;i++){
	    	row = sheet.getRow(rowNum);
	    	int idCategorie = (int) row.getCell(0).getNumericCellValue();
    		String nomCategorie = row.getCell(1).getStringCellValue();
    		String externalReferenceCategorie = row.getCell(2).getStringCellValue();
	    	Categorie categorie;
    		if(gestion.findCategorieById(idCategorie)!=null){
	    		categorie = gestion.findCategorieById(idCategorie);
	    	} else {
	    		categorie = gestion.ajouterCategorie();
	    	}
    		categorie.setNom(nomCategorie);
    		categorie.setExternalReference(externalReferenceCategorie);
    		gestion.updateCategorie(categorie);
    		rowNum++;
	    }
	    //Recup Fabricants
	    //Nous sommes à la ligne des fabricants
	    row = sheet.getRow(rowNum);
	    int nbFabricants = (int) row.getCell(1).getNumericCellValue(); //recuperation du nombre de fabricants
	    rowNum = rowNum + 2;//positionnement du rownum sur la ligne des premiers fabricants
	    for (int i=0; i<nbFabricants; i++){
	    	row = sheet.getRow(rowNum);
	    	int idFabricant = (int) row.getCell(0).getNumericCellValue();
	    	String nomFabricant = row.getCell(1).getStringCellValue();
	    	String adresseFabricant = row.getCell(2).getStringCellValue();
	    	String externalReferenceFabricant = row.getCell(3).getStringCellValue();
	    	Fabricant fabricant;
	    	if(gestion.findFabricantById(idFabricant)!=null){
	    		fabricant = gestion.findFabricantById(idFabricant);
	    	} else {
		    	fabricant = gestion.ajouterFabricant();
	    	}
	    		
	    	fabricant.setNom(nomFabricant);
	    	fabricant.setAdresse(adresseFabricant);
	    	fabricant.setExternalReference(externalReferenceFabricant);
	    	gestion.updateFabricant(fabricant);
	    	rowNum++;
	    }
	    //Recup Produits
	    //Nous sommes à la ligne des produits
	    row = sheet.getRow(rowNum);
	    int nbProduits = (int) row.getCell(1).getNumericCellValue();//recuperation du nombre de produits
	    rowNum = rowNum + 2;//positionnement du rownum sur la ligne des premiers produits
	    for (int i=0; i<nbProduits;i++){
	    	row = sheet.getRow(rowNum);
	    	int idProduit = (int) row.getCell(0).getNumericCellValue();
	    	String referenceProduit = row.getCell(1).getStringCellValue();
	    	String nomProduit = row.getCell(2).getStringCellValue();
	    	Fabricant fabricantProduit = gestion.getFabricantByExternalReference(row.getCell(3).getStringCellValue());
	    	Categorie categorieProduit = gestion.getCategorieByExternalReference(row.getCell(4).getStringCellValue());
	    	Produit produit;
	    	if(gestion.findById(idProduit)!=null){
	    		produit = gestion.findById(idProduit);
	    	} else {
	    		produit = new Produit();
	    	}
	    	produit.setReference(referenceProduit);
	    	produit.setNom(nomProduit);
	    	produit.setFabricant(fabricantProduit);
	    	produit.setCategorie(categorieProduit);
	    	gestion.updateProduit(produit);
	    	rowNum++;
	    }
	    resp.sendRedirect("magasin");
	}
	
}
