package com.guiftapps.arduinocontroller.Controller

import android.content.Context
import android.widget.Toast
import br.guiftapps.arduinocontroller.View.MainActivity
import com.guiftapps.arduinocontroller.R
import java.io.*
import java.lang.StringBuilder

class ConfigsManage {
    fun saveFile(data: String, ma: Context) {
        var f: FileOutputStream? = null
        try {
            f = ma.openFileOutput("app.cfg", Context.MODE_PRIVATE)
            f.write(data.toByteArray())
            Toast.makeText(
                ma.applicationContext,
               R.string.a1,
                Toast.LENGTH_LONG
            ).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            f?.close()
        }
    }

    fun readFile(sl : Context) : String {
        var fis: FileInputStream
        var br: BufferedReader
        var sb: StringBuilder? = null
        try {

            fis = sl.openFileInput("app.cfg")
            var isr = InputStreamReader(fis)
           br = BufferedReader(isr)
            sb = StringBuilder()
            var text = br.readLine()

            while (text != null) {
                sb.append(text).append(";")
                text = br.readLine()
            }
            fis.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }
}