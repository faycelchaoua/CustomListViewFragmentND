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

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.isetkelibia.customlistviewfragmentnd.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ListView maListViewPerso;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        /* ListView */
        // Récupération de la "ListView" créée dans le fichier activity_main.xml
        maListViewPerso = root.findViewById(R.id.list_office);

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
                String.valueOf(R.drawable.word),
                String.valueOf(R.drawable.excel),
                String.valueOf(R.drawable.powerpoint),
                String.valueOf(R.drawable.outlook)};
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
        maListViewPerso.setAdapter(adapter);

        // Interaction avec les items de la liste
        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap item = (HashMap) maListViewPerso.getItemAtPosition(position);
                Toast.makeText(getActivity(), "" + item.get("title"), Toast.LENGTH_SHORT).show();
            }
        });

        maListViewPerso.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap item = (HashMap) maListViewPerso.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                builder.setTitle(getString(R.string.adb_title));
                builder.setMessage(getString(R.string.adb_start_message) + " : " + item.get("title"));
                builder.setIcon(R.drawable.office);
                builder.setPositiveButton(getString(R.string.adb_btn_ok), null);
                builder.show();
                return true;
            }
        });
        return root;
    }
}