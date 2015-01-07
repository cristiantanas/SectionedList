package com.ctanas.android.sectionedlist;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.ctanas.android.sectionedlist.adapter.SectionedListAdapter;
import com.ctanas.android.sectionedlist.data.JsonMapper;
import com.ctanas.android.sectionedlist.data.PublicTransportDataMapper;
import com.ctanas.android.sectionedlist.data.PublicTransportLine;

import java.util.ArrayList;


public class MainActivity extends Activity {

    ListView sectionedListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sectionedListView = (ListView) findViewById(R.id.listView);

        SectionedListAdapter adapter = new SectionedListAdapter(this);

        JsonMapper jsonMapper = new JsonMapper(this);
        ArrayList<PublicTransportLine> linesInfo =
                jsonMapper.arrayOfPublicTransportLinesFromJson("tp_rodalies_barcelona.json");
        PublicTransportDataMapper dataMapper = new PublicTransportDataMapper();

        for ( PublicTransportLine line : linesInfo ) {

            adapter.addSection(
                    dataMapper.transform( line ),
                    dataMapper.transform( line.getListOfStations() )
            );

        }

        this.sectionedListView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );
        searchView.setIconifiedByDefault( false );
        searchView.setOnQueryTextListener( onQueryTextListener );

        return true;
    }


    SearchView.OnQueryTextListener  onQueryTextListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            SectionedListAdapter adapter = (SectionedListAdapter) sectionedListView.getAdapter();
            adapter.getFilter().filter( newText );
            return true;
        }
    };
}
