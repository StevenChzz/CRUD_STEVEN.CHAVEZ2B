package stevench.example.crudstevenchavez

import Modelo.ClaseConexion
import Modelo.TbTickets
import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val txtEmailContacto = findViewById<EditText>(R.id.txtEmailContacto)
        val txtFechaCreacion = findViewById<EditText>(R.id.txtFechaCreacion)
        val txtEstado = findViewById<EditText>(R.id.txtEstado)
        val txtFechaFinalizacion = findViewById<EditText>(R.id.txtFechaFinalizacion)
        val btnIngresarDatos = findViewById<Button>(R.id.btnIngresarDatos)
        val rcvTickets =findViewById<RecyclerView>(R.id.rcvTickets)

        rcvTickets.layoutManager= LinearLayoutManager(this)

        fun obtenerTickets():List<TbTickets> {

            val objconexion = ClaseConexion().cadenaConexion()

            val statement = objconexion?.createStatement()
            val resultSet = statement!!.executeQuery("SELECT * FROM TbTickets")!!

            val listaTickets = mutableListOf<TbTickets>()

            while (resultSet.next()){
                val uuid = resultSet.getString("uuid")
                val titulo = resultSet.getString("titulo")
                val description = resultSet.getString("descripcion")
                val autor = resultSet.getString("autor")
                val emailContacto = resultSet.getString("emailContacto")
                val fechaCreacion = resultSet.getString("fechaCreacion")
                val estado = resultSet.getString("estado")
                val fechaFinalizacion = resultSet.getString("fechaFinalizacion")

                val valoresJuntos = TbTickets(uuid, titulo, description, autor, emailContacto, fechaCreacion, estado, fechaFinalizacion)

                listaTickets.add(valoresJuntos)



            }
            return listaTickets


        }

        CoroutineScope(Dispatchers.IO).launch {
            val ticketsDB = obtenerTickets()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(ticketsDB)
                rcvTickets.adapter = adapter
            }
        }

        btnIngresarDatos.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().cadenaConexion()
                val addTicket= objConexion?.prepareStatement("insert into TbTickets (uuid, titulo, descripcion, autor, Emailcontacto, fechaCreacion, estado, fechaFinalizacion) values (?, ?, ?, ?, ?, ?, ?, ?)")!!
                addTicket.setString(1, UUID.randomUUID().toString())
                addTicket.setString(2, txtTitulo.text.toString())
                addTicket.setString(3, txtDescripcion.text.toString())
                addTicket.setString(4, txtAutor.text.toString())
                addTicket.setString(5,txtEmailContacto.text.toString())
                addTicket.setString(6, txtFechaCreacion.text.toString())
                addTicket.setString(7,txtEstado.text.toString())
                addTicket.setString(8, txtFechaFinalizacion.text.toString())
                addTicket.executeUpdate()

            }
        }







    }
}