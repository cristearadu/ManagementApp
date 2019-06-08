package com.examplee.radu.managementapp.Ticket;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examplee.radu.managementapp.R;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {

    Context context;
    ArrayList<Ticket> tickets;

    public TicketAdapter(Context c, ArrayList<Ticket> t) {
        context = c;
        tickets = t;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.ticket.setText(tickets.get(position).getTicketName());
        holder.type.setText(tickets.get(position).getType());
        holder.description.setText(tickets.get(position).getTicketDescription());

    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ticket, type, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ticket = (TextView) itemView.findViewById(R.id.ticket);
            type = (TextView) itemView.findViewById(R.id.type);
            description = (TextView) itemView.findViewById(R.id.description);


        }
    }
}
