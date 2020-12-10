package com.isetkelibia.customlistviewfragmentnd.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.isetkelibia.customlistviewfragmentnd.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class HomeFragment extends Fragment {

    HomeViewModel homeViewModel;
    ListView myListViewPerso;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        /* ListView */
        // Récupération de la "ListView" créée dans le fichier activity_main.xml
        myListViewPerso = root.findViewById(R.id.list_office);

        // Création de la "ArrayList" qui nous permettra de remplir la "ListView"
        ArrayList<HashMap<String, String>> listItems = new ArrayList<>();

        // On déclare la "HashMap" qui contiendra les informations pour un item
        HashMap<String, String> item;

        // Titre des items
        String[] title = new String[]{
                getResources().getString(R.string.word),
                getResources().getString(R.string.excel),
                getResources().getString(R.string.powerpoint),
                getResources().getString(R.string.outlook)};
        // Description des items
        String[] description = new String[]{
                getResources().getString(R.string.word_description),
                getResources().getString(R.string.excel_description),
                getResources().getString(R.string.powerpoint_description),
                getResources().getString(R.string.outlook_description)};
        // Icones (images) des items
        String[] icon = new String[]{
                String.valueOf(R.mipmap.word),
                String.valueOf(R.mipmap.excel),
                String.valueOf(R.mipmap.powerpoint),
                String.valueOf(R.mipmap.outlook)};
        // Creation des items de la liste
        for (int i = 0; i < 4; i++) {
            item = new HashMap<>();
            // Titre
            item.put("title", title[i]);
            // Description
            item.put("description", description[i]);
            // Icone
            item.put("icon", icon[i]);
            listItems.add(item);
        }

        // Creation d l’Adapter
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                listItems,
                R.layout.activity_list_item,
                new String[]{"title", "description", "icon"},
                new int[]{R.id.title, R.id.description, R.id.icon});
        // Association de l’adapter à la liste
        myListViewPerso.setAdapter(adapter);

        // Interaction avec les items de la liste
        myListViewPerso.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = ((TextView) view.findViewById(R.id.title)).getText().toString();
            Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
        });

        myListViewPerso.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedItem = ((TextView) view.findViewById(R.id.title)).getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setTitle(getString(R.string.adb_title));
            builder.setMessage(getString(R.string.adb_start_message) + " : " + selectedItem);
            builder.setIcon(R.mipmap.office);
            builder.setPositiveButton(getString(R.string.adb_btn_ok), null);
            builder.show();
            return true;
        });
        return root;
    }
}