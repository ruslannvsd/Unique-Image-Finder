package com.example.uniqueimagefinder.frags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.uniqueimagefinder.adapters.ImageAdapter;
import com.example.uniqueimagefinder.databinding.FragmentMainBinding;
import com.example.uniqueimagefinder.network.ApiService;
import com.example.uniqueimagefinder.utils.Cons;


public class MainFragment extends Fragment {
    FragmentMainBinding bnd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bnd = FragmentMainBinding.inflate(inflater, container, false);
        RecyclerView rv = bnd.imageRv;
        ImageAdapter ad = new ImageAdapter();
        bnd.enterSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (bnd.enterSearch.getText().length() != 0) {
                    String text = bnd.enterSearch.getText().toString();
                    if (text.contains(" ")) text = text.replace(" ", "+");
                    if (text.endsWith("+")) {
                        StringBuilder input = new StringBuilder();
                        input.append(text);
                        input.reverse();
                        input.delete(0, 1);
                        input.reverse();
                        text = input.toString();
                    }
                    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show();
                    new ApiService().getImages(
                            Cons.KEY, Cons.TYPE, Cons.SEARCH_TYPE, text, rv, ad, requireContext()
                    );
                }
            }
            return true;
        });
        return bnd.getRoot();
    }
}