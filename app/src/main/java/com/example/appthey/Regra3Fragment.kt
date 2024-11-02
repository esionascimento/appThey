package com.example.appthey

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.appthey.databinding.FragmentRegra3Binding

class Regra3Fragment : Fragment() {
    private var _binding: FragmentRegra3Binding? = null
    private lateinit var inputSe: EditText
    private lateinit var inputEquivale: EditText
    private lateinit var inputEntao: EditText
    private lateinit var input4: TextView
    private lateinit var buttonCalcular: Button
    private lateinit var buttonClear: Button

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_regra3, container, false)
        _binding = FragmentRegra3Binding.bind(view)

        inputSe = view.findViewById(R.id.f_regra3_input_1)
        inputEquivale = view.findViewById(R.id.f_regra3_input_2)
        inputEntao = view.findViewById(R.id.f_regra3_input_3)
        input4 = view.findViewById(R.id.f_regra3_input_4)
        buttonCalcular = view.findViewById(R.id.f_regra3_button_calcular)
        buttonClear = view.findViewById(R.id.f_regra3_button_clear)
        buttonCalcular.setOnClickListener {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)

            val valor1 = inputSe.text.toString().toDoubleOrNull()
            val valor2 = inputEquivale.text.toString().toDoubleOrNull()
            val valor3 = inputEntao.text.toString().toDoubleOrNull()
            if (valor1 != null && valor2 != null && valor3 != null) {
                val soma = calcularRegraDeTres(valor1, valor2, valor3)
                input4.text = soma.toString()
            }
        }
        buttonClear.setOnClickListener {
            inputSe.text = null
            inputEquivale.text = null
            inputEntao.text = null
            input4.text = null
            inputSe.requestFocus()
        }
        return view
    }

    fun calcularRegraDeTres(valor1: Double, valor2: Double, valorConhecido1: Double): Double {
        val proporcao = valor2 / valor1
        return proporcao * valorConhecido1
    }
}