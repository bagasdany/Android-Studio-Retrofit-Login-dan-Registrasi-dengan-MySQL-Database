package com.example.nayatiapp.AddCustomer;

import android.widget.Filter;

import com.example.nayatiapp.TrackingDatang.Adapter;
import com.example.nayatiapp.TrackingDatang.Track;
import com.example.nayatiapp.TrackingPulang.Pulang;

import java.util.ArrayList;

public class CustomFilterCustomer extends Filter {

    AdapterCustomer adapterCustomer;
    ArrayList<Customer> filterList;

    public CustomFilterCustomer(ArrayList<Customer> filterList,AdapterCustomer adapterCustomer)
    {
        this.adapterCustomer=adapterCustomer;
        this.filterList=filterList;

    }


    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Customer> filteredPets=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPets.add(filterList.get(i));
                }
            }

            results.count=filteredPets.size();
            results.values=filteredPets;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapterCustomer.customer= (ArrayList<Customer>) results.values;

        //REFRESH
        adapterCustomer.notifyDataSetChanged();

    }

}
