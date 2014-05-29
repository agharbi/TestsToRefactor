package bsp4_1.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import bsp4_1.model.Address;
import bsp4_1.model.Book;
import bsp4_1.model.Customer;
import bsp4_1.model.Invoice;
import bsp4_1.model.Item;
import bsp4_1.model.ToMuchItemsException;

public class TestsToRefactor {
	
	private Address a;
	private Customer c;
	private Invoice i;
	
	private final int DELIVERY_COSTS_5 = 7;
	private final int DELIVERY_COSTS_10 = 5;
	private int amount;
	
	private Book b1, b2, b3, b4, b5;
	
	List<Item> bookList = new ArrayList<Item>();
	
	//@Befor
	public void setUp() throws Exception{
		
		amount = 0;
		a = new Address();
		i = new Invoice();
		
		b1 = new Book(12.5, "das leben des max mustermann", "mustermann", 125);
		b2 = new Book(14.99, "sofies welt", "gaarder", 251);
		b3 = new Book(8.69, "the jungle books", "kipling", 215);
		b4 = new Book(15.99, "great expectations", "dickens", 314);
		b5 = new Book(12.99, "küsschen, küsschen", "dahl", 145);
		
		bookList.add(b1);
		bookList.add(b2);
		bookList.add(b3);
		bookList.add(b4);
		bookList.add(b5);
	}
	
	//After
	public void tearDown() throws Exception{
		a = null;
		c = null;
		b1 = null;
		b2 = null;
		b3 = null;
		b4 = null;
		b5 = null;
		i = null;
		amount = 0;		
	}
	

	@Test
	public void test_addSomeBooks() {

		
		int maxItemOrder = 10;

		Invoice i = null;

		try {
			a = new Address();
			c = new Customer(a, "franz", "beispiel");
			i = new Invoice();
			i.setCustomer(c);
			i.setMaxItemOrder(maxItemOrder);

			Random r = new Random();
			amount = r.nextInt(15);

			i.addItems(b1, amount);

			assertEquals(i.getItemCount(), amount);
			assertEquals(i.getCustomer().getFirstName(), "franz");
			assertEquals(i.getCustomer().getLastName(), "beispiel");
			assertEquals(i.getCustomer().getAddress(), a);
			assertEquals(i.getMaxItemOrder(), maxItemOrder);

		} catch (Exception e) {
			if (amount < 10) {
				assertFalse("dieser fehler hätte nicht auftreten sollen", true);
			} else {
				assertTrue("es wurden zuviele Items hinzugefügt", true);
			}
		} 
	}

	@Test
	public void test_addManyItems_getTotalPrice() {

		try {
			a = new Address();
			c = new Customer(a, "franz", "beispiel");

			i = new Invoice();
			i.setCustomer(c);
			i.setMaxItemOrder(maxItemOrder);

			Random r = new Random();
			amount = r.nextInt(5) + 1;
			double totalPrice = 0;

			for (Item item : bookList) {
				i.addItems(item, amount);

				if (amount < 5)
					totalPrice += amount * item.getPrice() + DELIVERY_COSTS_5;
				else
					totalPrice += amount * item.getPrice() + DELIVERY_COSTS_10;
			}

			if (totalPrice == i.getTotalPrice()) {
				assertTrue(true);
			}

		} catch (Exception e) {
			if (amount < 10) {
				assertFalse("dieser fehler hätte nicht auftreten dürfen", true);
			} else {
				assertTrue("es wurden zuviele Items hinzugefügt", true);
			}
		} 
	}

	@Test
	public void test_itemCount_changeMaxItemOrder() {
		int maxItemOrder = 150;

		try {
			a = new Address();
			c = new Customer(a, "franz", "beispiel");

			i = new Invoice();
			i.setCustomer(c);
			i.setMaxItemOrder(maxItemOrder);

			Random r = new Random();
			amount = r.nextInt(30) + 1;
			int itemCount = 0;
			for (Item item : bookList) {
				i.addItems(item, amount);
				itemCount += amount;
			}

			if (itemCount == i.getItemCount()) {
				assertTrue(true);
			} else {
				assertFalse(true);
			}

		} catch (Exception e) {
			assertFalse(true);
		} 
	}

	@Test(expected = Exception.class)
	public void test_addToManyItems() throws ToMuchItemsException {

		int amount = 1;

		try {
			a = new Address();
			c = new Customer(a, "franz", "beispiel");

			i = new Invoice();
			i.setCustomer(c);
			i.setMaxItemOrder(maxItemOrder);

			i.addItems(b1, amount);

		} 
	}
}
