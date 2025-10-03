package com.example.appthey

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.appthey.databinding.FragmentCustoCombustivelBinding
import kotlin.math.ceil

class CustoCombustivelFragment : Fragment() {
    private var _binding: FragmentCustoCombustivelBinding? = null
    val valorPadraoConsumoKmL = "9"
    val valorPadraoPrecoL = "7.5"
    val valorPadraoDesgasteKm = ""
    val valorPadraoIdaVolta = true

    private lateinit var inputDistanciaKm: EditText
    private lateinit var inputConsumoKmPorLitro: EditText
    private lateinit var inputprecoPorLitro: EditText
    private lateinit var inputDesgasteKm: EditText
    private lateinit var textResultadoLitroNescessario: TextView
    private lateinit var textResultadoValorCombustivel: TextView
    private lateinit var textResultadoValorDesgaste: TextView
    private lateinit var textResultadoTotal: TextView
    private lateinit var buttonCalcular: Button
    private lateinit var textResultadoPor2: TextView
    private lateinit var textResultadoPor3: TextView
    private lateinit var textResultadoPor4: TextView
    private lateinit var textResultadoPor5: TextView
    private lateinit var checkBoxIdaVolta: CheckBox
    private lateinit var checkBoxIsentoMotorista: CheckBox
    private lateinit var textResultadoQtdPessoa1: TextView
    private lateinit var textResultadoQtdPessoa2: TextView
    private lateinit var textResultadoQtdPessoa3: TextView
    private lateinit var textResultadoQtdPessoa4: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_custo_combustivel, container, false)
        _binding = FragmentCustoCombustivelBinding.bind(view)

        inputDistanciaKm = view.findViewById(R.id.f_cb_distancia_km)
        inputConsumoKmPorLitro = view.findViewById(R.id.f_cb_consumo_km_litro)
        inputprecoPorLitro = view.findViewById(R.id.f_cb_preco_litro)
        inputDesgasteKm = view.findViewById(R.id.f_cb_desgaste_km)
        textResultadoLitroNescessario =
            view.findViewById(R.id.f_cb_text_resultado_litros_nescessario)
        textResultadoValorCombustivel = view.findViewById(R.id.f_cb_text_resultado_valor_combustivel)
        textResultadoValorDesgaste = view.findViewById(R.id.f_cb_text_resultado_valor_desgaste)
        textResultadoTotal = view.findViewById(R.id.f_cb_text_resultado_total)
        buttonCalcular = view.findViewById(R.id.f_cb_calcular)

        textResultadoQtdPessoa1 = view.findViewById(R.id.f_cc_qtd_pessoa_1)
        textResultadoQtdPessoa2 = view.findViewById(R.id.f_cc_qtd_pessoa_2)
        textResultadoQtdPessoa3 = view.findViewById(R.id.f_cc_qtd_pessoa_3)
        textResultadoQtdPessoa4 = view.findViewById(R.id.f_cc_qtd_pessoa_4)

        textResultadoPor2 = view.findViewById(R.id.f_cb_text_resultado_por_2)
        textResultadoPor3 = view.findViewById(R.id.f_cb_text_resultado_por_3)
        textResultadoPor4 = view.findViewById(R.id.f_cb_text_resultado_por_4)
        textResultadoPor5 = view.findViewById(R.id.f_cb_text_resultado_por_5)

        checkBoxIdaVolta = view.findViewById(R.id.f_cb_checkBox_ida_volta)
        checkBoxIsentoMotorista = view.findViewById(R.id.f_cb_checkBox_isento_motorista)

        buttonCalcular.setOnClickListener {
            var valorDesgaste: Double? = null
            var valueDistanciaKm = inputDistanciaKm.text.toString().toDoubleOrNull()
            val valueConsumoKmL = inputConsumoKmPorLitro.text.toString().toDoubleOrNull()
            val valuePrecoL = inputprecoPorLitro.text.toString().toDoubleOrNull()
            val valueDesgasteKm = inputDesgasteKm.text.toString().toDoubleOrNull()
            Log.d("- CustoCombustivelFragment", "checkBoxIsentoMotorista: ${checkBoxIsentoMotorista.isChecked}")

            if (valueDistanciaKm != null && valueConsumoKmL != null && valuePrecoL != null) {
                if (checkBoxIdaVolta.isChecked) {
                    valueDistanciaKm *= 2
                }

                if (valueDesgasteKm != null) {
                    valorDesgaste = valueDistanciaKm * valueDesgasteKm
                }

                val resultado = calcularCustoViagem(valueDistanciaKm, valueConsumoKmL, valuePrecoL, valorDesgaste)
                Log.d("- CustoCombustivelFragment", "resultado: $resultado")

                textResultadoLitroNescessario.text = resultado.litrosNecessarios.toString()
                textResultadoValorCombustivel.text = String.format("R$ %.2f", resultado.custoCombustivel)
                textResultadoValorDesgaste.text = String.format("R$ %.2f", valorDesgaste ?: 0.0)
                textResultadoTotal.text = String.format("R$ %.2f", resultado.custoTotal)

                textResultadoPor2.text = String.format("R$ %.2f", divisao(resultado.custoTotal, 2, checkBoxIsentoMotorista.isChecked))
                textResultadoPor3.text = String.format("R$ %.2f", divisao(resultado.custoTotal, 3, checkBoxIsentoMotorista.isChecked))
                textResultadoPor4.text = String.format("R$ %.2f", divisao(resultado.custoTotal, 4, checkBoxIsentoMotorista.isChecked))
                textResultadoPor5.text = String.format("R$ %.2f", divisao(resultado.custoTotal, 5, checkBoxIsentoMotorista.isChecked))

                if (checkBoxIsentoMotorista.isChecked) {
                    textResultadoQtdPessoa1.text = "/1"
                    textResultadoQtdPessoa2.text = "/2"
                    textResultadoQtdPessoa3.text = "/3"
                    textResultadoQtdPessoa4.text = "/4"
                } else {
                    textResultadoQtdPessoa1.text = "/2"
                    textResultadoQtdPessoa2.text = "/3"
                    textResultadoQtdPessoa3.text = "/4"
                    textResultadoQtdPessoa4.text = "/5"
                }
            } else {
                Toast.makeText(context, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
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

        val custoDesgasteKm: EditText = view.findViewById(R.id.f_cb_desgaste_km)
        custoDesgasteKm.setText(valorPadraoDesgasteKm)

        val idaVolta: CheckBox = view.findViewById(R.id.f_cb_checkBox_ida_volta)
        idaVolta.isChecked = valorPadraoIdaVolta
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    data class ResultadoCalculo(
        val litrosNecessarios: Double,
        val custoCombustivel: Double,
        val custoTotal: Double
    )

    fun calcularCustoViagem(
        distanciaKm: Double,
        consumoKmPorLitro: Double,
        precoPorLitro: Double,
        valorDesgaste: Double? = null
    ): ResultadoCalculo {
        val litrosNecessarios = arredondarParaCima(distanciaKm / consumoKmPorLitro)
        val custoCombustivel = (litrosNecessarios * precoPorLitro)

        return ResultadoCalculo(
            arredondarParaCima(litrosNecessarios),
            arredondarParaCima(custoCombustivel),
            arredondarParaCima(custoCombustivel + (valorDesgaste ?: 0.0))
        )
    }

    fun arredondarParaCima(valor: Double): Double {
        return ceil(valor * 100) / 100
    }

    fun divisao(valorTotal: Double, qtdPessoas: Int, motoristaIsento: Boolean): Double {
        val divisor = if (motoristaIsento && qtdPessoas > 1) qtdPessoas - 1 else qtdPessoas
        return valorTotal / divisor
    }
//    fun divisao(valor: Double, por: Int): Double {
//        return (valor) / por
//    }
}