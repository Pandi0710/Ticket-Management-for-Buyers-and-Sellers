package com.example.EventTicketingSystemCLI;


public class Customer implements Runnable {

    private final com.example.EventTicketingSystemCLI.TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final String customerName;




    public Customer(com.example.EventTicketingSystemCLI.TicketPool ticketPool, int customerRetrievalRate, String customerName) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerName  = customerName;
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (ticketPool.ticketsLeft()) {
                boolean success = ticketPool.removeTicket(customerRetrievalRate, customerName);
                if (!success) {
                    System.out.println("Customer " + customerName + " can't retrieve more tickets.");
                    break;
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupt flag and exit loop
                    System.out.println("Customer " + customerName + " was distrubed while buying tickets.");
                    break;
                }
            } else {
                System.out.println("No tickets  for customer " + customerName);
                break;
            }
        }
        if (!Thread.currentThread().isInterrupted()) {
            System.out.println("Customer " + customerName + " has finished their ticket purchasing.");
        }
    }

}
