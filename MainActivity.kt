package com.example.weightsmith

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import org.w3c.dom.Text
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    // initialize the variables to call later
    private lateinit var OneRepMaxWtCalcField:EditText
    private lateinit var OneRepMaxRepCalcField:EditText
    private lateinit var OneRepMaxTextDisplay: TextView
    private lateinit var adjustedDisplayField:TextView
    private lateinit var BodyWeight: EditText
    private lateinit var relativeTierSquat: TextView
    private lateinit var relativeTierBench: TextView
    private lateinit var relativeTierDeadlift: TextView

    //initialize variables to one
    private var oneRepMax = 1.0
    private var percentage = 0.75
    private var selectedReps = 1
    private var adjustedWeight = 1
    private var bodyweight = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get reference to switch and buttons
        val calRelStrengthTierButton: Button = findViewById(R.id.main_activity_bt_submit_rl)
        val calcOneRepMax: Button = findViewById(R.id.main_activity_1mx_bt)
        val squat_switch: SwitchCompat = findViewById(R.id.main_activity_sw_squat)
        val bench_switch: SwitchCompat = findViewById(R.id.main_activity_bench_switch)
        val deadLift_switch: SwitchCompat = findViewById(R.id.main_activity_dl_switch)
        val adjuster: SeekBar = findViewById(R.id.main_activty_adjuster_sb)

        //set up the listeners for the widgets
        calRelStrengthTierButton.setOnClickListener { updateRelTier () }
        calcOneRepMax.setOnClickListener { dispMax() }

        // listener for seekbar
        adjuster.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Do something when the progress changes
                calcMax()
                selectedReps = progress
                adjustedWeight = (oneRepMax * (1 - 0.0333 * selectedReps)).roundToInt()

                //update textview
                adjustedDisplayField.text = "Wt: $adjustedWeight Reps: $selectedReps"


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do something when the user starts touching the SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do something when the user stops touching the SeekBar
            }
        })


        //set values for the late init
        OneRepMaxWtCalcField = findViewById(R.id.main_activity_et_max_weight)
        OneRepMaxRepCalcField = findViewById(R.id.main_activity_et_rep_max)
        OneRepMaxTextDisplay = findViewById(R.id.main_activity_tv_1repMax)
        adjustedDisplayField = findViewById(R.id.main_activity_eq_amt)
        BodyWeight = findViewById(R.id.main_activity_et_bodywt)
        relativeTierSquat = findViewById(R.id.main_activity_tv_squat)
        relativeTierBench = findViewById(R.id.main_activity_tv_bench)
        relativeTierDeadlift = findViewById(R.id.main_activity_tv_DL)


    }

    private fun ratio(){


    }

    private fun calcMax(){
        // need to update the value of one rep max
        // take values from the two text edits
        // use the Epley's equation
        val weightInput = OneRepMaxWtCalcField.text.toString()
        val repInput = OneRepMaxRepCalcField.text.toString()

        if (weightInput.isNotEmpty() && repInput.isNotEmpty()){
            val grabbedWeight = OneRepMaxWtCalcField.text.toString().toDouble()
            val grabbedReps = OneRepMaxRepCalcField.text.toString().toInt()

            oneRepMax = (grabbedWeight * (1 + (grabbedReps * 0.0333))).roundToInt().toDouble() //hack for getting rid of all the crazy decimals

        }
        else{
            oneRepMax = 1.0
        }

    }

    private fun calcPercent(){
        calcMax()
        val bwInput = BodyWeight.text.toString()

        if (bwInput.isNotEmpty()){
            val bodyweight = BodyWeight.text.toString().toDouble()
            percentage = (oneRepMax / bodyweight)
        }
        else{
            bodyweight = 1
        }


    }

    private fun dispMax(){
        //calc one rep max
        calcMax()

        //update text
        OneRepMaxTextDisplay.text = " 1 Rep Max: $oneRepMax"

    }

    private fun squatDispTier(){
        calcPercent()

        if (percentage<=0.75){
            relativeTierSquat.text = " Beginner "
        }
        else if ( percentage > 0.75 && percentage < 1.25 ){
            relativeTierSquat.text = " Novice "
        }
        else if ( percentage > 1.25 && percentage < 1.5 ){
            relativeTierSquat.text = " Intermediate "
        }
        else if ( percentage > 1.5 && percentage < 2.25 ){
            relativeTierSquat.text = " Advanced "
        }
        else {
            relativeTierSquat.text = " Elite "
        }

    }

    private fun benchDispTier(){
        calcPercent()

        if (percentage<=0.5){
            relativeTierBench.text = " Beginner "
        }
        else if ( percentage > 0.5 && percentage < 0.75 ){
            relativeTierBench.text = " Novice "
        }
        else if ( percentage > 0.75 && percentage < 1.25 ){
            relativeTierBench.text = " Intermediate "
        }
        else if ( percentage > 1.25 && percentage < 2.0 ){
            relativeTierBench.text = " Advanced "
        }
        else {
            relativeTierBench.text = " Elite "
        }

    }

    private fun deadliftDispTier(){
        calcPercent()

        if (percentage<=1.0){
            relativeTierDeadlift.text = " Beginner "
        }
        else if ( percentage > 1.0 && percentage < 1.5 ){
            relativeTierDeadlift.text = " Novice "
        }
        else if ( percentage > 1.5 && percentage < 2.0 ){
            relativeTierDeadlift.text = " Intermediate "
        }
        else if ( percentage > 2.0 && percentage < 2.5 ){
            relativeTierDeadlift.text = " Advanced "
        }
        else {
            relativeTierDeadlift.text = " Elite "
        }

    }

    private fun updateRelTier (){
        squatDispTier()
        benchDispTier()
        deadliftDispTier()
    }

    private fun findSet(){

    }
}