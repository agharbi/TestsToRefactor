package bsp4_1.model;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import java.util.List;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

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
		private final double DELTA = 1e-2;
		private int amount;
		
		private Book b1, b2, b3, b4, b5;
		
		List<Item> bookList = new ArrayList<Item>();
		
		@Before
		public void setUp() throws Exception{
			
			amount = 0;
			a = new Address();
			i = new Invoice();
			c = new Customer(a, "franz", "beispiel");
			
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
		
		@After
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
		public void test_addSomeBooks() throws Exception{

			int maxItemOrder = 10;
			//if (amount < 10)
			amount = 9;
			i.setMaxItemOrder(maxItemOrder);
			i.addItems(b1, amount);
			
			assertEquals(i.getItemCount(), amount);
			assertEquals(i.getMaxItemOrder(), maxItemOrder);
			}


		@Test
		public void test_setCustomer(){
			
			i.setCustomer(c);
			assertEquals(i.getCustomer().getFirstName() , "franz");
			assertEquals(i.getCustomer().getLastName() , "beispiel");
			assertEquals(i.getCustomer().getAddress() , a);
		}
		
		@Test
		public void test_addManyItems_getTotalPrice_Delivery_Costs_5() throws Exception{
			int maxItemOrder = 10;
			amount = 4;
			i.setMaxItemOrder(maxItemOrder);
			double totalPrice = 0;

			for (Item item : bookList) {
				i.addItems(item, amount);
				totalPrice += amount * item.getPrice();
			}
			totalPrice += DELIVERY_COSTS_5;
			
			assertEquals(totalPrice , i.getTotalPrice() , DELTA);
		}

		@Test
		public void test_addManyItems_getTotalPrice_Delivery_Costs_10() throws Exception{
			int maxItemOrder = 10;
			i.setMaxItemOrder(maxItemOrder);
			amount = 6;
			double totalPrice = 0;

			for (Item item : bookList) {
				i.addItems(item, amount);
				totalPrice += amount * item.getPrice();
			}
			totalPrice += DELIVERY_COSTS_10;
			
			assertEquals(totalPrice , i.getTotalPrice());
		}
		
		@Test
		public void test_itemCount_changeMaxItemOrder()throws Exception {
			int maxItemOrder = 150;
			amount = 25;
			i.setMaxItemOrder(maxItemOrder);

			int itemCount = 0;
				for (Item item : bookList) {
					i.addItems(item, amount);
					itemCount += amount;
				}

				assertEquals(itemCount , i.getItemCount()); 
			}

		@Test(expected = Exception.class)
		public void test_addToManyItems() throws ToMuchItemsException {
			int maxItemOrder = 0;
			int amount = 1;

			i.setMaxItemOrder(maxItemOrder);
			i.addItems(b1, amount);

		} 
}
