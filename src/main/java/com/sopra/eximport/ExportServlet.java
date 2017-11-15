package com.sopra.eximport;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.sopra.Categorie;
import com.sopra.Fabricant;
import com.sopra.OutilsGestionMagasin;
import com.sopra.Produit;

@WebServlet("/export.xls")
public class ExportServlet extends HttpServlet {

	@EJB
	private OutilsGestionMagasin gestion;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=magasin.xls");
		HSSFWorkbook workbook = new HSSFWorkbook();

		int rownum = 0;
		Sheet sheet = workbook.createSheet();
		// Categories
		List<Categorie> listeCategories = gestion.findAllCategories();
		int nbCategories = listeCategories.size();

		Row categoriesRow = sheet.createRow(rownum);
		categoriesRow.createCell(0).setCellValue("Liste des categories");
		categoriesRow.createCell(1).setCellValue(nbCategories);
		rownum++;

		Row legendeCategoriesRow = sheet.createRow(rownum);
		printCells(legendeCategoriesRow, Arrays.asList("Id categorie","Nom categorie",
				"Reference externe categorie"));
		rownum++;

		for (Categorie current : listeCategories) {
			Row categorieRow = sheet.createRow(rownum);
			categorieRow.createCell(0).setCellValue(current.getId());
			categorieRow.createCell(1).setCellValue(current.getNom());
			categorieRow.createCell(2).setCellValue(current.getExternalReference());
			rownum++;
		}
		// Fabricants
		List<Fabricant> listeFabricants = gestion.findAllFabricants();
		int nbFabricants = listeCategories.size();

		Row fabricantsRow = sheet.createRow(rownum);
		fabricantsRow.createCell(0).setCellValue("Liste des fabricants");
		fabricantsRow.createCell(1).setCellValue(nbFabricants);
		rownum++;

		Row legendeFabricantsRow = sheet.createRow(rownum);
		printCells(legendeFabricantsRow, Arrays.asList("Id fabricant","Nom fabricant",
				"Adresse fabricant","Reference externe fabricant"));
		rownum++;

		for (Fabricant current : listeFabricants) {
			Row fabricantRow = sheet.createRow(rownum);
			fabricantRow.createCell(0).setCellValue(current.getId());
			fabricantRow.createCell(1).setCellValue(current.getNom());
			fabricantRow.createCell(2).setCellValue(current.getAdresse());
			fabricantRow.createCell(3).setCellValue(current.getExternalReference());
			rownum++;
		}
		// Produits
		List<Produit> listeProduits = gestion.findAllProduits();
		int nbProduits = listeProduits.size();

		Row produitsRow = sheet.createRow(rownum);
		produitsRow.createCell(0).setCellValue("Liste des produits");
		produitsRow.createCell(1).setCellValue(nbProduits);
		rownum++;

		Row legendeProduitsRow = sheet.createRow(rownum);
		printCells(legendeProduitsRow, Arrays.asList("Id produit", "Reference produit", "Nom produit",
				"Reference externe fabricant", "Reference externe categorie"));
		rownum++;

		for (Produit current : listeProduits) {
			Row produitRow = sheet.createRow(rownum);
			produitRow.createCell(0).setCellValue(current.getId());
			produitRow.createCell(1).setCellValue(current.getReference());
			produitRow.createCell(2).setCellValue(current.getNom());
			produitRow.createCell(3).setCellValue(current.getFabricant().getExternalReference());
			produitRow.createCell(4).setCellValue(current.getCategorie().getExternalReference());
			rownum++;
		}

		workbook.write(resp.getOutputStream()); // Write workbook to response.
		workbook.close();
	}

	private void printCells(Row row, List<String> list) {
		for (int i = 0; i < list.size(); i++)
			row.createCell(i).setCellValue(list.get(i));
	}
}
