package com.signature.moviedb.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.signature.moviedb.R;
import com.signature.moviedb.adapter.PopularAdapter;
import com.signature.moviedb.adapter.UpcomingAdapter;
import com.signature.moviedb.helper.ItemClickSupport;
import com.signature.moviedb.model.Popular;
import com.signature.moviedb.model.Upcoming;
import com.signature.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PopularFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PopularFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopularFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PopularFragment newInstance(String param1, String param2) {
        PopularFragment fragment = new PopularFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView rv_popular;
    private MovieViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        rv_popular = view.findViewById(R.id.rv_popular_fragment);
        viewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        viewModel.getPopular();
        viewModel.getResultPopular().observe(getActivity(), showPopular);

        return view;

    }

    private Observer<Popular> showPopular = new Observer<Popular>() {
        @Override
        public void onChanged(Popular popular) {
            rv_popular.setLayoutManager(new LinearLayoutManager(getActivity()));
            PopularAdapter adapter = new PopularAdapter((getActivity()));
            adapter.setListPopular((ArrayList<Popular.Results>) popular.getResults());
            rv_popular.setAdapter(adapter);

            ItemClickSupport.addTo(rv_popular).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                    return false;
                }
            });

            ItemClickSupport.addTo(rv_popular).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", ""+popular.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_popularFragment_to_MovieDetailsFragment);
                }
            });

        }
    };
}