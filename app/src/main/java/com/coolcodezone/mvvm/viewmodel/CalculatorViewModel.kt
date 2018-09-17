package com.coolcodezone.mvvm.viewmodel

import android.app.Application
import androidx.databinding.BaseObservable
import androidx.lifecycle.ViewModel
import com.coolcodezone.mvvm.R
import com.coolcodezone.mvvm.model.RestaurantCalculator
import com.coolcodezone.mvvm.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor(
        app: Application, private val calculator: RestaurantCalculator = RestaurantCalculator()) : ObservableViewModel(app) {

    private var lastTipCalculated = TipCalculation()

    var inputCheckAmount = ""
    var inputTipPercentage = ""

    val outputCheckAmount: String get() = getApplication<Application>().getString(R.string.dollar_amount, lastTipCalculated.checkAmount)
    val outputTipAmount: String get() = getApplication<Application>().getString(R.string.dollar_amount, lastTipCalculated.tipAmount)
    val outputTotalDollarAmount: String get() = getApplication<Application>().getString(R.string.dollar_amount, lastTipCalculated.grandTotal)
    val locationName get() = lastTipCalculated.locationName

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tc: TipCalculation) {
        lastTipCalculated = tc
        notifyChange()
    }

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPct = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPct != null) {
            updateOutputs(calculator.calculateTip(checkAmount, tipPct))
        }
    }

    fun saveCurrentTip(name: String){
        val tipToSave = lastTipCalculated.copy(locationName = name)
        calculator.saveTipCalculation(tipToSave)

        updateOutputs(tipToSave)
    }
}

