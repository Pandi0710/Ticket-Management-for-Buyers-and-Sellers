package com.example.EventTicketingSystemCLI;


public class TicketPool {

    private  int maxCapacity;
    private int ticketsLeft;
    private int currentTicketsInPool;



    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.ticketsLeft = totalTickets;
        this.currentTicketsInPool = 0;
    }

    public synchronized boolean addTickets(int ticketsToAdd, String vendorName) {
        if (ticketsLeft <= 0 || currentTicketsInPool >= maxCapacity) {
            return false;
        }

        int ticketsToRelease = Math.min(ticketsToAdd, maxCapacity - currentTicketsInPool);
        ticketsToRelease = Math.min(ticketsToRelease, ticketsLeft);

        currentTicketsInPool += ticketsToRelease;
        ticketsLeft -= ticketsToRelease;

        System.out.println("Vendor "+vendorName+" added " + ticketsToRelease + " tickets." + currentTicketsInPool+ "Tickets in pool");

        notifyAll();
        return true;
    }

    public synchronized boolean removeTicket(int ticketsToRetrieve, String customerName) {
        while (currentTicketsInPool < ticketsToRetrieve) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        currentTicketsInPool -= ticketsToRetrieve;

        System.out.println("Customer " + customerName +" bought " + ticketsToRetrieve + " ticket(s). Tickets left in pool: " + currentTicketsInPool);

        notifyAll();  // Notify vendors that space is available
        return true;
    }

    public boolean ticketsLeft() {
        return ticketsLeft > 0 || currentTicketsInPool > 0;
    }
}