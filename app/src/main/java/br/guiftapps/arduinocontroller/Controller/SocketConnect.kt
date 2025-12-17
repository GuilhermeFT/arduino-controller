package com.guiftapps.arduinocontroller.Controller

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.widget.TextView
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress


class SocketConnect : AsyncTask<String, Void, Boolean>() {
    var listener: MyInterface? = null
    @SuppressLint("StaticFieldLeak")
    var textViewLog: TextView? = null
    private var m: String? = null

    fun Parameters(mListener: MyInterface) {
        listener = mListener
    }

    override fun doInBackground(vararg p0: String?): Boolean {
        if (p0[0].equals("TestConnection")) {
            var address: SocketAddress = InetSocketAddress(p0[1].toString(), p0[2].toString().toInt())
            var socket = Socket()
            try {
                socket.connect(address, 2000)
            } catch (e: Exception) {

            }

            if (socket.isConnected) {
                socket.close()
                return true
            }
        }

        if (p0[0].equals("SendMessage")) {
            val message = p0[3]

            var address: SocketAddress = InetSocketAddress(p0[1].toString(), p0[2].toString().toInt())
            var socket = Socket()
            try {
                socket.connect(address, 2000)
                val pw = PrintWriter(socket.getOutputStream())
                if (message != null) {
                    pw.write(message)
                }
                pw.flush()
                pw.close()
                socket.close()
                return true
            } catch (e: Exception) {
                return false
            }
        }
        return false
    }

    override fun onPostExecute(result: Boolean?) {
        if (result != null) {
            listener?.Verify(result)
        }
    }

}

interface MyInterface {
    fun Verify(r: Boolean)
}

