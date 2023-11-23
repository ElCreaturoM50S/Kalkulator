package com.example.kalkulator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var displayTextView: TextView
    private var currentInput: String = ""
    private var currentOperator: String? = null
    private var operand1: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tutaj mamy displaytext takie elegancki
        displayTextView = findViewById(R.id.textView)

        //wszystkie lizmy od 0 do 9
        val numberButtons = arrayOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        )

        //łączymy eventy liczb z funckjami
        for (buttonId in numberButtons) {
            findViewById<Button>(buttonId).setOnClickListener { onNumberButtonClick(it) }
        }

        //lista math operators
        val operatorButtons = arrayOf(
            R.id.buttonplus,
            R.id.buttonminus,

            R.id.buttonmultiply,
            R.id.buttondivide,

            R.id.buttonequal,

            R.id.buttonpower
        )

        //łączenie eventow operatorow z funckjami
        for (buttonId in operatorButtons) {
            findViewById<Button>(buttonId).setOnClickListener { onOperatorButtonClick(it) }
        }

        //czyszczenie
        findViewById<Button>(R.id.buttonC).setOnClickListener { clearDisplay() }

        //zmiany liczby dodatniej na liczbę ujemną i na odwrót :)
        findViewById<Button>(R.id.buttonplusminus).setOnClickListener { togglePlusMinus() }
    }

    private fun onNumberButtonClick(view: View) {
        val button = view as Button
        currentInput += button.text
        updateDisplay()
        Log.d("Jarvis", "Current Input: $currentInput")
    }

    private fun onOperatorButtonClick(view: View) {
        val operatorButton = view as Button

        when (val operator = operatorButton.text.toString()) {
            "=" -> {
                Log.d("Jarvis", "Equals button pressed")
                calculateResult()
            }
            "^" -> {
                Log.d("Jarvis", "Power button pressed")
                if (currentInput.isNotEmpty()) {
                    operand1 = currentInput.toDouble()
                    Log.d("Jarvis", operand1.toString())
                    currentInput = (operand1.pow(2)).toString()
                    updateDisplay()
                }
            }
            else -> {
                Log.d("Jarvis", "Operator button pressed: $operator")
                setOperator(operator)
                updateDisplay()
            }
        }
    }

    private fun setOperator(operator: String) {
        if (currentInput.isNotEmpty()) {
            operand1 = currentInput.toDouble()
            currentOperator = operator
            currentInput = ""
            Log.d("Jarvis", "Operand1: $operand1, Operator: $currentOperator")
        }
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty() && currentOperator != null) {

            Log.d("Jarvis", currentInput)
            val operand2 = currentInput.toDouble()

            Log.d("Jarvis","$currentOperator")
            //when to takie switch ale kotlinowy
            val result = when (currentOperator) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "X" -> operand1 * operand2
                "/" -> operand1 / operand2
                else -> Log.d("Jarvis","mamy problem")
            }
            currentInput = result.toString()
            updateDisplay()
            currentOperator = null
            Log.d("Jarvis", "Result: $result")
        }
    }

    //C
    private fun clearDisplay() {
        currentInput = ""
        operand1 = 0.0
        currentOperator = null
        updateDisplay()
        Log.d("Jarvis", "Cleared display")
    }

    //zmiana plus na minusa i na odwrót
    private fun togglePlusMinus() {
        if (currentInput.isNotEmpty()) {
            currentInput = (currentInput.toDouble() * -1).toString()
            updateDisplay()
            Log.d("Jarvis", "Toggled Plus/Minus. Current Input: $currentInput")
        }
    }

    //zwykły update
    private fun updateDisplay() {
        Log.d("Jarvis","Display updated")
        displayTextView.text = currentInput
    }
}
