package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.TbTickets
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stevench.example.crudstevenchavez.R

class Adaptador(var Datos: List<TbTickets>):  RecyclerView.Adapter<ViewHolder>() {

    fun actualizarLista(nuevaLista: List<TbTickets>) {
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun eliminarDatos(tituloTicket: String, position: Int) {

        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = ClaseConexion().cadenaConexion()

            val deleteTicket =
                objConexion?.prepareStatement("delete from tbTicket where titulo = ?")!!
            deleteTicket.setString(1, tituloTicket)
            deleteTicket.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()

        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = Datos[position]
        holder.txtNombreCard.text= item.titulo
        holder.txtDescripcionCard.text = item.descripcion
        holder.txtAutor.text=item.autor
        holder.txtEmailContacto.text= item.emailContacto
        holder.txtEstadoCard.text = item.estado
        holder.txtFechaCreacionCard.text = item.fechaCreacion
        holder.txtFechaFinalizacionCard.text = item.fechaFinalizacion

        //todo 2.Eliminar/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        val ticket = Datos[position]
        holder.txtNombreCard.text = ticket.titulo
        holder.txtDescripcionCard.text = ticket.descripcion
        holder.txtAutor.text =ticket.autor
        holder.txtAutor.text =ticket.emailContacto
        holder.txtEstadoCard.text = ticket.estado
        holder.txtFechaCreacionCard.text = ticket.fechaCreacion
        holder.txtFechaFinalizacionCard.text = ticket.fechaFinalizacion


        holder.btnEliminar.setOnClickListener {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setTitle("¿Desea eliminar este ticket?")

            builder.setPositiveButton("Si") { dialog, switch ->
                eliminarDatos(ticket.titulo, position)

            }

            builder.setNegativeButton("No") { dialog, switch ->
                dialog.dismiss()

            }
            val dialog = builder.create()
            dialog.show()

        }

        //todo Editar datos /////////////////////////////////////////////////////////////////////////////////////////////////////////

        fun actualizarLista(nuevaLista: List<TbTickets>){
            Datos = nuevaLista
            notifyDataSetChanged()
        }

        fun actualicePantalla(uuid: String, newtitulo: String, newDescripcion: String, newAutor: String, newEmailContacto: String, newFechaCreacion: String, newEstado: String, newFechaFinalizacion: String){
            val index = Datos.indexOfFirst { it.uuid == uuid }
            Datos[index].titulo = newtitulo
            Datos[index].descripcion = newDescripcion
            Datos[index].autor = newAutor
            Datos[index].emailContacto = newEmailContacto
            Datos[index].fechaCreacion = newFechaCreacion
            Datos[index].estado = newEstado
            Datos[index].fechaFinalizacion = newFechaFinalizacion

            notifyDataSetChanged()
        }

        fun actualizarDatos(newTitulo: String, newDescripcion: String, newAutor: String, newEmailContacto: String, newFechaCreacion: String, newEstado: String) {

        }

        fun actualizarDatos(newTitulo: String, newDescripcion: String, newAutor: String, newEmailContacto: String, newFechaCreacion: String, newEstado: String, newFechaFinalizacion: String, uuid: String){
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                val updateTicket = objConexion?.prepareStatement("update tbTicket set titulo = ?, descripcion = ?, fechaCreacion = ?, estado = ?, fechaFinalizacion = ? where uuid = ?")!!
                updateTicket.setString(1, newTitulo)
                updateTicket.setString(2, newDescripcion)
                updateTicket.setString(3, newAutor)
                updateTicket.setString(4, newEmailContacto)
                updateTicket.setString(5, newFechaCreacion)
                updateTicket.setString(6, newEstado)
                updateTicket.setString(7, newFechaFinalizacion)
                updateTicket.setString(8, uuid)

                updateTicket.executeUpdate()
                withContext(Dispatchers.Main){
                    actualicePantalla(uuid, newTitulo, newDescripcion, newAutor, newEmailContacto, newFechaCreacion, newEstado, newFechaFinalizacion)

                }
            }

            holder.btnEditar.setOnClickListener{
                //alert
                val context = holder.itemView.context

                val builder = AlertDialog.Builder(context)
                builder.setTitle("Actualizar")
                builder.setMessage("¿Quieres actualziar los datos?")

                val cuadroTexto = EditText(context)

                val txtNewTituloTicket = EditText(context).apply {
                    setText(item.titulo)
                }

                val txtNewDescripcion = EditText(context).apply {
                    setText(item.descripcion)
                }

                val txtNewAutor = EditText(context).apply {
                    setText(item.autor)
                }

                val txtNewEmailContacto = EditText(context).apply {
                    setText(item.emailContacto)
                }

                val txtNewFechaCreacion = EditText(context).apply {
                    setText(item.fechaCreacion)
                }

                val txtNewEstadoTicket = EditText(context).apply {
                    setText(item.estado)
                }

                val txtNewFechaFinalizacion = EditText(context).apply {
                    setText(item.fechaFinalizacion)
                }


                val layout = LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL
                    addView(txtNewTituloTicket)
                    addView(txtNewDescripcion)
                    addView(txtNewAutor)
                    addView(txtNewEmailContacto)
                    addView(txtNewFechaCreacion)
                    addView(txtNewEstadoTicket)
                    addView(txtNewFechaFinalizacion)

                }
                builder.setView(layout)


                //botones
                builder.setPositiveButton("Actualizar"){dialog, switch ->
                    actualizarDatos(txtNewTituloTicket.text.toString(), txtNewDescripcion.text.toString(), txtNewAutor.text.toString(), txtNewEmailContacto.text.toString(), txtNewFechaCreacion.text.toString(), txtNewEstadoTicket.text.toString(), txtNewFechaFinalizacion.text.toString(), item.uuid)}

                builder.setNegativeButton("Cancelar"){dialog, switch -> dialog.dismiss()}

                val dialog = builder.create()
                dialog.show()
            }
        }




    }
}