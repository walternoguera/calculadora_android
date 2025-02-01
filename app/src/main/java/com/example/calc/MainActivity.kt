package com.example.calc

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val textResultado = findViewById<TextView>(R.id.resultado)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btn7 = findViewById<Button>(R.id.btn7)
        val btn8 = findViewById<Button>(R.id.btn8)
        val btn9 = findViewById<Button>(R.id.btn9)
        val btn0 = findViewById<Button>(R.id.btn0)
        val btnSuma = findViewById<Button>(R.id.btnSuma)
        val btnResta = findViewById<Button>(R.id.btnResta)
        val btnMultiplicacion = findViewById<Button>(R.id.btnMultiplicacion)
        val btnDivision = findViewById<Button>(R.id.btnDivision)
        val btnPorcentaje = findViewById<Button>(R.id.btnPorcentaje)
        val btnResultado = findViewById<Button>(R.id.btnEsIgual)
        val btnAc = findViewById<Button>(R.id.btnAC)
        val btnMasMenos = findViewById<Button>(R.id.btnMasMenos)
        val btnPunto = findViewById<Button>(R.id.btnPunto)

        btn1.setOnClickListener{agregarCaracter("1")}
        btn2.setOnClickListener{agregarCaracter("2")}
        btn3.setOnClickListener{agregarCaracter("3")}
        btn4.setOnClickListener{agregarCaracter("4")}
        btn5.setOnClickListener{agregarCaracter("5")}
        btn6.setOnClickListener{agregarCaracter("6")}
        btn7.setOnClickListener{agregarCaracter("7")}
        btn8.setOnClickListener{agregarCaracter("8")}
        btn9.setOnClickListener{agregarCaracter("9")}
        btn0.setOnClickListener{agregarCaracter("0")}
        btnSuma.setOnClickListener{agregarCaracter("+")}
        btnResta.setOnClickListener {agregarCaracter("-")}
        btnMultiplicacion.setOnClickListener{agregarCaracter("*")}
        btnDivision.setOnClickListener{agregarCaracter("/")}
        btnPorcentaje.setOnClickListener{agregarCaracter("%")}
        btnPunto.setOnClickListener{agregarCaracter(".")}
        btnAc.setOnClickListener{limpiarPantalla()} //llama a la funcion limpiarPantalla
        btnResultado.setOnClickListener{calcularResultado()}
        btnMasMenos.setOnClickListener { cambiarSigno() }

        //==================asignar eventos a los botones científicos si estamos en landscape =======//
        if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            val btnSin = findViewById<Button>(R.id.btnSin)
            val btnCos = findViewById<Button>(R.id.btnCos)
            val btnTan = findViewById<Button>(R.id.btnTan)
            val btnSqrt = findViewById<Button>(R.id.btnSqrt)
            val btnParentesis = findViewById<Button>(R.id.btnParentesis)

            btnSin.setOnClickListener { agregarCaracter("sin(") }
            btnCos.setOnClickListener { agregarCaracter("cos(") }
            btnTan.setOnClickListener { agregarCaracter("tan(") }
            btnSqrt.setOnClickListener { agregarCaracter("sqrt(") }
            btnParentesis.setOnClickListener { agregarCaracter(")") }
        }
    }
        //=================================================================================//


        //funcion para agregar el número al textView controlando erroress
        fun agregarCaracter(caracter: String) {
            val textResultado = findViewById<TextView>(R.id.resultado)
            val textOperacion = findViewById<TextView>(R.id.operacionActual)

            val textoActual = textResultado.text.toString()

            if (textOperacion.text.toString().isEmpty() && textResultado.text.toString().isNotEmpty()) {
                textOperacion.text = ""
            }
            textOperacion.text = textOperacion.text.toString() + caracter

            textResultado.text = textoActual + caracter
        }

        //Evalua la expresión ingresada y muestra el resultado usando Exp4j
        fun calcularResultado() {
            val textResultado = findViewById<TextView>(R.id.resultado)
            val operacion = textResultado.text.toString()

            try {
                if (operacion.isEmpty()) return //si esta vacio no calcula nada

                val operacionConRadianes = operacion //Convierte sin(90) en sin(1.5708), ya que exp4j usa radianes
                    .replace(Regex("sin\\(([^)]+)\\)")) { "sin(" + Math.toRadians(it.groupValues[1].toDouble()) + ")" }
                    .replace(Regex("cos\\(([^)]+)\\)")) { "cos(" + Math.toRadians(it.groupValues[1].toDouble()) + ")" }
                    .replace(Regex("tan\\(([^)]+)\\)")) { "tan(" + Math.toRadians(it.groupValues[1].toDouble()) + ")" }

                val resultado = ExpressionBuilder(operacionConRadianes).build().evaluate()
                val resultadoFinal = if (resultado % 1 == 0.0) {
                    resultado.toInt().toString()
                } else {
                    resultado.toString()
                }
                textResultado.text = resultadoFinal
            } catch (e: ArithmeticException) {
                textResultado.text = "Error: Division por 0"
            } catch (e: Exception) {
                textResultado.text = "Error"
            }
        }

    fun cambiarSigno() {
        val textResultado = findViewById<TextView>(R.id.resultado)
        val textoActual = textResultado.text.toString()

        if (textoActual.isEmpty()) return //No hacer nada si está vacío

        try {
            //Encuentra el último número en la expresión y cambia su signo
            val regex = Regex("[-]?\\d+(\\.\\d+)?$")
            val match = regex.find(textoActual)

            if (match != null) {
                val numeroEncontrado = match.value
                val inicio = match.range.first
                val nuevoTexto = if (numeroEncontrado.startsWith("-")) {

                    textoActual.substring(0, inicio) + numeroEncontrado.drop(1)//Si el número es negativo, lo hace positivo quitando el "-"
                } else {

                    textoActual.substring(0, inicio) + "-" + numeroEncontrado //Si el número es positivo, lo convierte en negativo agregando "-"
                }

                textResultado.text = nuevoTexto
            }
        } catch (e: Exception) {
            textResultado.text = "Error"
        }
    }
    //====================================================================================//
    //Guarda el estado actual para restaurarlo despues del cambio de orientación
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val textResultado = findViewById<TextView>(R.id.resultado)
        val textOperacion = findViewById<TextView>(R.id.operacionActual)

        //Guardar el contenido de los TextViews
        outState.putString("texto_resultado", textResultado.text.toString())
        outState.putString("texto_operacion", textOperacion.text.toString())
    }

    //Restaura el contenido de los TextViews después de girar la pantalla
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val textResultado = findViewById<TextView>(R.id.resultado)
        val textOperacion = findViewById<TextView>(R.id.operacionActual)

        //Restaura el contenido guardado
        textResultado.text = savedInstanceState.getString("texto_resultado", "")
        textOperacion.text = savedInstanceState.getString("texto_operacion", "")
    }

    //====================================================================================//

    fun limpiarPantalla() {
        val textResultado = findViewById<TextView>(R.id.resultado)
        val textOperacion = findViewById<TextView>(R.id.operacionActual)

        textResultado.text = ""
        textOperacion.text = ""
    }
}