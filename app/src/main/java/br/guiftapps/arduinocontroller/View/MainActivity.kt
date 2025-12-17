package br.guiftapps.arduinocontroller.View

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.ads.*
import com.guiftapps.arduinocontroller.R

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener,
    SettingsFragment.OnFragmentInteractionListener, SerialFragment.OnFragmentInteractionListener {

    var mainFragment: MainFragment? = null
    var serialFragment: SerialFragment? = null
    var settingsFragment: SettingsFragment? = null

    private lateinit var mInterstitialAd: InterstitialAd

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
                mainFragment = MainFragment()
                setFragment(mainFragment!!)
                return@OnNavigationItemSelectedListener true
            }
            /* R.id.navigation_log -> {
                 setFragment(serialFragment!!)
                 return@OnNavigationItemSelectedListener true
             }*/
            R.id.navigation_settings -> {
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                }
                setFragment(settingsFragment!!)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    private lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainFragment = MainFragment()
        serialFragment = SerialFragment()
        settingsFragment = SettingsFragment()

        setFragment(mainFragment!!)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-2865932856120238/3329475036"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

        MobileAds.initialize(this) {
            mAdView = findViewById(R.id.adView2)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }
    }

    private fun setFragment(fragment: Fragment) {

        if (!fragment.isVisible) {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.commit()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
