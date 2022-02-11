package com.example.tippy

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INITIALTIP = 15
class MainActivity : AppCompatActivity() {
    private lateinit var etPriceLabel: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etPriceLabel = findViewById(R.id.etPrice)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipAmount = findViewById(R.id.tvTipResult)
        tvTipPercent = findViewById(R.id.tvRate)
        tvTotalAmount = findViewById(R.id.tvTotalResult)
        tvTipDescription = findViewById(R.id.tvTipDescription)

        seekBarTip.progress = INITIALTIP
        tvTipPercent.text = "$INITIALTIP%"
        updateTipDescription(INITIALTIP)

        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "Progress $p1")
                tvTipPercent.text = "$p1%"
                computeTip()
                updateTipDescription(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        etPriceLabel.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                computeTip()
            }

        })
    }

    private fun updateTipDescription(percent: Int) {
        val tipDesc = when (percent) {
            in 0..9 -> "Ew!"
            in 10..14 -> "Meh!"
            in 15..19 -> "Kinda works."
            in 20..25 -> "Pretty good!"
            else -> "Amazing <3"
        }

        val color = ArgbEvaluator().evaluate(
            percent.toFloat()/seekBarTip.max,
            ContextCompat.getColor(this, R.color.red),
            ContextCompat.getColor(this, R.color.green)
        ) as Int

        tvTipDescription.text = tipDesc
        tvTipDescription.setTextColor(color)
    }

    private fun computeTip() {
        if(etPriceLabel.text.isEmpty()){
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }

        val price = etPriceLabel.text.toString().toDouble()
        val tipPercent = seekBarTip.progress

        val tip = price * tipPercent / 100
        val total = price + tip

        tvTipAmount.text = "%.2f".format(tip)
        tvTotalAmount.text = "%.2f".format(total)
    }
}