package com.example.duynguyen.amashop.utils;

import android.support.v4.app.Fragment;

public interface NavigationHost {
    void navigateTo(Fragment fragment, boolean addToBackstack);

    void navigateBack(Boolean backToTheFirstScreen);
}
