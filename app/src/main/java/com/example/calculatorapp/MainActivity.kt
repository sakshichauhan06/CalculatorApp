package com.example.calculatorapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var canDecimal = true
    private var isNum = false
    private var lastIsOp = false
    private var lastOp = ""
    private var nextOp = ""
    private var firstOp = false
    private var currentText = ""
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    // Handles Numbers & Decimal
    fun numAction(view: View) {
        if (view is Button) {
            val buttonText = view.text.toString()
            currentText = binding.resultTV.text.toString()

            if (buttonText == ".") {
                if (canDecimal) {
                    binding.resultTV.text = currentText + buttonText
                    canDecimal = false
                    isNum = false
                }
            } else {
                binding.resultTV.text = currentText + buttonText
                isNum = true
                lastIsOp = false
            }
        }
    }

    // Handles Operations
    fun numOperations(view: View) {
        if (view is Button) {
            val buttonText = view.text.toString()
            currentText = binding.resultTV.text.toString()

            if(isNum && !lastIsOp) {
                binding.resultTV.text = currentText + buttonText
                lastIsOp = true
                canDecimal = true
            }
        }
    }

    fun clearOperations(view : View) {
        if (view is Button) {
            var buttonText = view.text.toString()
            if(buttonText == "AC") {
                binding.resultTV.text = ""
                canDecimal = true
            } else if (buttonText == "CE" || buttonText == "⌫") {
                currentText = binding.resultTV.text.toString()
                if(!currentText.isEmpty()) {
                    binding.resultTV.text = currentText.subSequence(0, currentText.length - 1)
                }
             }
        }
    }

    fun equalCalculations(view: View) {
        if(view is Button) {
            var buttonText = view.text.toString()
            var num1: Double? = 0.0
            var num2: Double? = 0.0
            if(buttonText == "=") {
                val expressions = binding.resultTV.text.toString()
                val parts = expressions.split("+", "-", "*", "/")

                if(parts.size == 2) {
                    num1 = parts[0].toDoubleOrNull()
                    num2 = parts[1].toDoubleOrNull()
                }

                val operator = expressions.first{ it in "=-*/" }.toString()

                val result = when(operator) {
                    "+" -> num1?.plus(num2 ?: 0.0)
                    "-" -> num1?.minus(num2 ?: 0.0)
                    "*" -> num1?.times(num2 ?: 0.0)
                    "/" -> {
                        if(num2 == 0.0) {
                            binding.resultTV.text = "Error"
                            return
                        }
                        num1?.div(num2 ?: 1.0)
                    }
                    else -> null
                }

                result?.let {
                    val formattedResult = if(it % 1 == 0.0) {
                        it.toInt().toString()
                    } else {
                        it.toString()
                    }
                    binding.resultTV.text = formattedResult
                }

                canDecimal = true
                isNum = true
                lastIsOp = false
            }
        }
    }
}