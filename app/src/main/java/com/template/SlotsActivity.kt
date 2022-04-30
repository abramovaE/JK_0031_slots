package com.template

import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.util.Log
import android.view.animation.AnimationUtils
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



        var adapter = EmojiAdapter(textViews, this)
        binding.rv.adapter = adapter


        binding.spinBtn.setOnClickListener { v ->

            val timer = object: CountDownTimer(5000, 1500) {
                override fun onTick(millisUntilFinished: Long) {
                    textViews.shuffle()
                    adapter.notifyDataSetChanged()
                }

                override fun onFinish() {
                    coins = coins - bet + (-5 until 5).random()
                    setCoinsText()
                }
            }
            timer.start()







        }



    }

//    private fun startAnim(){
//        var tableLayout = binding.tableLayout
//        for (i in 0 until tableLayout.childCount) {
//            val row = tableLayout.getChildAt(i) as TableRow
//            for (j in 0 until row.childCount) {
//                val tv = row.getChildAt(j) as TextView
//                var anim = AnimationUtils.loadAnimation(this, R.anim.incr);
//                tv.startAnimation(anim)
//            }
//        }
//    }

    private fun stopAnim(){}

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

    private fun fillRv(){

    }
    
    private fun fillTableLayout(){
//        val tableLayout = binding.tableLayout
//        tableLayout.removeAllViews()

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
//            tableLayout.addView(tableRow, i)
        }
    }
}