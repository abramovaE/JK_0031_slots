package com.template

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.template.databinding.ActivitySlotsBinding

import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView


class SlotsActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySlotsBinding

    var coins = 0
    var bet = 0
    val values = arrayOf("\uD83C\uDF53", "\uD83C\uDF4A", "\uD83C\uDF4B", "\uD83C\uDF47", "\uD83E\uDD5D")
    var textViews = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlotsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBetBtnText()

        coins = intent.getIntExtra("coins", 0)

        setCoinsText()

        initTextViewList()

        binding.betBtn.setOnClickListener { v->
            val editText = EditText(this)
            editText.inputType = InputType.TYPE_CLASS_NUMBER
            AlertDialog.Builder(this)
                .setMessage("Input coins count")
                .setView(editText)
                .setPositiveButton( "OK", {
                    dialog, id ->
                    bet = editText.text.toString().toInt()
                    setBetBtnText()
                })
                .show()
        }

        binding.menuBtn.setOnClickListener {
                v -> finish()
        }

        fillTableLayout()

        binding.spinBtn.setOnClickListener { v ->
            textViews.shuffle()
            fillTableLayout()
            coins = coins - bet + (-5 until 5).random()
            setCoinsText()
        }
    }

    private fun setCoinsText(){
        var coinsTvText = "Coins: ${coins}"
        binding.coinsTv.setText(coinsTvText)
    }

    private fun setBetBtnText(){
        binding.betBtn.setText("Bet: ${bet}")
    }

    private fun initTextViewList() {
        for (i in 0 until 9){
            var r = (0 until 5).random()
            textViews.add(values[r])
        }
    }
    
    private fun fillTableLayout(){
        val tableLayout = binding.tableLayout
        tableLayout.removeAllViews()

        var k = 0;
        for (i in 0 until 3) {
            val tableRow = TableRow(this)
            tableRow.setLayoutParams(
                TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT
                )
            )
            for (j in 0 until 3) {
                val textView = TextView(this)
                textView.text = textViews.get(k)
                textView.textSize = 50.0f
                k++
                tableRow.addView(textView, j)
            }
            tableLayout.addView(tableRow, i)
        }
    }
}