package com.template

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.template.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var initCoins = 0
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("slots_sp", Context.MODE_PRIVATE)
        initCoins = sharedPreferences.getInt("coins", 50)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCoinsText()

        binding.playBtn.setOnClickListener {
            if(initCoins == 0) {
                val someCoins = (0..100).random()
                AlertDialog.Builder(this)
                    .setMessage("You reward $someCoins coins")
                    .setPositiveButton("Claim") { _, _ ->
                        initCoins += someCoins
                        setCoinsText()
                    }
                    .show()
            } else {
                val intent = Intent(this, SlotsActivity::class.java)
                intent.putExtra("coins", initCoins)
                getCoins.launch(intent)
//                startActivity(intent)
            }
        }
        binding.exitBtn.setOnClickListener {

            Log.d("save coins", "" + initCoins)

            val view = layoutInflater.inflate(R.layout.dialog_exit, null)
            val dialog = AlertDialog.Builder(this)
                .setView(view)
                .show()

            val timer = object: CountDownTimer(3000, 1500) {
                override fun onTick(millisUntilFinished: Long) {
                }
                override fun onFinish() {
                    dialog.dismiss()
                    sharedPreferences.edit().putInt("coins", initCoins).apply()
                    this@MainActivity.finish()
                }
            }
            timer.start()

        }
    }

    private val getCoins =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                initCoins = it.data?.getIntExtra("coins", 0)!!
                setCoinsText()
                Log.d("coins", "coins: $initCoins")
            }
        }

    private fun setCoinsText(){
        val coinsText = "Your coins: $initCoins"
        binding.coinsTv.text = coinsText
    }
}