package com.example.mvp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Force light theme application-wide
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupCalculation()
    }

    private fun setupCalculation() {
        // Input EditTexts
        val reedEditText = findViewById<EditText>(R.id.Reed)
        val pickEditText = findViewById<EditText>(R.id.Pick)
        val warpEditText = findViewById<EditText>(R.id.Warp)
        val weftEditText = findViewById<EditText>(R.id.Weft)
        val warpRSEditText = findViewById<EditText>(R.id.WarpRS)
        val weftRSEditText = findViewById<EditText>(R.id.WeftRS)
        val ltoLEditText = findViewById<EditText>(R.id.LtoL)
        val warpRateEditText = findViewById<EditText>(R.id.WarpRate)
        val weftRateEditText = findViewById<EditText>(R.id.WeftRate)
        val jobRateEditText = findViewById<EditText>(R.id.JobRate)
        val sizingRateEditText = findViewById<EditText>(R.id.SizingRate)

        // Result TextViews
        val warpGMResult = findViewById<TextView>(R.id.WarpGMResult)
        val weftGMResult = findViewById<TextView>(R.id.WeftGMResult)
        val totalGMResult = findViewById<TextView>(R.id.TotalGMResult)
        val basicCostResult = findViewById<TextView>(R.id.BasicCostResult)
        val cost4Result = findViewById<TextView>(R.id.Cost4Result)
        val cost5Result = findViewById<TextView>(R.id.Cost5Result)
        val cost6Result = findViewById<TextView>(R.id.Cost6Result)
        val cost7Result = findViewById<TextView>(R.id.Cost7Result)

        // Buttons
        val calculateButton = findViewById<Button>(R.id.button)
        val clearButton = findViewById<Button>(R.id.Clear)

        // Decimal formatter for consistent display
        val decimalFormat = DecimalFormat("#.##")

        calculateButton.setOnClickListener {
            try {
                // Validate inputs
                if (!validateInputs(reedEditText, pickEditText, warpEditText, weftEditText,
                        warpRSEditText, weftRSEditText, ltoLEditText, warpRateEditText,
                        weftRateEditText, jobRateEditText, sizingRateEditText)) {
                    Toast.makeText(this, "Please fill all fields with valid numbers", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Get input values
                val reed = reedEditText.text.toString().toFloat()
                val pick = pickEditText.text.toString().toFloat()
                val warp = warpEditText.text.toString().toFloat()
                val weft = weftEditText.text.toString().toFloat()
                val warpRS = warpRSEditText.text.toString().toFloat()
                val weftRS = weftRSEditText.text.toString().toFloat()
                val ltoL = ltoLEditText.text.toString().toFloat()
                val warpRate = warpRateEditText.text.toString().toFloat()
                val weftRate = weftRateEditText.text.toString().toFloat()
                val jobRate = jobRateEditText.text.toString().toFloat()
                val sizingRate = sizingRateEditText.text.toString().toFloat()

                // Perform calculations
                val warpGram = (((reed * warpRS) / 1852 / (warp * ltoL)) * 120)
                val weftGram = (((pick * weftRS) / 1693.33 / weft) * 1.05)
                val totalGram = (warpGram + weftGram) + (warpGram * 0.008)

                val warpCost = (warpGram * warpRate)
                val weftCost = (weftGram * weftRate)
                val jobCost = jobRate * pick
                val sizingCost = sizingRate * warpGram
                val basicCost = (warpCost + weftCost + jobCost + sizingCost)

                // Calculate profit margins (updated to match image percentages)
                val cost4p = basicCost / 0.96  // 4% profit
                val cost5p = basicCost / 0.95  // 5% profit
                val cost6p = basicCost / 0.94  // 6% profit
                val cost7p = basicCost / 0.93  // 7% profit

                // Display results
                warpGMResult.text = "${decimalFormat.format(warpGram)} gm"
                weftGMResult.text = "${decimalFormat.format(weftGram)} gm"
                totalGMResult.text = "${decimalFormat.format(totalGram)} gm"
                basicCostResult.text = "₹${decimalFormat.format(basicCost)}"
                cost4Result.text = "₹${decimalFormat.format(cost4p)}"
                cost5Result.text = "₹${decimalFormat.format(cost5p)}"
                cost6Result.text = "₹${decimalFormat.format(cost6p)}"
                cost7Result.text = "₹${decimalFormat.format(cost7p)}"

                Toast.makeText(this, "Calculation completed successfully!", Toast.LENGTH_SHORT).show()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please enter valid numbers in all fields", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "An error occurred during calculation", Toast.LENGTH_SHORT).show()
            }
        }

        clearButton.setOnClickListener {
            // Clear all input fields
            reedEditText.setText("")
            pickEditText.setText("")
            warpEditText.setText("")
            weftEditText.setText("")
            warpRSEditText.setText("")
            weftRSEditText.setText("")
            ltoLEditText.setText("")
            warpRateEditText.setText("")
            weftRateEditText.setText("")
            jobRateEditText.setText("")
            sizingRateEditText.setText("")

            // Clear all result fields
            warpGMResult.text = ""
            weftGMResult.text = ""
            totalGMResult.text = ""
            basicCostResult.text = ""
            cost4Result.text = ""
            cost5Result.text = ""
            cost6Result.text = ""
            cost7Result.text = ""

            Toast.makeText(this, "All fields cleared", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            val text = editText.text.toString().trim()
            if (text.isEmpty()) {
                return false
            }
            try {
                text.toFloat()
            } catch (e: NumberFormatException) {
                return false
            }
        }
        return true
    }
}