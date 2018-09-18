package com.example.duynguyen.amashop.utils;

import android.support.v4.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface NavigationHost {
    public void navigateTo(Fragment fragment, boolean addToBackstack);

    public void navigateBack (Boolean backToTheFirstScreen);
}
