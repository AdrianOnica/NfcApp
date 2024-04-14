package com.adi.myapplication

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity(){
    private var nfcAdapter: NfcAdapter? = null
    lateinit var button: Button

    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        button.setOnClickListener {
            sendMessage("mesaj")
        }
    }

    private fun sendMessage(data: String) {
        val record: NdefRecord = NdefRecord.createMime("text/plain", data.toByteArray())
        val message: NdefMessage = NdefMessage(record)
        nfcAdapter?.setNdefPushMessage(message,this)
    }


    override fun onResume() {
        super.onResume()
       val intent: Intent = Intent(this, javaClass).addFlags(
           Intent.FLAG_ACTIVITY_SINGLE_TOP
       )
        val pendingIntent = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_MUTABLE)

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // onResume gets called after this to handle the intent
       val action: String? = intent.action
        if(NfcAdapter.ACTION_TAG_DISCOVERED == action) {
            extractMessage(intent)?.let { it
                val message: NdefMessage = it
                textView.text = processMessage(message)
            }
        }
    }

    private fun processMessage(ndefMessage: NdefMessage):String {
       val records:  Array<NdefRecord> = ndefMessage.records
        return records[0].payload.toString()
    }

    private fun extractMessage(intent: Intent): NdefMessage? {
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)

        if(tag == null) {
            textView.text = "Tagul este null"
            return null
        }
        val ndef = Ndef.get(tag)
        if(ndef == null) {
            textView.text = "Telefonul nu suporta Ndef"
            return null
        }

        try{
            ndef.connect()
            return ndef.ndefMessage
        } catch (_: Exception) {

        }
        return null
    }
}