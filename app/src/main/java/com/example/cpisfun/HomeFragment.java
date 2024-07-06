package com.example.cpisfun;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4,
            linearLayout5, linearLayout6, linearLayout7, linearLayout8, linearLayout9, linearLayout10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        linearLayout1 = view.findViewById(R.id.linearLayout1);
        linearLayout2 = view.findViewById(R.id.linearLayout2);
        linearLayout3 = view.findViewById(R.id.linearLayout3);
        linearLayout4 = view.findViewById(R.id.linearLayout4);
        linearLayout5 = view.findViewById(R.id.linearLayout5);
        linearLayout6 = view.findViewById(R.id.linearLayout6);
        linearLayout7 = view.findViewById(R.id.linearLayout7);
        linearLayout8 = view.findViewById(R.id.linearLayout8);
        linearLayout9 = view.findViewById(R.id.linearLayout9);
        linearLayout10 = view.findViewById(R.id.linearLayout10);

        // Set click listeners for each LinearLayout
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        linearLayout5.setOnClickListener(this);
        linearLayout6.setOnClickListener(this);
        linearLayout7.setOnClickListener(this);
        linearLayout8.setOnClickListener(this);
        linearLayout9.setOnClickListener(this);
        linearLayout10.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        if (v.getId() == R.id.linearLayout1) {
            intent = new Intent(getActivity(), DataStructureActivity.class);
        } else if (v.getId() == R.id.linearLayout2) {
            intent = new Intent(getActivity(), DynamicProgrammingActivity.class);
        } else if (v.getId() == R.id.linearLayout3) {
            intent = new Intent(getActivity(), StringProcessingActivity.class);
        } else if (v.getId() == R.id.linearLayout4) {
            intent = new Intent(getActivity(), LinerAlgebraActivity.class);
        } else if (v.getId() == R.id.linearLayout5) {
            intent = new Intent(getActivity(), CombinatoricsActivity.class);
        } else if (v.getId() == R.id.linearLayout6) {
            intent = new Intent(getActivity(), NumericalMethodsActivity.class);
        } else if (v.getId() == R.id.linearLayout7) {
            intent = new Intent(getActivity(), GeometryActivity.class);
        } else if (v.getId() == R.id.linearLayout8) {
            intent = new Intent(getActivity(), GraphActivity.class);
        } else if (v.getId() == R.id.linearLayout9) {
            intent = new Intent(getActivity(), AlgebraActivity.class);
        } else if (v.getId() == R.id.linearLayout10) {
            intent = new Intent(getActivity(), MiscellaneousActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

}
