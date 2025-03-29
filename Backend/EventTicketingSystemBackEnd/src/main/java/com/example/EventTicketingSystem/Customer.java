package com.example.EventTicketingSystem;


public class Customer implements Runnable {

    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final String customerName;




    public Customer(TicketPool ticketPool, int customerRetrievalRate,  String customerName) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerName = customerName;
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (ticketPool.ticketsLeft()) {
                boolean success = ticketPool.removeTicket(customerRetrievalRate, customerName);
                if (!success) {
                    System.out.println("Customer " + customerName + " could not retrieve more tickets.");
                    break;
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupt flag and exit loop
                    System.out.println("Customer " + customerName + " was interrupted while buying tickets.");
                    break;
                }
            } else {
                System.out.println("No tickets left for customer " + customerName);
                break;
            }
        }
        if (!Thread.currentThread().isInterrupted()) {
            System.out.println("Customer " + customerName + " has finished their ticket purchasing.");
        }
    }

}
