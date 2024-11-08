package com.example.appthey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.appthey.databinding.FragmentCustoCombustivelBinding
import kotlin.math.ceil

class CustoCombustivelFragment : Fragment() {
    private var _binding: FragmentCustoCombustivelBinding? = null
    val valorPadraoConsumoKmL = "9"
    val valorPadraoPrecoL = "8"
    private lateinit var inputDistanciaKm: EditText
    private lateinit var inputConsumoKmPorLitro: EditText
    private lateinit var inputprecoPorLitro: EditText
    private lateinit var textResultadoLitroNescessario: TextView
    private lateinit var textResultadoTotal: TextView
    private lateinit var buttonCalcular: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_custo_combustivel, container, false)
        _binding = FragmentCustoCombustivelBinding.bind(view)

        inputDistanciaKm = view.findViewById(R.id.f_cb_distancia_km)
        inputConsumoKmPorLitro = view.findViewById(R.id.f_cb_consumo_km_litro)
        inputprecoPorLitro = view.findViewById(R.id.f_cb_preco_litro)
        textResultadoLitroNescessario = view.findViewById(R.id.f_cb_text_resultado_litros_nescessario)
        textResultadoTotal = view.findViewById(R.id.f_cb_text_resultado_total)
        buttonCalcular = view.findViewById(R.id.f_cb_calcular)

        buttonCalcular.setOnClickListener {
//            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//            imm?.hideSoftInputFromWindow(view.windowToken, 0)

            val valueDistancioKm = inputDistanciaKm.text.toString().toDoubleOrNull()
            val valueConsumoKmL = inputConsumoKmPorLitro.text.toString().toDoubleOrNull()
            val valuePrecoL = inputprecoPorLitro.text.toString().toDoubleOrNull()

            if (valueDistancioKm != null && valueConsumoKmL != null && valuePrecoL != null) {
                val resultado = calcularCustoViagem(valueDistancioKm, valueConsumoKmL, valuePrecoL)
                textResultadoLitroNescessario.text = resultado.litrosNecessarios.toString()
                textResultadoTotal.text = String.format("R$ %.2f",resultado.custoTotal)
            }
        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val consumoL: EditText = view.findViewById(R.id.f_cb_consumo_km_litro)
        consumoL.setText(valorPadraoConsumoKmL)

        val precoL: EditText = view.findViewById(R.id.f_cb_preco_litro)
        precoL.setText(valorPadraoPrecoL)


//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    data class ResultadoCalculo(
        val litrosNecessarios: Double,
        val custoTotal: Double
    )
    fun calcularCustoViagem(distanciaKm: Double, consumoKmPorLitro: Double, precoPorLitro: Double): ResultadoCalculo {
        val litrosNecessarios = arredondarParaCima(distanciaKm / consumoKmPorLitro)
        val custoTotal = litrosNecessarios * precoPorLitro
        return ResultadoCalculo(arredondarParaCima(litrosNecessarios), arredondarParaCima(custoTotal))
    }

    fun arredondarParaCima(valor: Double): Double {
        return ceil(valor * 100) / 100
    }
}