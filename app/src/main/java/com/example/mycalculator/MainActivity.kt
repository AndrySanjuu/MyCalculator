package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var resultShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        if(resultShown)
            onClear(view)

        tvInput?.append((view as Button).text)
    }

    fun onClear(view: View){
        tvInput?.text = ""
        resultShown = false
    }

    fun onDecimalPoint(view: View){
        tvInput?.text?.let{
            if(it.isNotEmpty() && !it.endsWith("."))
                tvInput?.append(".")
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(!isOperatorAdded(it.toString()) || (it.isEmpty() && (view as Button).text == "-"))
                tvInput?.append((view as Button).text)
        }
    }

    fun onEqual(view: View){
        tvInput?.text?.let{
            if(!it.substring(it.length-1).contains(Regex("[.+\\-*/]")))
            {
                var prefix = ""
                try {
                    var value = it.toString()
                    if(it.startsWith("-")){
                        prefix = "-"
                        value = it.substring(1)
                    }

                    val splitValue = value.split(Regex("[+\\-*/]"))

                    var one = prefix + splitValue[0]
                    var two = splitValue[1]


                    val operator = value.substring(one.length, one.length+1)

                    tvInput?.text =  when (operator) {
                        "+" -> (one.toDouble() + two.toDouble()).toString()
                        "-" -> (one.toDouble() - two.toDouble()).toString()
                        "*" -> (one.toDouble() * two.toDouble()).toString()
                        "/" -> (one.toDouble() / two.toDouble()).toString()
                        else -> "GneGne"
                    }

                    resultShown = true

                } catch (e: ArithmeticException){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean{
        if (value.startsWith("-") && value.length > 1) {
            return value.substring(1).contains(Regex("[+\\-*/]"))
        }
        return value.contains(Regex("[+\\-*/]")) || value.isEmpty()
    }
}