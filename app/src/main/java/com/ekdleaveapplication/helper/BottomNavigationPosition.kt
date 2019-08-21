package com.example.shyari_new.helper



import androidx.fragment.app.Fragment
import com.ekdleaveapplication.R
import com.ekdleaveapplication.fragment.ProfileFragment


enum class BottomNavigationPosition(val position: Int, val id: Int) {
    HOME(0, R.id.home),
    DASHBOARD(2, R.id.notifications),
    NOTIFICATIONS(1, R.id.dashboard),
    PROFILE(3, R.id.profile);
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.HOME.id -> BottomNavigationPosition.HOME
    BottomNavigationPosition.DASHBOARD.id -> BottomNavigationPosition.DASHBOARD
    BottomNavigationPosition.NOTIFICATIONS.id -> BottomNavigationPosition.NOTIFICATIONS

    BottomNavigationPosition.PROFILE.id -> BottomNavigationPosition.PROFILE
    else -> BottomNavigationPosition.HOME
}

fun BottomNavigationPosition.createFragment(): Fragment = when (this) {
    BottomNavigationPosition.HOME -> ProfileFragment.newInstance()
    BottomNavigationPosition.DASHBOARD -> ProfileFragment.newInstance()
    BottomNavigationPosition.NOTIFICATIONS -> ProfileFragment.newInstance()
    BottomNavigationPosition.PROFILE -> ProfileFragment.newInstance()
}

fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.HOME -> ProfileFragment.TAG
    BottomNavigationPosition.DASHBOARD -> ProfileFragment.TAG
    BottomNavigationPosition.NOTIFICATIONS -> ProfileFragment.TAG
    BottomNavigationPosition.PROFILE -> ProfileFragment.TAG
}

