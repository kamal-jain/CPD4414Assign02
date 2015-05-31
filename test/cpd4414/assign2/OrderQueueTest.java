/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 * Updated 2015 Mark Russell <mark.russell@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpd4414.assign2;

import cpd4414.assign2.OrderQueue;
import cpd4414.assign2.Purchase;
import cpd4414.assign2.Order;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueueTest {

    public OrderQueueTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Cafeteria");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);

        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }

    @Test
    public void testWhenNoCustomerExistsThenThrownAnException() {
        boolean didThrow = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("", "");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        try {

            orderQueue.add(order);
        } catch (Exception ex) {
            didThrow = true;
        }
        assertTrue(didThrow);
    }

    @Test
    public void testWhenNoPurchasesThenThrownAnException() {
        boolean didThrow = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("First", "Order");
        order.addPurchase(new Purchase("AB0001", 150));
        order.addPurchase(new Purchase("AB0002", 250));
        try {
            orderQueue.add(order);
        } catch (Exception ex) {
            didThrow = true;
        }
        assertTrue(didThrow);
    }

    /**
     * Test of add method, of class OrderQueue.
     */
    @Test
    public void testAdd() throws Exception {
        System.out.println("add");
        Order order = null;
        OrderQueue instance = new OrderQueue();
        instance.add(order);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetNextWhenOrdersInSystemThenGetNextAvailable() throws OrderQueue.NoCustomerException, OrderQueue.NoPurchasesException, Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("Order1", "NewOrder");
        order.addPurchase(new Purchase("Entry", 8));
        orderQueue.add(order);
        Order order1 = new Order("Order2", "NewOrder1");
        order1.addPurchase(new Purchase("Entry", 4));
        orderQueue.add(order1);

        Order result = orderQueue.next();
        assertEquals(result, order);
        assertNull(result.getTimeProcessed());
    }

    @Test
    public void testGetNextWhenNoOrdersInSystemThenReturnNull() throws OrderQueue.NoCustomerException, OrderQueue.NoPurchasesException {
        OrderQueue orderQueue = new OrderQueue();
        Order result = orderQueue.next();
        assertNull(result);
    }
    @Test
    public void testProcessWhenTimeReceivedIsSetThenSetTimeProcessedToNow() throws OrderQueue.NoCustomerException, OrderQueue.NoPurchasesException, OrderQueue.NoTimeReceivedException, Exception{
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("Order", "NewOrder");
        order.addPurchase(new Purchase("", 15));
        orderQueue.add(order);
        Order order1 = new Order("Order1", "NewOrder1");
        order1.addPurchase(new Purchase("", 25));
        orderQueue.add(order1);

        Order next = orderQueue.next();
        orderQueue.process(next);

        long expResult = new Date().getTime();
        long result = next.getTimeProcessed().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
      @Test
    public void testProcessWhenTimeReceivedNotSetThenThrowException() {
        boolean didThrow = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("Orde", "NewOrder");
        order.addPurchase(new Purchase("", 15));

        orderQueue.process(order);

        assertTrue(didThrow);
    }
}
