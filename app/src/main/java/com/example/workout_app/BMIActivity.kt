package com.example.workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workout_app.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW" //Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" //US_UNIT_VIEW
    }

    private  var currentVisibleView:String = METRIC_UNIT_VIEW // A variable to hold a value to make a selected view visible

    private var binding:ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBMI)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        //set the backButton
        binding?.toolbarBMI?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId:Int ->

            if (checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitView()
            }
            else{
                makeVisibleUsUnitsView()
            }
        }


        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
//            if (validateMetricUnits()){
//                val heightValue:Float = binding?.etMetricUnitHeight?.text.toString().toFloat()/100
//
//                val weightValue:Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
//
//                val bmi = weightValue/(heightValue*heightValue)
//
//                displayBmiResult(bmi)
//
//
//            }
//            else{
//                Toast.makeText(this@BMIActivity,"Please enter valid values",Toast.LENGTH_SHORT).show()
//            }

        }
    }

    private fun makeVisibleMetricUnitView(){
        currentVisibleView = METRIC_UNIT_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE // METRIC  Weight UNITS VIEW is Visible
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE // METRIC  Height UNITS VIEW is Visible
        binding?.tilUsMetricUnitWeight?.visibility = View.GONE // make weightInPounds view Gone.
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.GONE // make heightInFee  view Gone.
        binding?.tilMetricUsUnitHeightInch?.visibility = View.GONE // make heightInInch  view Gone.

        //so as to clear the weight and height metric values
        binding?.etMetricUnitWeight?.text!!.clear()
        binding?.etMetricUnitHeight?.text!!.clear()

        //make the results layout invisible
        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE

    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW // Current View is updated here.
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE // METRIC  Height UNITS VIEW is InVisible
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE // METRIC  Weight UNITS VIEW is InVisible
        binding?.tilUsMetricUnitWeight?.visibility = View.VISIBLE // make weight view visible.
        binding?.tilMetricUsUnitHeightFeet?.visibility = View.VISIBLE // make height feet view visible.
        binding?.tilMetricUsUnitHeightInch?.visibility = View.VISIBLE // make height inch view visible.

        binding?.etUsMetricUnitWeight?.text!!.clear() // weight value is cleared.
        binding?.etUsMetricUnitHeightFeet?.text!!.clear() // height feet value is cleared.
        binding?.etUsMetricUnitHeightInch?.text!!.clear() // height inch is cleared.

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun displayBmiResult(bmi:Float){
        val bmiLabel:String
        val bmiDescription:String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }

    private fun validateMetricUnits():Boolean{
        var isValid = true
        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun calculateUnits(){
        if (currentVisibleView == METRIC_UNIT_VIEW){
            if (validateMetricUnits()){
                val heightValue:Float = binding?.etMetricUnitHeight?.text.toString().toFloat()/100

                val weightValue:Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue/(heightValue*heightValue)

                displayBmiResult(bmi)


            }
            else{
                Toast.makeText(this@BMIActivity,"Please enter valid values",Toast.LENGTH_SHORT).show()
            }

        }
        else{
            if (validateUsUnits()){
                //get the heightValue that is in Feet
                val usUnitHeightValueFeet:String = binding?.etUsMetricUnitHeightFeet?.text.toString()
                //get the heightValue that is in Inch
                val usUnitHeightValueInch:String = binding?.etUsMetricUnitHeightInch?.text.toString()
                //get the weightValue that is in Pounds
                val usUnitWeightValue:Float = binding?.etUsMetricUnitWeight?.text.toString().toFloat()

                val heightValue = usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() *12

                val bmi = 703 * (usUnitWeightValue/ (heightValue*heightValue))

                displayBmiResult(bmi)

            }
            else{
                Toast.makeText(this@BMIActivity,"Please Enter Valid Values",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private  fun validateUsUnits():Boolean{
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid

    }
}