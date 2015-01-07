package com.ctanas.android.sectionedlist.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ctanas.android.sectionedlist.R;
import com.ctanas.android.sectionedlist.model.PublicTransportStationItem;
import com.ctanas.android.sectionedlist.model.SectionedListItem;
import com.ctanas.android.sectionedlist.model.StringHeaderItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SectionedListAdapter extends BaseAdapter implements Filterable {

    private static final int        TYPE_LIST_ITEM = 0;
    private static final int        TYPE_LIST_HEADER = 1;
    private static final int        TYPE_COUNT = TYPE_LIST_HEADER + 1;

    private LayoutInflater layoutInflater;

    private Map<StringHeaderItem, List<PublicTransportStationItem>> sections =
            new LinkedHashMap<StringHeaderItem, List<PublicTransportStationItem>>();

    private Map<StringHeaderItem, List<PublicTransportStationItem>> filteredSections =
            new LinkedHashMap<StringHeaderItem, List<PublicTransportStationItem>>();

    private Filter                  filter;

    public SectionedListAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addSection(StringHeaderItem header, List<PublicTransportStationItem> items) {
        this.sections.put(header, items);
        this.filteredSections.put(header, items);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {

        int viewType = -1;

        for ( StringHeaderItem header : this.filteredSections.keySet() ) {

            List<PublicTransportStationItem> stations = this.filteredSections.get(header);
            int sectionCount = stations.size() + 1; // Number of items + header

            if ( position < sectionCount ) {
                viewType = position == 0 ? TYPE_LIST_HEADER : TYPE_LIST_ITEM;
                return viewType;
            }
            else {
                position -= sectionCount;
            }

        }

        return -1;
    }

    @Override
    public int getCount() {

        int totalCount = 0;

        for ( List<PublicTransportStationItem> stations : this.filteredSections.values() ) {
            totalCount += stations.size() + 1;
        }

        return totalCount;
    }

    @Override
    public Object getItem(int position) {

        Object item = null;

        for ( StringHeaderItem header : this.filteredSections.keySet() ) {

            List<PublicTransportStationItem> stations = this.filteredSections.get(header);
            int sectionCount = stations.size() + 1;

            if ( position < sectionCount ) {
                item = position == 0 ? header : stations.get(position - 1);
                return item;
            }
            else {
                position -= sectionCount;
            }

        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SectionedListItem item = (SectionedListItem) this.getItem(position);

        if ( convertView == null ) {
            int resourceId = item.isHeader() ?
                    R.layout.default_list_header :
                    R.layout.default_list_item;

            convertView = this.layoutInflater.inflate(resourceId, parent, false);
        }

        if ( item.isHeader() ) {
            TextView headerTitle = (TextView) convertView.findViewById(R.id.header);
            headerTitle.setText( ((StringHeaderItem) item).getHeaderTitle() );
        }
        else {
            TextView content = (TextView) convertView.findViewById(R.id.content);
            content.setText( ((PublicTransportStationItem) item).getCommonName() );
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {

        if ( this.filter == null ) {
            this.filter = new FilterByStationName();
        }

        return this.filter;
    }


    private class FilterByStationName extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults = new FilterResults();

            if ( constraint != null && constraint.length() > 0 ) {

                Map<StringHeaderItem, List<PublicTransportStationItem>> newSections =
                        new LinkedHashMap<StringHeaderItem, List<PublicTransportStationItem>>();

                for ( StringHeaderItem header : sections.keySet() ) {

                    List<PublicTransportStationItem> stations = sections.get(header);
                    List<PublicTransportStationItem> filteredStations = new ArrayList<PublicTransportStationItem>();

                    for ( PublicTransportStationItem station : stations ) {

                        if ( station.getCommonName().toLowerCase().contains(
                                constraint.toString().toLowerCase()
                        )
                                ) {
                            filteredStations.add(station);
                        }

                    }

                    if ( filteredStations.size() > 0 ) {
                        newSections.put(header, filteredStations);
                    }
                }

                filterResults.values = newSections;
                filterResults.count = newSections.size();

            }
            else {
                filterResults.values = sections;
                filterResults.count = sections.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {

            Map<StringHeaderItem, List<PublicTransportStationItem>> filteredValues =
                    (Map<StringHeaderItem, List<PublicTransportStationItem>>) filterResults.values;
            filteredSections.clear();
            filteredSections.putAll(filteredValues);
            notifyDataSetChanged();

        }
    }
}
