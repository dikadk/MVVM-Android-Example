package com.coolcodezone.mvvm.model

import androidx.lifecycle.LiveData
import java.math.RoundingMode

class RestaurantCalculator(val repository: TipCalculationRepository = TipCalculationRepository()) {
    fun calculateTip(checkInput: Double, tipPrc: Int): TipCalculation {

        val tipAmount = (checkInput * (tipPrc.toDouble() / 100.0f))
                .toBigDecimal()
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()

        val grandTotal = checkInput + tipAmount
        return TipCalculation(checkAmount = checkInput, tipPct = tipPrc, tipAmount = tipAmount, grandTotal = grandTotal)
    }

    fun saveTipCalculation(tc: TipCalculation) {
        repository.saveTipCalculation(tc)
    }

    fun loadTipCalculationByLocationName(locationName: String): TipCalculation? {
        return repository.loadTipCalculationByName(locationName)
    }

    fun loadSavedTipCalculations(): LiveData<List<TipCalculation>> {
        return repository.loadSavedTipCalculations()
    }

}
