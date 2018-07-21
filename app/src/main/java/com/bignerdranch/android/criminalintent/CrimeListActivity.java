package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

//    //TODO: DELETE THIS IF NOT REQUIRED TO FIX ONACTIVITYRESULT() PROBLEM IN FRAGMENT...
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d("CrimeListActivity","Entering the hosting activity's onActivityResult method");
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
