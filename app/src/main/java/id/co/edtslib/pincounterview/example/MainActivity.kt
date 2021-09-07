package id.co.edtslib.pincounterview.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.co.edtslib.pincounterview.PINCounterDelegate
import id.co.edtslib.pincounterview.PINCounterView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val pinCounterView = findViewById<PINCounterView>(R.id.pinCounterView)
        pinCounterView.delegate = object : PINCounterDelegate {
            override fun onExpired() {
                Toast.makeText(this@MainActivity, "Hi, counter is expired", Toast.LENGTH_SHORT).show()

            }
        }
        pinCounterView.start()
    }
}