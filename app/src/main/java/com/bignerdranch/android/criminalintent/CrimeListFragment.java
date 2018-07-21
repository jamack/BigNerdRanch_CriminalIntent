package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class CrimeListFragment extends Fragment {

    // For Challenge
    private static final int REQUEST_CODE_EDIT = 0;

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    // For Challenge
    private CrimeLab crimeLab = CrimeLab.get(getActivity());
    private List<Crime> crimes = crimeLab.getCrimes();
    private UUID mSelectedCrime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // For Challenge
        crimeLab = CrimeLab.get(getActivity());
        crimes = crimeLab.getCrimes();

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        // For Challenge, made class fields & moved code to OnCreateView
//        CrimeLab crimeLab = CrimeLab.get(getActivity());
//        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
//        else {
//            mAdapter.notifyDataSetChanged();
//        }
    }

    // For Challenge
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CrimeListFragment","Entering onActivityResult");
        if (resultCode != Activity.RESULT_OK) {
            Log.d("CrimeListFragment", "Activity result is NOT okay");
            return;
        } else if (mAdapter == null) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_EDIT:
                UUID editedCrime = (UUID) data.getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
                for (int position = 0; position < crimes.size(); position++) {
                    if (crimes.get(position).getID() == editedCrime) {
                        Log.d("CrimeListFragment", "In onActivityResult method; about to call mAdapter.notifyItemChanged");
                        mAdapter.notifyItemChanged(position);
                    }
                }
                break;
            default:
                Log.d("CrimeListFragment","In onActivityResult method; request code not handled by switch statement");
        }

//        if(requestCode == REQUEST_CODE_EDIT) {
//            if (resultCode == Activity.RESULT_OK && mSelectedCrime != null) {
//
//                UUID editedCrime = data.getE
//
//                updateItem();
//            }
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder {
        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mSolvedImageView = itemView.findViewById(R.id.crime_solved);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // For Challenge
                    mSelectedCrime = mCrime.getID();
                    Intent intent = CrimeActivity.newIntent(getActivity(), mSelectedCrime);
                    startActivityForResult(intent, REQUEST_CODE_EDIT);

//                    Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getID());
//                    startActivity(intent);
                }
            });
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }
    }

//    private class CrimeHolder extends RecyclerView.ViewHolder {
//        public CrimeHolder(View itemView) {
//            super(itemView);
//        }
//    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

//        public void updatedCrime(UUID crime) {
//            for (Crime crime : mCrimes) {
//                if (crime.getID() == mCr)
//            }
//        }
    }
}
