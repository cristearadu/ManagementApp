package com.examplee.radu.managementapp.Ticket;

public class Ticket {

    String ticketName;
    String ticketDescription;
    String type;

    public Ticket(String ticketName, String type,String ticketDescription) {
        this.ticketName = ticketName;
        this.type = type;
        this.ticketDescription = ticketDescription;

    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
