package com.coolcodezone.mvvm.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TipCalculationRepositoryTest {
    lateinit var repository: TipCalculationRepository

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = TipCalculationRepository()
    }

    @Test
    fun testSaveTip() {
        val tip = TipCalculation(locationName = "Pancake Paradise",
                checkAmount = 100.0, tipPct = 25,
                tipAmount = 25.0, grandTotal = 125.0)

        repository.saveTipCalculation(tip)

        assertEquals(tip, repository.loadTipCalculationByName(tip.locationName))
    }


    @Test
    fun testLoadSavedTipCalculation() {
        val tip1 = TipCalculation(
                locationName = "Pancake Paradise",
                checkAmount = 100.0, tipPct = 25,
                tipAmount = 25.5, grandTotal = 125.0
        )
        val tip2 = TipCalculation(
                locationName = "Veggie Paradise",
                checkAmount = 100.0, tipPct = 25,
                tipAmount = 25.5, grandTotal = 125.0
        )

        repository.saveTipCalculation(tip1)
        repository.saveTipCalculation(tip2)

        repository.loadSavedTipCalculations().observeForever{ tipCalculations ->
            assertEquals(2, tipCalculations?.size)
        }
    }
}