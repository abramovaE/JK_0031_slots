package com.template

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivitySlotsBinding

class SlotsActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySlotsBinding

    var coins = 0
    var bet = 0
    private val values = arrayOf("\uD83C\uDF53", "\uD83C\uDF4A", "\uD83C\uDF4B", "\uD83C\uDF47", "\uD83E\uDD5D")
    var textViews = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlotsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBetBtnText()

        coins = intent.getIntExtra("coins", 0)

        setCoinsText()
        initTextViewList()


        binding.betBtn.setOnClickListener {
            val editText = EditText(this)
            editText.inputType = InputType.TYPE_CLASS_NUMBER
            AlertDialog.Builder(this)
                .setMessage("Input coins count")
                .setView(editText)
                .setPositiveButton( "OK") { _, _ ->
                    bet = editText.text.toString().toInt()
                    setBetBtnText()
                }
                .show()
        }

        binding.menuBtn.setOnClickListener {
            intent.putExtra("coins", coins)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }



        val adapter = EmojiAdapter(textViews, this)
        binding.rv.adapter = adapter


        binding.spinBtn.setOnClickListener {
            binding.spinBtn.isClickable = false

            val timer = object: CountDownTimer(5000, 1500) {
                override fun onTick(millisUntilFinished: Long) {
                    textViews.shuffle()
                    adapter.notifyDataSetChanged()
                }

                override fun onFinish() {
                    coins = coins - bet + (-5 until 5).random()
                    setCoinsText()
                    binding.spinBtn.isClickable = true

                }
            }
            timer.start()
        }
    }

    private fun setCoinsText(){
        val coinsTvText = "Coins: $coins"
        binding.coinsTv.text = coinsTvText
    }

    private fun setBetBtnText(){
        val text = "Bet: $bet"
        binding.betBtn.text = text
    }

    private fun initTextViewList() {
        for (i in 0 until 9){
            val r = (0 until 5).random()
            textViews.add(values[r])
        }
    }
}