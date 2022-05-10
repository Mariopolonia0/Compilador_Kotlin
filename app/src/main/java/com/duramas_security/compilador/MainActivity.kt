package com.duramas_security.compilador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.duramas_security.compilador.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var listavariable = ArrayList<Variable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        binding.codigoEditText.setText("fun main(){\n\n}")
        return true
    }

    fun CompilarCodigo(){

        var sacarlinea = binding.codigoEditText.text.toString().split("\n")
        for(i in sacarlinea.indices)
            Log.w("ver",sacarlinea[i])

        if(comprobar(sacarlinea)){
            binding.resultadoTextView.text =binding.resultadoTextView.text.toString() + "error en el fun de sintaxis"
        }

        if(comprobarMain(sacarlinea[0].split(" "))){
            Log.i("kk2","dd")
            binding.resultadoTextView.setText("\nNitido")

            if (!comprobarVariable(binding.codigoEditText.text.toString().split("\n")))
                binding.resultadoTextView.text = binding.resultadoTextView.text.toString() + "error en la sintaxis\nError"
        }
        else {
            binding.resultadoTextView.text =
                binding.resultadoTextView.text.toString() + "\n" + "error en el fun main\nError"
            return
        }

       Imprimir(sacarlinea)
            //binding.resultadoTextView.text = binding.resultadoTextView.text.toString() + "\n"+ "Nitido 2"

    }

    fun comprobar(codigo :List<String>):Boolean{
        var error = true
        for (i in codigo.indices){
            var div = codigo[i].split("(")

            when(div[0].replace(" ","",false)){
                "println" ->{
                    error = false
                }
                "print" ->{
                    error = false
                }
                "if"->{
                    error = false
                }

                "for"->{
                    error = false
                }
            }
        }

        for (i in codigo.indices){

            when(codigo[i]){
                "var"->{
                    for (e in codigo.indices) {
                        when (codigo[e]) {
                            ":" -> {
                                when(codigo[e]){
                                    "Int"->{
                                        error = false
                                    }
                                }
                            }
                        }
                    }
                }
                "val" ->{
                    error = false
                }
            }
        }
        return error
    }


    fun Imprimir(codigo :List<String>):Boolean{

        var nitido = false

        for (i in codigo.indices){
            var linea = codigo[i].split("(")
            for(e in linea.indices){
                when(linea[e].replace(" ","",false)){
                    "println"->{

                        var dir = linea[1].split("+")
                        if(dir.indices.last > 0){
                            for (d in dir.indices){
                                Log.e("d",dir[d])
                                var dircarter = dir[d].toCharArray()
                                if (dircarter[0] == '\"'){
                                    var a = dir[d].replace("\"","",false)
                                    a = a.replace(")","",false)
                                    //binding.resultadoTextView.text = ""
                                    binding.resultadoTextView.text = binding.resultadoTextView.text.toString() + "\n"+ a
                                }else{
                                    var valor = ""

                                    var a = dir[d].replace(")","",false)
                                    for (q in listavariable.indices){
                                        Log.e("ya si entramos",a)
                                        if (a == listavariable[q].descripcion)
                                            valor = listavariable[q].valor

                                        Log.e("variable"+q.toString(),listavariable[q].descripcion+" "+listavariable[q].valor)
                                        Log.e("ya si salimos",valor)
                                    }
                                    //binding.resultadoTextView.text = ""
                                    binding.resultadoTextView.text = binding.resultadoTextView.text.toString() + valor
                                }
                            }
                        }else{

                            var a = linea[1].replace("\"","",false)
                            a = a.replace(")","",false)
                            //binding.resultadoTextView.text = ""
                            binding.resultadoTextView.text = binding.resultadoTextView.text.toString() + "\n"+ a
                            nitido = true
                        }
                        break
                    }

                    "print" ->{
                        var a = linea[1].replace("\"","",false)
                        a = a.replace(")","",false)
                        binding.resultadoTextView.text = ""
                        binding.resultadoTextView.text = binding.resultadoTextView.text.toString() + a
                        nitido = true
                        break
                    }

                    "if"->{
                        var dir = linea[1].replace(")","",false)
                        var dir1 = dir.split(" ")
                        when(dir1[1]){
                            ">"->{

                                if (dir[0] > dir[4]){
                                    Log.e("ya si salimos","verdadero"+codigo[i+1])
                                    var lineaim = codigo[i+1].split("(")
                                    var r = lineaim[1].replace("\"","",false)
                                    r = r.replace(")","",false)
                                    Log.e("ya si salimos 2",r)
                                    binding.resultadoTextView.text = ""
                                    binding.resultadoTextView.text = r
                                    return true
                                }else{
                                    Log.e("ya si salimos","falso")
                                    var lineaim = codigo[i+3].split("(")
                                    var a = lineaim[1].replace("\"","",false)
                                    a = a.replace(")","",false)
                                    binding.resultadoTextView.text = ""
                                    binding.resultadoTextView.text = binding.resultadoTextView.text.toString() + "\n"+ a
                                    return true
                                }
                            }
                            "<"->{
                                if (dir[0].toInt() > dir[2].toInt()){
                                    var lineaim = codigo[i+1].split("(")
                                    var a = lineaim[1].replace("\"","",false)
                                    a = a.replace(")","",false)
                                    binding.resultadoTextView.text = ""
                                    binding.resultadoTextView.text = binding.resultadoTextView.text.toString() + "\n"+ a
                                    nitido = true
                                }
                            }

                        }
                    }

                    "for"->{
                        var dir = linea[1].replace(")","",false)
                        var dir1 = dir.split("..")
                        var dir2 = dir1[0].split(" ")

                        for (h in dir2[2].toInt()..dir1[1].toInt()){
                            var lineaim = codigo[i+1].split("(")
                            var r = lineaim[1].replace("\"","",false)
                            r = r.replace(")","",false)
                            binding.resultadoTextView.text =binding.resultadoTextView.text.toString() +"\n"+  r
                        }
                           return true
                    }
                }
            }
        }
        return nitido
    }

    fun comprobarVariable(codigo :List<String>):Boolean{
        var nitido = false

        for (i in codigo.indices){
            Log.w("linea"+i.toString(),codigo[i])
            var linea = codigo[i].split(" ")
            for (l in linea.indices)
                Log.w("text de linea."+l.toString(),linea[l])
            for (l in linea.indices){
                if (linea[l] == "var"){
                    when(linea[l+2]){
                        ":"->{
                            nitido = true
                            listavariable.add(Variable(linea[l+1],"Int",linea[l+5]))
                        }
                        "="->{
                            nitido = true
//                            Log.w("comprobar ="+i.toString(),linea[l+3])
//                            Log.w("comprobar ="+i.toString(),linea[l+5])

                            var valor = linea[l+3]
                            var valor1 = linea[l+5]


                            for (q in listavariable.indices){

                                if (valor == listavariable[q].descripcion)
                                    valor = listavariable[q].valor
                                if (valor1 == listavariable[q].descripcion)
                                    valor1 = listavariable[q].valor

                            }
                            when(linea[l+4]){
                                "+"->{
                                    Log.w("sumando ="+i.toString(),linea[l+4])

                                    val sumar = valor.toInt()+ valor1.toInt()
                                    listavariable.add(Variable(linea[l+1],"Int",sumar.toString()))
                                }
                                "-"->{
                                    Log.w("resta ="+i.toString(),linea[l+4])

                                    val resta = valor.toInt()-valor1.toInt()
                                    listavariable.add(Variable(linea[l+1],"Int",resta.toString()))
                                }
                                "*"->{
                                    Log.w("multiplicacion ="+i.toString(),linea[l+4])

                                    val multiplicacion = valor.toInt()*valor1.toInt()
                                    Log.w("multiplicacion =",multiplicacion.toString())
                                    listavariable.add(Variable(linea[l+1],"Int",multiplicacion.toString()))
                                }
                                "/"->{
                                    Log.w("divicion ="+i.toString(),linea[i+4])

                                    val divicion = valor.toInt()/valor1.toInt()
                                    listavariable.add(Variable(linea[l+1],"Int",divicion.toString()))
                                }
                            }
                        }
                    }
                }
            }
        }
        for (i in listavariable.indices)
            Log.w("lista variable",listavariable[i].valor)
        return nitido
    }

    fun comprobarMain(codigo :List<String>):Boolean{
        var nitido = false
        for (i in codigo.indices){
            when(codigo[i]){
                "fun"->{
                    for (e in codigo.indices) {
                        when (codigo[e]) {
                            "main(){" -> {
                                return true
                            }
                            else->{
                                nitido = false
                                binding.resultadoTextView.text = "error en el fun main"
                            }
                        }
                    }
                }
                else->{
                    nitido = false
                    binding.resultadoTextView.text = "error en el fun main"
                }
            }
        }
        return nitido
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.Run-> {
                CompilarCodigo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}