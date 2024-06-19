package stevench.example.crudstevenchavez

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class login : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //todo login////////////////////////////////////////////////////////////////////////////////////
        val txtCorreo = findViewById<TextView>(R.id.txtCorreo)
        val txtContrasena = findViewById<TextView>(R.id.txtContrasena)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)


        btnIngresar.setOnClickListener{

            val pantallaPrincipal = Intent(this, MainActivity::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                val comprobarUsuario = objConexion?.prepareStatement("select * from tbUsuario where email = ? AND contrasena = ?")!!
                comprobarUsuario.setString(1, txtCorreo.text.toString())
                comprobarUsuario.setString(2, txtContrasena.text.toString())

                val resultado = comprobarUsuario.executeQuery()

                if(resultado.next()){
                    startActivity(pantallaPrincipal)
                }
                else{
                    println("No se ha encontrado a el ususario")
                }
            }
        }

        btnCrearCuenta.setOnClickListener{
            val intent = Intent(this, registro::class.java)
            startActivity(intent)
        }
    }
}