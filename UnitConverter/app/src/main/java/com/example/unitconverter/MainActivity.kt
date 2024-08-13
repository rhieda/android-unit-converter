package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    print(innerPadding)
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {

    val heightSpacing: Modifier = Modifier.height(16.dp)
    val horizontalSpacing: Modifier = Modifier.padding(16.dp)

    var inputValue: String by remember {
        mutableStateOf("")
    }

    var outputValue by remember {
        mutableStateOf("")
    }

    var inputUnit by remember {
        mutableStateOf("Centimeters")
    }

    var outputUnit by remember {
        mutableStateOf("Meters")
    }

    var isLeftDropdownExpanded by remember {
        mutableStateOf(false)
    }

    var isRightDropdownExpanded by remember {
        mutableStateOf(false)
    }
    
    var conversionFactor = remember {
        mutableDoubleStateOf(0.01)
    }

    var outputConversionFactor = remember {
        mutableDoubleStateOf(1.00)
    }

    val textStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        color = Color.Red
        )

    fun calculate() {
        val inputValue: Double = inputValue.toDoubleOrNull() ?: 0.0
        val result: Int = (inputValue * conversionFactor.value * 100.0 / outputConversionFactor.value).roundToInt() / 100
        outputValue = result.toString()
    }

    fun convertUnits(unit: String, convFactor: Double) {
        conversionFactor.value = convFactor
        inputUnit = unit
        calculate()
    }

    fun convertOutputUnits(unit: String, convFactor: Double) {
        outputConversionFactor.value = convFactor
        outputUnit = unit
        calculate()
    }

    @Composable
    fun makeSpacer(spacing: Modifier) {
        Spacer(spacing)
    }

    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text("Unit Converter",
            style = MaterialTheme.typography.headlineLarge
        )
        makeSpacer(spacing = heightSpacing)
        OutlinedTextField(value = inputValue, onValueChange = {
            print(it)
            inputValue = it
            convertUnits(unit = inputUnit, convFactor = conversionFactor.value)
        })
        makeSpacer(spacing = heightSpacing)
        Row {
            Box {
                Button({
                    print("Select clicked")
                    isLeftDropdownExpanded = true
                }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown,
                        contentDescription = "Arrow Down")
                }
                DropdownMenu(
                    expanded = isLeftDropdownExpanded,
                    onDismissRequest = {
                        isLeftDropdownExpanded = false
                    }
                ) {
                    DropdownMenuItem(text = {
                        Text("Centimeters")
                    }, onClick = {
                        isLeftDropdownExpanded = false
                        convertUnits("Centimeters", 0.01)
                    })

                    DropdownMenuItem(text = {
                        Text("Meters")
                    }, onClick = {
                        isLeftDropdownExpanded = false
                        convertUnits("Meters", 1.0)
                    })

                    DropdownMenuItem(text = {
                        Text("Feet")
                    }, onClick = {
                        isLeftDropdownExpanded = false
                        convertUnits("Feet", 0.3048)
                    })

                    DropdownMenuItem(text = {
                        Text("Millimeters")
                    }, onClick = {
                        isLeftDropdownExpanded = false
                        convertUnits("Millimeters", 0.001)
                    })
                }
            }
            makeSpacer(heightSpacing)
            makeSpacer(spacing = horizontalSpacing)
            Box {
                Button({
                    print("Select clicked")
                    isRightDropdownExpanded = true
                }) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown,
                        contentDescription = "Arrow Down")
                }
                DropdownMenu(
                    expanded = isRightDropdownExpanded,
                    onDismissRequest = {
                        isRightDropdownExpanded = false
                    }
                ) {
                    DropdownMenuItem(text = {
                        Text("Centimeters")
                    }, onClick = {
                        isRightDropdownExpanded = false
                        convertOutputUnits("Centimeters", 0.01)
                    })

                    DropdownMenuItem(text = {
                        Text("Meters")
                    }, onClick = {
                        isRightDropdownExpanded = false
                        convertOutputUnits("Meters", 1.0)
                    })

                    DropdownMenuItem(text = {
                        Text("Feet")
                    }, onClick = {
                        isRightDropdownExpanded = false
                        convertOutputUnits("Feet", 0.3048)
                    })

                    DropdownMenuItem(text = {
                        Text("Millimeters")
                    }, onClick = {
                        isRightDropdownExpanded = false
                        convertOutputUnits("Millimeters", 0.001)
                    })
                }
            }
        }
        makeSpacer(spacing = heightSpacing)
        Text("Result: $outputValue $outputUnit",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}