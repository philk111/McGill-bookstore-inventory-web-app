package org.mcgill.ccs2_505.assignment02.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mcgill.ccs2_505.assignment02.inventory.Book;
import org.mcgill.ccs2_505.assignment02.inventory.CompactDisc;
import org.mcgill.ccs2_505.assignment02.inventory.Inventory;
import org.mcgill.ccs2_505.assignment02.inventory.InventoryEntry;

/**
 * Servlet implementation class InventoryController
 */

public class UpdateInventory extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Inventory inventory;
	
    public void init() {
		this.inventory = (Inventory) getServletContext().getAttribute("inventory");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Retrieve Parameters */
		String bookUIDP = request.getParameter("BOOK_UID");
		String bookQtyP = request.getParameter("BOOK_QTY");
		String bookPriceP = request.getParameter("BOOK_PRICE");
		String bookAuthorP = request.getParameter("BOOK_AUTHOR");
		
		String cdUIDP = request.getParameter("CD_UID");
		String cdQtyP = request.getParameter("CD_QTY");
		String cdPriceP = request.getParameter("CD_PRICE");
		String cdArtistP = request.getParameter("CD_ARTIST");
		
		/* Create the InventoryEntries. Will be null if there is an issue */
		InventoryEntry bookIE = createBookIE(bookUIDP, bookQtyP, bookPriceP, bookAuthorP);
		InventoryEntry cdIE = createCDIE(cdUIDP, cdQtyP, cdPriceP, cdArtistP);
		
		HttpSession session = request.getSession();
		String name = request.getUserPrincipal().getName();
		
		/* Build the error message if need be */
		String message = "";
		if(bookIE != null && cdIE != null) {
			inventory.add(bookIE);
			inventory.add(cdIE);
			session.setAttribute("log", name + ',' + bookIE.getID() + ',' + bookIE.getPrice() + ',' + bookIE.getQuantity() + ',' + bookIE.getAuthor());
			session.removeAttribute("log");
			session.setAttribute("log", name + ',' + cdIE.getID() + ',' + cdIE.getPrice() + ',' + cdIE.getQuantity() + ',' + cdIE.getArtist());
			session.removeAttribute("log");
		}
		else if(bookIE != null) {
			inventory.add(bookIE);
			session.setAttribute("log", name + ',' + bookIE.getID() + ',' + bookIE.getPrice() + ',' + bookIE.getQuantity() + ',' + bookIE.getAuthor());
			session.removeAttribute("log");
			message = "CD entry is invalid. Ignored.";
		}
		else if(cdIE != null) {
			inventory.add(cdIE);
			session.setAttribute("log", name + ',' + cdIE.getID() + ',' + cdIE.getPrice() + ',' + cdIE.getQuantity() + ',' + cdIE.getArtist());
			session.removeAttribute("log");
			message = "Book entry is invalid. Ignored.";
		}
		else {
			message = "Book and CD entries are invalid. Both ignored.";
		}
		
		request.setAttribute("errorMsg", message);
		
		RequestDispatcher dispatch = request.getRequestDispatcher(response.encodeURL("/displayInventory.do"));
		dispatch.forward(request, response);
	}
	
	/**
	 * 
	 * @param id The ID of the book as a String. Will be checked for validity
	 * @param qty The quantity of the book as a String. Will be checked for validity
	 * @param price The price of the book as a String. Will be checked for validity
	 * @param author The author of the book as a String. Will be checked for validity
	 * @return An InventoryEntry object representing these items
	 */
	private InventoryEntry createBookIE(String id, String qty, String price, String author) {
		if(isValidIE(id, qty, price, author)) {
			return new InventoryEntry(new Book(id, Double.parseDouble(price), author), 
					Integer.parseInt(qty));
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param id The ID of the CD as a String. Will be checked for validity
	 * @param qty The quantity of the CD as a String. Will be checked for validity
	 * @param price The price of the CD as a String. Will be checked for validity
	 * @param artist The Artist of the CD as a String. Will be checked for validity
	 * @return An InventoryEntry object representing these items
	 */
	private InventoryEntry createCDIE(String id, String qty, String price, String artist) {
		if(isValidIE(id, qty, price, artist)) {
			return new InventoryEntry(new CompactDisc(id, Double.parseDouble(price), artist), 
					Integer.parseInt(qty));
		}
		return null;
	}
	
	/**
	 * A helper method to check the validity of any InventoryEntry
	 * @param id The ID of the InventoryEntry to be verified
	 * @param qty The quantity of the InventoryEntry to be verified
	 * @param price The price of the InventoryEntry to be verified
	 * @param other The other attribute of the InventoryEntry to be verified
	 * @return True if the all parameters pass their respective tests
	 */
	private boolean isValidIE(String id, String qty, String price, String other) {
		if(id == null || other == null) {
			return false;
		}
		try {
			if(Integer.parseInt(qty) > 0 && Double.parseDouble(price) > 0.0) {
				return true;
			}
		}
		catch(NumberFormatException e) {
			return false;
		}
		return false;
	}

}
