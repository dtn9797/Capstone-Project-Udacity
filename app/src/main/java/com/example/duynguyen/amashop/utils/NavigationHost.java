package com.example.duynguyen.amashop.utils;

import android.support.v4.app.Fragment;

public interface NavigationHost {
    public void navigateTo(Fragment fragment, boolean addToBackstack);
}
