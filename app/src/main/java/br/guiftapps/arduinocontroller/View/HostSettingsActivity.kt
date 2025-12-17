package br.guiftapps.arduinocontroller.View

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.guiftapps.arduinocontroller.Controller.ConfigsManage
import com.guiftapps.arduinocontroller.Controller.MyInterface
import com.guiftapps.arduinocontroller.Controller.SocketConnect
import com.guiftapps.arduinocontroller.R

class HostSettingsActivity : AppCompatActivity(), MyInterface {

    private var ad = ""
    private var prt = ""
    lateinit var mAdView : AdView
    private lateinit var mInterstitialAd: InterstitialAd

    override fun Verify(r: Boolean) {
        if (r) {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
            ConfigsManage().saveFile("$ad\n$prt", applicationContext)
            finish()
        } else {
            Toast.makeText(applicationContext, getString(R.string.a2), Toast.LENGTH_LONG).show()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_settings)
        var c = findViewById<Button>(R.id.btn_connect)
        var address = findViewById<EditText>(R.id.edt_address)
        var port = findViewById<EditText>(R.id.edt_port)
        var data = ConfigsManage().readFile(applicationContext).split(";")

        if (!data!![0].equals("") and (!data!![0].equals("null"))) {
            address.setText(data[0])
            port.setText(data[1])
        }
        c.setOnClickListener {
            if (address.text.toString().isNotEmpty() and port.text.toString().isNotEmpty()) {

                ad = address.text.toString()
                prt = port.text.toString()
                var socketConnect = SocketConnect()
                socketConnect.Parameters(this)
                socketConnect.execute("TestConnection", address!!.text.toString(), port!!.text.toString())
            }
        }

            mAdView = findViewById(R.id.adView2)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)

    }
}
