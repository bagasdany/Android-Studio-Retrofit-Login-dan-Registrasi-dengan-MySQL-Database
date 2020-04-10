package com.example.nayatiapp.TrackingPulang;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilterPulang extends Filter{

    AdapterPulang adapterPulang;
    ArrayList<Pulang> filterList;


    public CustomFilterPulang(ArrayList<Pulang> filterList,AdapterPulang adapterPulang)
    {
        this.adapterPulang = adapterPulang;
        this.filterList = filterList;

    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Pulang> filteredPets=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNameP().toUpperCase().contains(constraint))
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

        adapterPulang.pets= (ArrayList<Pulang>) results.values;

        //REFRESH
        adapterPulang.notifyDataSetChanged();

    }

}
