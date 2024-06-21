package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.TbTickets
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
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

        fun actualicePantalla(uuid: String, newtitulo: String, newDescripcion: String, newAutor: String, newEmailContacto: String, newEstado: String, newFechaFinalizacion: String){
            val index = Datos.indexOfFirst { it.uuid == uuid }
            Datos[index].titulo = newtitulo
            Datos[index].descripcion = newDescripcion
            Datos[index].autor = newAutor
            Datos[index].emailContacto = newEmailContacto
            Datos[index].estado = newEstado
            Datos[index].fechaFinalizacion = newFechaFinalizacion

            notifyDataSetChanged()
        }

        fun actualizarDatos(newTitulo: String, newDescripcion: String, newAutor: String, newEmailContacto: String,  newEstado: String, newFechaFinalizacion: String, uuid: String) {
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                val updateTicket =
                    objConexion?.prepareStatement("update tbTicket set titulo = ?, descripcion = ?, fechaCreacion = ?, estado = ?, fechaFinalizacion = ? where uuid = ?")!!
                updateTicket.setString(1, newTitulo)
                updateTicket.setString(2, newDescripcion)
                updateTicket.setString(3, newAutor)
                updateTicket.setString(4, newEmailContacto)
                updateTicket.setString(5, newEstado)
                updateTicket.setString(6, newFechaFinalizacion)
                updateTicket.setString(7, uuid)

                updateTicket.executeUpdate()
                withContext(Dispatchers.Main) {
                    actualicePantalla(
                        newTitulo,
                        newDescripcion,
                        newAutor,
                        newEmailContacto,
                        newEstado,
                        newFechaFinalizacion,
                        uuid
                    )

                }
            }
        }

        holder.btnEditar.setOnClickListener {
            val context = holder.itemView.context

            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL

            val txt1 = EditText(context)
            layout.addView(txt1)
            txt1.setText(ticket.titulo)
            val txt2 = EditText(context)
            layout.addView(txt2)
            txt2.setText(ticket.descripcion)
            val txt3 = EditText(context)
            layout.addView(txt3)
            txt3.setText(ticket.autor)
            val txt4 = EditText(context)
            layout.addView(txt4)
            txt4.setText(ticket.emailContacto)
            val txt5 = EditText(context)
            txt5.setText(ticket.estado)
            layout.addView(txt5)
            val txt6 = EditText(context)
            txt6.setText(ticket.fechaFinalizacion)
            layout.addView(txt6)

            val uuid = ticket.uuid

            val builder = AlertDialog.Builder(context)
            builder.setView(layout)
            builder.setTitle("Editar Ticket")


            builder.setPositiveButton("Aceptar") { dialog, which ->
                actualizarDatos(
                    txt1.text.toString(),
                    txt2.text.toString(),
                    txt3.text.toString(),
                    txt4.text.toString(),
                    txt5.text.toString(),
                    txt6.text.toString(),
                    uuid
                )
                Toast.makeText(context, "Ticket editado correctamente", Toast.LENGTH_SHORT).show()

            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

        }
    }




}