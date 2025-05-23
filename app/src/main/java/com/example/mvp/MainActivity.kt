package com.example.mvp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
        Calculate()
    }
    fun Calculate()
    {
        var Reed = findViewById<EditText>(R.id.Reed)
        var Pick = findViewById<EditText>(R.id.Pick)
        var Warp = findViewById<EditText>(R.id.Warp)
        var Weft = findViewById<EditText>(R.id.Weft)
        var WarpRS = findViewById<EditText>(R.id.WarpRS)
        var WeftRS = findViewById<EditText>(R.id.WeftRS)
        var LtoL = findViewById<EditText>(R.id.LtoL)
        var WarpRate = findViewById<EditText>(R.id.WarpRate)
        var WeftRate = findViewById<EditText>(R.id.WeftRate)
        var JobRate = findViewById<EditText>(R.id.JobRate)
        var SizingRate= findViewById<EditText>(R.id.SizingRate)

        var Button = findViewById<Button>(R.id.button)
        var ClearButton = findViewById<Button>(R.id.Clear)

        Button.setOnClickListener{
            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()

            var Reed = Reed.text.toString().toInt()
            var Pick = Pick.text.toString().toInt()
            var Warp = Warp.text.toString().toInt()
            var Weft = Weft.text.toString().toInt()
            var WarpRS = WarpRS.text.toString().toInt()
            var WeftRS = WeftRS.text.toString().toInt()
            var LtoL = LtoL.text.toString().toInt()
            var WarpRate = WarpRate.text.toString().toInt()
            var WeftRate = WeftRate.text.toString().toInt()
            var JobRate = JobRate.text.toString().toInt()
            var SizingRate = SizingRate.text.toString().toInt()

            var Warp_Gram = (((Reed*WarpRS)/1852/(Warp*LtoL))*120)
            var Weft_Gram = (((Pick * WeftRS)/1693.33/Weft)*1.05)
            var Total_Gram = (Warp_Gram + Weft_Gram)+( Warp_Gram*0.008)
            var WarpCost = (Warp_Gram*WarpRate)
            var WeftCost = (Weft_Gram*WeftRate)
            var JobCost = JobRate * Pick
            var SizingCost = SizingRate * Warp_Gram
            var BasicCost = (WarpCost+WeftCost+JobCost+SizingCost)

            var Cost5p = BasicCost/0.95
            var Cost6p = BasicCost/0.94
            var Cost7p = BasicCost/0.93
            var Cost8p = BasicCost/0.92

            val resultSummary = """
                        Warp Gram: $Warp_Gram
                        Weft Gram: $Weft_Gram
                        Total Gram: $Total_Gram
                        Warp Cost: ₹$WarpCost
                        Weft Cost: ₹$WeftCost
                        Job Cost: ₹$JobCost
                        Sizing Cost: ₹$SizingCost
                        Basic Cost: ₹$BasicCost
                        Cost +5% Profit: ₹$Cost5p
                        Cost +6% Profit: ₹$Cost6p
                        Cost +7% Profit: ₹$Cost7p
                        Cost +8% Profit: ₹$Cost8p
                    """.trimIndent()

            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("results", resultSummary)
            startActivity(intent)

        }

        ClearButton.setOnClickListener{

            Reed.setText("")
            Pick.setText("")
            Warp.setText("")
            Weft.setText("")
            WarpRS.setText("")
            WeftRS.setText("")
            LtoL.setText("")
            WarpRate.setText("")
            WeftRate.setText("")
            JobRate.setText("")
            SizingRate.setText("")

            Toast.makeText(this, "Clear Button Clicked", Toast.LENGTH_SHORT).show()
        }

    }
}