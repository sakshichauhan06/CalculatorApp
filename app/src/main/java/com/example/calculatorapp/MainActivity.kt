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
            } else if (buttonText == "CE") {
                currentText = binding.resultTV.text.toString()
                if(!currentText.isEmpty()) {
                    binding.resultTV.text = currentText.subSequence(0, currentText.length - 1)
                }
             }
        }
    }
}