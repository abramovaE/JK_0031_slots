package com.template

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.template.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var initCoins = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCoinsText()

        binding.playBtn.setOnClickListener { v->
            if(initCoins == 0) {
                val someCoins = (0..100).random()
                AlertDialog.Builder(this)
                    .setMessage("You reward ${someCoins} coins")
                    .setPositiveButton("Claim", { dialog, id ->
                        initCoins += someCoins
                        setCoinsText()
                    })
                    .show()
            } else {
                val intent = Intent(this, SlotsActivity::class.java)
                intent.putExtra("coins", initCoins)
                startActivity(intent)
            }
        }
        binding.exitBtn.setOnClickListener { v->
            this.finish()
        }
    }


    private fun setCoinsText(){
        val coinsText = "Your coins: ${initCoins}"
        binding.coinsTv.setText(coinsText)
    }
}